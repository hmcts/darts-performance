package simulations.Scripts.Utilities;

import java.nio.file.Paths;
import java.util.Random;
import java.util.Optional;

public class AppConfig {

    //Performance Env
    public static final String PERFORMANCE_GATEWAY_BASE_URL= "http://darts-gateway.test.platform.hmcts.net";
    public static final String PERFORMANCE_DARTS_API_BASE_URL= "https://darts-api.test.platform.hmcts.net";
    public static final String PERFORMANCE_DARTS_BASE_URL= "https://darts.test.apps.hmcts.net";
    public static final String PERFORMANCE_CLINET_ID = "85118ee2-6544-4180-a16e-99615365f209";
    public static final String PERFORMANCE_CLIENT_SECRET = "zKO8Q~0Ab-4MZKHcghuhLKvzG8Ot~lkzr4EaacjI";        
    public static final String PERFORMANCE_AZURE_AD_B2C_CLIENT_ID = "363c11cb-48b9-44bf-9d06-9a3973f6f413";
    public static final String PERFORMANCE_DARTS_PORTAL_SIGNIN = "/hmctsstgextid.onmicrosoft.com/B2C_1_darts_externaluser_signin/";
    public static final String PERFORMANCE_DARTS_PORTAL_Auth_LOGIN = PERFORMANCE_DARTS_PORTAL_SIGNIN + "oauth2/v2.0/authorize";
    public static final String PERFORMANCE_B2B_Login = "https://hmctsstgextid.b2clogin.com";
    public static final String PERFORMANCE_B2B_Token = "/hmctsstgextid.onmicrosoft.com/B2C_1_ropc_darts_signin/oauth2/v2.0/token";
    public static final String PERFORMANCE_SCOPE = "https://hmctsstgextid.onmicrosoft.com/85118ee2-6544-4180-a16e-99615365f209/Functional.Test";
    public static final String PERFORMANCE_GRANT_TYPE = "password";
    
    //Staging Env
    public static final String STAGING_GATEWAY_BASE_URL= "http://darts-gateway.staging.platform.hmcts.net";
    public static final String STAGING_DARTS_API_BASE_URL= "https://darts-api.staging.platform.hmcts.net";
    public static final String STAGING_DARTS_BASE_URL= "https://darts.staging.apps.hmcts.net";
    public static final String STAGING_CLINET_ID = "85118ee2-6544-4180-a16e-99615365f209";
    public static final String STAGING_CLIENT_SECRET = "zKO8Q~0Ab-4MZKHcghuhLKvzG8Ot~lkzr4EaacjI";        
    public static final String STAGING_AZURE_AD_B2C_CLIENT_ID = "363c11cb-48b9-44bf-9d06-9a3973f6f413";
    public static final String STAGING_DARTS_PORTAL_SIGNIN = "/hmctsstgextid.onmicrosoft.com/B2C_1_darts_externaluser_signin/";
    public static final String STAGING_DARTS_PORTAL_Auth_LOGIN = STAGING_DARTS_PORTAL_SIGNIN + "oauth2/v2.0/authorize";
    public static final String STAGING_B2B_Login = "https://hmctsstgextid.b2clogin.com";
    public static final String STAGING_B2B_Token = "/hmctsstgextid.onmicrosoft.com/B2C_1_ropc_darts_signin/oauth2/v2.0/token";
    public static final String STAGING_SCOPE = "https://hmctsstgextid.onmicrosoft.com/85118ee2-6544-4180-a16e-99615365f209/Functional.Test";
    public static final String STAGING_GRANT_TYPE = "password";


    public static final long RANK_UP_TIME_SECONDS;
    public static final long RANK_DOWN_TIME_SECONDS;
    public static final int REQUESTS_PER_SECOND;
    private static final double REQUESTS_PER_SECOND_PER_USER;
    public static final TestType TEST_TYPE;
    public static final boolean DEBUG;
    public static final String ENVIRONMENT;

    //Users
    public static final int USERS_PER_SECOND;
    public static final int PIPELINE_USERS_PER_SECOND;
    public static final int CONSTANT_CONCURRENT_USERS;

    //Test Times Set up
    public static final long TEST_DURATION_SECONDS;
    public static final long TEST_DURATION_MINUTES;
    public static final long BASELINE_TEST_DURATION_MINUTES;
    public static final long RAMP_TEST_DURATION_MINUTES;
    public static final long SPIKE_TEST_DURATION_MINUTES;

    //Soap Requests
    public static final int SOAP_BASELINE_REPEATS;
    public static final int SOAP_RAMPUP_REPEATS;
    public static final int SOAP_SPIKE_REPEATS;

    public static final int SOAP_BASELINE_PACE_DURATION_MILLIS;
    public static final int SOAP_RAMPUP_PACE_DURATION_MILLIS;
    public static final int SOAP_SPIKE_PACE_DURATION_MILLIS;


    //public static final String DB_URL = "darts-api-stg.postgres.database.azure.com";

