package Scenario.DartsPortal;

import Headers.Headers;
import Utilities.AppConfig;
import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalLogoutScenario {


    private DartsPortalLogoutScenario() {}

    public static ChainBuilder DartsPortalLogoutRequest() {
        return group("Darts Portal Logout")
            .on(
              exec(
                http("Darts-Portal - Auth - Logout")
                .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/logout")
                .headers(Headers.DartsPortalHeaders0)
                )  
              .exec(
                    http("Darts-Portal - Auth - Logout-callback")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/logout-callback")
                    .headers(Headers.DartsPortalHeaders0)                    
                  )
              .exec(session -> {
                  Object stateProperties = session.get("stateProperties");
                  System.out.println("Extracted StateProperties: " + stateProperties);
                  return session;
              })
              .exec(
                http("Darts-Portal - App - Config")
                  .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/app/config")
                  .headers(Headers.DartsPortalHeaders1)
              )
            );
      }  
}