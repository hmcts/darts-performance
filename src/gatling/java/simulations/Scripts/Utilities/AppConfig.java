package simulations.Scripts.Utilities;

import java.nio.file.Paths;
import java.util.Random;
import java.util.Optional;

public class AppConfig {


    
    //EXTERNAL Tenant
    //old public static final String PERFORMANCE_EXTERNAL_CLIENT_ID = "test";
    public static final String PERFORMANCE_EXTERNAL_CLIENT_ID = "test";
    //old public static final String PERFORMANCE_EXTERNAL_CLIENT_SECRET = "test";
    public static final String PERFORMANCE_EXTERNAL_CLIENT_SECRET = "test";
    //old public static final String PERFORMANCE_EXTERNAL_AZURE_AD_B2C_CLIENT_ID = "test";
    public static final String PERFORMANCE_EXTERNAL_AZURE_AD_B2C_CLIENT_ID = "test";
    
    // Old public static final String TENANT_NAME = "hmctsstgextid";

    public static final String TENANT_NAME = "test";

    //INTERNAL Tenant
    public static final String PERFORMANCE_INTERNAL_CLIENT_ID = "test";     
    public static final String PERFORMANCE_INTERNAL_CLIENT_SECRET = "test";    
    public static final String PERFORMANCE_INTERNAL_TENANT_ID = "test";

    //Performance Env
    public static final String PERFORMANCE_GATEWAY_BASE_URL= "test";
    public static final String PERFORMANCE_PROXY_BASE_URL= "test";
    public static final String PERFORMANCE_DARTS_API_BASE_URL= "test";
    public static final String PERFORMANCE_DARTS_BASE_URL= "test";
    public static final String PERFORMANCE_DARTS_PORTAL_SIGNIN = "/" + TENANT_NAME+".onmicrosoft.com/B2C_1_darts_externaluser_signin/";
    public static final String PERFORMANCE_DARTS_PORTAL_Auth_LOGIN = PERFORMANCE_DARTS_PORTAL_SIGNIN + "oauth2/v2.0/authorize";
    public static final String PERFORMANCE_B2B_Login = "https://"+TENANT_NAME+".b2clogin.com";
    public static final String PERFORMANCE_B2B_Token = "/"+TENANT_NAME+".onmicrosoft.com/B2C_1_ropc_darts_signin/oauth2/v2.0/token";
    // Old public static final String PERFORMANCE_SCOPE = ""test";";
    public static final String PERFORMANCE_SCOPE = "https://"+TENANT_NAME+".onmicrosoft.com/" + PERFORMANCE_EXTERNAL_CLIENT_ID + "/test";

    public static final String PERFORMANCE_GRANT_TYPE = "password";
 
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
    public static final int JUDGE_RAMP_UP_USERS;
    public static final int COURT_CLERK_RAMP_UP_USERS;
    public static final int COURT_MANAGER_RAMP_UP_USERS;
    public static final int TRANSCRIBER_RAMP_UP_USERS;
    public static final int LANGUAGE_SHOP_RAMP_UP_USERS;

    //Users Ramp up Duration
    public static final int RAMP_UP_DURATION_OF_JUDGES;
    public static final int RAMP_UP_DURATION_OF_COURT_CLERK;
    public static final int RAMP_UP_DURATION_OF_COURT_MANAGER;
    public static final int RAMP_UP_DURATION_OF_TRANSCRIBER;
    public static final int RAMP_UP_DURATION_OF_LANGUAGE_SHOP;

    //Test Times Set up
    public static final long TEST_DURATION_SECONDS;
    public static final long TEST_DURATION_MINUTES;
    public static final long SMOKE_TEST_DURATION_MINUTES;
    public static final long BASELINE_NORMAL_DURATION_MINUTES;
    public static final long PEAK_TEST_DURATION_MINUTES;
    
    //Soap Requests
    public static final int SOAP_BASELINE_REPEATS;
    public static final int SOAP_RAMPUP_REPEATS;
    public static final int SOAP_SPIKE_REPEATS;
   
