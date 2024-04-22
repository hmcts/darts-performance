
import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class AddCaseWithToken extends Simulation {

  {
    HttpProtocolBuilder httpProtocol = http
      .baseUrl("http://darts-gateway.test.platform.hmcts.net")
      .inferHtmlResources()
      .acceptEncodingHeader("gzip,deflate")
      .contentTypeHeader("text/xml;charset=UTF-8")
      .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");
    
    Map<CharSequence, String> headers_0 = new HashMap<>();
    headers_0.put("Client-Type", "SOAPUI Gateway Suite");
    headers_0.put("SOAPAction", "");


    ScenarioBuilder scn = scenario("AddCaseWithToken")
      .exec(
        http("request_0")
          .post("/service/darts")
          .headers(headers_0)
          .body(RawFileBody("addcasewithtoken/0000_request.dat"))
      );

	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
