package DartsApi;

import Scenario.DartsApi.DeleteAudioRequestScenario;
import Scenario.DartsApi.GetApiTokenScenario;
import Utilities.AppConfig;
import Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


public class AudioRequestDeleteSimulation extends Simulation {   
  {
    final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();

    final HttpProtocolBuilder httpProtocol = http
        .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT).httpsPort(AppConfig.PROXY_PORT))
        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
        .inferHtmlResources();

    final ScenarioBuilder scn1 = scenario("Audio Requests:DELETE")
        .exec(GetApiTokenScenario.getApiToken())
        .repeat(10)    
        .on(exec(DeleteAudioRequestScenario.DeleteAudioRequest().feed(feeder))    
        );

    setUp(
        scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }    
}