    //Events Requests
    public static final int EVENTS_SMOKE_REPEATS;
    public static final int EVENTS_BASELINE_NORMAL_REPEATS;
    public static final int EVENTS_PEAK_REPEATS;
    
    //POST Audio Requests
    public static final int POST_AUDIO_REQUEST_SMOKE_REPEATS;
    public static final int POST_AUDIO_REQUEST_BASELINE_NORMAL_REPEATS;
    public static final int POST_AUDIO_REQUEST_PEAK_REPEATS;
    
    //GET Audio Requests
    public static final int GET_AUDIO_REQUEST_SMOKE_REPEATS;
    public static final int GET_AUDIO_REQUEST_BASELINE_NORMAL_REPEATS;
    public static final int GET_AUDIO_REQUEST_PEAK_REPEATS;   
    
    //DELETE Audio Requests
    public static final int DELETE_AUDIO_REQUEST_SMOKE_REPEATS;
    public static final int DELETE_AUDIO_REQUEST_BASELINE_NORMAL_REPEATS;
    public static final int DELETE_AUDIO_REQUEST_PEAK_REPEATS;
    
    //Add Cases Requests
    public static final int ADD_CASES_SMOKE_REPEATS;
    public static final int ADD_CASES_BASELINE_NORMAL_REPEATS;
    public static final int ADD_CASES_PEAK_REPEATS;  
    
    //GET Cases Requests
    public static final int GET_CASES_SMOKE_REPEATS;
    public static final int GET_CASES_BASELINE_NORMAL_REPEATS;
    public static final int GET_CASES_PEAK_REPEATS;  
    
    //Add Log Entry Requests
    public static final int ADD_LOG_ENTRY_SMOKE_REPEATS;
    public static final int ADD_LOG_ENTRY_BASELINE_NORMAL_REPEATS;
    public static final int ADD_LOG_ENTRY_PEAK_REPEATS; 
    
    //Get Log Entry Requests
    public static final int GET_LOG_ENTRY_SMOKE_REPEATS;
    public static final int GET_LOG_ENTRY_BASELINE_NORMAL_REPEATS;
    public static final int GET_LOG_ENTRY_PEAK_REPEATS; 

    //POST Audio (Api)
    public static final int POST_AUDIO_SMOKE_REPEATS;
    public static final int POST_AUDIO_BASELINE_NORMAL_REPEATS;
    public static final int POST_AUDIO_PEAK_REPEATS; 

    //Add Audio (Soap)
    public static final int ADD_AUDIO_SMOKE_REPEATS;
    public static final int ADD_AUDIO_BASELINE_NORMAL_REPEATS;
    public static final int ADD_AUDIO_PEAK_REPEATS; 

    //Test Durations
    public static final int SMOKE_PACE_DURATION_MINS;
    public static final int BASELINE_NORMAL_PACE_DURATION_MINS;
    public static final int PEAK_PACE_DURATION_MINS;

    //DB Connections
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

        //Users
        JUDGE_RAMP_UP_USERS = Integer.parseInt(getProperty("JUDGE_RAMP_UP_USERS", "1"));
        COURT_CLERK_RAMP_UP_USERS = Integer.parseInt(getProperty("COURT_CLERK_RAMP_UP_USERS", "1"));
        COURT_MANAGER_RAMP_UP_USERS = Integer.parseInt(getProperty("COURT_MANAGER_RAMP_UP_USERS", "1"));
        TRANSCRIBER_RAMP_UP_USERS = Integer.parseInt(getProperty("TRANSCRIBER_RAMP_UP_USERS", "1"));
        LANGUAGE_SHOP_RAMP_UP_USERS = Integer.parseInt(getProperty("LANGUAGE_SHOP_RAMP_UP_USERS", "1"));