    static {
        TEST_TYPE = TestType.valueOf(getProperty("TEST_URL", TestType.PERFORMANCE.name()));
        DEBUG = Boolean.parseBoolean(getProperty("DEBUG", "false"));
        ENVIRONMENT = getProperty("ENVIRONMENT", TEST_TYPE.name());

        RANK_UP_TIME_SECONDS = Long.parseLong(getProperty("RANK_UP_TIME_SECONDS", "120"));
        RANK_DOWN_TIME_SECONDS = Long.parseLong(getProperty("RANK_DOWN_TIME_SECONDS", "120"));

        //Users
        PIPELINE_USERS_PER_SECOND = Integer.parseInt(getProperty("PIPELINE_USERS_PER_SECOND", "1"));

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

        //Test Times Set up
        TEST_DURATION_SECONDS = Long.parseLong(getProperty("TEST_DURATION_SECONDS", "600"));
        TEST_DURATION_MINUTES = Long.parseLong(getProperty("TEST_DURATION_MINUTES", "30"));
        BASELINE_TEST_DURATION_MINUTES = Long.parseLong(getProperty("BASELINE_TEST_DURATION_MINUTES", "30"));
        RAMP_TEST_DURATION_MINUTES = Long.parseLong(getProperty("RAMP_TEST_DURATION_MINUTES", "20"));
        SPIKE_TEST_DURATION_MINUTES = Long.parseLong(getProperty("SPIKE_TEST_DURATION_MINUTES", "10"));

        //Soap Requests
        SOAP_BASELINE_REPEATS = Integer.parseInt(getProperty("SOAP_BASELINE_REPEATS", "100"));
        SOAP_RAMPUP_REPEATS = Integer.parseInt(getProperty("SOAP_RAMPUP_REPEATS", "50"));
        SOAP_SPIKE_REPEATS = Integer.parseInt(getProperty("SOAP_SPIKE_REPEATS", "100"));

        SOAP_BASELINE_PACE_DURATION_MILLIS = Integer.parseInt(getProperty("SOAP_BASELINE_PACE_DURATION_MILLIS", "6000"));
        SOAP_RAMPUP_PACE_DURATION_MILLIS = Integer.parseInt(getProperty("SOAP_RAMPUP_PACE_DURATION_MILLIS", "6000"));
        SOAP_SPIKE_PACE_DURATION_MILLIS = Integer.parseInt(getProperty("SOAP_SPIKE_PACE_DURATION_MILLIS", "1800"));


    
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
        GATEWAY_BASE_URL(PERFORMANCE_GATEWAY_BASE_URL),
        DARTS_BASE_URL(PERFORMANCE_DARTS_API_BASE_URL),        
        DARTS_PORTAL_BASE_URL(PERFORMANCE_DARTS_BASE_URL),
        
        //Tennat details
        DARTS_PORTAL_Auth_LOGIN(PERFORMANCE_DARTS_PORTAL_Auth_LOGIN),
        DARTS_PORTAL_SIGNIN(PERFORMANCE_DARTS_PORTAL_SIGNIN),
        B2B_Login(PERFORMANCE_B2B_Login),
        B2B_Token(PERFORMANCE_B2B_Token),
        SCOPE(PERFORMANCE_SCOPE),
        GRANT_TYPE(PERFORMANCE_GRANT_TYPE),
        CLINET_ID(PERFORMANCE_CLINET_ID),
        CLIENT_SECRET(PERFORMANCE_CLIENT_SECRET),  
        AZURE_AD_B2C_CLIENT_ID(PERFORMANCE_AZURE_AD_B2C_CLIENT_ID),

        //Users
        DARTS_API_USERNAME("darts.transcriber@hmcts.net"),
        DARTS_API_GLOBAL_USERNAME("darts_global_test_user"),
        DARTS_API_PASSWORD("Password@1"),
        DARTS_SOAP_USERNAME("xhibit_ws_user"),
        DARTS_SOAP_PASSWORD("l0g1c@"),
        DARTS_API_USERNAME2("darts.admin@hmcts.net"),
        DARTS_API_PASSWORD2("Password@1"),
        DARTS_SOAP_EXTERNAL_USERNAME("viq_ws_user"),
        DARTS_SOAP_EXTERNAL_PASSWORD("darts");

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
        ContextRegistryService("/service/darts/runtime/ContextRegistryService"),
        DARTSService("/service/darts/DARTSService"),
        StandardService("/service/darts");
        private final String endpoint;

        SoapServiceEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getEndpoint() {
            return endpoint;
        }
    }

    // Get the base directory of the project
    public static final String BASE_DIR = System.getProperty("user.dir");

    // Common part of the CSV file paths
    public static final String CSV_FILE_COMMON_PATH = "DARTS_Performance_Gradle/src/gatling/resources";

    // Specific CSV file names
    public static final String COURT_HOUSE_AND_COURT_ROOMS_CSV = "GetAllCourtroomsAndCourthouses.csv";
    public static final String TRANSCRIPTION_POST_CSV = "Transcription_Post.csv";
    public static final String AUDIO_REQUEST_POST_CSV = "Audio_Request_Post.csv";
    public static final String TRANSFORMED_MEDIA_CSV = "TransformedMedia.csv";

