package Utilities;

import java.util.List;
import java.util.Map;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.CheckBuilder;

public class Feeders {

    public static final FeederBuilder<?> EventCode;
    public static final FeederBuilder<String> AudioRequestCSV;

    static {
        EventCode = listFeeder("eventCode", List.of("DL", "A", "B")).random();
        AudioRequestCSV = CoreDsl.csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();
    }    
    public static CheckBuilder.Final saveBearerToken() {
        return CoreDsl.jsonPath("$.access_token").saveAs("bearerToken");
    }                

    public static FeederBuilder<Object> listFeeder(String key, List<Object> items) {
        return CoreDsl.listFeeder(items.stream()
            .map(item -> Map.of(key, item)).toList()
        );
    }
}