        //Users Ramp up Duration
        RAMP_UP_DURATION_OF_JUDGES = Integer.parseInt(getProperty("JUDGE_RAMP_UP_USERS", "1"));
        RAMP_UP_DURATION_OF_COURT_CLERK = Integer.parseInt(getProperty("JUDGE_RAMP_UP_USERS", "1"));
        RAMP_UP_DURATION_OF_COURT_MANAGER = Integer.parseInt(getProperty("JUDGE_RAMP_UP_USERS", "1"));
        RAMP_UP_DURATION_OF_TRANSCRIBER = Integer.parseInt(getProperty("JUDGE_RAMP_UP_USERS", "1"));
        RAMP_UP_DURATION_OF_LANGUAGE_SHOP = Integer.parseInt(getProperty("JUDGE_RAMP_UP_USERS", "1"));

        //Pipeline users
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
        SMOKE_TEST_DURATION_MINUTES = Long.parseLong(getProperty("BASELINE_TEST_DURATION_MINUTES", "30"));
        BASELINE_NORMAL_DURATION_MINUTES = Long.parseLong(getProperty("RAMP_TEST_DURATION_MINUTES", "20"));
        PEAK_TEST_DURATION_MINUTES = Long.parseLong(getProperty("SPIKE_TEST_DURATION_MINUTES", "10"));

        //Soap Requests
        SOAP_BASELINE_REPEATS = Integer.parseInt(getProperty("SOAP_BASELINE_REPEATS", "100"));
        SOAP_RAMPUP_REPEATS = Integer.parseInt(getProperty("SOAP_RAMPUP_REPEATS", "50"));
        SOAP_SPIKE_REPEATS = Integer.parseInt(getProperty("SOAP_SPIKE_REPEATS", "100"));
        
        //Event Requests
        EVENTS_SMOKE_REPEATS = Integer.parseInt(getProperty("EVENTS_SMOKE_REPEATS", "538"));
        EVENTS_BASELINE_NORMAL_REPEATS = Integer.parseInt(getProperty("EVENTS_BASELINE_NORMAL_REPEATS", "2692"));
        EVENTS_PEAK_REPEATS = Integer.parseInt(getProperty("EVENTS_PEAK_REPEATS", "5384"));

        //POST Audio Requests
        POST_AUDIO_REQUEST_SMOKE_REPEATS = Integer.parseInt(getProperty("POST_AUDIO_REQUEST_SMOKE_REPEATS", "9"));
        POST_AUDIO_REQUEST_BASELINE_NORMAL_REPEATS = Integer.parseInt(getProperty("POST_AUDIO_REQUEST_BASELINE_NORMAL_REPEATS", "43"));
        POST_AUDIO_REQUEST_PEAK_REPEATS = Integer.parseInt(getProperty("POST_AUDIO_REQUEST_PEAK_REPEATS", "95"));

        //GET Audio Requests
        GET_AUDIO_REQUEST_SMOKE_REPEATS = Integer.parseInt(getProperty("GET_AUDIO_REQUEST_SMOKE_REPEATS", "9"));
        GET_AUDIO_REQUEST_BASELINE_NORMAL_REPEATS = Integer.parseInt(getProperty("GET_AUDIO_REQUEST_BASELINE_NORMAL_REPEATS", "43"));
        GET_AUDIO_REQUEST_PEAK_REPEATS = Integer.parseInt(getProperty("GET_AUDIO_REQUEST_PEAK_REPEATS", "95"));
                
        //GET Audio Requests
        DELETE_AUDIO_REQUEST_SMOKE_REPEATS = Integer.parseInt(getProperty("DELETE_AUDIO_REQUEST_SMOKE_REPEATS", "9"));
        DELETE_AUDIO_REQUEST_BASELINE_NORMAL_REPEATS = Integer.parseInt(getProperty("DELETE_AUDIO_REQUEST_BASELINE_NORMAL_REPEATS", "43"));
        DELETE_AUDIO_REQUEST_PEAK_REPEATS = Integer.parseInt(getProperty("DELETE_AUDIO_REQUEST_PEAK_REPEATS", "95"));
                
