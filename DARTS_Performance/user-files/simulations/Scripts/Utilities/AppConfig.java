package Utilities;

import java.nio.file.Paths;
import java.util.Random;

public class AppConfig {

    //Performance Env
    public static final String PERFORMANCE_GATEWAY_BASE_URL= "test";
    public static final String PERFORMANCE_DARTS_API_BASE_URL= "test";
    public static final String PERFORMANCE_DARTS_BASE_URL= "test";
    public static final String PERFORMANCE_CLINET_ID = "test";
    public static final String PERFORMANCE_CLIENT_SECRET = "test";
    public static final String PERFORMANCE_AZURE_AD_B2C_CLIENT_ID = "test";
    public static final String PERFORMANCE_DARTS_PORTAL_SIGNIN = "test";
    public static final String PERFORMANCE_DARTS_PORTAL_Auth_LOGIN = PERFORMANCE_DARTS_PORTAL_SIGNIN + "test";
    public static final String PERFORMANCE_B2B_Login = "test";
    public static final String PERFORMANCE_B2B_Token = "test";
    public static final String PERFORMANCE_SCOPE = "test";
    public static final String PERFORMANCE_GRANT_TYPE = "test";

    //Staging Env
    public static final String STAGING_GATEWAY_BASE_URL= "test";
    public static final String STAGING_DARTS_API_BASE_URL= "test";
    public static final String STAGING_DARTS_BASE_URL= "test";
    public static final String STAGING_CLINET_ID = "test";
    public static final String STAGING_CLIENT_SECRET = "test";
    public static final String STAGING_AZURE_AD_B2C_CLIENT_ID = "test";
    public static final String STAGING_DARTS_PORTAL_SIGNIN = "test";
    public static final String STAGING_DARTS_PORTAL_Auth_LOGIN = STAGING_DARTS_PORTAL_SIGNIN + "test";
    public static final String STAGING_B2B_Login = "test";
    public static final String STAGING_B2B_Token = "test";
    public static final String STAGING_SCOPE = "test";
    public static final String STAGING_SCOPE_GRANT_TYPE = "test";


    // Base URL
    public enum EnvironmentURL {  
        GATEWAY_BASE_URL(STAGING_GATEWAY_BASE_URL),
        DARTS_BASE_URL(STAGING_DARTS_API_BASE_URL),        
        DARTS_PORTAL_BASE_URL(STAGING_DARTS_BASE_URL),

        //Tennat details
        DARTS_PORTAL_Auth_LOGIN(STAGING_DARTS_PORTAL_Auth_LOGIN),
        DARTS_PORTAL_SIGNIN(STAGING_DARTS_PORTAL_SIGNIN),
        B2B_Login(STAGING_B2B_Login),
        B2B_Token(STAGING_B2B_Token),
        SCOPE(STAGING_SCOPE),
        GRANT_TYPE(STAGING_SCOPE_GRANT_TYPE),
        CLINET_ID(STAGING_CLINET_ID),
        CLIENT_SECRET(STAGING_CLIENT_SECRET),  
        AZURE_AD_B2C_CLIENT_ID(STAGING_AZURE_AD_B2C_CLIENT_ID),

        //Users
        DARTS_API_USERNAME("darts.transcriber@hmcts.net"),
        DARTS_API_GLOBAL_USERNAME("darts_global_test_user"),
        DARTS_API_PASSWORD("Password@1"),
        DARTS_SOAP_USERNAME("xhibit_user"),
        DARTS_SOAP_PASSWORD("Password@1"),
        DARTS_API_USERNAME2("PerfTranscirber01@hmcts.net"),
        DARTS_API_PASSWORD2("PerfTester@01");

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
        StandardService("test");
        private final String endpoint;

        SoapServiceEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getEndpoint() {
            return endpoint;
        }
    }

    // Common part of the CSV file paths               
    public static final String CSV_FILE_COMMON_PATH = "C:\\Users\\a.cooper\\Desktop\\Performance.Testing\\DARTS\\darts-performance\\DARTS_Performance\\user-files\\Data\\";
    
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
        public static final String[] AUDIO_FILES = {"sample.mp2", "00h05m.mp2", "00h10m.mp2", "00h10m.mp2", "00h15m.mp2","00h20m.mp2", "02h.mp2"};
        public static final String[] AUDIO_FILES2 = {"02h.mp2"};

        // Method to select a random audio file
        public static String getRandomAudioFile() {
            Random random = new Random();
            int index = random.nextInt(AUDIO_FILES2.length);
            return AUDIO_FILES2[index];
        }
}
