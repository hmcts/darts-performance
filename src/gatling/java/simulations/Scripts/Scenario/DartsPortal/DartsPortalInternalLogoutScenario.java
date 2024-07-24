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
                    .formParam("request", "eyJrZGZfdmVyIjoyLCJjdHgiOiJFQzhjTDhHbnVuU2taYkpUejN4ZWlkUHdmRUZ5QlU4MSIsImFsZyI6IkhTMjU2In0.eyJ3aW5fdmVyIjoiMTAuMC4xOTA0MS40NDc0Iiwic2NvcGUiOiJvcGVuaWQgYXphIHByb2ZpbGUgNmJjM2I5NTgtNjg5Yi00OWY1LTkwMDYtMzZkMTY1ZjMwZTAwXC8uZGVmYXVsdCBvZmZsaW5lX2FjY2VzcyBvcGVuaWQiLCJyZWZyZXNoX3Rva2VuIjoiMC5BUTBBak1iLXVTM0pIa2FhbHowRG9QR0xnb2M3cWpodG9CZElzblY2TVdtSTJUc05BR0EuQWdBQkF3RUFBQUFwVHdKbXpYcWRSNEJOMm1paGVRTVlBZ0RzX3dVQTlQXy1JOXQtR2d0U05ka0VucUtBZW8wUG5HbEY4SWRzN1I1eDl4cTFRMUhlOVp3S1dNOE84QjZVMUpyck80dnNIVkRjRjZBeFBaRDdETU5SYWsxX0dYdWJ2LURJNzlzWUUtbkNFb1RjMVAzYTJFVHRQajBuUzZhMGVmZzFUX1QxaXBEdmluTUdTbi1oYmhEZWRwMGs4bnh0clJRYVJ1eFRqRlRqeFkxQWl4VGw0aTZtOXo5YWxEbXF1S1E3V1ZESnMydVZqZm5ZMVd5aEFFaEpPZnIyeHlrSXd5bmItS2plekVzRXRVRUFvNERkcGhISG9ON3dmbHY5Wjl2LTBPM2VjWGY0c0JCbVVQS2J1V01YbHZtWHo5cUxGVzdJZi1mRXNKc2VRMld6Qi1VUk5TazJ6LWlubGdlOEZMaXZTUWRyTXFVQjRLX2Q0Z05KVEFzU0JaZVVUSXZQTk9fSEVlTGhhMVJYTWNaN3BNSjF0SWMzNG9ldUotLTZlb0loRzV6a2k5U3M1aXFjOXZDTUdEY2h3cS1ucm5FdllXcG51ZGlRQVVZQjljRHdmTHNlRnlsaUVFNmpIZzA0QzdFQ0RLNWpGWG83SUg3YlJuT2VtQS1uU3dHS2wwc1BDY2V0aG8tdjZlYWl5YW85TUkxbUs4b0hyX3NUMWFyS3RxTHY2M2JsV0dUNXU3ZFk5a1ZFN28tRWpLbUNLa21sRUZDZWdqZXQybEpJc2MzM0s1MzR6LTF3X0NhNTJJY3JiSTNWNUk0Y2pkWXdESWlqOG9Pd1h5NHZOYzF2N1NUbjVaUS1SaWUyQk1YOFBXNWpKSXJWZldVdUxEZlZhNmlqdWNOM09MdThSWGl6b0lPYkdyOW5ydXlMN1djbnRDSWJTWXlKNDF2cmpJTWEwUGtkd2taUUp2aEZwMENJV0Y0T0JXeHpCWXNrVHFKZHpMNjhqMTZocHd6MWZqd0YtRVBONUl0NWpNTV9lYW1DMWZaZUYtb1VhNUp1Q2t5UGQ4SEtNZ2hvYnRQR1FqSk56dk44aFJmR2VjM0x6eHVRRHEzTWFwYnNVek5HTGdUTmMwZW1tYk00d19Lbl84THJnYjBBTW0yaE1yNzVPOHg3QzJmV1FfQ055QzY3LTNJVkd6bXVDOUJLX1RhU2xCdEtBdmFQMUNGNW9UUGpOVG15cWsxZ1hraFVacm85b1pycnJ6MEZyS3VkR0hoa1ctQ2NFSThqaTVVOE9rNEtMS3M5dmhZOXAzVVU1WW1fMHR0cllSZ1gtSWhsMTZYa1pkZGNFdUMwQmg2VkFKQTdfNTB0bUI4TnlLdGR1SGoyUUxrTjg1YlNPSVlKTERNR0E0dTluR2RzYmhGNmpadEMyOUtkMFNMVWc0NCIsInJlZGlyZWN0X3VyaSI6Im1zLWFwcHgtd2ViOlwvXC9NaWNyb3NvZnQuQUFELkJyb2tlclBsdWdpblwvMWZlYzhlNzgtYmNlNC00YWFmLWFiMWItNTQ1MWNjMzg3MjY0IiwiaXNzIjoiYWFkOmJyb2tlcnBsdWdpbiIsImlhdCI6MTcyMTgyNjcxNSwiZ3JhbnRfdHlwZSI6InJlZnJlc2hfdG9rZW4iLCJjbGllbnRfaWQiOiIxZmVjOGU3OC1iY2U0LTRhYWYtYWIxYi01NDUxY2MzODcyNjQiLCJhdWQiOiJsb2dpbi5taWNyb3NvZnRvbmxpbmUuY29tIn0.tarZ6oj7wQ2f7gdZC_OavL5oDKjgbTIvZrbgFlma1KM")
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