        //Add Cases Requests
        ADD_CASES_SMOKE_REPEATS = Integer.parseInt(getProperty("ADD_CASES_SMOKE_REPEATS", "698"));
        ADD_CASES_BASELINE_NORMAL_REPEATS = Integer.parseInt(getProperty("ADD_CASES_REQUEST_BASELINE_NORMAL_REPEATS", "3490"));
        ADD_CASES_PEAK_REPEATS = Integer.parseInt(getProperty("ADD_CASES_REQUEST_PEAK_REPEATS", "6980"));
        
        //Get Cases Requests
        GET_CASES_SMOKE_REPEATS = Integer.parseInt(getProperty("GET_CASES_SMOKE_REPEATS", "158"));
        GET_CASES_BASELINE_NORMAL_REPEATS = Integer.parseInt(getProperty("GET_CASES_REQUEST_BASELINE_NORMAL_REPEATS", "790"));
        GET_CASES_PEAK_REPEATS = Integer.parseInt(getProperty("GET_CASES_REQUEST_PEAK_REPEATS", "1580"));

        //Add Log Entry Requests
        ADD_LOG_ENTRY_SMOKE_REPEATS = Integer.parseInt(getProperty("ADD_LOG_ENTRY_SMOKE_REPEATS", "2114"));
        ADD_LOG_ENTRY_BASELINE_NORMAL_REPEATS = Integer.parseInt(getProperty("ADD_LOG_ENTRY_BASELINE_NORMAL_REPEATS", "10571"));
        ADD_LOG_ENTRY_PEAK_REPEATS = Integer.parseInt(getProperty("ADD_LOG_ENTRY_PEAK_REPEATS", "21142"));

        //Get Log Entry Requests
        GET_LOG_ENTRY_SMOKE_REPEATS = Integer.parseInt(getProperty("GET_LOG_ENTRY_SMOKE_REPEATS", "2114"));
        GET_LOG_ENTRY_BASELINE_NORMAL_REPEATS = Integer.parseInt(getProperty("GET_LOG_ENTRY_BASELINE_NORMAL_REPEATS", "10571"));
        GET_LOG_ENTRY_PEAK_REPEATS = Integer.parseInt(getProperty("GET_LOG_ENTRY_PEAK_REPEATS", "21142"));


        //POST Audio (Api)
        POST_AUDIO_SMOKE_REPEATS = Integer.parseInt(getProperty("POST_AUDIO_SMOKE_REPEATS", "2114"));
        POST_AUDIO_BASELINE_NORMAL_REPEATS = Integer.parseInt(getProperty("POST_AUDIO_BASELINE_NORMAL_REPEATS", "10571"));
        POST_AUDIO_PEAK_REPEATS = Integer.parseInt(getProperty("POST_AUDIO_PEAK_REPEATS", "21142"));
        
        //Add Audio (Soap)
        ADD_AUDIO_SMOKE_REPEATS = Integer.parseInt(getProperty("ADD_AUDIO_SMOKE_REPEATS", "2776"));
        ADD_AUDIO_BASELINE_NORMAL_REPEATS = Integer.parseInt(getProperty("ADD_AUDIO_BASELINE_NORMAL_REPEATS", "13881"));
        ADD_AUDIO_PEAK_REPEATS = Integer.parseInt(getProperty("ADD_AUDIO_PEAK_REPEATS", "27762"));

        //Test Durations
        SMOKE_PACE_DURATION_MINS = Integer.parseInt(getProperty("SMOKE_PACE_DURATION_MINS", "30"));
        BASELINE_NORMAL_PACE_DURATION_MINS = Integer.parseInt(getProperty("BASELINE_NORMAL_PACE_DURATION_MINS", "20"));
        PEAK_PACE_DURATION_MINS = Integer.parseInt(getProperty("PEAK_PACE_DURATION_MINS", "10"));
     
