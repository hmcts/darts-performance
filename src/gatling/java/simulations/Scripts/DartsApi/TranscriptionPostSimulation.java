package simulations.Scripts.DartsApi;

import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.PostTranscriptionScenario;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class TranscriptionPostSimulation extends Simulation {   
  {
    final HttpProtocolBuilder httpProtocol = http
        .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
        .inferHtmlResources();

    final ScenarioBuilder scn1 = scenario("Transcription:POST")        
        .repeat(200)   
        .on(exec(feed(Feeders.createTranscriptionPostDetails()))        
        .exec(GetApiTokenScenario.getApiTokenDynamic()) 
        .exec(PostTranscriptionScenario.PostTranscription())    
        );

    setUp(
        scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }    
}