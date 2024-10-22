package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.UserInfoLogger;
import io.gatling.javaapi.core.*;
import scala.util.Random;
import simulations.Scripts.Utilities.NumberGenerator;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
public final class DartsPortalExternalLoginScenario {

    private static final Random randomNumber = new Random();

    private DartsPortalExternalLoginScenario() {}

    public static ChainBuilder DartsPortalExternalLoginRequest() {      
        return group("Darts Portal External Login")
            .on(
            exec(
                http("Darts-Portal - Auth - Login")
                  .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/login")
                  .headers(Headers.getHeaders(0))
                  )  
                .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Login"))
            
              .exec(
                http("B2C_1_darts_externaluser_signin - Oauth2/v2.0 - Authorize")
                  .get(AppConfig.EnvironmentURL.B2B_Login.getUrl() + AppConfig.EnvironmentURL.DARTS_PORTAL_Auth_LOGIN.getUrl() + 
                  "?client_id=" + AppConfig.EnvironmentURL.EXTERNAL_CLIENT_ID.getUrl() +
                  "&redirect_uri=" + AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + 
                  "/auth/callback&scope=openid&prompt=login&response_mode=form_post&response_type=code")
                  .headers(Headers.getHeaders(0))
                  .check(Feeders.saveStateProperties())
                  .check(Feeders.saveCsrf())          
                )
                .exec(UserInfoLogger.logDetailedErrorMessage("B2C_1_darts_externaluser_signin - Oauth2/v2.0 - Authorize"))

              .exitHereIfFailed() 
              .exec(session -> {
                Object stateProperties = session.get("stateProperties");
                System.out.println("Extracted StateProperties: " + stateProperties);
                return session;
              })
              .exec(
                http("Darts-Portal - Auth - Azuread-b2c-login")
                  .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/azuread-b2c-login?screenName=loginScreen&ui_locales=en")
                  .headers(Headers.getHeaders(17))
              )
              .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Azuread-b2c-login"))

              // .exec(session -> {
              //   String xmlPayload = RequestBodyBuilder.buildDartsPortalPerftraceRequest(session);
              //   return session.set("xmlPayload", xmlPayload);
              // })
              .exitHereIfFailed() 
              .pause(2, 5)
              .exec(
                http("B2C_1_darts_externaluser_signin - Client - Perftrace")
                  .post(AppConfig.EnvironmentURL.DARTS_PORTAL_SIGNIN.getUrl() + "/client/perftrace?tx=StateProperties=#{stateProperties}&p=B2C_1_darts_externaluser_signin")
                  .headers(Headers.getHeaders(19))
                  //.body(StringBody(session -> session.get("xmlPayload"))).asJson()
                  .body(RawFileBody("perftrace/0000_request.bin"))
                )
                .exec(UserInfoLogger.logDetailedErrorMessage("B2C_1_darts_externaluser_signin - Client - Perftrace"))  
                .exitHereIfFailed() 
                .exec(session -> {
                  String email = session.getString("Email");
                  String password = session.getString("Password");
                  String userName = session.getString("user_name");
                  System.out.println("Email: " + email);
                  System.out.println("Password: " + password);
                  System.out.println("User Name: " + userName);
                  return session;
              })
              .exec(UserInfoLogger.logDetailedErrorMessage("B2C_1_darts_externaluser_signin - SelfAsserted"))
              .exitHereIfFailed() 
                .exec(
                  http("B2C_1_darts_externaluser_signin - SelfAsserted")
                    .post(AppConfig.EnvironmentURL.DARTS_PORTAL_SIGNIN.getUrl() + "/SelfAsserted?tx=StateProperties=#{stateProperties}&p=B2C_1_darts_externaluser_signin")
                   // .headers(Headers.DartsPortalHeaders21)
                   .headers(Headers.getHeaders(21))
                   .formParam("request_type", "RESPONSE")
                    .formParam("email", "#{Email}")
                    .formParam("password", "#{Password}")
                    .check(status().is(200))
                    .check(status().saveAs("status"))
                )
                .exitHereIfFailed()
                .exec(
                    http("B2C_1_darts_externaluser_signin - Api - CombinedSigninAndSignup - Confirmed")
                      .get(AppConfig.EnvironmentURL.DARTS_PORTAL_SIGNIN.getUrl() + "/api/CombinedSigninAndSignup/confirmed?rememberMe=false&csrf_token=#{csrf}&tx=StateProperties=#{stateProperties}&p=B2C_1_darts_externaluser_signin&diags=%7B%22pageViewId%22%3A%223ec520ab-1a56-4387-a71c-8f4357eb169d%22%2C%22pageId%22%3A%22CombinedSigninAndSignup%22%2C%22trace%22%3A%5B%7B%22ac%22%3A%22T005%22%2C%22acST%22%3A1708515180%2C%22acD%22%3A2%7D%2C%7B%22ac%22%3A%22T021%20-%20URL%3A" + AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "%2Fauth%2Fazuread-b2c-login%3FscreenName%3DloginScreen%26ui_locales%3Den%22%2C%22acST%22%3A1708515180%2C%22acD%22%3A267%7D%2C%7B%22ac%22%3A%22T019%22%2C%22acST%22%3A1708515180%2C%22acD%22%3A3%7D%2C%7B%22ac%22%3A%22T004%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A2%7D%2C%7B%22ac%22%3A%22T003%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A1%7D%2C%7B%22ac%22%3A%22T035%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A0%7D%2C%7B%22ac%22%3A%22T030Online%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A0%7D%2C%7B%22ac%22%3A%22T035%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A0%7D%2C%7B%22ac%22%3A%22T002%22%2C%22acST%22%3A1708515190%2C%22acD%22%3A0%7D%2C%7B%22ac%22%3A%22T018T010%22%2C%22acST%22%3A1708515189%2C%22acD%22%3A608%7D%5D%7D")
                      .headers(Headers.getHeaders(0))
                      .check(Feeders.saveTokenCode())
                )
                .exec(UserInfoLogger.logDetailedErrorMessage("B2C_1_darts_externaluser_signin - Api - CombinedSigninAndSignup - Confirmed"))

                .exitHereIfFailed() 
                .exec(
                  http("Darts-Portal - Auth - Callback")
                      .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/callback")
                      .headers(Headers.getHeaders(20))
                      .formParam("code", "#{TokenCode}")
                      .check(status().is(200))
                      .check(status().saveAs("status"))
                )

                .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Callback"))

                .exitHereIfFailed() 
                .exec(
                  http("Darts-Portal - App - Config")
                      .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/app/config")
                      .headers(Headers.getHeaders(15))
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