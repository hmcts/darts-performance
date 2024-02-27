package DartsApi;

import scenario.*;
import Utilities.AppConfig;
import Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


public class AudioGETPreviewSimulation extends Simulation {   
  {
    final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();

    final HttpProtocolBuilder httpProtocol = http
        .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT).httpsPort(AppConfig.PROXY_PORT))
        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
        .inferHtmlResources();

    final ScenarioBuilder scn1 = scenario("Audio Requests:GET Preview")
        .exec(GetApiTokenScenario.getApiToken())
        .repeat(1)    
        .on(exec(GetAudioPreviewScenario.GetAudioPreview().feed(feeder))    
        );

    setUp(        
        scn1.injectOpen(constantUsersPerSec(10).during(1)).protocols(httpProtocol));
    }    
}