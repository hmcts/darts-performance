package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.UserInfoLogger;
import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalExternalLogoutScenario {


    private DartsPortalExternalLogoutScenario() {}

    public static ChainBuilder DartsPortalExternalLogoutRequest() {
        return group("Darts Portal External Logout")
            .on(
              exec(
                http("Darts-Portal - Auth - Logout")
                .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/logout")
                .headers(Headers.portalLoginHeaders(Headers.PortalCommonHeaders))
              )  
              .exec(UserInfoLogger.logUserInfoOnFailure("Darts-Portal - Auth - Logout"))

              .exitHereIfFailed() 
              .exec(
                    http("Darts-Portal - Auth - Logout-callback")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/logout-callback")
                    .headers(Headers.portalLoginHeaders(Headers.PortalCommonHeaders))                   
              )
              .exec(UserInfoLogger.logUserInfoOnFailure("Darts-Portal - Auth - Logout-callback"))

              .exitHereIfFailed() 
              .exec(
                  http("Darts-Portal - App - Config")
                      .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/app/config")
                      .headers(Headers.DartsPortalHeaders4)
              )
              .exec(UserInfoLogger.logUserInfoOnFailure("Darts-Portal - App - Config"))

              .exitHereIfFailed() 
            );
      }  
}