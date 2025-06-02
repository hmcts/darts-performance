package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.UserInfoLogger;
import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalInternalLogoutScenario {


    private DartsPortalInternalLogoutScenario() {}

    public static ChainBuilder DartsPortalInternalLogoutRequest() {
        return group("Darts Portal Internal Logout")
            .on(
              exec(
                http("Darts-Portal - Auth - Logout")
                .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/logout")
                .headers(Headers.getHeaders(0))
                .check(regex("https:\\/\\/login\\.microsoftonline\\.com\\/([a-f0-9\\-]+)").find().saveAs("extractedUUID"))  // Updated regex pattern to extract the UUID

              )
              .exec(session -> {
                // Print the extracted UUID
                String uuid = session.getString("extractedUUID");
                System.out.println("Extracted UUID: " + uuid);
                return session;
            })
              .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Logout"))

              .exitHereIfFailed()  
              .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Login Microsoftonline - Oauth2 - Token"))

              .exitHereIfFailed() 
              .exec(
                  http("Darts-Portal - Login Microsoftonline - Oauth2 - v2.0 - Logoutsession")
                    .post(session -> "https://login.microsoftonline.com/" + session.getString("extractedUUID") + "/oauth2/v2.0/logoutsession")
                    .headers(Headers.getHeaders(2))
                    .formParam("sessionId", "#{sessionId}")
                    .formParam("canary", "#{canary}")
                    .formParam("hpgrequestid", "#{sessionId}")
                    .formParam("state", "")
                    .formParam("msaSession", "0")
                    .formParam("ctx", "")
                    .formParam("postLogoutRedirectUriValid", "0")
                    .formParam("post_logout_redirect_uri", "https://darts.test.apps.hmcts.net/auth/logout-callback")
                    .formParam("i19", "")
              )
              .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Login Microsoftonline - Oauth2 - v2.0 - Logoutsession"))
  
              .exitHereIfFailed()   
              .exec(
                  http("Darts-Portal - Auth - Internal - Logout-callback")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/internal/logout-callback?sid=#{sessionId}")                
                    .headers(Headers.getHeaders(5))
              )
              .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Internal - Logout-callback"))

              .exitHereIfFailed() 
              .exec(
                http("Darts-Portal - App - Config")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/app/config")
                    .headers(Headers.getHeaders(6))
                )
              .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - App - Config"))

              .exitHereIfFailed() 
              .exec(
                    http("Darts-Portal - Auth - Logout-callback")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/logout-callback")
                    .headers(Headers.getHeaders(0))
                  )
              .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Logout-callback"))
              
              .exitHereIfFailed() 
              .exec(
                    http("Darts-Portal - App - Config")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/app/config")
                        .headers(Headers.getHeaders(7))
              )
              .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - App - Config"))

              .exitHereIfFailed() 
            );
      }  
}