        //DataBase Connections
        DB_URL = getProperty("DB_URL", "test");
        DB_USERNAME = getProperty("DB_USERNAME", "test");
        DB_PASSWORD = getProperty("DB_PASSWORD", "test");
    
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
        PROXY_BASE_URL(PERFORMANCE_PROXY_BASE_URL),        
        DARTS_BASE_URL(PERFORMANCE_DARTS_API_BASE_URL),        
        DARTS_PORTAL_BASE_URL(PERFORMANCE_DARTS_BASE_URL),
        
        //Tennat details
        DARTS_PORTAL_Auth_LOGIN(PERFORMANCE_DARTS_PORTAL_Auth_LOGIN),
        DARTS_PORTAL_SIGNIN(PERFORMANCE_DARTS_PORTAL_SIGNIN),
        B2B_Login(PERFORMANCE_B2B_Login),
        B2B_Token(PERFORMANCE_B2B_Token),
        SCOPE(PERFORMANCE_SCOPE),
        GRANT_TYPE(PERFORMANCE_GRANT_TYPE),
       
        //External Tennat
        EXTERNAL_CLIENT_ID(PERFORMANCE_EXTERNAL_CLIENT_ID),
        EXTERNAL_CLIENT_SECRET(PERFORMANCE_EXTERNAL_CLIENT_SECRET),  
        EXTERNAL_AZURE_AD_B2C_CLIENT_ID(PERFORMANCE_EXTERNAL_AZURE_AD_B2C_CLIENT_ID),
       
        //Internal Tennat
        INTERNAL_CLIENT_ID(PERFORMANCE_INTERNAL_CLIENT_ID),
        INTERNAL_CLIENT_SECRET(PERFORMANCE_INTERNAL_CLIENT_SECRET),  
        INTERNAL_AZURE_AD_B2C_CLIENT_ID(PERFORMANCE_INTERNAL_TENANT_ID),


        //Users
        DARTS_API_USERNAME("test"),
        DARTS_API_GLOBAL_USERNAME("test"),
        DARTS_API_PASSWORD("test"),
        DARTS_SOAP_USERNAME("test"),
        DARTS_SOAP_PASSWORD("test"),
        DARTS_API_USERNAME2("test"),
        DARTS_API_PASSWORD2("test"),
        DARTS_SOAP_EXTERNAL_USERNAME("test"),
        DARTS_SOAP_EXTERNAL_PASSWORD("test");

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

    // Get the base directory of the project
    public static final String BASE_DIR = System.getProperty("user.dir");

    // Common part of the CSV file paths
    public static final String CSV_FILE_COMMON_PATH = "src/gatling/resources/";

    // Specific CSV file names
    public static final String COURT_HOUSE_AND_COURT_ROOMS_CSV = "GetAllCourtroomsAndCourthouses.csv";
    public static final String TRANSCRIPTION_POST_CSV = "Transcription_Post.csv";
    public static final String AUDIO_REQUEST_POST_CSV = "Audio_Request_Post.csv";
    public static final String CASE_HOUSE_ROOMS_HEARING_FILE_PATH_CSV = "Case_House_Room_HearingDetails.csv";
    public static final String TRANSFORMED_MEDIA_DOWNLOAD_IDS_FILE_PATH_CSV = "Transformed_Media_Download.csv";
    public static final String TRANSFORMED_MEDIA_PLAYBACK_IDS_FILE_PATH_CSV = "Transformed_Media_Playback.csv";

    // Specific CSV file for Users
    public static final String DARTS_PORTAL_USERS_CSV = "Users.csv";
    public static final String DARTS_PORTAL_COURTCLERK_USERS_CSV = "UsersCourtClerks.csv";
    public static final String DARTS_PORTAL_COURTMANAGER_USERS_CSV = "UsersCourtManager.csv";
    public static final String DARTS_PORTAL_TRANSCRIBERS_USERS_CSV = "UsersTranscribers.csv";
    public static final String DARTS_PORTAL_LANGUAGESHOP_USERS_CSV = "UsersLanguageShop.csv";
    public static final String DARTS_PORTAL_JUDGE_USERS_CSV = "UsersJudge.csv";

