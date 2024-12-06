package simulations.Scripts.PerformanceTests.DartsBaseLinePeakTests;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.ScenarioBuilder.CourtClerkUsersScenarioBuild;
import simulations.Scripts.ScenarioBuilder.CourtManagerUsersScenarioBuild;
import simulations.Scripts.ScenarioBuilder.JudgeUserScenario;
import simulations.Scripts.ScenarioBuilder.LanguageShopUserScenario;
import simulations.Scripts.ScenarioBuilder.TranscriberUsersScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class PortalTestSimulation extends Simulation {   
    
    public static AtomicInteger global400ErrorCounter = new AtomicInteger(0);

    private static final String BASE_LINE_PEAK_JUDGE_USERS = "DARTS - Portal - Judge Users";
    private static final String BASE_LINE_PEAK_COURT_CLERK_USERS = "DARTS - Portal - Court Clerk Users";
    private static final String BASE_LINE_PEAK_COURT_MANAGER_USERS = "DARTS - Portal - Court Manager Users";
    private static final String BASE_LINE_PEAK_TRANSCRIBER_USERS = "DARTS - Portal - Transcriber Users";
    private static final String BASE_LINE_PEAK_LANGUAGE_USERS = "DARTS - Portal - Language Shop Users";

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }    
   
    
        public PortalTestSimulation() {
            HttpProtocolBuilder httpProtocolInternal = configureInternalHttp();
            HttpProtocolBuilder httpProtocolExternal = configureExternalHttp();
    
            setUpScenarios(httpProtocolExternal, httpProtocolInternal);
        }
    
        private void setUpScenarios(HttpProtocolBuilder httpProtocolExternal, HttpProtocolBuilder httpProtocolInternal) {
            setUp(
                CourtClerkUsersScenarioBuild.build(BASE_LINE_PEAK_COURT_CLERK_USERS)
                    .injectOpen(rampUsers(AppConfig.getCourtClerkUsers())
                    .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_COURT_CLERK)))
                    .protocols(httpProtocolInternal),
    
                CourtManagerUsersScenarioBuild.build(BASE_LINE_PEAK_COURT_MANAGER_USERS)
                    .injectOpen(rampUsers(AppConfig.getCourtManagerUsers())
                    .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_COURT_MANAGER)))
                    .protocols(httpProtocolInternal),
    
                TranscriberUsersScenario.build(BASE_LINE_PEAK_TRANSCRIBER_USERS)
                    .injectOpen(rampUsers(AppConfig.getTranscriberUsers())
                    .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_TRANSCRIBER)))
                    .protocols(httpProtocolExternal),
    
                JudgeUserScenario.build(BASE_LINE_PEAK_JUDGE_USERS)
                    .injectOpen(rampUsers(AppConfig.getJudgeUsers())
                    .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_JUDGES)))
                    .protocols(httpProtocolInternal),
    
                LanguageShopUserScenario.build(BASE_LINE_PEAK_LANGUAGE_USERS)
                    .injectOpen(rampUsers(AppConfig.getLanguageShopUsers())
                    .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_LANGUAGE_SHOP)))
                    .protocols(httpProtocolExternal) 
            );
        }
    
        private HttpProtocolBuilder configureInternalHttp() {
            return http
                .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
                .baseUrl("https://login.microsoftonline.com")
                .acceptHeader("application/json, text/plain, */*")
                .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        }
    
        private HttpProtocolBuilder configureExternalHttp() {
            return http
                .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
                .baseUrl(AppConfig.EnvironmentURL.B2B_Login.getUrl())
                .acceptHeader("application/json, text/plain, */*")
                .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        }    
    }
    