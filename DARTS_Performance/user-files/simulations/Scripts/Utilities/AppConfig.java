package Utilities;

import java.nio.file.Paths;
import java.util.Random;

public class AppConfig {
   
    // Base URL
    public enum EnvironmentURL {  

        GATEWAY_BASE_URL("https://darts-gateway.staging.platform.hmcts.net"),
        DARTS_BASE_URL("https://darts-api.staging.platform.hmcts.net"),
        DARTS_PORTAL_Auth_LOGIN("test"),
        B2B_Login("test"),
        B2B_Token("test"),
        SCOPE("testScope"),
        GRANT_TYPE("test"),
        CLINET_ID("test_CLINET_ID"),
        CLIENT_SECRET("test_CLIENT_SECRET"),        
        DARTS_API_USERNAME("test"),
        DARTS_API_GLOBAL_USERNAME("test"),
        DARTS_API_PASSWORD("test"),
        DARTS_SOAP_USERNAME("test"),
        DARTS_SOAP_PASSWORD("test"),
        AZURE_AD_B2C_CLIENT_ID("test"),
        DARTS_PORTAL_BASE_URL("test");

        private final String url;
        EnvironmentURL(String url) {
            this.url = url;
        }
        public String getUrl() {
            return url;
        }
    }
    
    // Proxy settings
    public static final String PROXY_HOST = "127.0.0.1";
    public static final int PROXY_PORT = 8888;

    // SOAP service endpoint
    public enum SoapServiceEndpoint {
        ContextRegistryService("test"),
        DARTSService("test"),
        TEST2("/soap/test2");
        private final String endpoint;

        SoapServiceEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getEndpoint() {
            return endpoint;
        }
    }

    // Common part of the CSV file paths               
    public static final String CSV_FILE_COMMON_PATH = "C:\\Users\\a.cooper\\Desktop\\Performance.Testing\\DARTS\\darts-performance\\DARTS_Performance\\user-files\\Data";
    
    // Specific CSV file names
    public static final String COURT_HOUSE_AND_COURT_ROOMS_CSV = "CourtHouse_And_CourtRooms.csv";
    public static final String TRANSCRIPTION_POST_CSV = "Transcription_Post.csv";
    public static final String AUDIO_REQUEST_POST_CSV = "Audio_Request_Post.csv";
    public static final String TRANSFORMED_MEDIA_CSV = "TransformedMedia.csv";

    public static final String DARTS_PORTAL_USERS_CSV = "Users.csv";

    // Full CSV file paths
    public static final String COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH = Paths.get(CSV_FILE_COMMON_PATH, COURT_HOUSE_AND_COURT_ROOMS_CSV).toString();
    public static final String TRANSCRIPTION_POST_FILE_PATH = Paths.get(CSV_FILE_COMMON_PATH, TRANSCRIPTION_POST_CSV).toString();
    public static final String AUDIO_REQUEST_POST_FILE_PATH = Paths.get(CSV_FILE_COMMON_PATH, AUDIO_REQUEST_POST_CSV).toString();
    public static final String TRANSFORMED_MEDIA_FILE_PATH = Paths.get(CSV_FILE_COMMON_PATH, TRANSFORMED_MEDIA_CSV).toString();
    public static final String DARTS_PORTAL_USERS_FILE_PATH = Paths.get(CSV_FILE_COMMON_PATH, DARTS_PORTAL_USERS_CSV).toString();

        // List of audio files
        public static final String[] AUDIO_FILES = {"sample.mp2", "00h05m.mp2", "00h10m.mp2", "00h10m.mp2", "00h15m.mp2","00h20m.mp2"};

        // Method to select a random audio file
        public static String getRandomAudioFile() {
            Random random = new Random();
            int index = random.nextInt(AUDIO_FILES.length);
            return AUDIO_FILES[index];
        }

}
