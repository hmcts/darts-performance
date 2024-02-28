package scenario;

import Headers.Headers;
import RequestBodyBuilder.RequestBodyBuilder;
import Utilities.AppConfig;
import Utilities.Feeders;
import io.gatling.javaapi.core.*;
import scala.util.Random;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalLoginScenario {

   // private static final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();    
    private static final FeederBuilder<String> feeder = csv(AppConfig.DARTS_PORTAL_USERS_FILE_PATH).random();
    private static final Random randomNumber = new Random();

    private DartsPortalLoginScenario() {}

    public static ChainBuilder DartsPortalLoginRequest() {
        return group("Darts Portal Login")
            .on(exec(feed(feeder))
            .exec(
                http("Darts-Portal - Auth - Login")
                  .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/login")
                  .headers(Headers.DartsPortalHeaders0))
              )
              .exec(
                    http("B2C_1_darts_externaluser_signin - Oauth2/v2.0 - Authorize")
                      .get(AppConfig.EnvironmentURL.B2B_Login.getUrl() + AppConfig.EnvironmentURL.DARTS_PORTAL_Auth_LOGIN.getUrl() + 
                      "?client_id=" + AppConfig.EnvironmentURL.AZURE_AD_B2C_CLIENT_ID.getUrl() +
                      "&redirect_uri=" + AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + 
                      "/auth/callback&scope=openid&prompt=login&response_mode=form_post&response_type=code")
                      .headers(Headers.DartsPortalHeaders0)
                      .check(Feeders.saveStateProperties())
                      .check(Feeders.saveCsrf())          
                    )
                .exec(session -> {
                  Object stateProperties = session.get("stateProperties");
                  System.out.println("Extracted StateProperties: " + stateProperties);
                  return session;
              })
              .exec(
                http("Darts-Portal - Auth - Azuread-b2c-login")
                  .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/azuread-b2c-login?screenName=loginScreen&ui_locales=en")
                  .headers(Headers.DartsPortalHeaders1)
              )
              // .exec(session -> {
              //   String xmlPayload = RequestBodyBuilder.buildDartsPortalPerftraceRequest(session);
              //   return session.set("xmlPayload", xmlPayload);
              // })
              .exec(
                http("B2C_1_darts_externaluser_signin - Client - Perftrace")
                  .post(AppConfig.EnvironmentURL.DARTS_PORTAL_SIGNIN.getUrl() + "/client/perftrace?tx=StateProperties=#{stateProperties}&p=B2C_1_darts_externaluser_signin")
                  .headers(Headers.DartsPortalHeaders2)
                  //.body(StringBody(session -> session.get("xmlPayload"))
                  .body(RawFileBody("recordedsimulation/0002_request.bin"))
                )
                .pause(8)
                .exec(
                  http("B2C_1_darts_externaluser_signin - SelfAsserted")
                    .post(AppConfig.EnvironmentURL.DARTS_PORTAL_SIGNIN.getUrl() + "/SelfAsserted?tx=StateProperties=#{stateProperties}&p=B2C_1_darts_externaluser_signin")
                    .headers(Headers.DartsPortalHeaders21)
                    .formParam("request_type", "RESPONSE")
                    .formParam("email", AppConfig.EnvironmentURL.DARTS_API_USERNAME2.getUrl())
                    .formParam("password", AppConfig.EnvironmentURL.DARTS_API_PASSWORD2.getUrl())
                    .check(status().is(200)))
                .exec(
                    http("B2C_1_darts_externaluser_signin - Api - CombinedSigninAndSignup - Confirmed")
                      .get(AppConfig.EnvironmentURL.DARTS_PORTAL_SIGNIN.getUrl() + "/api/CombinedSigninAndSignup/confirmed?rememberMe=false&csrf_token=#{csrf}&tx=StateProperties=#{stateProperties}&p=B2C_1_darts_externaluser_signin&diags=%7B%22pageViewId%22%3A%223ec520ab-1a56-4387-a71c-8f4357eb169d%22%2C%22pageId%22%3A%22CombinedSigninAndSignup%22%2C%22trace%22%3A%5B%7B%22ac%22%3A%22T005%22%2C%22acST%22%3A1708515180%2C%22acD%22%3A2%7D%2C%7B%22ac%22%3A%22T021%20-%20URL%3A" + AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "%2Fauth%2Fazuread-b2c-login%3FscreenName%3DloginScreen%26ui_locales%3Den%22%2C%22acST%22%3A1708515180%2C%22acD%22%3A267%7D%2C%7B%22ac%22%3A%22T019%22%2C%22acST%22%3A1708515180%2C%22acD%22%3A3%7D%2C%7B%22ac%22%3A%22T004%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A2%7D%2C%7B%22ac%22%3A%22T003%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A1%7D%2C%7B%22ac%22%3A%22T035%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A0%7D%2C%7B%22ac%22%3A%22T030Online%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A0%7D%2C%7B%22ac%22%3A%22T035%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A0%7D%2C%7B%22ac%22%3A%22T002%22%2C%22acST%22%3A1708515190%2C%22acD%22%3A0%7D%2C%7B%22ac%22%3A%22T018T010%22%2C%22acST%22%3A1708515189%2C%22acD%22%3A608%7D%5D%7D")
                      .headers(Headers.DartsPortalHeaders0)
                      .check(Feeders.saveTokenCode()))
                .exec(
                  http("Darts-Portal - Auth - Callback")
                      .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/callback")
                      .headers(Headers.DartsPortalHeaders3)
                      .formParam("code", "#{TokenCode}"))
                .exec(
                  http("Darts-Portal - App - Config")
                      .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/app/config")
                      .headers(Headers.DartsPortalHeaders4))
                .exec(
                  http("Darts-Portal - Auth - Is-authenticated")
                      .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
                      .headers(Headers.DartsPortalHeaders4))
                .exec(   
                  http("Darts-Portal - Auth - Is-authenticated")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
                        .headers(Headers.DartsPortalHeaders4))
                .exec(    
                  http("Darts-Portal - User - Profile")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/profile")
                        .headers(Headers.DartsPortalHeaders4))
                .exec(     
                  
                  http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
                        .headers(Headers.DartsPortalHeaders4))
                .exec(    
                  http("Darts-Portal - Api - Courthouses")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/courthouses")
                        .headers(Headers.DartsPortalHeaders5)
                    );
            }   
}