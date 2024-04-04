package simulations.Scripts.Utilities;

import static io.gatling.javaapi.core.CoreDsl.jsonPath;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.jdbc.JdbcDsl;
import io.gatling.javaapi.core.CheckBuilder;

public class Feeders {

    public static final FeederBuilder<String> AudioRequestCSV;
    private static final AtomicInteger COUNTER;
    private static final Logger log = Logger.getLogger(Feeders.class.getName());
    //public static final FeederBuilder<Object> RANDOM_USER_FEEDER;


    static {
        AudioRequestCSV = CoreDsl.csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();
        COUNTER = new AtomicInteger(0);
        // RANDOM_USER_FEEDER = jdbcFeeder("SELECT * FROM darts.user_account "
        // + "order by RANDOM()").random();

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
    public static CheckBuilder.Final saveCaseId() {
        return CoreDsl.jsonPath("$.[*]").ofMap().findRandom().saveAs("getCaseId");
    }
    public static CheckBuilder.Final saveUserId() {
        return CoreDsl.jsonPath("$.userId").saveAs("getUserId");
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

        private static final String[] REQUEST_TYPES = {"DOWNLOAD", "PLAYBACK"};

        // Define the percentages for each request type (must sum up to 100)
        private static final int DOWNLOAD_PERCENTAGE = 70; //% chance
        private static final int PLAYBACK_PERCENTAGE = 30; //% chance

        public static String getRandomRequestType() {
        int totalPercentage = DOWNLOAD_PERCENTAGE + PLAYBACK_PERCENTAGE;
        int randomNumber = ThreadLocalRandom.current().nextInt(totalPercentage) + 1; // Generate random number between 1 and the total percentage

        if (randomNumber <= DOWNLOAD_PERCENTAGE) {
            return REQUEST_TYPES[0]; // DOWNLOAD
        } else {
            return REQUEST_TYPES[1]; // PLAYBACK
        }
    }

    // public static FeederBuilder<Object> jdbcFeeder(String sql) {
            
    //         log.info("Creating jdbcFeeder: " + AppConfig.DB_URL + ", " + AppConfig.DB_USERNAME + ", " + AppConfig.DB_PASSWORD + ", " + sql);
    
    //         return JdbcDsl.jdbcFeeder(AppConfig.DB_URL, AppConfig.DB_USERNAME, AppConfig.DB_PASSWORD, sql);       
    // }    

    public static void resetCounter() {
        COUNTER.set(0);
    }
}
