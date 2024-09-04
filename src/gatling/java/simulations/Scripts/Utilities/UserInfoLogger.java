package simulations.Scripts.Utilities;

import io.gatling.javaapi.core.ChainBuilder;
import static io.gatling.javaapi.core.CoreDsl.exec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserInfoLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoLogger.class);

    public static ChainBuilder logDetailedErrorMessage(String requestName) {
        return exec(session -> {
            if (session.isFailed()) {
                String errorStatusCode = session.contains("status") ? session.getString("status") : "N/A";
                String errorType = session.contains("errorType") ? session.getString("errorType") : "N/A";
                String errorTitle = session.contains("errorTitle") ? session.getString("errorTitle") : "N/A";
                String errorStatus = session.contains("errorStatus") ? session.getString("errorStatus") : "N/A";
                
                String email = session.contains("Email") ? session.getString("Email") : "N/A";
                String password = session.contains("Password") ? session.getString("Password") : "N/A";

                String errorMessage = String.format(
                    "Request '%s' failed with status code: %s. " +
                    "Error Type: %s, Error Title: %s, Error Status: %s. " +
                    "User Details: Email=%s, Password=%s.",
                    requestName, errorStatusCode, errorType, errorTitle, errorStatus, email, password
                );
                LOGGER.error(errorMessage);
            }
            return session;
        });
    }

    public static ChainBuilder logDetailedErrorMessage(String requestName, String trmId) {
        return exec(session -> {
            if (session.isFailed()) {
                String errorStatusCode = session.contains("status") ? session.getString("status") : "N/A";
                String errorType = session.contains("errorType") ? session.getString("errorType") : "N/A";
                String errorTitle = session.contains("errorTitle") ? session.getString("errorTitle") : "N/A";
                String errorStatus = session.contains("errorStatus") ? session.getString("errorStatus") : "N/A";
                
                String email = session.contains("Email") ? session.getString("Email") : "N/A";
                String password = session.contains("Password") ? session.getString("Password") : "N/A";

                String errorMessage = String.format(
                    "Request '%s' failed with status code: %s. " +
                    "Error Type: %s, Error Title: %s, Error Status: %s. " +
                    "Failed on trm_id: %s. " +
                    "User Details: Email=%s, Password=%s.",
                    requestName, errorStatusCode, errorType, errorTitle, errorStatus, trmId, email, password
                );
                LOGGER.error(errorMessage);
            }
            return session;
        });
    }
    
    public static ChainBuilder logDetailedErrorMessage(String requestName, String regexName, String expectedPattern) {
        return exec(session -> {
            if (session.isFailed()) {
                String email = session.contains("Email") ? session.getString("Email") : "N/A";
                String password = session.contains("Password") ? session.getString("Password") : "N/A";
                String userName = session.contains("user_name") ? session.getString("user_name") : "N/A";

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
