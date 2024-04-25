package simulations.Scripts.DartsSoap;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentDailyListTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AddDocumentTokenSimulation2 extends Simulation {
    {
        HttpProtocolBuilder httpProtocol = http
            .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
            .baseUrl(EnvironmentURL.GATEWAY_BASE_URL.getUrl())
            .inferHtmlResources()
            .acceptEncodingHeader("gzip,deflate")
            .contentTypeHeader("text/xml;charset=UTF-8")
            .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");

        // Scenario 1: AddDocumentDailyListTokenScenario
        final ScenarioBuilder scn1 = scenario("DARTS - GateWay - Soap - AddDocument:POST - Daily List Token")
            .feed(Feeders.createCourtHouseAndCourtRooms())
            .exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
            .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
            .repeat(15)
            .on(
                doIf(session -> session.get("registrationToken") == null)
                    .then(
                        exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
                            .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
                    )
            
            .exec(AddDocumentDailyListTokenScenario.AddDocumentDailyListToken().pace(Duration.ofMinutes(2))));

        // Scenario 2: AddDocumentEventTokenScenario
        final ScenarioBuilder scn2 = scenario("DARTS - GateWay - Soap - AddDocument:POST - Event Token")
            .feed(Feeders.createCourtHouseAndCourtRooms())
            .exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
            .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
            .repeat(538)
            .on(
                doIf(session -> session.get("registrationToken") == null)
                    .then(
                        exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
                            .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
                    )            
            .exec(AddDocumentEventTokenScenario.AddDocumentEventToken().pace(Duration.ofMillis(2000))));

        // Setting up scenarios
        setUp(
            scn1.injectOpen(rampUsers(1).during(Duration.ofMinutes(30))).protocols(httpProtocol),
            scn2.injectOpen(rampUsers(1).during(Duration.ofMinutes(30))).protocols(httpProtocol)
        );
    }
}
