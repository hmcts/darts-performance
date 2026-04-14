package simulations.Scripts.DartsApi.CreateData;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.InseartAudioIntoHearingScenario;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;


public class AddAudioToHearingSimulation extends Simulation {
    {
        final HttpProtocolBuilder httpProtocol = http
                .baseUrl(EnvironmentURL.B2B_Login.getUrl())
                .inferHtmlResources();

        final ScenarioBuilder scn1 = scenario("Inseart Audio Into Hearing")
                .exec(GetApiTokenScenario.getApiToken())
                .repeat(1)
                .on(exec(InseartAudioIntoHearingScenario.InseartAudioIntoHearing()
                ));

        setUp(
                scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }
}