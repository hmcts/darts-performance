package simulations.Scripts.DartsSoap;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.AppConfig.TestType;
import simulations.Scripts.Utilities.Feeders;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.util.List;


import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public abstract class ProfileAddDocumentSOAPUserSimulation extends Simulation {

    FeederBuilder<String> feeder = csv(AppConfig.COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH).random();
    private final HttpProtocolBuilder httpProtocol;

    protected ProfileAddDocumentSOAPUserSimulation() {
        this.httpProtocol = http
        //    .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
            .baseUrl(EnvironmentURL.PROXY_BASE_URL.getUrl())
            .inferHtmlResources()
            .acceptEncodingHeader("gzip,deflate")
            .contentTypeHeader("text/xml;charset=UTF-8")
            .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");            
            setupClosed();
    }
            private ScenarioBuilder getScenarioBuilder() {
              Feeders.resetCounter();
              return getScenario();
          }


        //Method kept as useful for manual testing
        public void setupOpen() {
            addAssertions(
                setUp(
                    getScenarioBuilder()
                        .injectOpen(simulationProfileOpen().toArray(new OpenInjectionStep[0]))
                )
            );
        }

        public void setupClosed() {
            addAssertions(
                setUp(
                    getScenarioBuilder()
                        .injectClosed(simulationProfileClosed().toArray(new ClosedInjectionStep[0]))
                )
            );
        }    
        
        private void addAssertions(SetUp setUp) {
        setUp.protocols(httpProtocol)
            .assertions(
                //No failed requests
                global().failedRequests().count().is(0L),
                //95% of requests should respond within 500ms
                global().responseTime().percentile3().lte(500)
            );
        }

          protected abstract ScenarioBuilder getScenario();

        // Method kept as useful for manual testing
        public List<OpenInjectionStep> simulationProfileOpen() {
            switch (AppConfig.TEST_TYPE) {
                case PERFORMANCE:
                    if (AppConfig.DEBUG) {
                        return List.of(atOnceUsers(1));
                    } else {
                        return List.of(
                            rampUsersPerSec(0.00).to(AppConfig.USERS_PER_SECOND)
                                .during(Duration.ofSeconds(AppConfig.RANK_UP_TIME_SECONDS)),
                            constantUsersPerSec(AppConfig.USERS_PER_SECOND).during(
                                Duration.ofSeconds(AppConfig.TEST_DURATION_SECONDS)),
                            rampUsersPerSec(AppConfig.USERS_PER_SECOND).to(0.00)
                                .during(Duration.ofSeconds(AppConfig.RANK_DOWN_TIME_SECONDS))
                        );
                    }
                case PIPELINE:
                    return List.of(rampUsers(AppConfig.PIPELINE_USERS_PER_SECOND).during(Duration.ofMinutes(2)));
                default:
                    throw new UnsupportedOperationException("Unsupported test type: " + AppConfig.TEST_TYPE);
            }
        }

        public List<ClosedInjectionStep> simulationProfileClosed() {
            if (AppConfig.TEST_TYPE == TestType.PERFORMANCE) {
                return List.of(
                    rampConcurrentUsers(0).to(AppConfig.CONSTANT_CONCURRENT_USERS)
                        .during(Duration.ofSeconds(AppConfig.RANK_UP_TIME_SECONDS)),
                    constantConcurrentUsers(AppConfig.CONSTANT_CONCURRENT_USERS).during(AppConfig.TEST_DURATION_SECONDS),
                    rampConcurrentUsers(AppConfig.CONSTANT_CONCURRENT_USERS).to(0)
                        .during(Duration.ofSeconds(AppConfig.RANK_DOWN_TIME_SECONDS))
                );
            }
            throw new UnsupportedOperationException("Unsupported test type: " + AppConfig.TEST_TYPE);
        }
}
