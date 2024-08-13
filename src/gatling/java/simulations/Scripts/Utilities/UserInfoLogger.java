package simulations.Scripts.Utilities;

import io.gatling.javaapi.core.ChainBuilder;
import static io.gatling.javaapi.core.CoreDsl.exec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserInfoLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoLogger.class);

    public static ChainBuilder logDetailedErrorMessage(String requestName) {
        return exec(session -> {
            if (session.contains("errorStatusCode")) {
                String errorStatusCode = session.getString("errorStatusCode");
                String errorType = session.contains("errorType") ? session.getString("errorType") : "N/A";
                String errorTitle = session.contains("errorTitle") ? session.getString("errorTitle") : "N/A";
                String errorStatus = session.contains("errorStatus") ? session.getString("errorStatus") : "N/A";

                String errorMessage = String.format(
                    "Request '%s' failed with status code: %s. " +
                    "Error Type: %s, Error Title: %s, Error Status: %s.",
                    requestName, errorStatusCode, errorType, errorTitle, errorStatus
                );
                LOGGER.error(errorMessage);
            }
            return session;
        });
    };
    
    public static ChainBuilder logDetailedErrorMessage(String requestName, String regexName, String expectedPattern) {
        return exec(session -> {
            if (session.isFailed()) {
                String email = session.getString("Email");
                String password = session.getString("Password");
                String userName = session.getString("user_name");
                String errorMessage = String.format(
                    "Request '%s' failed for user: Email=%s, Password=%s, User Name=%s. " +
                    "Failed to match the regex '%s' with pattern '%s'. The pattern did not find any matches. " +
                    "Please check if the response contains the expected format and verify the regex pattern.",
                    requestName, email, password, userName, regexName, expectedPattern
                );
                LOGGER.error(errorMessage);
            }
            return session;
        });
    }
}