    // Specific CSV file for Users
    public static final String DARTS_PORTAL_USERS_CSV = "Users.csv";
    public static final String DARTS_PORTAL_COURTCLERK_USERS_CSV = "UsersCourtClerks.csv";
    public static final String DARTS_PORTAL_TRANSCRIBERS_USERS_CSV = "UsersTranscribers.csv";
    public static final String DARTS_PORTAL_LANGUAGESHOP_USERS_CSV = "UsersLanguageShop.csv";

    // Specific CSV file for Judges
    public static final String DARTS_PORTAL_JUDGES_CSV = "JudgeName2.csv";

    // Full CSV file paths
    public static final String COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, COURT_HOUSE_AND_COURT_ROOMS_CSV).toString();
    public static final String TRANSCRIPTION_POST_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, TRANSCRIPTION_POST_CSV).toString();
    public static final String AUDIO_REQUEST_POST_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, AUDIO_REQUEST_POST_CSV).toString();
    public static final String TRANSFORMED_MEDIA_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, TRANSFORMED_MEDIA_CSV).toString();
    public static final String DARTS_PORTAL_USERS1_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, DARTS_PORTAL_USERS_CSV).toString();
    public static final String DARTS_PORTAL_COURTCLERK_USERS_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, DARTS_PORTAL_COURTCLERK_USERS_CSV).toString();
    public static final String DARTS_PORTAL_TRANSCRIBERS_USERS_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, DARTS_PORTAL_TRANSCRIBERS_USERS_CSV).toString();
    public static final String DARTS_PORTAL_LANGUAGESHOP_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, DARTS_PORTAL_LANGUAGESHOP_USERS_CSV).toString();
    public static final String DARTS_PORTAL_JUDGES_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, DARTS_PORTAL_JUDGES_CSV).toString();


    public static String getFilePath(String simulationType) {
        switch (simulationType) {
            case "Simulation1":
                return Paths.get(CSV_FILE_COMMON_PATH, DARTS_PORTAL_USERS_CSV).toString();
            case "Simulation2":
                return Paths.get(CSV_FILE_COMMON_PATH, DARTS_PORTAL_COURTCLERK_USERS_CSV).toString();
            case "Simulation3":
                return Paths.get(CSV_FILE_COMMON_PATH, DARTS_PORTAL_TRANSCRIBERS_USERS_CSV).toString();
            default:
                throw new IllegalArgumentException("Invalid simulation type: " + simulationType);
        }
    }
        // List of Document files
        public static final String[] DOCUMENT_FILES = {"SampleDoc2.docx"};

        // Method to select a random audio file
        public static String getRandomDocumentFile() {
            Random random = new Random();
            int index = random.nextInt(DOCUMENT_FILES.length);
            return DOCUMENT_FILES[index];
        }

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
            addValueToBuilder(builder, "Rank Down Time Seconds", String.valueOf(RANK_DOWN_TIME_SECONDS));

            //Users
            addValueToBuilder(builder, "Users Per Second", String.valueOf(USERS_PER_SECOND));
            addValueToBuilder(builder, "Constant concurrent users", String.valueOf(CONSTANT_CONCURRENT_USERS));
            addValueToBuilder(builder, "Pipeline Users Per Second", String.valueOf(PIPELINE_USERS_PER_SECOND));

            //Test Times Set up
            addValueToBuilder(builder, "Test Duration Seconds", String.valueOf(TEST_DURATION_SECONDS));
            addValueToBuilder(builder, "Test Duration Minutes", String.valueOf(TEST_DURATION_MINUTES));
            addValueToBuilder(builder, "Test Duration Minutes", String.valueOf(BASELINE_TEST_DURATION_MINUTES));
            addValueToBuilder(builder, "Test Duration Minutes", String.valueOf(RAMP_TEST_DURATION_MINUTES));
            addValueToBuilder(builder, "Test Duration Minutes", String.valueOf(SPIKE_TEST_DURATION_MINUTES));

            //Soap Requests
            addValueToBuilder(builder, "Number of repeats for the baseline Soap Request", String.valueOf(SOAP_BASELINE_REPEATS));
            addValueToBuilder(builder, "Number of repeats for the ramp up Soap Request", String.valueOf(SOAP_RAMPUP_REPEATS));
            addValueToBuilder(builder, "Number of repeats for the spike Soap Request", String.valueOf(SOAP_SPIKE_REPEATS));

            addValueToBuilder(builder, "The duration in milliseconds of pacing for the repeats within the baseline Soap Request", String.valueOf(SOAP_BASELINE_PACE_DURATION_MILLIS));
            addValueToBuilder(builder, "The duration in milliseconds of pacing for the repeats within the ramp up Soap Request", String.valueOf(SOAP_RAMPUP_PACE_DURATION_MILLIS));
            addValueToBuilder(builder, "The duration in milliseconds of pacing for the repeats within the spike Soap Request", String.valueOf(SOAP_SPIKE_PACE_DURATION_MILLIS));       




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
