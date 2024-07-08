package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class GetApiTokenScenario {

  private static final String GROUP_NAME = "B2C_1_Ropc_Darts_Signin - Token";
  private GetApiTokenScenario() {}

    public static ChainBuilder getApiToken() {
      return group(GROUP_NAME + "DARTS - Api - B2C - Oauth2 - Token:GET")
        .on(exec( 
              http("DARTS - Api - Token:GET")
                  .get(EnvironmentURL.B2B_Token.getUrl())
                  .headers(Headers.ApiHeaders)
                  .formParam("grant_type", EnvironmentURL.GRANT_TYPE.getUrl())
                  .formParam("client_id", EnvironmentURL.EXTERNAL_CLIENT_ID.getUrl())
                  .formParam("client_secret", EnvironmentURL.EXTERNAL_CLIENT_SECRET.getUrl())
                  .formParam("scope", EnvironmentURL.SCOPE.getUrl())
                  .formParam("username", EnvironmentURL.DARTS_API_GLOBAL_USERNAME.getUrl())
                  .formParam("password", EnvironmentURL.DARTS_API_PASSWORD.getUrl())
                  .check(Feeders.saveBearerToken())                    
                  ).exec(session -> {
              Object bearerToken = session.get("bearerToken");
              if (bearerToken != null) {
                  System.out.println("bearerToken: " + bearerToken.toString());
              } else {
                  System.out.println("No value saved using saveAs.");
              }
              return session;
          })
      );
   }
}