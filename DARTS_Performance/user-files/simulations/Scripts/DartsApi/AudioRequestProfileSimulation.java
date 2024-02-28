package DartsApi;

import Scenario.DartsApi.GetApiTokenScenario;
import Scenario.DartsApi.GetAudioRequestScenario;
import Utilities.AppConfig;
import Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AudioRequestProfileSimulation extends Simulation {   
  {
    final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();

    final HttpProtocolBuilder httpProtocol = http
        .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT).httpsPort(AppConfig.PROXY_PORT))
        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
        .inferHtmlResources();

    final ScenarioBuilder scn1 = scenario("Audio Requests:POST")
        .exec(GetApiTokenScenario.getApiToken())
        //.exec(GetAudioRequestScenario.GetAudioRequest().feed(feeder))
        .repeat(10)    
        .on(exec(GetAudioRequestScenario.GetAudioRequestPlayBack().feed(feeder))           
        );
        

    setUp(
        scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }    
}