    // Specific CSV file for Judges
    public static final String DARTS_PORTAL_JUDGES_CSV = "JudgeName2.csv";

    // Full CSV file paths
    public static final String COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, COURT_HOUSE_AND_COURT_ROOMS_CSV).toString();
    public static final String TRANSCRIPTION_POST_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, TRANSCRIPTION_POST_CSV).toString();
    public static final String AUDIO_REQUEST_POST_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, AUDIO_REQUEST_POST_CSV).toString();
    public static final String DARTS_PORTAL_USERS1_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, DARTS_PORTAL_USERS_CSV).toString();
    public static final String DARTS_PORTAL_COURTCLERK_USERS_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, DARTS_PORTAL_COURTCLERK_USERS_CSV).toString();
    public static final String DARTS_PORTAL_COURTMANAGER_USERS_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, DARTS_PORTAL_COURTMANAGER_USERS_CSV).toString();
    public static final String DARTS_PORTAL_TRANSCRIBERS_USERS_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, DARTS_PORTAL_TRANSCRIBERS_USERS_CSV).toString();
    public static final String DARTS_PORTAL_LANGUAGESHOP_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, DARTS_PORTAL_LANGUAGESHOP_USERS_CSV).toString();
    public static final String DARTS_PORTAL_JUDGE_USERS_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, DARTS_PORTAL_JUDGE_USERS_CSV).toString();
    
    public static final String DARTS_PORTAL_JUDGES_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, DARTS_PORTAL_JUDGES_CSV).toString();
    public static final String CASE_HOUSE_ROOMS_HEARING_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, CASE_HOUSE_ROOMS_HEARING_FILE_PATH_CSV).toString();
    public static final String TRANSFORMED_MEDIA_DOWNLOAD_IDS_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, TRANSFORMED_MEDIA_DOWNLOAD_IDS_FILE_PATH_CSV).toString();
    public static final String TRANSFORMED_MEDIA_PLAYBACK_IDS_FILE_PATH = Paths.get(BASE_DIR, CSV_FILE_COMMON_PATH, TRANSFORMED_MEDIA_PLAYBACK_IDS_FILE_PATH_CSV).toString();


        // List of Document files
        public static final String[] DOCUMENT_FILES = {"SampleDoc2.docx"};

        // Method to select a random audio file
        public static String getRandomDocumentFile() {
            Random random = new Random();
            int index = random.nextInt(DOCUMENT_FILES.length);
            return DOCUMENT_FILES[index];
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
            addValueToBuilder(builder, "Test Duration Minutes", String.valueOf(SMOKE_TEST_DURATION_MINUTES));
            addValueToBuilder(builder, "Test Duration Minutes", String.valueOf(BASELINE_NORMAL_DURATION_MINUTES));
            addValueToBuilder(builder, "Test Duration Minutes", String.valueOf(PEAK_TEST_DURATION_MINUTES));

            //Soap Requests
            addValueToBuilder(builder, "Number of repeats for the baseline Soap Request", String.valueOf(SOAP_BASELINE_REPEATS));
            addValueToBuilder(builder, "Number of repeats for the ramp up Soap Request", String.valueOf(SOAP_RAMPUP_REPEATS));
            addValueToBuilder(builder, "Number of repeats for the spike Soap Request", String.valueOf(SOAP_SPIKE_REPEATS));

            addValueToBuilder(builder, "The duration in minutes of pacing for the repeats within the smoke test", String.valueOf(SMOKE_PACE_DURATION_MINS));
            addValueToBuilder(builder, "The duration in minutes of pacing for the repeats within the baseline standard test", String.valueOf(BASELINE_NORMAL_PACE_DURATION_MINS));
            addValueToBuilder(builder, "The duration in minutes of pacing for the repeats within the peak test", String.valueOf(PEAK_PACE_DURATION_MINS));       

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
