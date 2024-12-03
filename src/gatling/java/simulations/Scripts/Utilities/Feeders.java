package simulations.Scripts.Utilities;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.sql.Connection;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.jdbc.JdbcDsl;
import io.gatling.javaapi.core.CheckBuilder;


public class Feeders {

    public static final FeederBuilder<String> AudioRequestCSV;
    public static final FeederBuilder<String> JudgesCSV;
    public static final FeederBuilder<String> LanguageShopUsers;
    public static final FeederBuilder<String> CourtClerkUsers;
    public static final FeederBuilder<String> CourtManagerUsers;
    public static final FeederBuilder<String> TranscriberUsers;
    public static final FeederBuilder<String> JudgeUsers;

    public static final FeederBuilder<String> CourtHouseAndCourtRooms;
    public static final FeederBuilder<String> CaseHouseRoomsHearingDetails;
    public static final FeederBuilder<String> TransformedMediaDownloadIdCSV;
    public static final FeederBuilder<String> TransformedMediaPlaybackIdCSV;
    public static final FeederBuilder<String> TransformedMediaDeleteIdsCSV;    
    public static final FeederBuilder<String> TranscriptionPostDetails;
    public static final FeederBuilder<String> TranscriptionPatchAcceptDetails;

    private static final AtomicInteger COUNTER;
    private static final Logger log = Logger.getLogger(Feeders.class.getName());

    static {        
        //Audio Files
        AudioRequestCSV = CoreDsl.csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).circular();

        //Judges from cases
        JudgesCSV = CoreDsl.csv(AppConfig.DARTS_PORTAL_JUDGES_FILE_PATH).random();

        //Users
        LanguageShopUsers = CoreDsl.csv(AppConfig.DARTS_PORTAL_LANGUAGESHOP_FILE_PATH).circular();
        CourtClerkUsers = CoreDsl.csv(AppConfig.DARTS_PORTAL_COURTCLERK_USERS_CSV).circular();
        TranscriberUsers = CoreDsl.csv(AppConfig.DARTS_PORTAL_TRANSCRIBERS_USERS_FILE_PATH).circular();
        CourtManagerUsers = CoreDsl.csv(AppConfig.DARTS_PORTAL_COURTMANAGER_USERS_FILE_PATH).circular();
        JudgeUsers = CoreDsl.csv(AppConfig.DARTS_PORTAL_JUDGE_USERS_FILE_PATH).circular();

        
        //CourtHouseDetails
        CourtHouseAndCourtRooms = CoreDsl.csv(AppConfig.COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH).random();
        CaseHouseRoomsHearingDetails = CoreDsl.csv(AppConfig.CASE_HOUSE_ROOMS_HEARING_FILE_PATH).random();
        
        //Transformed Media Id's
        TransformedMediaDownloadIdCSV = CoreDsl.csv(AppConfig.TRANSFORMED_MEDIA_DOWNLOAD_IDS_FILE_PATH).circular();
        TransformedMediaPlaybackIdCSV = CoreDsl.csv(AppConfig.TRANSFORMED_MEDIA_PLAYBACK_IDS_FILE_PATH).circular();
        TransformedMediaDeleteIdsCSV = CoreDsl.csv(AppConfig.TRANSFORMED_MEDIA_DELETE_IDS_FILE_PATH).circular();

        //Transcription Post body details
        TranscriptionPostDetails = CoreDsl.csv(AppConfig.TRANSCRIPTION_POST_FILE_PATH).circular();
        TranscriptionPatchAcceptDetails = CoreDsl.csv(AppConfig.TRANSCRIPTION_PATCH_ACCEPT_FILE_PATH).circular();
    
