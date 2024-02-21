package RequestBodyBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import Utilities.RandomStringGenerator;
import io.gatling.javaapi.core.Session;

public class RequestBodyBuilder {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final String[] REQUEST_TYPES = {"DOWNLOAD", "PLAYBACK"};

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

}