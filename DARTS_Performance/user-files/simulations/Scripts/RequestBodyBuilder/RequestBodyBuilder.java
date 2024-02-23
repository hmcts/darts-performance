package RequestBodyBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import Utilities.AppConfig;
import Utilities.NumberGenerator;
import Utilities.RandomStringGenerator;
import io.gatling.javaapi.core.Session;

public class RequestBodyBuilder {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final String[] REQUEST_TYPES = {"DOWNLOAD", "PLAYBACK"};
    public static final NumberGenerator generatorCourtHouseCode = new NumberGenerator(100);

    // Define the percentages for each request type (must sum up to 100)
    private static final int DOWNLOAD_PERCENTAGE = 70; //% chance
    private static final int PLAYBACK_PERCENTAGE = 30; //% chance

    public static String buildAudioRequestBody(String hearingId, String userId, LocalDateTime startTime, LocalDateTime endTime) {
        String startTimeFormatted = formatTime(startTime);
        String endTimeFormatted = formatTime(endTime);
        String requestType = getRandomRequestType();

        return String.format("{\"hearing_id\": \"%s\", " +
                "\"requestor\": \"%s\", " +
                "\"start_time\": \"%s\", " +
                "\"end_time\": \"%s\", " +
                "\"request_type\": \"%s\"}",
                hearingId, userId, startTimeFormatted, endTimeFormatted, requestType);
    }

    private static String formatTime(LocalDateTime time) {
        return time.format(formatter);
    }

    private static String getRandomRequestType() {
        int totalPercentage = DOWNLOAD_PERCENTAGE + PLAYBACK_PERCENTAGE;
        int randomNumber = ThreadLocalRandom.current().nextInt(totalPercentage) + 1; // Generate random number between 1 and the total percentage

        if (randomNumber <= DOWNLOAD_PERCENTAGE) {
            return REQUEST_TYPES[0]; // DOWNLOAD
        } else {
            return REQUEST_TYPES[1]; // PLAYBACK
        }
    }

    public static String buildTranscriptionRequestBody(String hearingId, String caseId, String transcriptionUrgencyId, String transcriptionTypeId, String comment) {
        return String.format("{\"hearing_id\": \"%s\", " +
                "\"case_id\": \"%s\", " +
                "\"transcription_urgency_id\": \"%s\", " +
                "\"transcription_type_id\": \"%s\", " +
                "\"comment\": \"%s\"}",
                hearingId, caseId, transcriptionUrgencyId, transcriptionTypeId, comment);
    }
    public static String buildPostAudioApiRequest(Session session) {
        // Retrieve values from session or define defaults if needed
        String courtHouseName = session.get("CourtHouseName").toString();    
        String courtRoom = session.get("CourtRoom").toString(); 
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String caseName = randomStringGenerator.generateRandomString(10);

        return String.format(
        "{\"started_at\": \"1972-11-25T17:28:59.936Z\", \"ended_at\": \"1972-11-25T18:28:59.936Z\", \"channel\": 1, \"total_channels\": 4, \"format\": \"mp2\", \"filename\": \"sample.mp2\", \"courthouse\": \"%s\", \"courtroom\": \"%s\", \"file_size\": 937.96, \"checksum\": \"TVRMwq16b4mcZwPSlZj/iQ==\", \"cases\": [\"PerfCase_%s\"] }",
        courtHouseName, courtRoom, caseName);
    }


