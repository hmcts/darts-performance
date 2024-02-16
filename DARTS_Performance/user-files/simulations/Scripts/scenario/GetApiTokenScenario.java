package scenario;

import java.util.*;

import Utilities.Feeders;
import Headers.Headers;
import Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class GetApiTokenScenario {

  private static final String GROUP_NAME = "Test";

  private GetApiTokenScenario() {}

    public static ChainBuilder getApiToken() {
      return group(GROUP_NAME + "DARTS - Api - Token:GET")
        .on(exec( 
              http("DARTS - Api - Token:GET")
                  .get(EnvironmentURL.B2B_Token.getUrl())
                  .headers(Headers.ApiHeaders)
                  .formParam("grant_type", EnvironmentURL.GRANT_TYPE.getUrl())
                  .formParam("client_id", EnvironmentURL.CLINET_ID.getUrl())
                  .formParam("client_secret", EnvironmentURL.CLIENT_SECRET.getUrl())
                  .formParam("scope", EnvironmentURL.SCOPE.getUrl())
                  .formParam("username", EnvironmentURL.DARTS_API_USERNAME.getUrl())
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