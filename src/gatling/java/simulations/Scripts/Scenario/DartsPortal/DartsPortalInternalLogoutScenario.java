package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalInternalLogoutScenario {


    private DartsPortalInternalLogoutScenario() {}

    public static ChainBuilder DartsPortalInternalLogoutRequest() {
        return group("Darts Portal Logout")
            .on(
              exec(
                http("Darts-Portal - Auth - Logout")
                .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/logout")
                .headers(Headers.portalLoginHeaders(Headers.PortalCommonHeaders))
              ) 
              .exec(
                  http("Darts-Portal - Login Microsoftonline - Oauth2 - Token")
                    .post("https://login.microsoftonline.com/b9fec68c-c92d-461e-9a97-3d03a0f18b82/oauth2/token")
                    .headers(Headers.portalLogOutHeaders(Headers.PortalCommonHeaders))
                    .formParam("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer")
                    .formParam("request", "***REMOVED***")
                    .formParam("client_info", "1")
                    .formParam("windows_api_version", "2.0.1")
                    .formParam("wam_compat", "2.0")
                    .formParam("x-client-SKU", "MSAL.xplat.Win32")
                    .formParam("x-client-Ver", "1.1.0+00747db6")
                    .formParam("x-client-OS", "10.0.19041.3636")
                    .formParam("x-client-src-SKU", "MSAL.xplat.Win32")
                    .formParam("mkt", "en-gb")  
              )
              .exec(
                  http("Darts-Portal - Login Microsoftonline - Oauth2 - v2.0 - Logoutsession")
                    .post("https://login.microsoftonline.com//e575f663-b30a-4786-89ad-319842dfe853/oauth2/v2.0/logoutsession")
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
              .exec(
                  http("Darts-Portal - Auth - Internal - Logout-callback")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/internal/logout-callback?sid=#{sessionId}")                
                    .headers(Headers.getHeaders(5))
              )
              .exec(
                http("Darts-Portal - App - Config")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/app/config")
                    .headers(Headers.getHeaders(6))
                )
              .exec(
                    http("Darts-Portal - Auth - Logout-callback")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/logout-callback")
                    .headers(Headers.portalLoginHeaders(Headers.PortalCommonHeaders))                   
                  )
                  .exec(
                    http("Darts-Portal - App - Config")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/app/config")
                        .headers(Headers.getHeaders(7))
                  )
            );
      }  
}