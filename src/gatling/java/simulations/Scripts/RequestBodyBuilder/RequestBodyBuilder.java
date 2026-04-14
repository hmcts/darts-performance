package simulations.Scripts.RequestBodyBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gatling.javaapi.core.Session;
import lombok.SneakyThrows;
import scala.util.Random;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.NumberGenerator;
import simulations.Scripts.Utilities.RandomStringGenerator;
import simulations.models.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class RequestBodyBuilder {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final String[] REQUEST_TYPES = {"DOWNLOAD", "PLAYBACK"};

    // Define the percentages for each request type (must sum up to 100)
    private static final int DOWNLOAD_PERCENTAGE = 70; //% chance
    private static final int PLAYBACK_PERCENTAGE = 30; //% chance

    @SneakyThrows
    public static String toJson(Object value) {
        return MAPPER.writeValueAsString(value);
    }

    private static Integer parseInteger(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    public static String buildPOSTAudioRequestBody(Session session) {

        String hearingId = session.get("hea_id") != null ? session.get("hea_id").toString() : "";
        String userId = session.get("usr_id") != null ? session.get("usr_id").toString() : "";
        String startTime = session.get("start_ts") != null ? session.get("start_ts").toString() : "";
        String endTime = session.get("end_ts") != null ? session.get("end_ts").toString() : "";

        return toJson(PostAudioRequestBody.builder()
                .hearingId(hearingId)
                .requestor(userId)
                .startTime(startTime)
                .endTime(endTime)
                .build());
    }

    private static String formatTime(LocalDateTime time) {
        return time.format(formatter);
    }

    private static String getRandomRequestType() {
        int totalPercentage = DOWNLOAD_PERCENTAGE + PLAYBACK_PERCENTAGE;
        int randomNumber = ThreadLocalRandom.current().nextInt(totalPercentage)
                + 1; // Generate random number between 1 and the total percentage

        if (randomNumber <= DOWNLOAD_PERCENTAGE) {
            return REQUEST_TYPES[0]; // DOWNLOAD
        } else {
            return REQUEST_TYPES[1]; // PLAYBACK
        }
    }

    public static String buildTranscriptionRequestBody(Session session) {
        String hearingId = session.get("hea_id") != null ? session.get("hea_id").toString() : null;
        String caseId = session.get("cas_id") != null ? session.get("cas_id").toString() : null;
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String randomComment = randomStringGenerator.generateRandomString(10);

        return toJson(
                TranscriptionRequestBody.builder()
                        .hearingId(hearingId)
                        .caseId(caseId)
                        .transcriptionUrgencyId("3")
                        .transcriptionTypeId("999")
                        .comment("Perf_" + randomComment)
                        .build()
        );

    }

    public static String buildTranscriptionPatchAcceptRequestBody(Session session) {
        String transcriptionId = session.get("tra_id") != null ? session.get("tra_id").toString() : null;
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String randomComment = randomStringGenerator.generateRandomString(10);

        return toJson(
                TranscriptionPatchAcceptRequestBody.builder()
                        .transcriptionStatusId("3")
                        .workflowComment("Moved to Accepted_" + randomComment)
                        .build()
        );
    }

    public static String buildGetCredentialType(Session session) {

        String originalRequest =
                "rQQIARAA02I20jOwUjFLsTQ2SDFJ1E1LTUzWNTFMTtW1SDGw0E00TbJMMzNOs7S0MCgS4hJ416We_lKtyH32q6pu_S"
                        +
                        "-3Tq1itMgoKSkottLXT0ksKinWK0ktLtFLLCgo1svITQby81JL9BNLSzL0M_NKUovyEnP0kxNzcpISk7N3MDJeYGR8wch4i4nf3xGoxAhE5BdlVqW-YmL4xMSZll-UG1-QX1yyiVkl1dTcNM3MzFg3ydggUdfE3MJM18IyMUXX2NDSwsQoJS3VwtT4FDNbfkFqXmbKBRbGVyw8BsxWHBxcAgwSDAoMP1gYF7ECnc91LZXBes4Cp7X_GOPsdBQZTrHqF6cbFISl-zs7JZqZmfkUeziahmSVVkZWZqfnROaEBOdn-haZeRVoJ0Y6utoaWRlOYGP8wMbYwc6wi5Nsnx_gZfjBt-rzqSmfWla883jFr5MRVRFpZublnellVulb4eUSGlngnBQUaVIQFhTsblmYHOGtHRJRVpkaYWlhu0GA4YEAAwA1";
        String flowToken = session.get("flowToken") != null ? session.get("flowToken").toString() : null;
        String userName = session.get("Email") != null ? session.get("Email").toString() : null;

        return toJson(
                GetCredentialType.builder()
                        .username(userName)
                        .otherIdpSupported(false)
                        .checkPhones(false)
                        .remoteNGCSupported(true)
                        .cookieBannerShown(false)
                        .fidoSupported(true)
                        .originalRequest(originalRequest)
                        .country("GB")
                        .forceOtpLogin(false)
                        .externalFederationDisallowed(false)
                        .remoteConnectSupported(false)
                        .federationFlags(0)
                        .signup(false)
                        .flowToken(flowToken)
                        .accessPassSupported(true)
                        .qrCodePinSupported(true)
                        .build()
        );
    }


    @SneakyThrows
    public static String buildSearchCaseRequestBody(Session session) {
        String caseNumber = Optional.ofNullable(session.get("caseNumber"))
                .map(Object::toString)
                .orElse(null);
        String courtHouseName = Optional.ofNullable(session.get("courthouse_name"))
                .map(Object::toString)
                .orElse(null);
        Integer courtHouseId = Optional.ofNullable(session.get("cth_id"))
                .map(value -> Integer.valueOf(value.toString()))
                .orElse(null);
        String courtRoom = Optional.ofNullable(session.get("CourtRoom"))
                .map(Object::toString)
                .orElse(null);
        String defendantName = Optional.ofNullable(session.get("defendantFirstName"))
                .map(value -> value.toString())
                .orElse(null);

        // Extract fromDate from session
        String fromDateStr = session.getString("getfromDate");

        // Ensure fromDate is not null and parse it
        LocalDate fromDate = Optional.ofNullable(fromDateStr)
                .map(LocalDate::parse) // Convert String to LocalDate
                .orElse(LocalDate.now()); // Default to today if null

        // Get the user type from the session
        String userType = session.get("Type").toString();
        // log.info("userType for Audio Request: " + userType);

        // Determine request type based on user type
        AdvancedSearchPayload.AdvancedSearchPayloadBuilder builder = AdvancedSearchPayload.builder()
                .caseNumber(caseNumber)
                .courtroom(courtRoom)
                .dateFrom("1970-04-01")
                .dateTo("2025-04-07");
        if (courtHouseId != null) {
            builder.courthouseIds(List.of(courtHouseId));
        }
        if (!userType.equalsIgnoreCase("LanguageShop")) {
            builder.eventTextContains(courtHouseName);
        }

        return new ObjectMapper().writeValueAsString(builder.build());
    }


    public static String buildChangeRetentionsBody(Session session) {
        String caseId = Optional.ofNullable(session.get("getCaseId"))
                .map(Object::toString)
                .orElse(null);
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String randomComment = randomStringGenerator.generateRandomString(10);

        return toJson(RetentionRequestBody.builder()
                .caseId(caseId)
                .isPermanentRetention(true)
                .comments("Perf_Comment_" + randomComment)
                .build());
    }

    public static String buildTranscriptionsBody(Session session) {
        String hearingId = session.get("getHearingId") != null ? session.get("getHearingId").toString() : null;
        String caseId = session.get("getCaseId") != null ? session.get("getCaseId").toString() : null;
        Integer transcriptionTypeId = parseInteger(session.get("transcriptionTypeId"));
        String getHearingdate =
                session.get("getHearingdate") != null ? session.get("getHearingdate").toString() : null;

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
        OffsetDateTime endUtc = startUtc.plusHours(1);

        /* ── 5. format both as ISO‑8601 with trailing Z ───────────────────── */
        DateTimeFormatter isoZ = DateTimeFormatter.ISO_INSTANT; // 2025‑01‑12T09:09:09Z
        String startTime = isoZ.format(startUtc);
        String endTime = isoZ.format(endUtc);

        return toJson(TranscriptionsRequestBody.builder()
                .caseId(caseId)
                .hearingId(hearingId)
                .transcriptionTypeId(transcriptionTypeId)
                .transcriptionUrgencyId(4)
                .comment(randomComment)
                .startDateTime(startTime)
                .endDateTime(endTime)
                .build());
    }

    public static String buildAudioRequestBody(Session session, Object getHearingId, Object requestor,
                                               Object audioStartDate, Object audioEndDate, Object requestType) {
        return toJson(AudioRequestBody.builder()
                .hearingId(getHearingId)
                .requestor(requestor)
                .startTime(String.valueOf(audioStartDate))
                .endTime(String.valueOf(audioEndDate))
                .build());
    }


    public static String buildTranscriptionApprovalRequestBody(Session session) {

        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String randomComment = randomStringGenerator.generateRandomString(10);

        TranscriptionApprovalRequestBody approve = TranscriptionApprovalRequestBody.builder()
                .transcriptionStatusId("3")
                .build();

        TranscriptionApprovalRequestBody reject = TranscriptionApprovalRequestBody.builder()
                .transcriptionStatusId("4")
                .workflowComment(randomComment)
                .build();

        // Create a random number generator
        Random random = new Random();

        // Generate a random number between 0 and 1
        // If the generated number is less than 0.5, select Approve, otherwise select Reject
        String selectedAction;
        if (random.nextDouble() < 0.5) {
            selectedAction = toJson(approve);
        } else {
            selectedAction = toJson(reject);
        }

        // Return the selected string
        return selectedAction;
    }

    public static String buildPostAudioApiRequest(Session session, String randomAudioFile) {
        // Retrieve values from session or define defaults if needed
        String courtHouseName =
                session.get("courthouse_display_name") != null ? session.get("courthouse_display_name").toString() : "";
        String courtRoomName = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";
        String courtCaseNumber = session.get("case_number") != null ? session.get("case_number").toString() : "";
        String hearingDate = session.get("hearing_date") != null ? session.get("hearing_date").toString() : "";

        // RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        // String caseName1 = randomStringGenerator.generateRandomString(10);
        // String caseName2 = randomStringGenerator.generateRandomString(10);
        // String caseName3 = randomStringGenerator.generateRandomString(10);

        return toJson(AudioUploadRequestBody.builder()
                .startedAt(hearingDate + "T17:28:59.936Z")
                .endedAt(hearingDate + "T18:28:59.936Z")
                .channel(1)
                .totalChannels(4)
                .format("mp2")
                .filename(randomAudioFile)
                .courthouse(courtHouseName)
                .courtroom(courtRoomName)
                .fileSize(64003968d)
                .checksum("TVRMwq16b4mcZwPSlZj/iQ==")
                .cases(List.of(courtCaseNumber))
                .build());
    }

    public static String buildPostAudioLinkingForCaseApiRequest(Session session, String randomAudioFile,
                                                                String caseName) {
        // Retrieve values from session or define defaults if needed
        String courtHouseName = session.get("courthouse_name") != null ? session.get("courthouse_name").toString() : "";
        String courtRoom = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";


        return toJson(AudioUploadRequestBody.builder()
                .startedAt("2024-11-11T12:02:00.000Z")
                .endedAt("2024-11-11T13:02:00.000Z")
                .channel(1)
                .totalChannels(4)
                .format("mp2")
                .filename(randomAudioFile)
                .courthouse(courtHouseName)
                .courtroom(courtRoom)
                .fileSize(937.96)
                .checksum("TVRMwq16b4mcZwPSlZj/iQ==")
                .cases(List.of("PerfCase_" + caseName))
                .build());
    }

    public static String buildPostCaseSearchApiRequest(Session session) {
        // Retrieve values from session or define defaults if needed
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String caseName = randomStringGenerator.generateRandomString(10);

        return toJson(CaseSearchRequestBody.builder()
                .caseNumber("PerfCase_" + caseName)
                .build());
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

        return toJson(CaseRequestBody.builder()
                .courthouse(courtHouseName)
                .caseNumber("PerfCase_" + caseName)
                .caseType("1")
                .defendants(List.of("PerfDefendant_" + defendantsName))
                .judges(List.of("PerfJudge_" + judgeName))
                .prosecutors(List.of("PerfProsecutors_" + prosecutorsName))
                .defenders(List.of("PerfDefendersName_" + defendersName))
                .build());
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

        return toJson(CaseRequestBody.builder()
                .courthouse(courtHouseName)
                .caseNumber("PerfCase_" + caseName)
                .caseType("1")
                .defendants(List.of("PerfDefendant_" + defendantsName))
                .judges(List.of("PerfJudge_" + judgeName))
                .prosecutors(List.of("PerfProsecutors_" + prosecutorsName))
                .defenders(List.of("PerfDefendersName_" + defendersName))
                .build());
    }

    public static String buildDartsPortalPerftraceRequest(Session session) {
        PerfTraceRequest.PerfTraceNavigation navigation = PerfTraceRequest.PerfTraceNavigation.builder()
                .type(0)
                .redirectCount(0)
                .build();

        PerfTraceRequest.PerfTraceTiming timing = PerfTraceRequest.PerfTraceTiming.builder()
                .connectStart(1709047511193d)
                .navigationStart(1709047510670d)
                .secureConnectionStart(1709047511228d)
                .fetchStart(1709047511190d)
                .domContentLoadedEventStart(1709047511811d)
                .responseStart(1709047511729d)
                .domInteractive(1709047511811d)
                .domainLookupEnd(1709047511190d)
                .responseEnd(1709047511731d)
                .redirectStart(0d)
                .requestStart(1709047511489d)
                .unloadEventEnd(0d)
                .unloadEventStart(0d)
                .domLoading(1709047511757d)
                .domComplete(1709047511842d)
                .domainLookupStart(1709047511190d)
                .loadEventStart(1709047511842d)
                .domContentLoadedEventEnd(1709047511811d)
                .loadEventEnd(1709047511842d)
                .redirectEnd(0d)
                .connectEnd(1709047511489d)
                .build();

        PerfTraceRequest.PerfTraceEntry navigationEntry = PerfTraceRequest.PerfTraceEntry.builder()
                .name("https://hmctsstgextid.b2clogin.com/hmctsstgextid.onmicrosoft.com/B2C_1_darts_externaluser_signin/oauth2/v2.0/authorize?client_id=363c11cb-48b9-44bf-9d06-9a3973f6f413&redirect_uri=https%3A%2F%2Fdarts.test.apps.hmcts.net%2Fauth%2Fcallback&scope=openid&prompt=login&response_mode=form_post&response_type=code")
                .entryType("navigation")
                .startTime(0d)
                .duration(1171.7000000029802)
                .initiatorType("navigation")
                .deliveryType("")
                .nextHopProtocol("http/1.1")
                .renderBlockingStatus("non-blocking")
                .workerStart(0d)
                .redirectStart(0d)
                .redirectEnd(0d)
                .fetchStart(520d)
                .domainLookupStart(520d)
                .domainLookupEnd(520d)
                .connectStart(522.2000000029802)
                .secureConnectionStart(557.7999999970198)
                .connectEnd(818.2000000029802)
                .requestStart(818.2999999970198)
                .responseStart(1059d)
                .firstInterimResponseStart(0d)
                .responseEnd(1060.7999999970198)
                .transferSize(69665)
                .encodedBodySize(69365)
                .decodedBodySize(176293)
                .responseStatus(200)
                .serverTiming(List.of())
                .unloadEventStart(0d)
                .unloadEventEnd(0d)
                .domInteractive(1140.4000000059605)
                .domContentLoadedEventStart(1140.4000000059605)
                .domContentLoadedEventEnd(1140.7999999970198)
                .domComplete(1171.5999999940395)
                .loadEventStart(1171.7000000029802)
                .loadEventEnd(1171.7000000029802)
                .type("navigate")
                .redirectCount(0)
                .activationStart(0)
                .criticalCHRestart(0)
                .build();

        PerfTraceRequest.PerfTraceEntry visibilityEntry = PerfTraceRequest.PerfTraceEntry.builder()
                .name("visible")
                .entryType("visibility-state")
                .startTime(0d)
                .duration(0d)
                .build();

        List<PerfTraceRequest.PerfTraceServerTiming> serverTimings = List.of(
                PerfTraceRequest.PerfTraceServerTiming.builder().name("dtSInfo").duration(0d).description("0").build(),
                PerfTraceRequest.PerfTraceServerTiming.builder().name("dtRpid").duration(0d).description("1087509397")
                        .build(),
                PerfTraceRequest.PerfTraceServerTiming.builder().name("dtTao").duration(0d).description("1").build()
        );

        PerfTraceRequest.PerfTraceEntry resourceEntry = PerfTraceRequest.PerfTraceEntry.builder()
                .name("https://darts.test.apps.hmcts.net/auth/azuread-b2c-login?screenName=loginScreen&ui_locales=en")
                .entryType("resource")
                .startTime(1132d)
                .duration(411.90000000596046)
                .initiatorType("xmlhttprequest")
                .deliveryType("")
                .nextHopProtocol("http/1.1")
                .renderBlockingStatus("non-blocking")
                .workerStart(0d)
                .redirectStart(0d)
                .redirectEnd(0d)
                .fetchStart(1132d)
                .domainLookupStart(1132d)
                .domainLookupEnd(1132d)
                .connectStart(1133.2999999970198)
                .secureConnectionStart(1159.4000000059605)
                .connectEnd(1299.2000000029802)
                .requestStart(1299.2999999970198)
                .responseStart(1543.4000000059605)
                .firstInterimResponseStart(0d)
                .responseEnd(1543.9000000059605)
                .transferSize(5876)
                .encodedBodySize(5576)
                .decodedBodySize(5576)
                .responseStatus(200)
                .serverTiming(serverTimings)
                .build();

        PerfTraceRequest.PerfTraceConnection connection = PerfTraceRequest.PerfTraceConnection.builder()
                .onchange(null)
                .effectiveType("3g")
                .rtt(950d)
                .downlink(1.4)
                .saveData(false)
                .build();

        return toJson(PerfTraceRequest.builder()
                .navigation(navigation)
                .timing(timing)
                .entries(List.of(navigationEntry, visibilityEntry, resourceEntry))
                .connection(connection)
                .build());
    }

    public static String buildDartsPortalPerftraceRequest2(Session session) {
        PerfTraceRequest.PerfTraceNavigation navigation = PerfTraceRequest.PerfTraceNavigation.builder()
                .type(0)
                .redirectCount(0)
                .build();

        PerfTraceRequest.PerfTraceTiming timing = PerfTraceRequest.PerfTraceTiming.builder()
                .connectStart(522.20000000298)
                .navigationStart(522.20000000298)
                .secureConnectionStart(557.79999999702)
                .fetchStart(520d)
                .domContentLoadedEventStart(1140.40000000596)
                .responseStart(0d)
                .domInteractive(1140.40000000596)
                .domainLookupEnd(520d)
                .responseEnd(1060.79999999702)
                .redirectStart(0d)
                .requestStart(818.29999999702)
                .unloadEventEnd(0d)
                .unloadEventStart(0d)
                .domLoading(1709047511757d)
                .domComplete(1709047511842d)
                .domainLookupStart(520d)
                .loadEventStart(1709047511842d)
                .domContentLoadedEventEnd(1140.79999999702)
                .loadEventEnd(1171.70000000298)
                .redirectEnd(0d)
                .connectEnd(818.20000000298)
                .build();

        PerfTraceRequest.PerfTraceEntry navigationEntry = PerfTraceRequest.PerfTraceEntry.builder()
                .name(AppConfig.EnvironmentURL.B2B_Login.getUrl() + "/"
                        + AppConfig.EnvironmentURL.DARTS_PORTAL_Auth_LOGIN.getUrl() + "?client_id="
                        + AppConfig.EnvironmentURL.EXTERNAL_AZURE_AD_B2C_CLIENT_ID.getUrl()
                        + "&redirect_uri=https%3A%2F%2Fdarts.test.apps.hmcts.net%2Fauth%2Fcallback&scope=openid&prompt=login&response_mode=form_post&response_type=code")
                .entryType("navigation")
                .startTime(0d)
                .duration(2919.9000000953674)
                .initiatorType("navigation")
                .deliveryType("")
                .nextHopProtocol("http/1.1")
                .renderBlockingStatus("non-blocking")
                .workerStart(0d)
                .redirectStart(0d)
                .redirectEnd(0d)
                .fetchStart(670.7000000476837)
                .domainLookupStart(670.7000000476837)
                .domainLookupEnd(670.7000000476837)
                .connectStart(672.2000000476837)
                .secureConnectionStart(843.5)
                .connectEnd(1729.7000000476837)
                .requestStart(1729.7000000476837)
                .responseStart(2786.7999999523163)
                .firstInterimResponseStart(0d)
                .responseEnd(2788.7000000476837)
                .transferSize(69668)
                .encodedBodySize(69368)
                .decodedBodySize(176293)
                .responseStatus(200)
                .serverTiming(List.of())
                .unloadEventStart(0d)
                .unloadEventEnd(0d)
                .domInteractive(2879.4000000953674)
                .domContentLoadedEventStart(2879.4000000953674)
                .domContentLoadedEventEnd(2879.7000000476837)
                .domComplete(2919.7999999523163)
                .loadEventStart(2919.9000000953674)
                .loadEventEnd(2919.9000000953674)
                .type("navigate")
                .redirectCount(0)
                .activationStart(0)
                .criticalCHRestart(0)
                .build();

        PerfTraceRequest.PerfTraceEntry visibilityEntry = PerfTraceRequest.PerfTraceEntry.builder()
                .name("visible")
                .entryType("visibility-state")
                .startTime(0d)
                .duration(0d)
                .build();

        List<PerfTraceRequest.PerfTraceServerTiming> serverTimings = List.of(
                PerfTraceRequest.PerfTraceServerTiming.builder().name("dtSInfo").duration(0d).description("0").build(),
                PerfTraceRequest.PerfTraceServerTiming.builder().name("dtRpid").duration(0d).description("454511542")
                        .build(),
                PerfTraceRequest.PerfTraceServerTiming.builder().name("dtTao").duration(0d).description("1").build()
        );

        PerfTraceRequest.PerfTraceEntry resourceEntry = PerfTraceRequest.PerfTraceEntry.builder()
                .name(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl()
                        + "/auth/azuread-b2c-login?screenName=loginScreen&ui_locales=en")
                .entryType("resource")
                .startTime(2869.7999999523163)
                .duration(265d)
                .initiatorType("xmlhttprequest")
                .deliveryType("")
                .nextHopProtocol("http/1.1")
                .renderBlockingStatus("non-blocking")
                .workerStart(0d)
                .redirectStart(0d)
                .redirectEnd(0d)
                .fetchStart(2869.7999999523163)
                .domainLookupStart(2869.7999999523163)
                .domainLookupEnd(2869.7999999523163)
                .connectStart(2871.4000000953674)
                .secureConnectionStart(2893d)
                .connectEnd(2983d)
                .requestStart(2983.0999999046326)
                .responseStart(3134.5)
                .firstInterimResponseStart(0d)
                .responseEnd(3134.7999999523163)
                .transferSize(5875)
                .encodedBodySize(5575)
                .decodedBodySize(5575)
                .responseStatus(200)
                .serverTiming(serverTimings)
                .build();

        return toJson(PerfTraceRequest.builder()
                .navigation(navigation)
                .timing(timing)
                .entries(List.of(navigationEntry, visibilityEntry, resourceEntry))
                .build());
    }

    public static String buildCourtHousePostBody(Session session) {

        // Generate a random court house name
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String courtHouseName = randomStringGenerator.generateRandomString(10);

        return toJson(CourtHouseRequestBody.builder()
                .courthouseName("PerfCourtHouse_" + courtHouseName)
                .displayName("PerfCourtHouse_" + courtHouseName)
                .build());
    }

    public static String buildEventsPostBody(Session session) {

        // Generate a random court house name
        String courtHouseName = session.get("courthouse_name") != null ? session.get("courthouse_name").toString() : "";
        String courtRoomName = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";
        String courtCaseNumber = session.get("case_number") != null ? session.get("case_number").toString() : "";

        return toJson(EventRequestBody.builder()
                .eventId("1")
                .type("30300")
                .subType("")
                .courthouse(courtHouseName)
                .courtroom(courtRoomName)
                .caseNumbers(List.of(courtCaseNumber))
                .dateTime("2024-04-05T12:02:00.000Z")
                .build());
    }

    public static String buildUpdateCaseWithEventsPostBody(Session session) {

        // Generate a random court house name
        String courtHouseName = session.get("courthouse_name") != null ? session.get("courthouse_name").toString() : "";
        String courtRoomName = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";
        String courtCaseNumber = session.get("case_number") != null ? session.get("case_number").toString() : "";

        return toJson(EventRequestBody.builder()
                .eventId("1")
                .type("30300")
                .subType("")
                .courthouse("Gloucester")
                .courtroom("1")
                .caseNumbers(List.of("T202400144"))
                .dateTime("2024-04-11T12:02:00.000Z")
                .build());
    }

    public static String buildDuplicateEventsPostBody(Session session, String eventId) {

        // Generate a random court house name
        String courtHouseName = session.get("courthouse_name") != null ? session.get("courthouse_name").toString() : "";
        String courtRoomName = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";
        String courtCaseNumber = session.get("case_number") != null ? session.get("case_number").toString() : "";
        return toJson(EventRequestBody.builder()
                .eventId(eventId)
                .messageId("This is a Perf test for Duplication tasks")
                .eventText("Perf_event text for Duplication")
                .type("30300")
                .subType("")
                .courthouse(courtHouseName)
                .courtroom(courtRoomName)
                .caseNumbers(List.of(courtCaseNumber))
                .dateTime("2024-04-05T12:02:00.000Z")
                .build());
    }

    public static String buildInterpreterUsedEventBody(Session session) {

        // Generate a random court house name
        String courtHouseName = session.get("courthouse_name") != null ? session.get("courthouse_name").toString() : "";
        String courtRoomName = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";
        String courtCaseNumber = session.get("case_number") != null ? session.get("case_number").toString() : "";

        return toJson(EventRequestBody.builder()
                .eventId("74")
                .type("2917")
                .subType("3979")
                .courthouse(courtHouseName)
                .courtroom(courtRoomName)
                .caseNumbers(List.of(courtCaseNumber))
                .dateTime("2024-04-05T12:02:00.000Z")
                .build());
    }

    public static String buildInterpreterUsedForUpdatedEventBody(Session session) {

        // Generate a random court house name
        String courtHouseName =
                session.get("courthouse_display_name") != null ? session.get("courthouse_display_name").toString() : "";
        String courtRoomName = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";
        String courtCaseNumber = session.get("case_number") != null ? session.get("case_number").toString() : "";
        String hearing_date = session.get("hearing_date") != null ? session.get("hearing_date").toString() : "";

        return toJson(EventRequestBody.builder()
                .eventId("74")
                .type("2917")
                .subType("3979")
                .courthouse(courtHouseName)
                .courtroom(courtRoomName)
                .caseNumbers(List.of(courtCaseNumber))
                .dateTime(hearing_date + "T12:02:00.000Z")
                .build());

    }

    public static String buildEventsRetentionsPostBody(Session session) {

        // Generate a random court house name
        String courtHouseName = session.get("courthouse_name") != null ? session.get("courthouse_name").toString() : "";
        String courtRoomName = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";
        String courtCaseNumber = session.get("case_number") != null ? session.get("case_number").toString() : "";
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String randomComment = randomStringGenerator.generateRandomString(10);

        RetentionPolicy retentionPolicy = RetentionPolicy.builder()
                .caseRetentionFixedPolicy("1")
                .caseTotalSentence("1")
                .build();

        return toJson(EventRequestBody.builder()
                .type("30300")
                .eventId("218")
                .courthouse(courtHouseName)
                .courtroom(courtRoomName)
                .caseNumbers(List.of(courtCaseNumber))
                .eventText("Perf_Event_" + randomComment)
                .dateTime("2024-04-05T12:02:00.000Z")
                .retentionPolicy(retentionPolicy)
                .startTime("2024-07-19T17:27:33.212Z")
                .endTime("2024-07-19T17:27:33.212Z")
                .isMidTier(true)
                .build());
    }

    public static String buildRetentionsPostBody(Session session) {

        // Generate a random court house name
        String courtCaseId = session.get("cas_id") != null ? session.get("cas_id").toString() : "";
        // Generate a random comment
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String randomComment = randomStringGenerator.generateRandomString(10);

        return toJson(RetentionRequestBody.builder()
                .caseId(courtCaseId)
                .isPermanentRetention(true)
                .comments("Perf_" + randomComment)
                .build());
    }


    public static String buildEventsForLinkingCasePostBody(Session session, String caseName) {

        // Generate a random court house name
        String courtHouseName = session.get("courthouse_name") != null ? session.get("courthouse_name").toString() : "";
        String courtRoomName = session.get("courtroom_name") != null ? session.get("courtroom_name").toString() : "";
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        int test = (int) NumberGenerator.generateRandom13DigitNumber();

        return toJson(EventRequestBody.builder()
                .eventId(test)
                .messageId(Long.parseLong(currentTimeMillis))
                .type("30300")
                .subType("")
                .courthouse(courtHouseName)
                .courtroom(courtRoomName)
                .caseNumbers(List.of("PerfCase_" + caseName))
                .dateTime("2024-11-11T12:02:00.000Z")
                .startTime("2024-11-11T12:02:00.000Z")
                .endTime("2024-11-11T13:02:00.000Z")
                .build());
    }
}
