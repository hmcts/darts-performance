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
      return group(GROUP_NAME +  "DARTS - Api - B2C - Oauth2 - Token:GET")
        .on(exec( 
              http("DARTS - Api - Token:GET")
                  .get(EnvironmentURL.B2B_Token.getUrl())
                  .headers(Headers.getHeaders(26))
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
                  System.out.println("bearerToken Created"); //+ bearerToken.toString());
              } else {
                  System.out.println("No bearer Token value saved.");
              }
              return session;
            })
          );
        }

        public static ChainBuilder getApiTokenDynamic() {
          return group(GROUP_NAME +  "DARTS - Api - B2C - Oauth2 - Token:GET")
              .on(exec(session -> {
                  // Retrieve Email and Password from the session
                  String email = session.getString("Email");
                  String password = session.getString("Password");
                  System.out.println("Email: " + email);
                  System.out.println("Password: " + password);
                  return session;
              })
              .exec( 
                http("DARTS - Api - Token:GET")
                    .get(EnvironmentURL.INTERNAL_B2B_Token.getUrl())
                    .headers(Headers.getHeaders(26))
                    .formParam("grant_type", EnvironmentURL.GRANT_TYPE.getUrl())
                    .formParam("client_id", EnvironmentURL.INTERNAL_CLIENT_ID.getUrl())
                    .formParam("client_secret", EnvironmentURL.INTERNAL_CLIENT_SECRET.getUrl())
                    .formParam("scope", EnvironmentURL.INTERNAL_SCOPE.getUrl())
                    .formParam("username", "#{Email}") // Use the #{Email} from the feeder
                    .formParam("password", "#{Password}") // Use the #{Password} from the feeder
                    .check(Feeders.saveBearerToken())                    
            )
                .exec(session -> {
                    Object bearerToken = session.get("bearerToken");
                    if (bearerToken != null) {
                      System.out.println("bearerToken Created"); //+ bearerToken.toString());
                    } else {
                        System.out.println("No bearer Token value saved.");
                    }
                    return session;
                })
        );   
    
   }
}