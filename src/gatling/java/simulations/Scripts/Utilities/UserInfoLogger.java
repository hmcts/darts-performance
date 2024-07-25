package simulations.Scripts.Utilities;

import io.gatling.javaapi.core.ChainBuilder;
import static io.gatling.javaapi.core.CoreDsl.exec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserInfoLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoLogger.class);

    public static ChainBuilder logUserInfoOnFailure(String requestName) {
        return exec(session -> {
            if (session.isFailed()) {
                String email = session.getString("Email");
                String password = session.getString("Password");
                String userName = session.getString("user_name");
                LOGGER.error("Request '{}' failed for user: Email={}, Password={}, User Name={}", requestName, email, password, userName);
            }
            return session;
        });
    }
}