    public static String buildDartsPortalPerftraceRequest(Session session) {
        return String.format(
            "{" +
            "   \"navigation\": {" +
            "       \"type\": 0," +
            "       \"redirectCount\": 0" +
            "   }," +
            "   \"timing\": {" +
            "       \"connectStart\": %d," +
            "       \"navigationStart\": %d," +
            "       \"secureConnectionStart\": %d," +
            "       \"fetchStart\": %d," +
            "       \"domContentLoadedEventStart\": %d," +
            "       \"responseStart\": %d," +
            "       \"domInteractive\": %d," +
            "       \"domainLookupEnd\": %d," +
            "       \"responseEnd\": %d," +
            "       \"redirectStart\": %d," +
            "       \"requestStart\": %d," +
            "       \"unloadEventEnd\": %d," +
            "       \"unloadEventStart\": %d," +
            "       \"domLoading\": %d," +
            "       \"domComplete\": %d," +
            "       \"domainLookupStart\": %d," +
            "       \"loadEventStart\": %d," +
            "       \"domContentLoadedEventEnd\": %d," +
            "       \"loadEventEnd\": %d," +
            "       \"redirectEnd\": %d," +
            "       \"connectEnd\": %d" +
            "   }," +
            "   \"entries\": [" +
            "       {" +
            "           \"name\": \"" + AppConfig.EnvironmentURL.B2B_Login.getUrl() + "/AppConfig.EnvironmentURL.DARTS_PORTAL_Auth_LOGIN.getUrl()?client_id=" + AppConfig.EnvironmentURL.AZURE_AD_B2C_CLIENT_ID.getUrl() + "&redirect_uri=https%3A%2F%2Fdarts.staging.apps.hmcts.net%2Fauth%2Fcallback&scope=openid&prompt=login&response_mode=form_post&response_type=code\"," +
            "           \"entryType\": \"navigation\"," +
            "           \"startTime\": 0," +
            "           \"duration\": 2919.9000000953674," +
            "           \"initiatorType\": \"navigation\"," +
            "           \"deliveryType\": \"\"," +
            "           \"nextHopProtocol\": \"http/1.1\"," +
            "           \"renderBlockingStatus\": \"non-blocking\"," +
            "           \"workerStart\": 0," +
            "           \"redirectStart\": 0," +
            "           \"redirectEnd\": 0," +
            "           \"fetchStart\": 670.7000000476837," +
            "           \"domainLookupStart\": 670.7000000476837," +
            "           \"domainLookupEnd\": 670.7000000476837," +
            "           \"connectStart\": 672.2000000476837," +
            "           \"secureConnectionStart\": 843.5," +
            "           \"connectEnd\": 1729.7000000476837," +
            "           \"requestStart\": 1729.7000000476837," +
            "           \"responseStart\": 2786.7999999523163," +
            "           \"firstInterimResponseStart\": 0," +
            "           \"responseEnd\": 2788.7000000476837," +
            "           \"transferSize\": 69668," +
            "           \"encodedBodySize\": 69368," +
            "           \"decodedBodySize\": 176293," +
            "           \"responseStatus\": 200," +
            "           \"serverTiming\": []," +
            "           \"unloadEventStart\": 0," +
            "           \"unloadEventEnd\": 0," +
            "           \"domInteractive\": 2879.4000000953674," +
            "           \"domContentLoadedEventStart\": 2879.4000000953674," +
            "           \"domContentLoadedEventEnd\": 2879.7000000476837," +
            "           \"domComplete\": 2919.7999999523163," +
            "           \"loadEventStart\": 2919.9000000953674," +
            "           \"loadEventEnd\": 2919.9000000953674," +
            "           \"type\": \"navigate\"," +
            "           \"redirectCount\": 0," +
            "           \"activationStart\": 0," +
            "           \"criticalCHRestart\": 0" +
            "       }," +
            "       {" +
            "           \"name\": \"visible\"," +
            "           \"entryType\": \"visibility-state\"," +
            "           \"startTime\": 0," +
            "           \"duration\": 0" +
            "       }," +
            "       {" +
            "           \"name\": \"" + AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/azuread-b2c-login?screenName=loginScreen&ui_locales=en\"," +
            "           \"entryType\": \"resource\"," +
            "           \"startTime\": 2869.7999999523163," +
            "           \"duration\": 265," +
            "           \"initiatorType\": \"xmlhttprequest\"," +
            "           \"deliveryType\": \"\"," +
            "           \"nextHopProtocol\": \"http/1.1\"," +
            "           \"renderBlockingStatus\": \"non-blocking\"," +
            "           \"workerStart\": 0," +
            "           \"redirectStart\": 0," +
            "           \"redirectEnd\": 0," +
            "           \"fetchStart\": 2869.7999999523163," +
            "           \"domainLookupStart\": 2869.7999999523163," +
            "           \"domainLookupEnd\": 2869.7999999523163," +
            "           \"connectStart\": 2871.4000000953674," +
            "           \"secureConnectionStart\": 2893," +
            "           \"connectEnd\": 2983," +
            "           \"requestStart\": 2983.0999999046326," +
            "           \"responseStart\": 3134.5," +
            "           \"firstInterimResponseStart\": 0," +
            "           \"responseEnd\": 3134.7999999523163," +
            "           \"transferSize\": 5875," +
            "           \"encodedBodySize\": 5575," +
            "           \"decodedBodySize\": 5575," +
            "           \"responseStatus\": 200," +
            "           \"serverTiming\": [" +
            "               {\"name\": \"dtSInfo\",\"duration\": 0,\"description\": \"0\"}," +
            "               {\"name\": \"dtRpid\",\"duration\": 0,\"description\": \"454511542\"}," +
            "               {\"name\": \"dtTao\",\"duration\": 0,\"description\": \"1\"}" +
            "           ]" +
            "       }," +
            "   ]" +
            "}"); 
    }

    public static String buildCourtHousePostBody(Session session) {
        
        // Generate a random court house name
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String courtHouseName = randomStringGenerator.generateRandomString(10);

        return String.format("{\"courthouse_name\": \"PerfCourtHouse_%s\", " +
        "\"code\": \" "+ generatorCourtHouseCode.generateNextNumber() + "\", " +
        "\"display_name\": \"PerfCourtHouse_%s\", " +                
        "\"region_id\": \"0\"}",
        courtHouseName, courtHouseName);
    }
}