package simulations.Scripts.Utilities;

import io.gatling.javaapi.core.Session;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class Util {

    public static void logSession(Session session) {
        log.info("Request: " + session.getString("responseBody"));
        log.info("Response: " + session.getString("responseBody"));
    }

    public static void logSession(Session session, String... keys) {
        for (String key : keys) {
            log.info("Session Key: {} Value: {}", key, session.getString(key));
        }
    }

    public static Duration getDurationFromSeconds(int seconds) {
//        return Duration.ofSeconds(seconds);
        return Duration.ofSeconds(0);
    }

    public static Duration getDurationFromMillis(int millis) {
        return Duration.ofMillis(millis);
//        return Duration.ofSeconds(0);
    }
}
