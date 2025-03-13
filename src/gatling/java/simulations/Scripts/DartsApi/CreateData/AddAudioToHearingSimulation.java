package simulations.Scripts.DartsApi.CreateData;

import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.InseartAudioIntoHearingScenario;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


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