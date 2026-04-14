package simulations.Scripts.DartsSoap;

import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.HttpUtil;

import static io.gatling.javaapi.core.CoreDsl.*;

public class RegisterWithTokenSOAPSimulation extends Simulation {

    FeederBuilder<String> feeder = csv(AppConfig.COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH).random();

    {
        HttpProtocolBuilder httpProtocol =
                HttpUtil.getHttpProtocol()
                        .baseUrl(EnvironmentURL.PROXY_BASE_URL.getUrl())
                        .inferHtmlResources()
                        .acceptEncodingHeader("gzip,deflate")
                        .contentTypeHeader("text/xml;charset=UTF-8")
                        .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");

        final ScenarioBuilder scn = scenario("DARTS - GateWay - Soap - RegisterWithToken")
                .feed(feeder)
                .repeat(1)
                .on(exec(RegisterWithTokenScenario.registerWithToken(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
                );

        setUp(
                scn.injectOpen(constantUsersPerSec(50).during(1)).protocols(httpProtocol));
    }
}
