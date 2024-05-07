package simulations.Scripts.Utilities;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.CheckBuilder;

public class Feeders {

    public static final FeederBuilder<String> AudioRequestCSV;
    public static final FeederBuilder<String> JudgesCSV;
    public static final FeederBuilder<String> LanguageShopUsers;
    public static final FeederBuilder<String> CourtClerkUsers;
    public static final FeederBuilder<String> TranscriberUsers;
    public static final FeederBuilder<String> CourtHouseAndCourtRooms;

    private static final AtomicInteger COUNTER;
    private static final Logger log = Logger.getLogger(Feeders.class.getName());

   
    // Add AppConfig.EnvironmentURL.DARTS_EXTERNAL_USERNAME.getUrl() and AppConfig.EnvironmentURL.DARTS_EXTERNAL_PASSWORD.getUrl()
    public static String getDartsExternalUsernameUrl() {
        return AppConfig.EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl();
    }

    public static String getDartsExternalPasswordUrl() {
        return AppConfig.EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl();
    }

    static {        
        //Audio Files
        AudioRequestCSV = CoreDsl.csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();
        
        //Judges from cases
        JudgesCSV = CoreDsl.csv(AppConfig.DARTS_PORTAL_JUDGES_FILE_PATH).random();

        //Users
        LanguageShopUsers = CoreDsl.csv(AppConfig.DARTS_PORTAL_LANGUAGESHOP_FILE_PATH).circular();
        CourtClerkUsers = CoreDsl.csv(AppConfig.DARTS_PORTAL_COURTCLERK_USERS_CSV).circular();
        TranscriberUsers = CoreDsl.csv(AppConfig.DARTS_PORTAL_TRANSCRIBERS_USERS_FILE_PATH).circular();
        
        //CourtHouseDetails
        CourtHouseAndCourtRooms = CoreDsl.csv(AppConfig.COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH).random();

        COUNTER = new AtomicInteger(0);
        // RANDOM_USER_FEEDER = jdbcFeeder("SELECT * FROM darts.user_account "
        // + "order by RANDOM()").random();

    }   
    public static FeederBuilder<String> createCourtHouseAndCourtRooms() {
        return CourtHouseAndCourtRooms;
    }
    
    public static FeederBuilder<String> createJudgesFeeder() {
        return JudgesCSV;
    }

    public static FeederBuilder<String> createTranscriberUsers() {
        return TranscriberUsers;
    }

    public static FeederBuilder<String> createLanguageShopUsers() {
        return LanguageShopUsers;
    }

    public static FeederBuilder<String> createCourtClerkUsers() {
        return CourtClerkUsers;
    }
    public static String getRandomEventCode() {
        List<String> eventCodes = List.of("DL", "DL", "DL");
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
        return CoreDsl.jsonPath("$.transformed_media_details[*].transformed_media_id").findRandom().saveAs("getTransformedMediaId");
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
