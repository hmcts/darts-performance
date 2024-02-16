package DartsApi;

import scenario.*;
import scenario.GetApiTokenScenario;
import Utilities.AppConfig;
import Utilities.AppConfig.EnvironmentURL;
import Utilities.TimestampGenerator;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AudioRequestSimulation extends Simulation {
   
  {
    final String GROUP_NAME = "Audio Request";

    final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();

    final HttpProtocolBuilder httpProtocol = http
    .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT).httpsPort(AppConfig.PROXY_PORT))
    .baseUrl(EnvironmentURL.B2B_Login.getUrl())
    .inferHtmlResources()
    .acceptHeader("application/json")
    .acceptEncodingHeader("gzip, deflate, br")
    .contentTypeHeader("application/json")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
  

    final ScenarioBuilder scn1 = scenario("Test1")
    .exec(GetApiTokenScenario.getApiToken())
    .repeat(10).on(
        exec(session -> {
            LocalDateTime startTimeParsed = LocalDateTime.now();
            LocalDateTime endTimeParsed = TimestampGenerator.getRandomTime(startTimeParsed, 1, 5);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            String startTimeFormatted = startTimeParsed.format(formatter);
            String endTimeFormatted = endTimeParsed.format(formatter);
            
            // Set the calculated values in the session
            session.set("startTimeFormatted", startTimeFormatted)
                   .set("endTimeFormatted", endTimeFormatted);
            
            // Return the session
            return session;
        })
        .exec(AudioRequestPostScenario.audioRequestPost())
        .feed(feeder)
    );


    setUp(
        scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }    
}



// final ScenarioBuilder scn1 = scenario("Test1")
// //.feed(feeder)
// .exec(GetApiTokenScenario.getApiToken())
// .repeat(10).on(
//     exec(session -> {

//         return session.set("startTimeFormatted", startTimeFormatted)
//                       .set("endTimeFormatted", endTimeFormatted);
//     })
// )
// .feed(feeder)
// .exec(AudioRequestPostScenario.audioRequestPost());
// //.feed(feeder);
// setUp(
//     scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
// }    
// }