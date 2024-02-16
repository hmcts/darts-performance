package scenario;

import Headers.Headers;
import Utilities.AppConfig;
import Utilities.AppConfig.EnvironmentURL;
import Utilities.TimestampGenerator;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public final class AudioRequestPostScenario {
  

  private static final String GROUP_NAME = "Audio Request";

  private static final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();

  private static final LocalDateTime startTimeParsed = LocalDateTime.now();

  // Generate random hour difference between 1 and 5
  private static final LocalDateTime endTimeParsed = TimestampGenerator.getRandomTime(startTimeParsed, 1, 5); // Get end time as LocalDateTime

  // Format start and end times in the desired format
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
  private static final String startTimeFormatted = startTimeParsed.format(formatter); // Format start time
  private static final String endTimeFormatted = endTimeParsed.format(formatter); // Format end time
  
  private static final String[] REQUEST_TYPES = {"DOWNLOAD", "PLAYBACK"};
    // Function to randomly select a request type
    private static String getRandomRequestType() {
      int randomIndex = ThreadLocalRandom.current().nextInt(REQUEST_TYPES.length);
      return REQUEST_TYPES[randomIndex];
  }
    private AudioRequestPostScenario( ) {}

    public static ChainBuilder audioRequestPost() {  
      
      return group(GROUP_NAME + "DARTS - Api - AudioRequest:Post")
      .on(exec(         
          http("DARTS - Api - AudioRequest:Post")
          .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests")
          .headers(Headers.AuthorizationHeaders)
          .body(StringBody("{\"hearing_id\": #{hea_id}, \"requestor\": #{usr_id}, \"start_time\": \"" + startTimeFormatted + "\", \"end_time\": \"" + endTimeFormatted + "\", \"request_type\": \"" + getRandomRequestType() + "\"}")).asJson()
          .check(status().saveAs("statusCode"))
          .check(status().in(200, 409))
        ));      
  }
}