        COUNTER = new AtomicInteger(0);
        // RANDOM_USER_FEEDER = jdbcFeeder("SELECT * FROM darts.user_account "
        // + "order by RANDOM()").random();

    }   
    public static FeederBuilder<String> createCourtHouseAndCourtRooms() {
        return CourtHouseAndCourtRooms;
    }

    public static FeederBuilder<String> createCaseHouseRoomsHearingDetails() {
        return CaseHouseRoomsHearingDetails;
    }

    public static FeederBuilder<String> createTranscriptionPostDetails() {
        return TranscriptionPostDetails;
    }
    public static FeederBuilder<String> createTranscriptionPatchAcceptDetails() {
        return TranscriptionPatchAcceptDetails;
    }
    public static FeederBuilder<String> createAudioRequestCSV() {
        return AudioRequestCSV;
    }    

    public static FeederBuilder<String> createTransformedMediaDownloadIdCSV() {
        return TransformedMediaDownloadIdCSV;
    }
    public static FeederBuilder<String> createTransformedMediaPlaybackIdCSV() {
        return TransformedMediaPlaybackIdCSV;
    }
    

    public static FeederBuilder<String> createTransformedMediaDeleteIdsCSV() {
        return TransformedMediaDeleteIdsCSV;
    }
    
    
    public static FeederBuilder<String> createJudgesFeeder() {
        return JudgesCSV;
    }

    public static FeederBuilder<String> createTranscriberUsers() {
        return TranscriberUsers;
    }
    public static FeederBuilder<String> createJudgeUsers() {
        return JudgeUsers;
    }

    public static FeederBuilder<String> createLanguageShopUsers() {
        return LanguageShopUsers;
    }

    public static FeederBuilder<String> createCourtClerkUsers() {
        return CourtClerkUsers;
    }
    public static FeederBuilder<String> createCourtManagerUsers() {
        return CourtManagerUsers;
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
        return CoreDsl.jsonPath("$[*].case_id").findRandom().saveAs("getCaseId");
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

    public static CheckBuilder.Final saveNewCreatedTransformedMediaId() {
        return CoreDsl.jsonPath("$.transformed_media_details[*].[?(@.media_request_id=='#{getRequestId}')].transformed_media_id").saveAs("trm_id");
    }

    public static CheckBuilder.Final saveCaseIdFromTransformedMediaId() {
        return CoreDsl.jsonPath("$.transformed_media_details[*].case_id").findRandom().saveAs("getCaseId");
    }

    public static CheckBuilder.Final saveRegistrationToken() {
        return CoreDsl.regex("<return[^>]*>([^<]+)<\\/return>").find(0).saveAs("registrationToken");
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

        // List of audio files
        //public static final String[] AUDIO_FILES = {"sample.mp2", "00h10m.mp2", "00h15m.mp2","00h20m.mp2", "02h.mp2"};
        private static final String[] AUDIO_FILES = {"1mb.mp2", "4mb.mp2", "16mb.mp2", "64mb.mp2", "256mb.mp2"};
        //private static final String[] AUDIO_FILES = {"1mb.mp2"};

        // Corresponding percentages (must sum up to 100)
        private static final int[] PERCENTAGES = {5, 10, 30, 40, 15};
       // private static final int[] PERCENTAGES = {5};

        // Method to select a random audio file based on percentages
        public static String getRandomAudioFile() {
            int totalPercentage = 0;

            // Calculate the total percentage
            for (int percentage : PERCENTAGES) {
                totalPercentage += percentage;
            }

            // Generate a random number between 1 and totalPercentage
            int randomNumber = ThreadLocalRandom.current().nextInt(totalPercentage) + 1;

            // Find the index of the audio file corresponding to the random number
            int runningSum = 0;
            for (int i = 0; i < PERCENTAGES.length; i++) {
                runningSum += PERCENTAGES[i];
                if (randomNumber <= runningSum) {
                    return AUDIO_FILES[i];
                }
            }

            // Fallback return (should not reach here)
            return AUDIO_FILES[0];
        }

        // Main method for testing
        public static void main(String[] args) {
            // Test the getRandomAudioFile method
            for (int i = 0; i < 100; i++) {
                System.out.println(getRandomAudioFile());
            }
        }

    public static void executeUpdate(String sql) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            log.info("Executing update: " + AppConfig.DB_URL + ", " + AppConfig.DB_USERNAME + ", " + sql);
            connection = DriverManager.getConnection(AppConfig.DB_URL, AppConfig.DB_USERNAME, AppConfig.DB_PASSWORD);
            preparedStatement = connection.prepareStatement(sql);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                log.info("Update successful");
            } else {
                log.info("No rows affected");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }    

    // This method can be used for SELECT queries
    public static FeederBuilder<Object> jdbcFeeder(String sql) {
        log.info("Creating jdbcFeeder: " + AppConfig.DB_URL + ", " + AppConfig.DB_USERNAME + ", " + AppConfig.DB_PASSWORD + ", " + sql);
        return JdbcDsl.jdbcFeeder(AppConfig.DB_URL, AppConfig.DB_USERNAME, AppConfig.DB_PASSWORD, sql);
    }

    public static FeederBuilder<Object> jdbcFeeder2() {
        log.info("Creating jdbcFeeder dynamically...");
        
        // Fetch the SQL dynamically for each execution
        String sql = SQLQueryProvider.getCaseRetentionForChildObjectQuery();  
        log.info("Executing SQL: " + sql);
    
        // Create the feeder
        FeederBuilder<Object> feeder = JdbcDsl.jdbcFeeder(AppConfig.DB_URL, AppConfig.DB_USERNAME, AppConfig.DB_PASSWORD, sql);

    
        return feeder;
    }
    
    
    

    public static void resetCounter() {
        COUNTER.set(0);
    }
}
