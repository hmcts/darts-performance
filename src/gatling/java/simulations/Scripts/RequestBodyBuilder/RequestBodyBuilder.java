package simulations.Scripts.RequestBodyBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;

import java.time.LocalDate;

//import uk.gov.hmcts.juror.support.generation.generators.value.LocalTimeGeneratorImpl;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.NumberGenerator;
import simulations.Scripts.Utilities.RandomDateGenerator;
import simulations.Scripts.Utilities.RandomStringGenerator;

import io.gatling.javaapi.core.Session;
import scala.util.Random;

public class RequestBodyBuilder {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final String[] REQUEST_TYPES = {"DOWNLOAD", "PLAYBACK"};
    public static final NumberGenerator generatorCourtHouseCode = new NumberGenerator(15);

    // Define the percentages for each request type (must sum up to 100)
    private static final int DOWNLOAD_PERCENTAGE = 70; //% chance
    private static final int PLAYBACK_PERCENTAGE = 30; //% chance

    public static String buildPOSTAudioRequestBody(Session session) {

        String hearingId = session.get("hea_id") != null ? session.get("hea_id").toString() : "";
        String userId = session.get("usr_id") != null ? session.get("usr_id").toString() : "";
        String startTime = session.get("start_ts") != null ? session.get("start_ts").toString() : "";
        String endTime = session.get("end_ts") != null ? session.get("end_ts").toString() : "";

        String requestType = getRandomRequestType();

        return String.format("{\"hearing_id\": \"%s\", " 
                            + "\"requestor\": \"%s\", " 
                            + "\"start_time\": \"%s\", " 
                            + "\"end_time\": \"%s\"}",
                hearingId, userId, startTime, endTime);
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

    public static String buildTranscriptionRequestBody(Session session) {
        String hearingId = session.get("hea_id") != null ? "" + session.get("hea_id").toString() + "" : "null";
        String caseId = session.get("cas_id") != null ? "" + session.get("cas_id").toString() + "" : "null";
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String randomComment = randomStringGenerator.generateRandomString(10);

        return String.format("{\"hearing_id\": \"%s\", " 
              +  "\"case_id\": \"%s\", " 
              +  "\"transcription_urgency_id\": \"3\", " 
              +  "\"transcription_type_id\": \"999\", " 
              +  "\"comment\": \"Perf_%s\"}",
                hearingId, caseId, randomComment);
    }

    public static String buildTranscriptionPatchAcceptRequestBody(Session session) {
        String transcriptionId = session.get("tra_id") != null ? "" + session.get("tra_id").toString() + "" : "null";
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String randomComment = randomStringGenerator.generateRandomString(10);

        return String.format("{\"transcription_status_id\": \"3\", " +                
                "\"workflow_comment\": \"Moved to Accepted_%s\"}",
                transcriptionId, randomComment);
    }
    public static String buildGetCredentialType(Session session) {
    
        String originalRequest = "rQQIARAA02I20jOwUjFLsTQ2SDFJ1E1LTUzWNTFMTtW1SDGw0E00TbJMMzNOs7S0MCgS4hJ416We_lKtyH32q6pu_S-3Tq1itMgoKSkottLXT0ksKinWK0ktLtFLLCgo1svITQby81JL9BNLSzL0M_NKUovyEnP0kxNzcpISk7N3MDJeYGR8wch4i4nf3xGoxAhE5BdlVqW-YmL4xMSZll-UG1-QX1yyiVkl1dTcNM3MzFg3ydggUdfE3MJM18IyMUXX2NDSwsQoJS3VwtT4FDNbfkFqXmbKBRbGVyw8BsxWHBxcAgwSDAoMP1gYF7ECnc91LZXBes4Cp7X_GOPsdBQZTrHqF6cbFISl-zs7JZqZmfkUeziahmSVVkZWZqfnROaEBOdn-haZeRVoJ0Y6utoaWRlOYGP8wMbYwc6wi5Nsnx_gZfjBt-rzqSmfWla883jFr5MRVRFpZublnellVulb4eUSGlngnBQUaVIQFhTsblmYHOGtHRJRVpkaYWlhu0GA4YEAAwA1";
        String flowToken = session.get("flowToken") != null ? "" + session.get("flowToken").toString() + "" : "null";
        String userName = session.get("Email") != null ? "" + session.get("Email").toString() + "" : "null";
        
        return String.format("{\"username\":\"%s\"," 
        + "\"isOtherIdpSupported\":false," 
        + "\"checkPhones\":false," 
        +"\"isRemoteNGCSupported\":true," 
        +"\"isCookieBannerShown\":false," 
        +"\"isFidoSupported\":true," 
        +"\"originalRequest\":\"%s\"," 
        +"\"country\":\"GB\"," 
        +"\"forceotclogin\":false," 
        +"\"isExternalFederationDisallowed\":false," 
        +"\"isRemoteConnectSupported\":false," 
        +"\"federationFlags\":0," 
        +"\"isSignup\":false," 
        +"\"flowToken\":\"%s\"," 
        +"\"isAccessPassSupported\":true,"
        +"\"isQrCodePinSupported\":true}",
        userName, originalRequest, flowToken);
    }


    public static String buildSearchCaseRequestBody(Session session) {
        String caseNumber = Optional.ofNullable(session.get("caseNumber"))
                    .map(value -> "\"" + value.toString() + "\"")
                    .orElse("null");
        String courtHouseName = Optional.ofNullable(session.get("courthouse_name"))
                    .map(value -> "\"" + value.toString() + "\"")
                    .orElse("null");
        String courtHouseId = Optional.ofNullable(session.get("cth_id"))
                    .map(value -> "\"" + value.toString() + "\"")
                    .orElse("null");
        String courtRoom = Optional.ofNullable(session.get("CourtRoom"))
                    .map(value -> "\"" + value.toString() + "\"")
                    .orElse("null");
        String defendantName = Optional.ofNullable(session.get("defendantFirstName"))
                    .map(value -> "\"" + value.toString() + "\"")
                    .orElse("null");
    
        // Extract fromDate from session
        String fromDateStr = session.getString("getfromDate");
    
        // Ensure fromDate is not null and parse it
        LocalDate fromDate = Optional.ofNullable(fromDateStr)
                .map(LocalDate::parse) // Convert String to LocalDate
                .orElse(LocalDate.now()); // Default to today if null
    
        // Add 14 days to fromDate
        LocalDate formattedDateTo = fromDate.plusWeeks(24);
        // LocalDate formattedDateTo = fromDate.plusDays(1);

        // Format both dates as strings for JSON
        String formattedFromDateStr = "\"" + fromDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "\"";
        String formattedDateToStr = "\"" + formattedDateTo.format(DateTimeFormatter.ISO_LOCAL_DATE) + "\"";
    
    
 //       System.out.println("Date at " + courtHouseName + ": " + formattedFromDateStr + " for user: " + email + " test " + defendantName);
 //       System.out.println("From Date: " + formattedFromDateStr);
 //       System.out.println("To Date: " + formattedDateToStr);
    
        String eventTextContains = Optional.ofNullable(session.get("EventTextContains"))
                    .map(value -> "\"" + value.toString() + "\"")
                    .orElse("null");

        // Get the user type from the session
        String userType = session.get("Type").toString();
        // System.out.println("userType for Audio Request: " + userType);
        
        // Determine request type based on user type
        if (userType.equalsIgnoreCase("LanguageShop")) {
            // If the user type is LanguageShop, build the JSON payload
            return String.format("{\"case_number\":%s," 
                            + "\"courthouse_ids\":[%s]," 
                            + "\"courtroom\":%s," 
                            + "\"judge_name\":null," 
                            + "\"defendant_name\":null," 
                            + "\"event_text_contains\":null," 
                            + "\"date_from\":\"1970-04-01\"," 
                            + "\"date_to\":\"2025-04-07\"}",
                    caseNumber, courtHouseId, courtRoom);
        } else {
            // If not LanguageShop, build the JSON payload
            return String.format("{\"case_number\":%s," 
                            + "\"courthouse_ids\":[%s]," 
                            + "\"courtroom\":%s," 
                            + "\"judge_name\":null," 
                            + "\"defendant_name\":null," 
                            + "\"event_text_contains\":%s," 
                            + "\"date_from\":\"1970-04-01\"," 
                            + "\"date_to\":\"2025-04-07\"}",
                    caseNumber, courtHouseId, courtRoom, courtHouseName);
        }
    }

    

    public static String buildChangeRetentionsBody(Session session) {
        String caseId = session.get("getCaseId") != null ? "\"" + session.get("getCaseId").toString() + "\"" : "null";
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String randomComment = randomStringGenerator.generateRandomString(10);        
    
        return String.format("{\"case_id\":%s," 
        +"\"is_permanent_retention\":true,"
        +"\"comments\":\"Perf_Comment_%s\"}",
        caseId, randomComment);
    }
    public static String buildTranscriptionsBody(Session session) {
        String hearingId = session.get("getHearingId") != null ? session.get("getHearingId").toString() : "null";       
        String caseId = session.get("getCaseId") != null ? session.get("getCaseId").toString() : "null";       
        String transcriptionTypeId = session.get("transcriptionTypeId") != null ? session.get("transcriptionTypeId").toString() : "null";
        String getHearingdate = session.get("getHearingdate") != null ? session.get("getHearingdate").toString() : "null";

        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String randomComment = randomStringGenerator.generateRandomString(10);

      LocalDate hearingDate = LocalDate.parse(getHearingdate);

        /* ── 2. generate a random time of day (0‑23h, 0‑59m, 0‑59s) ───────── */
        ThreadLocalRandom r = ThreadLocalRandom.current();
        int h = r.nextInt(0, 24);
        int m = r.nextInt(0, 60);
        int s = r.nextInt(0, 60);
        LocalTime randomTime = LocalTime.of(h, m, s);   

        /* ── 3. fuse into an OffsetDateTime in UTC (Z) ─────────────────────── */
        OffsetDateTime startUtc = OffsetDateTime.of(hearingDate, randomTime, ZoneOffset.UTC);

        /* ── 4. add +1 hour to get the end time ───────────────────────────── */
        OffsetDateTime endUtc   = startUtc.plusHours(1);

        /* ── 5. format both as ISO‑8601 with trailing Z ───────────────────── */
        DateTimeFormatter isoZ = DateTimeFormatter.ISO_INSTANT; // 2025‑01‑12T09:09:09Z
        String startTime = isoZ.format(startUtc);
        String endTime   = isoZ.format(endUtc);
    
        return String.format("{\"case_id\":\"%s\", " 
            + "\"hearing_id\":\"%s\", " 
            + "\"transcription_type_id\":%s, " 
            + "\"transcription_urgency_id\":4, " 
            + "\"comment\":\"%s\", " 
            + "\"start_date_time\":\"%s\","
            + "\"end_date_time\":\"%s\""
            + "}",
            caseId, hearingId, transcriptionTypeId, randomComment, startTime, endTime);
    }

    public static String buildAudioRequestBody(Session session, Object getHearingId, Object requestor, Object audioStartDate, Object audioEndDate, Object requestType) {
        return String.format("{\"hearing_id\": %s, " 
        +"\"requestor\": %s, " 
        +"\"start_time\": \"%s\", " 
        +"\"end_time\": \"%s\"}",
        getHearingId, requestor, audioStartDate, audioEndDate);
    }



    public static String buildTranscriptionApprovalRequestBody(Session session) {

        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String randomComment = randomStringGenerator.generateRandomString(10);
    
        // Define variables for Approve
        String approve = "{\"transcription_status_id\": \"3\"}";
    
        // Define variables for Reject
        String reject = String.format("{\"transcription_status_id\": \"4\", " 
                +"\"workflow_comment\": \"%s\"}", randomComment);
    
        // Create a random number generator
        Random random = new Random();
    
        // Generate a random number between 0 and 1
        // If the generated number is less than 0.5, select Approve, otherwise select Reject
        String selectedAction;
        if (random.nextDouble() < 0.5) {
            selectedAction = approve;
        } else {
            selectedAction = reject;
        }
    
        // Return the selected string
        return selectedAction;
    }
    public static String buildPostAudioApiRequest(Session session, String randomAudioFile) {
        // Retrieve values from session or define defaults if needed
        String courtHouseName = session.get("courthouse_display_name") != null ? session.get("courthouse_display_name").toString() : "";
        String courtRoomName = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";
        String courtCaseNumber = session.get("case_number") != null ? session.get("case_number").toString() : "";
        String hearingDate = session.get("hearing_date") != null ? session.get("hearing_date").toString() : "";

        // RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        // String caseName1 = randomStringGenerator.generateRandomString(10);
        // String caseName2 = randomStringGenerator.generateRandomString(10);
        // String caseName3 = randomStringGenerator.generateRandomString(10);

        return String.format(
        "{\"started_at\": \"%sT17:28:59.936Z\", " 
        + " \"ended_at\": \"%sT18:28:59.936Z\", " 
        +" \"channel\": 1,  " 
        +" \"total_channels\": 4,  " 
        +" \"format\": \"mp2\",  " 
        +" \"filename\": \"%s\",  " 
        +" \"courthouse\": \"%s\",  " 
        +" \"courtroom\": \"%s\",  " 
        +" \"file_size\": 64003968,  " 
        +" \"checksum\": \"TVRMwq16b4mcZwPSlZj/iQ==\",  " 
        +" \"cases\": [\"%s\"] }",
        hearingDate, hearingDate, randomAudioFile, courtHouseName, courtRoomName, courtCaseNumber);
    }

    public static String buildPostAudioLinkingForCaseApiRequest(Session session, String randomAudioFile, String caseName) {
        // Retrieve values from session or define defaults if needed
        String courtHouseName = session.get("courthouse_name") != null ? session.get("courthouse_name").toString() : "";
        String courtRoom = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";  
         



        return String.format(
        "{\"started_at\": \"2024-11-11T12:02:00.000Z\", " 
        +" \"ended_at\": \"2024-11-11T13:02:00.000Z\", " 
        +" \"channel\": 1,  " 
        +" \"total_channels\": 4,  " 
        +" \"format\": \"mp2\",  " 
        +" \"filename\": \"%s\",  " 
        +" \"courthouse\": \"%s\",  " 
        +" \"courtroom\": \"%s\",  " 
        +" \"file_size\": 937.96,  " 
        +" \"checksum\": \"TVRMwq16b4mcZwPSlZj/iQ==\",  " 
        +" \"cases\": [\"PerfCase_%s\"] }",
        randomAudioFile, courtHouseName, courtRoom, caseName);
    }

    public static String buildPostCaseSearchApiRequest(Session session) {
        // Retrieve values from session or define defaults if needed
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String caseName = randomStringGenerator.generateRandomString(10);

        return String.format(
        "{\"case_number\": \"PerfCase_\"%s\"}",
        caseName);
    }

    public static String buildPostCaseApiRequest(Session session) {
        // Retrieve values from session or define defaults if needed
        String courtHouseName = session.get("courthouse_name") != null ? session.get("courthouse_name").toString() : "";
    
        // Create a single instance of RandomStringGenerator
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
    
        // Generate random strings for each field
        String caseName = randomStringGenerator.generateRandomString(10);
        String defendantsName = randomStringGenerator.generateRandomString(10);
        String judgeName = randomStringGenerator.generateRandomString(10);
        String prosecutorsName = randomStringGenerator.generateRandomString(10);
        String defendersName = randomStringGenerator.generateRandomString(10);
    
        // Return formatted JSON string
        return String.format(
            "{" 
                +"\"courthouse\": \"%s\", " 
                +"\"case_number\": \"PerfCase_%s\", " 
                +"\"case_type\": \"1\", " 
                +"\"defendants\": [ " 
                +  "\"PerfDefendant_%s\" " 
                +"], " 
                +"\"judges\": [ " 
                +  "\"PerfJudge_%s\" " 
                +"], " 
                +"\"prosecutors\": [ " 
                +  "\"PerfProsecutors_%s\" " 
                +"], "  
                +"\"defenders\": [ " 
                +  "\"PerfDefendersName_%s\" " 
                +"] " 
              +"}",
              courtHouseName, caseName, defendantsName, judgeName, prosecutorsName, defendersName);
    }

    public static String buildPostCaseForLinkingAudioApiRequest(Session session, String caseName) {
        // Retrieve values from session or define defaults if needed
        String courtHouseName = session.get("courthouse_name") != null ? session.get("courthouse_name").toString() : "";
    
        // Create a single instance of RandomStringGenerator
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
    
        // Generate random strings for each field
        String defendantsName = randomStringGenerator.generateRandomString(10);
        String judgeName = randomStringGenerator.generateRandomString(10);
        String prosecutorsName = randomStringGenerator.generateRandomString(10);
        String defendersName = randomStringGenerator.generateRandomString(10);
    
        // Return formatted JSON string
        return String.format(
            "{" 
                +"\"courthouse\": \"%s\", " 
                +"\"case_number\": \"PerfCase_%s\", " 
                +"\"case_type\": \"1\", " 
                +"\"defendants\": [ " 
                +  "\"PerfDefendant_%s\" " 
                +"], " 
                +"\"judges\": [ " 
                +  "\"PerfJudge_%s\" " 
                +"], " 
                +"\"prosecutors\": [ " 
                +  "\"PerfProsecutors_%s\" " 
                +"], " 
                +"\"defenders\": [ " 
                +  "\"PerfDefendersName_%s\" " 
                +"] " 
              +"}",
              courtHouseName, caseName, defendantsName, judgeName, prosecutorsName, defendersName);
    }

    public static String buildDartsPortalPerftraceRequest(Session session) {
        return String.format(
        "{" +
        "  \"navigation\": {" +
        "    \"type\": 0," +
        "    \"redirectCount\": 0" +
        "  }," +
        "  \"timing\": {" +
        "    \"connectStart\": 1709047511193," +
        "    \"navigationStart\": 1709047510670," +
        "    \"secureConnectionStart\": 1709047511228," +
        "    \"fetchStart\": 1709047511190," +
        "    \"domContentLoadedEventStart\": 1709047511811," +
        "    \"responseStart\": 1709047511729," +
        "    \"domInteractive\": 1709047511811," +
        "    \"domainLookupEnd\": 1709047511190," +
        "    \"responseEnd\": 1709047511731," +
        "    \"redirectStart\": 0," +
        "    \"requestStart\": 1709047511489," +
        "    \"unloadEventEnd\": 0," +
        "    \"unloadEventStart\": 0," +
        "    \"domLoading\": 1709047511757," +
        "    \"domComplete\": 1709047511842," +
        "    \"domainLookupStart\": 1709047511190," +
        "    \"loadEventStart\": 1709047511842," +
        "    \"domContentLoadedEventEnd\": 1709047511811," +
        "    \"loadEventEnd\": 1709047511842," +
        "    \"redirectEnd\": 0," +
        "    \"connectEnd\": 1709047511489" +
        "  }," +
        "  \"entries\": [" +
        "    {" +
        "      \"name\": \"https://hmctsstgextid.b2clogin.com/hmctsstgextid.onmicrosoft.com/B2C_1_darts_externaluser_signin/oauth2/v2.0/authorize?client_id=363c11cb-48b9-44bf-9d06-9a3973f6f413&redirect_uri=https%3A%2F%2Fdarts.test.apps.hmcts.net%2Fauth%2Fcallback&scope=openid&prompt=login&response_mode=form_post&response_type=code\"," +
        "      \"entryType\": \"navigation\"," +
        "      \"startTime\": 0," +
        "      \"duration\": 1171.7000000029802," +
        "      \"initiatorType\": \"navigation\"," +
        "      \"deliveryType\": \"\"," +
        "      \"nextHopProtocol\": \"http/1.1\"," +
        "      \"renderBlockingStatus\": \"non-blocking\"," +
        "      \"workerStart\": 0," +
        "      \"redirectStart\": 0," +
        "      \"redirectEnd\": 0," +
        "      \"fetchStart\": 520," +
        "      \"domainLookupStart\": 520," +
        "      \"domainLookupEnd\": 520," +
        "      \"connectStart\": 522.2000000029802," +
        "      \"secureConnectionStart\": 557.7999999970198," +
        "      \"connectEnd\": 818.2000000029802," +
        "      \"requestStart\": 818.2999999970198," +
        "      \"responseStart\": 1059," +
        "      \"firstInterimResponseStart\": 0," +
        "      \"responseEnd\": 1060.7999999970198," +
        "      \"transferSize\": 69665," +
        "      \"encodedBodySize\": 69365," +
        "      \"decodedBodySize\": 176293," +
        "      \"responseStatus\": 200," +
        "      \"serverTiming\": []," +
        "      \"unloadEventStart\": 0," +
        "      \"unloadEventEnd\": 0," +
        "      \"domInteractive\": 1140.4000000059605," +
        "      \"domContentLoadedEventStart\": 1140.4000000059605," +
        "      \"domContentLoadedEventEnd\": 1140.7999999970198," +
        "      \"domComplete\": 1171.5999999940395," +
        "      \"loadEventStart\": 1171.7000000029802," +
        "      \"loadEventEnd\": 1171.7000000029802," +
        "      \"type\": \"navigate\"," +
        "      \"redirectCount\": 0," +
        "      \"activationStart\": 0," +
        "      \"criticalCHRestart\": 0" +
        "    }," +
        "    {" +
        "      \"name\": \"visible\"," +
        "      \"entryType\": \"visibility-state\"," +
        "      \"startTime\": 0," +
        "      \"duration\": 0" +
        "    }," +
        "    {" +
        "      \"name\": \"https://darts.test.apps.hmcts.net/auth/azuread-b2c-login?screenName=loginScreen&ui_locales=en\"," +
        "      \"entryType\": \"resource\"," +
        "      \"startTime\": 1132," +
        "      \"duration\": 411.90000000596046," +
        "      \"initiatorType\": \"xmlhttprequest\"," +
        "      \"deliveryType\": \"\"," +
        "      \"nextHopProtocol\": \"http/1.1\"," +
        "      \"renderBlockingStatus\": \"non-blocking\"," +
        "      \"workerStart\": 0," +
        "      \"redirectStart\": 0," +
        "      \"redirectEnd\": 0," +
        "      \"fetchStart\": 1132," +
        "      \"domainLookupStart\": 1132," +
        "      \"domainLookupEnd\": 1132," +
        "      \"connectStart\": 1133.2999999970198," +
        "      \"secureConnectionStart\": 1159.4000000059605," +
        "      \"connectEnd\": 1299.2000000029802," +
        "      \"requestStart\": 1299.2999999970198," +
        "      \"responseStart\": 1543.4000000059605," +
        "      \"firstInterimResponseStart\": 0," +
        "      \"responseEnd\": 1543.9000000059605," +
        "      \"transferSize\": 5876," +
        "      \"encodedBodySize\": 5576," +
        "      \"decodedBodySize\": 5576," +
        "      \"responseStatus\": 200," +
        "      \"serverTiming\": [" +
        "        {" +
        "          \"name\": \"dtSInfo\"," +
        "          \"duration\": 0," +
        "          \"description\": \"0\"" +
        "        }," +
        "        {" +
        "          \"name\": \"dtRpid\"," +
        "          \"duration\": 0," +
        "          \"description\": \"1087509397\"" +
        "        }," +
        "        {" +
        "          \"name\": \"dtTao\"," +
        "          \"duration\": 0," +
        "          \"description\": \"1\"" +
        "        }" +
        "      ]" +
        "    }" +
        "  ]," +
        "  \"connection\": {" +
        "    \"onchange\": null," +
        "    \"effectiveType\": \"3g\"," +
        "    \"rtt\": 950," +
        "    \"downlink\": 1.4," +
        "    \"saveData\": false" +
        "  }" +
        "}"); 
    }    
    
    public static String buildDartsPortalPerftraceRequest2(Session session) {
        return String.format(
            "{" +
            "   \"navigation\": {" +
            "       \"type\": 0," +
            "       \"redirectCount\": 0" +
            "   }," +
            "   \"timing\": {" +
            "       \"connectStart\": 522.20000000298," +
            "       \"navigationStart\": 522.20000000298," +
            "       \"secureConnectionStart\": 557.79999999702," +
            "       \"fetchStart\": 520," +
            "       \"domContentLoadedEventStart\": 1140.40000000596," +
            "       \"responseStart\": 0," +
            "       \"domInteractive\": 1140.40000000596," +
            "       \"domainLookupEnd\": 520," +
            "       \"responseEnd\": 1060.79999999702," +
            "       \"redirectStart\": 0," +
            "       \"requestStart\": 818.29999999702," +
            "       \"unloadEventEnd\": 0," +
            "       \"unloadEventStart\": 0," +
            "       \"domLoading\": 1709047511757," +
            "       \"domComplete\": 1709047511842," +
            "       \"domainLookupStart\": 520," +
            "       \"loadEventStart\": 1709047511842," +
            "       \"domContentLoadedEventEnd\": 1140.79999999702," +
            "       \"loadEventEnd\": 1171.70000000298," +
            "       \"redirectEnd\": 0," +
            "       \"connectEnd\": 818.20000000298," +
            "   }," +
            "   \"entries\": [" +
            "       {" +
            "           \"name\": \"" + AppConfig.EnvironmentURL.B2B_Login.getUrl() + "/" + AppConfig.EnvironmentURL.DARTS_PORTAL_Auth_LOGIN.getUrl() + "?client_id=" + AppConfig.EnvironmentURL.EXTERNAL_AZURE_AD_B2C_CLIENT_ID.getUrl() + "&redirect_uri=https%3A%2F%2Fdarts.test.apps.hmcts.net%2Fauth%2Fcallback&scope=openid&prompt=login&response_mode=form_post&response_type=code\"," +
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

        return String.format("{\"courthouse_name\": \"PerfCourtHouse_%s\", "
        // "\"code\": \" "+ generatorCourtHouseCode.generateNextNumber() + "\", " +
        +"\"display_name\": \"PerfCourtHouse_%s\"}",                
        // "\"region_id\": \"0\"}",
        courtHouseName, courtHouseName);
    }
    
    public static String buildEventsPostBody(Session session) {
        
        // Generate a random court house name
        String courtHouseName = session.get("courthouse_name") != null ? session.get("courthouse_name").toString() : "";
        String courtRoomName = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";
        String courtCaseNumber = session.get("case_number") != null ? session.get("case_number").toString() : "";
        
        return String.format("{\"event_id\": \"1\", " 
        +"\"type\": \"30300\", " 
        +"\"sub_type\": \"\", " 
        +"\"courthouse\": \"%s\", " 
        +"\"courtroom\": \"%s\", " 
        +"\"case_numbers\": [ " 
        +"\"%s\" ], "            
        +"\"date_time\": \"2024-04-05T12:02:00.000Z\"}",
    courtHouseName, courtRoomName, courtCaseNumber);
    }

    public static String buildUpdateCaseWithEventsPostBody(Session session) {
        
        // Generate a random court house name
        String courtHouseName = session.get("courthouse_name") != null ? session.get("courthouse_name").toString() : "";
        String courtRoomName = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";
        String courtCaseNumber = session.get("case_number") != null ? session.get("case_number").toString() : "";
        
        return String.format("{\"event_id\": \"1\", " 
        +"\"type\": \"30300\", " 
        +"\"sub_type\": \"\", " 
        +"\"courthouse\": \"Gloucester\", " 
        +"\"courtroom\": \"1\", " 
        +"\"case_numbers\": [ " 
        +"\"T202400144\" ], "            
        +"\"date_time\": \"2024-04-11T12:02:00.000Z\"}",
    courtHouseName, courtRoomName, courtCaseNumber);
    }

    public static String buildDuplicateEventsPostBody(Session session, String eventId) {

        // Generate a random court house name
        String courtHouseName = session.get("courthouse_name") != null ? session.get("courthouse_name").toString() : "";
        String courtRoomName = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";
        String courtCaseNumber = session.get("case_number") != null ? session.get("case_number").toString() : "";
        return String.format("{\"event_id\": \"%s\", " 
                +"\"message_id\": \"This is a Perf test for Duplication tasks\", " 
                +"\"event_text\": \"Perf_event text for Duplication\", " 
                +"\"type\": \"30300\", " 
                +"\"sub_type\": \"\", " 
                +"\"courthouse\": \"%s\", " 
                +"\"courtroom\": \"%s\", " 
                +"\"case_numbers\": [ " 
                +"\"%s\" ], " 
                +"\"date_time\": \"2024-04-05T12:02:00.000Z\"}",
            eventId, courtHouseName, courtRoomName, courtCaseNumber);
    }   

    public static String buildInterpreterUsedEventBody(Session session) {
        
        // Generate a random court house name
        String courtHouseName = session.get("courthouse_name") != null ? session.get("courthouse_name").toString() : "";
        String courtRoomName = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";
        String courtCaseNumber = session.get("case_number") != null ? session.get("case_number").toString() : "";

            return String.format("{\"event_id\": \"74\", " 
            +"\"type\": \"2917\", " 
            +"\"sub_type\": \"3979\", " 
            +"\"courthouse\": \"%s\", " 
            +"\"courtroom\": \"%s\", " 
            +"\"case_numbers\": [ " 
            +"\"%s\" ], "        
            +"\"date_time\": \"2024-04-05T12:02:00.000Z\"}",
        courtHouseName, courtRoomName, courtCaseNumber);
    }
    public static String buildInterpreterUsedForUpdatedEventBody(Session session) {
        
        // Generate a random court house name
        String courtHouseName = session.get("courthouse_display_name") != null ? session.get("courthouse_display_name").toString() : "";
        String courtRoomName = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";
        String courtCaseNumber = session.get("case_number") != null ? session.get("case_number").toString() : "";
        String hearing_date = session.get("hearing_date") != null ? session.get("hearing_date").toString() : "";

            return String.format("{\"event_id\": \"74\", " 
            +"\"type\": \"2917\", " 
            +"\"sub_type\": \"3979\", " 
            +"\"courthouse\": \"%s\", " 
            +"\"courtroom\": \"%s\", " 
            +"\"case_numbers\": [ " 
            +"\"%s\" ], "        
            +"\"date_time\": \"%sT12:02:00.000Z\"}",
        courtHouseName, courtRoomName, courtCaseNumber, hearing_date);

    }
    public static String buildEventsRetentionsPostBody(Session session) {
        
        // Generate a random court house name
        String courtHouseName = session.get("courthouse_name") != null ? session.get("courthouse_name").toString() : "";
        String courtRoomName = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";
        String courtCaseNumber = session.get("case_number") != null ? session.get("case_number").toString() : "";
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String randomComment = randomStringGenerator.generateRandomString(10);

    //     return String.format("{ \"type\": \"30300\", " 
    //     +"\"event_id\": \"218\", " 
    //     +"\"courthouse\": \"York\", " 
    //     +"\"courtroom\": \"1\"," 
    //     +"\"case_numbers\": [ " 
    //     +"\"SHUl9Ky1AD\" ], "    
    //     +"\"event_text\": \"Perf_Event_%s\","      
    //     +"\"date_time\": \"2024-02-26T12:02:00.000Z\"," 
    //     +"\"retention_policy\": {" 
    //     +    "\"case_retention_fixed_policy\": \"1\"," 
    //     +    "\"case_total_sentence\": \"1\"}," 
    //     +"\"start_time\": \"2024-02-26T17:27:33.212Z\"," 
    //     +"\"end_time\": \"2024-02-26T17:27:33.212Z\"," 
    //     +"\"is_mid_tier\": true}",
    // courtHouseName, courtRoomName, courtCaseNumber, randomComment);
    // }

   
        return String.format("{ \"type\": \"30300\", " 
        +"\"event_id\": \"218\", " 
        +"\"courthouse\": \"%s\", " 
        +"\"courtroom\": \"%s\"," 
        +"\"case_numbers\": [ " 
        +"\"%s\" ], "    
        +"\"event_text\": \"Perf_Event_%s\","      
        +"\"date_time\": \"2024-04-05T12:02:00.000Z\"," 
        +"\"retention_policy\": {" 
        +    "\"case_retention_fixed_policy\": \"1\"," 
        +    "\"case_total_sentence\": \"1\"}," 
        +"\"start_time\": \"2024-07-19T17:27:33.212Z\"," 
        +"\"end_time\": \"2024-07-19T17:27:33.212Z\"," 
        +"\"is_mid_tier\": true}",
    courtHouseName, courtRoomName, courtCaseNumber, randomComment);
    }

    public static String buildRetentionsPostBody(Session session) {
        
        // Generate a random court house name
        String courtCaseId = session.get("cas_id") != null ? session.get("cas_id").toString() : "";
        // Generate a random comment
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String randomComment = randomStringGenerator.generateRandomString(10);

        return String.format("{\"case_id\": \"%s\", " 
        +"\"is_permanent_retention\": \"true\", " 
        + "\"comments\": \"Perf_%s\"}",
        courtCaseId, randomComment);
    }


    public static String buildEventsForLinkingCasePostBody(Session session, String caseName) {
        
        // Generate a random court house name
        String courtHouseName = session.get("courthouse_name") != null ? session.get("courthouse_name").toString() : "";
        String courtRoomName = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        int test = (int)NumberGenerator.generateRandom13DigitNumber();

        return String.format("{\"event_id\":" + test + ", " 
        +"\"message_id\":" + currentTimeMillis + ", " 
        +"\"type\": \"30300\", " 
        +"\"sub_type\": \"\", " 
        +"\"courthouse\": \"%s\", " 
        +"\"courtroom\": \"%s\", " 
        +"\"case_numbers\": [ " 
        +"\"PerfCase_%s\" ], "       
        +"\"date_time\": \"2024-11-11T12:02:00.000Z\", " 
        +"\"start_time\": \"2024-11-11T12:02:00.000Z\", " 
        +"\"end_time\": \"2024-11-11T13:02:00.000Z\"}",
    courtHouseName, courtRoomName, caseName);
    }
}