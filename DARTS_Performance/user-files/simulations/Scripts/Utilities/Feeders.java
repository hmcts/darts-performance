package Utilities;

import java.util.List;
import java.util.Map;
import java.util.Random;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.CheckBuilder;

public class Feeders {

    public static final FeederBuilder<String> AudioRequestCSV;

    static {
        AudioRequestCSV = CoreDsl.csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();
    }    

    public static String getRandomEventCode() {
        List<String> eventCodes = List.of("DL", "DL2", "DL3");
        return eventCodes.get(new Random().nextInt(eventCodes.size()));
    }

    public static CheckBuilder.Final saveBearerToken() {
        return CoreDsl.jsonPath("$.access_token").saveAs("bearerToken");
    }
    public static CheckBuilder.Final saveStateProperties() {
        return CoreDsl.regex("StateProperties=(.*?)\"").find().saveAs("stateProperties");
    }
    public static CheckBuilder.Final saveCsrf() {
        return CoreDsl.regex("csrf\":\"(.*?)\"").find().saveAs("csrf");
    }

    public static CheckBuilder.Final saveTokenCode() {
        return CoreDsl.css("input[name='code']", "value").saveAs("TokenCode");
    }
   
    public static CheckBuilder.Final saveTransformedMediaId() {
        return CoreDsl.jsonPath("$.transformed_media_details[*].transformed_media_id").findAll().saveAs("getTransformedMediaId");
    }

    public static CheckBuilder.Final saveRegistrationToken() {
        return CoreDsl.regex("<return>(.*?)</return>").find(0).saveAs("registrationToken");
    }

    public static FeederBuilder<Object> listFeeder(String key, List<Object> items) {
        return CoreDsl.listFeeder(items.stream()
            .map(item -> Map.of(key, item)).toList()
        );
    }
}
