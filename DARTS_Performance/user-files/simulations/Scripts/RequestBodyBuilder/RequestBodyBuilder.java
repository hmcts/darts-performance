package RequestBodyBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class RequestBodyBuilder {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final String[] REQUEST_TYPES = {"DOWNLOAD", "PLAYBACK"};


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
        int randomIndex = ThreadLocalRandom.current().nextInt(REQUEST_TYPES.length);
        return REQUEST_TYPES[randomIndex];
    }
}