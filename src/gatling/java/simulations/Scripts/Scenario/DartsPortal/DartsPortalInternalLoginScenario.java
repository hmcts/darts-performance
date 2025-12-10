package simulations.Scripts.Scenario.DartsPortal;

import io.gatling.javaapi.core.ChainBuilder;
import lombok.extern.slf4j.Slf4j;
import simulations.Scripts.Headers.Headers;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.UserInfoLogger;
import simulations.Scripts.Utilities.NumberGenerator;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import simulations.Scripts.Utilities.Util;

@Slf4j
public final class DartsPortalInternalLoginScenario {

    private DartsPortalInternalLoginScenario() {}

    public static ChainBuilder DartsPortalInternalLoginRequest() {

        return group("Darts Portal Internal Login")
            .on(   
                exec(
                    http("Darts-Portal - Login")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/login")
                        .headers(Headers.getHeaders(0))
                        )
                .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Login"))
                
                .exitHereIfFailed()
                .exec(
                    http("Darts-Portal - App - Config")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/App/Config")
                        .headers(Headers.getHeaders(0))
                    )
                .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - App - Config"))
                .exitHereIfFailed() 

                ///This Gets changed and causes internal users to fail log in. If this happens manually go into the portal with 1 user and grab it from the cookies. 
                .exec(
                    addCookie(Cookie("brcap",
                      "0; " +
                      "MicrosoftApplicationsTelemetryDeviceId=47678720-1774-4ebc-a900-ba48cc6ddc32; " +
                      "ESTSSSOTILES=1; " +
                      "AADSSOTILES=1; " +
                      "MSFPC=GUID=3eb0c7bd1f474492906bee53a7f9f0a3&HASH=3eb0&LV=202309&V=4&LU=1694096158878; " +
                      "x-ms-gateway-slice=estsfd; " +
                      "stsservicecookie=estsfd; " +
                      "AADSSO=NA|NoExtension; " +
                      "CCState=Q3VFQkNoWnpkR1Z3YUdWdUxtOXJaV1ZtWlVCaloya3VZMjl0RWdFQklna0pnTkZKTzQ1OTNVZ3FDUW1oOTZsa0c3dmRTRElxQ2hJS0VOSlpwY21yZWhOUHB1M242Y1VxN0ljU0NRbGcxZWVJbXJYZFNCb0pDZVYzaUx3L3M5MUlNaW9LRWdvUXJJU2JTU0VUZjBLcUZ5WjhwcGRYbUJJSkNjYXNvaitBdHQxSUdna0pBeFJIY3lXMDNVZ3lLZ29TQ2hCa09KamRIUXM5U28wZmFKOUhqczZpRWdrSm9qVzFDNFMyM1VnYUNRbWhXbFUvS2JUZFNEZ0FTQUZTRWdvUWpNYit1UzNKSGthYWx6MERvUEdMZ2xvU0NoQ0tpbllJNThGSVQ1Smo5S1RJVHZtZENvc0JDaGh6ZEdWd2FHVnVMbTlyWldWbVpVQm9iV04wY3k1dVpYUVNBUUVpQ1FrQTN5V3pmU0hjU0NvSkNhQzExNXhTdk4xSU1pb0tFZ29RL0tUdUIxbFdra21RTnhBQzFDdWlCUklKQ1F0SGlzRFJ0dDFJR2drSnNEVzI5SGEwM1VnNEFVZ0FVaElLRUczNUgxUHBDaXBHalMyK3g4QzBJSUphRWdvUUJhVmt6emE0SkVtZ3ExR2JEWDVRdFJJU0NoQnBSVm5jbnR5WlRLeSt0RHBYMXVTeEdna0oxak8yOUhhMDNVZ3lwQUVDQUFFZkFRQUFBRld0S2w2NVpxWkZxYmFNQVhWZEZBUURBT3ovQlFEMC8vL2RBZlpaOEJWZ0dTK3lnZis2ajF3TmJnb0FyanR0akFpQko3d0pXOHlDOVIxUFo0SWxQamxXQnA0eGRkUEJzcjRaZFExcXVPWXU4bHNPMDJsbWJoTWh6RXJxZ1o5NFVEVFN5Qi91anNjUmIwUDFpYjllTDR4M2s3cEZydjdZM21ZK1hSYVBKa1ZrKzVUaEZDS2YvQTlTVWFZMzdGRUJaRFFLa1dRRlFIZVdHSERWWHc9PQ=="
                    ))
                  )


                .exec(http("Darts-Portal - Auth - Internal - Login")
                .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/internal/login")
                .headers(Headers.getHeaders(27))
                .check(regex("(?s).*\"sessionId\":\"([^\"]+)\".*").findRandom().saveAs("sessionId"))
                .check(regex("(?s).*client-request-id=([a-fA-F0-9-]+).*").findRandom().optional().saveAs("clientRequestId"))
                .check(regex("(?s).*\"sCtx\":\"([^\"]+)\".*").findRandom().optional().saveAs("sCtx"))
                .check(regex("(?s).*\"sFT\":\"([^\"]+)\".*").findRandom().optional().saveAs("flowToken"))
                .check(regex("(?s).*\"canary\":\"([^\"]+)\".*").findRandom().optional().saveAs("canary"))
                .check(status().is(200))
                .check(status().saveAs("status"))
             )

            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Internal - Login"))

            .exitHereIfFailed() 
            .exec(session -> {
                String sessionId = session.getString("sessionId");
                String clientRequestId = session.getString("clientRequestId");
                String sCtx = session.getString("sCtx");
                String email = session.getString("Email");
                String flowToken = session.getString("flowToken");

                if (sessionId != null) {
                    log.info("Session ID: " + sessionId);
                    if (clientRequestId != null) {
                        log.info("Client Request ID: " + clientRequestId);
                    } else {
                        log.info("Client Request ID not found in response.");
                    }
                    if (sCtx != null) {
                        log.info("sCtx: " + sCtx);
                    }
                     else {
                        log.info("sCtx not found in response. User:" + email);
                    }
                    if (flowToken != null) {
                        log.info("Flow Token: " + flowToken);
                    } else {
                        log.info("Flow Token not found in response.");
                    }
                } else {
                    log.info("No value saved for sessionId using saveAs.");
                }
                return session;
            })
            .exec(session -> {
                String xmlPayload = RequestBodyBuilder.buildGetCredentialType(session);
                return session.set("xmlPayload", xmlPayload);
            })
            .exec(http("Darts-Portal - Login Microsoftonline - Common - GetCredentialType")
                .post("https://login.microsoftonline.com/common/GetCredentialType?mkt=en-US")
                .headers(Headers.getHeaders(28))
                .body(StringBody("#{xmlPayload}")).asJson()
                .check(status().is(200))
                .check(status().saveAs("status"))
                .check(bodyString().saveAs("responseBody"))        
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Login Microsoftonline - Common - GetCredentialType"))

            .exitHereIfFailed() 
            .pause(Util.getDurationFromSeconds(2), Util.getDurationFromSeconds(10))

            .exec(
                    addCookie(Cookie("brcap",
                      "0; " +
                      "MicrosoftApplicationsTelemetryDeviceId=47678720-1774-4ebc-a900-ba48cc6ddc32; " +
                      "ESTSSSOTILES=1; " +
                      "AADSSOTILES=1; " +
                      "MSFPC=GUID=3eb0c7bd1f474492906bee53a7f9f0a3&HASH=3eb0&LV=202309&V=4&LU=1694096158878; " +
                      "x-ms-gateway-slice=estsfd; " +
                      "stsservicecookie=estsfd; " +
                      "AADSSO=NA|NoExtension; " +
                      "CCState=Q3VFQkNoWnpkR1Z3YUdWdUxtOXJaV1ZtWlVCaloya3VZMjl0RWdFQklna0pnTkZKTzQ1OTNVZ3FDUW1oOTZsa0c3dmRTRElxQ2hJS0VOSlpwY21yZWhOUHB1M242Y1VxN0ljU0NRbGcxZWVJbXJYZFNCb0pDZVYzaUx3L3M5MUlNaW9LRWdvUXJJU2JTU0VUZjBLcUZ5WjhwcGRYbUJJSkNjYXNvaitBdHQxSUdna0pBeFJIY3lXMDNVZ3lLZ29TQ2hCa09KamRIUXM5U28wZmFKOUhqczZpRWdrSm9qVzFDNFMyM1VnYUNRbWhXbFUvS2JUZFNEZ0FTQUZTRWdvUWpNYit1UzNKSGthYWx6MERvUEdMZ2xvU0NoQ0tpbllJNThGSVQ1Smo5S1RJVHZtZENvc0JDaGh6ZEdWd2FHVnVMbTlyWldWbVpVQm9iV04wY3k1dVpYUVNBUUVpQ1FrQTN5V3pmU0hjU0NvSkNhQzExNXhTdk4xSU1pb0tFZ29RL0tUdUIxbFdra21RTnhBQzFDdWlCUklKQ1F0SGlzRFJ0dDFJR2drSnNEVzI5SGEwM1VnNEFVZ0FVaElLRUczNUgxUHBDaXBHalMyK3g4QzBJSUphRWdvUUJhVmt6emE0SkVtZ3ExR2JEWDVRdFJJU0NoQnBSVm5jbnR5WlRLeSt0RHBYMXVTeEdna0oxak8yOUhhMDNVZ3lwQUVDQUFFZkFRQUFBRld0S2w2NVpxWkZxYmFNQVhWZEZBUURBT3ovQlFEMC8vL2RBZlpaOEJWZ0dTK3lnZis2ajF3TmJnb0FyanR0akFpQko3d0pXOHlDOVIxUFo0SWxQamxXQnA0eGRkUEJzcjRaZFExcXVPWXU4bHNPMDJsbWJoTWh6RXJxZ1o5NFVEVFN5Qi91anNjUmIwUDFpYjllTDR4M2s3cEZydjdZM21ZK1hSYVBKa1ZrKzVUaEZDS2YvQTlTVWFZMzdGRUJaRFFLa1dRRlFIZVdHSERWWHc9PQ=="
                    ))
                  )
            .exec(
                http("Darts-Portal - Login")
                .post("/e575f663-b30a-4786-89ad-319842dfe853/login")
                .headers(Headers.getHeaders(29))
                .formParam("i13", "0")
                .formParam("login", "#{Email}")
                .formParam("loginfmt", "#{Email}")
                .formParam("type", "11")
                .formParam("LoginOptions", "3")
                .formParam("lrt", "")
                .formParam("lrtPartition", "")
                .formParam("hisRegion", "")
                .formParam("hisScaleUnit", "")
                .formParam("passwd", "#{Password}")
                .formParam("ps", "2")
                .formParam("psRNGCDefaultType", "")
                .formParam("psRNGCEntropy", "")
                .formParam("psRNGCSLK", "")
                .formParam("canary", "#{canary}")
                .formParam("ctx", "#{sCtx}")
                .formParam("hpgrequestid", "#{sessionId}")
                .formParam("flowToken", "#{flowToken}")
                .formParam("PPSX", "")
                .formParam("NewUser", "1")
                .formParam("FoundMSAs", "")
                .formParam("fspost", "0")
                .formParam("i21", "0")
                .formParam("CookieDisclosure", "0")
                .formParam("IsFidoSupported", "1")
                .formParam("isSignupPost", "0")
                .formParam("DfpArtifact", "")
                .formParam("i19", "3306")

                .check(regex("(?s).*\"sessionId\":\"([^\"]+)\".*").findRandom().saveAs("sessionId"))
                .check(regex("(?s).*client-request-id=([a-fA-F0-9-]+).*").findRandom().optional().saveAs("clientRequestId"))
                .check(regex("(?s).*\"sCtx\":\"([^\"]+)\".*").findRandom().optional().saveAs("sCtx"))
                .check(regex("(?s).*\"sFT\":\"([^\"]+)\".*").findRandom().optional().saveAs("flowToken"))
                .check(regex("(?s).*\"canary\":\"([^\"]+)\".*").findRandom().optional().saveAs("canary"))

                // .check(regex("name=\"code\" value=\"([^\"]*)\"").saveAs("codeToken"))
                // .check(regex("name=\"session_state\" value=\"([^\"]*)\"").saveAs("sessionState"))
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Login", "code=\"([^\"]*)\"", "#{responseBody}"))
            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - MicrosoftOnline - KMSI")
                .post("https://login.microsoftonline.com/kmsi")
                .headers(Headers.getHeaders(30))
                .formParam("LoginOptions", "3")
                .formParam("type", "28")
                .formParam("ctx", "#{sCtx}")
                .formParam("hpgrequestid", "#{sessionId}")
                .formParam("flowToken", "#{flowToken}")
                .formParam("canary", "#{canary}")
                .formParam("i19", "7178")
                .check(status().in(200, 302))
                .check(regex("name=\"code\" value=\"([^\"]*)\"").optional().saveAs("codeToken"))
                .check(regex("name=\"session_state\" value=\"([^\"]*)\"").optional().saveAs("sessionState"))
            )

            .exec(   
                http("Darts-Portal - Auth - Internal - Callback")
                    .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/internal/callback")
                    .headers(Headers.getHeaders(4))
                    .formParam("code", "#{codeToken}")
                    .formParam("session_state", "#{sessionState}")
                    .check(status().is(200))
                    .check(status().saveAs("status"))
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Internal - Callback"))

            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - App - Config")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/app/config")
                    .headers(Headers.getHeaders(15))
                    .check(status().is(200))
                    .check(status().saveAs("status"))
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - App - Config"))

            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - Auth - Is-authenticated")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
                    .headers(Headers.getHeaders(14))
                )

            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - Auth - Is-authenticated")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
                    .headers(Headers.getHeaders(14))
                )

            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - User - Profile")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/profile")
                    .headers(Headers.getHeaders(15))
                    .check(Feeders.saveUserId())
            )
            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
                    .headers(Headers.getHeaders(15))
                    )
            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - Api - Courthouses")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/courthouses")
                    .headers(Headers.getHeaders(15))
                    .check(status().is(200))
                    .check(status().saveAs("status"))
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Courthouses"))

            .exitHereIfFailed()
        );
    }
}
