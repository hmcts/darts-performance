package simulations.Scripts.Utilities;

import java.nio.file.Paths;
import java.util.Random;
import java.util.Optional;

public class AppConfig {

    //Performance Env
    public static final String PERFORMANCE_GATEWAY_BASE_URL= "test";
    public static final String PERFORMANCE_DARTS_API_BASE_URL= "test";
    public static final String PERFORMANCE_DARTS_BASE_URL= "test";
    public static final String PERFORMANCE_CLINET_ID = "test";
    public static final String PERFORMANCE_CLIENT_SECRET = "test";       
    public static final String PERFORMANCE_AZURE_AD_B2C_CLIENT_ID = "test";
    public static final String PERFORMANCE_DARTS_PORTAL_SIGNIN = "test";
    public static final String PERFORMANCE_DARTS_PORTAL_Auth_LOGIN = PERFORMANCE_DARTS_PORTAL_SIGNIN + "oauth2/v2.0/authorize";
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
    public static final String STAGING_GRANT_TYPE = "test";
    public static final long RANK_UP_TIME_SECONDS;
    public static final long TEST_DURATION_SECONDS;
    public static final long RANK_DOWN_TIME_SECONDS;
    public static final int USERS_PER_SECOND;
    public static final int CONSTANT_CONCURRENT_USERS;
    public static final int PIPELINE_USERS_PER_SECOND;
    public static final int REQUESTS_PER_SECOND;
    private static final double REQUESTS_PER_SECOND_PER_USER;
    public static final TestType TEST_TYPE;
    public static final boolean DEBUG;
    public static final String ENVIRONMENT;
    public static final String DB_URL;
    public static final String DB_USERNAME;
    public static final String DB_PASSWORD;
    //public static final String DB_URL = "darts-api-stg.postgres.database.azure.com";

    static {
        TEST_TYPE = TestType.valueOf(getProperty("TEST_URL", TestType.PERFORMANCE.name()));
        DEBUG = Boolean.parseBoolean(getProperty("DEBUG", "false"));
        ENVIRONMENT = getProperty("ENVIRONMENT", TEST_TYPE.name());

        RANK_UP_TIME_SECONDS = Long.parseLong(getProperty("RANK_UP_TIME_SECONDS", "120"));
        RANK_DOWN_TIME_SECONDS = Long.parseLong(getProperty("RANK_DOWN_TIME_SECONDS", "120"));
        TEST_DURATION_SECONDS = Long.parseLong(getProperty("TEST_DURATION_SECONDS", "600"));
        PIPELINE_USERS_PER_SECOND = Integer.parseInt(getProperty("PIPELINE_USERS_PER_SECOND", "10"));

        DB_URL = getProperty("DB_URL", "test");
        DB_USERNAME = getProperty("DB_USERNAME", "test");
        DB_PASSWORD = getProperty("DB_PASSWORD", "test");

        if (hasProperty("USERS_PER_HOUR")) {
            USERS_PER_SECOND = Integer.parseInt((Double.parseDouble(getProperty("USERS_PER_HOUR", "3600")) / 3600.0) +
                "");
        } else {
            USERS_PER_SECOND = Integer.parseInt(getProperty("USERS_PER_SECOND", "1"));
        }
        if (hasProperty("REQUESTS_PER_HOUR_PER_USER")) {
            REQUESTS_PER_SECOND_PER_USER =
                Double.parseDouble(getProperty("REQUESTS_PER_HOUR_PER_USER","3600")) / 3600.0;
        } else {
            REQUESTS_PER_SECOND_PER_USER = Double.parseDouble(getProperty("REQUESTS_PER_SECOND_PER_USER", "1"));
        }
        CONSTANT_CONCURRENT_USERS = Integer.parseInt(getProperty("CONSTANT_CONCURRENT_USERS", "1"));
        REQUESTS_PER_SECOND = Math.max(1, (int) (REQUESTS_PER_SECOND_PER_USER * USERS_PER_SECOND)); 
    }

    private static boolean hasProperty(String name) {
        return System.getProperty(name) != null || System.getenv(name) != null;
    }

    private static String getProperty(String name, String defaultValue) {
        String value = System.getProperty(name);
        if (value != null) {
            return value;
        }
        return Optional.ofNullable(System.getenv(name)).orElse(defaultValue);
    }

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
        GRANT_TYPE(STAGING_GRANT_TYPE),
        CLINET_ID(STAGING_CLINET_ID),
        CLIENT_SECRET(STAGING_CLIENT_SECRET),  
        AZURE_AD_B2C_CLIENT_ID(STAGING_AZURE_AD_B2C_CLIENT_ID),

        //Users
        DARTS_API_USERNAME("test"),
        DARTS_API_GLOBAL_USERNAME("test"),
        DARTS_API_PASSWORD("test"),
        DARTS_EXTERNAL_SOAP_USERNAME("test"),
        DARTS_EXTERNAL_SOAP_PASSWORD("test"),
        DARTS_API_USERNAME2("test"),
        DARTS_API_PASSWORD2("test");

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


        public static String asString() {
            StringBuilder builder = new StringBuilder();
            addValueToBuilder(builder, "Test Type", TEST_TYPE.name());
            addValueToBuilder(builder, "Debug", String.valueOf(DEBUG));
            addValueToBuilder(builder, "Rank Up Time Seconds", String.valueOf(RANK_UP_TIME_SECONDS));
            addValueToBuilder(builder, "Test Duration Seconds", String.valueOf(TEST_DURATION_SECONDS));
            addValueToBuilder(builder, "Rank Down Time Seconds", String.valueOf(RANK_DOWN_TIME_SECONDS));
            addValueToBuilder(builder, "Users Per Second", String.valueOf(USERS_PER_SECOND));
            addValueToBuilder(builder, "Constant concurrent users", String.valueOf(CONSTANT_CONCURRENT_USERS));
            addValueToBuilder(builder, "Pipeline Users Per Second", String.valueOf(PIPELINE_USERS_PER_SECOND));
            return builder.toString();
        }
        private static void addValueToBuilder(StringBuilder builder, String key, String value) {
            builder.append(key).append(": ").append(value).append("\n");
        }

        public enum TestType {
            PERFORMANCE,
            PIPELINE;
        }
}