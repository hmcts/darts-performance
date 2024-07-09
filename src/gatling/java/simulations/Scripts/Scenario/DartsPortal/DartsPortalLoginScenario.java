package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.Session;
import scala.util.Random;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalLoginScenario {

    private static final Random randomNumber = new Random();

    private DartsPortalLoginScenario() {}

    public static ChainBuilder DartsPortalLoginRequest() {
        return group("Darts Portal Login")
            .on(
                exec(session -> {
                    // Get the user type from the session
                    String userType = session.getString("Type");
                    System.out.println("UserType for Audio Request: " + userType);

                    String loginUser;
                    String clientId;

                    // Determine request type and client ID based on user type
                    switch (userType.toLowerCase()) {
                        case "transcriber":
                        case "languageshop":
                            loginUser = "External";
                            clientId = AppConfig.EnvironmentURL.EXTERNAL_AZURE_AD_B2C_CLIENT_ID.getUrl();
                            break;
                        case "CourtClerk":
                        case "CourtManager":
                            loginUser = "Internal";
                            clientId = AppConfig.EnvironmentURL.INTERNAL_AZURE_AD_B2C_CLIENT_ID.getUrl();
                            break;
                        default:
                            loginUser = "Internal";
                            clientId = AppConfig.EnvironmentURL.INTERNAL_AZURE_AD_B2C_CLIENT_ID.getUrl();
                            break;
                    }

                    // Set login user type and client ID in the session
                    session = session.set("loginUser", loginUser).set("clientId", clientId);
                    System.out.println("Login User is: " + loginUser);
                    System.out.println("Client ID is: " + clientId);

                    return session;
                })
                .exec(
                    http("Darts-Portal - Auth - Login")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/login")
                        .headers(Headers.portalLoginHeaders(Headers.PortalCommonHeaders))
                )
                .exec(session -> {
                    // Use the client ID from the session
                    String clientId = session.getString("clientId");

                    return session.set("clientIdUrl", AppConfig.EnvironmentURL.B2B_Login.getUrl() + AppConfig.EnvironmentURL.DARTS_PORTAL_Auth_LOGIN.getUrl()
                        + "?client_id=" + clientId
                        + "&redirect_uri=" + AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl()
                        + "/auth/callback&scope=openid&prompt=login&response_mode=form_post&response_type=code");
                })
                .exec(
                    http("B2C_1_darts_externaluser_signin - Oauth2/v2.0 - Authorize")
                        .get("#{clientIdUrl}")
                        .headers(Headers.portalLoginHeaders(Headers.PortalCommonHeaders))
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
                        .headers(Headers.AzureadB2cLoginHeaders(Headers.PortalCommonHeaders))
                )
                .pause(3)
                .exec(
                    http("B2C_1_darts_externaluser_signin - Client - Perftrace")
                        .post(AppConfig.EnvironmentURL.DARTS_PORTAL_SIGNIN.getUrl() + "/client/perftrace?tx=StateProperties=#{stateProperties}&p=B2C_1_darts_externaluser_signin")
                        .headers(Headers.PerftraceHeaders(Headers.PortalCommonHeaders))
                        .body(RawFileBody("perftrace/0000_request.bin"))
                )
                .exec(
                    http("B2C_1_darts_externaluser_signin - SelfAsserted")
                        .post(AppConfig.EnvironmentURL.DARTS_PORTAL_SIGNIN.getUrl() + "/SelfAsserted?tx=StateProperties=#{stateProperties}&p=B2C_1_darts_externaluser_signin")
                        .headers(Headers.headers_0)
                        .formParam("request_type", "RESPONSE")
                        .formParam("email", "#{Email}")
                        .formParam("password", "#{Password}")
                        .check(status().is(200))
                )
                .exitHereIfFailed()
                .exec(
                    http("B2C_1_darts_externaluser_signin - Api - CombinedSigninAndSignup - Confirmed")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_SIGNIN.getUrl() + "/api/CombinedSigninAndSignup/confirmed?rememberMe=false&csrf_token=#{csrf}&tx=StateProperties=#{stateProperties}&p=B2C_1_darts_externaluser_signin&diags=%7B%22pageViewId%22%3A%223ec520ab-1a56-4387-a71c-8f4357eb169d%22%2C%22pageId%22%3A%22CombinedSigninAndSignup%22%2C%22trace%22%3A%5B%7B%22ac%22%3A%22T005%22%2C%22acST%22%3A1708515180%2C%22acD%22%3A2%7D%2C%7B%22ac%22%3A%22T021%20-%20URL%3A" + AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "%2Fauth%2Fazuread-b2c-login%3FscreenName%3DloginScreen%26ui_locales%3Den%22%2C%22acST%22%3A1708515180%2C%22acD%22%3A267%7D%2C%7B%22ac%22%3A%22T019%22%2C%22acST%22%3A1708515180%2C%22acD%22%3A3%7D%2C%7B%22ac%22%3A%22T004%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A2%7D%2C%7B%22ac%22%3A%22T003%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A1%7D%2C%7B%22ac%22%3A%22T035%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A0%7D%2C%7B%22ac%22%3A%22T030Online%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A0%7D%2C%7B%22ac%22%3A%22T035%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A0%7D%2C%7B%22ac%22%3A%22T002%22%2C%22acST%22%3A1708515190%2C%22acD%22%3A0%7D%2C%7B%22ac%22%3A%22T018T010%22%2C%22acST%22%3A1708515189%2C%22acD%22%3A608%7D%5D%7D")
                        .headers(Headers.portalLoginHeaders(Headers.PortalCommonHeaders))
                        .check(Feeders.saveTokenCode())
                )
                .exec(
                    http("Darts-Portal - Auth - Callback")
                        .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/callback")
                        .headers(Headers.DartsPortalHeaders3(Headers.PortalCommonHeaders))
                        .formParam("code", "#{TokenCode}")
                        .check(status().is(200))
                )
                .exec(
                    http("Darts-Portal - App - Config")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/app/config")
                        .headers(Headers.DartsPortalHeaders4)
                )
                .exec(
                    http("Darts-Portal - Auth - Is-authenticated")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
                        .headers(Headers.DartsPortalHeaders4)
                )
                .exec(
                    http("Darts-Portal - Auth - Is-authenticated")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
                        .headers(Headers.DartsPortalHeaders4)
                )
                .exec(
                    http("Darts-Portal - User - Profile")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/profile")
                        .headers(Headers.DartsPortalHeaders4)
                        .check(Feeders.saveUserId())
                )
                .exec(
                    http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
                        .headers(Headers.DartsPortalHeaders4)
                )
                .exec(
                    http("Darts-Portal - Api - Courthouses")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/courthouses")
                        .headers(Headers.DartsPortalHeaders5)
                )
            );
    }
}
