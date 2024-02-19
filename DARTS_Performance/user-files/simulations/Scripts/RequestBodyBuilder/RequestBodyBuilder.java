package RequestBodyBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class RequestBodyBuilder {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final String[] REQUEST_TYPES = {"DOWNLOAD", "PLAYBACK"};

    // Define the percentages for each request type (must sum up to 100)
    private static final int DOWNLOAD_PERCENTAGE = 70; //% chance
    private static final int PLAYBACK_PERCENTAGE = 30; //% chance

    public static String buildRequestBody(String hearingId, String userId, LocalDateTime startTime, LocalDateTime endTime) {
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
}