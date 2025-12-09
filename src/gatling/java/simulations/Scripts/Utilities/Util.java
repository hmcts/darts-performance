package simulations.Scripts.Utilities;

import io.gatling.javaapi.core.Session;
import lombok.extern.slf4j.Slf4j;

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
}
