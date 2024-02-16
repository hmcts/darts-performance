package DartsSoap;

import Headers.Headers;
import SOAPRequestBuilder.SOAPRequestBuilder;
import Utilities.AppConfig;
import Utilities.AppConfig.EnvironmentURL;
import Utilities.AppConfig.SoapServiceEndpoint;

import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AddDocumentSOAPUser extends Simulation {

  private static final String BASE_URL = EnvironmentURL.GATEWAY_BASE_URL.getUrl();
  private static final String SERVICE = SoapServiceEndpoint.ContextRegistryService.getEndpoint();
  FeederBuilder<String> feeder = csv(AppConfig.COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH).random();
  {
    HttpProtocolBuilder httpProtocol = http
      .proxy(Proxy("127.0.0.1", 8888).httpsPort(8888))
      .baseUrl(BASE_URL)
      .inferHtmlResources()
      .acceptEncodingHeader("gzip,deflate")
      .contentTypeHeader("text/xml;charset=UTF-8")
      .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");
    
      Map<CharSequence, String> soapHeaders = Headers.SoapHeaders;

      ScenarioBuilder scn = scenario("AddDocumentSOAPUser")
      .feed(feeder) // Feed data from the CSV file
      .exec(session -> {
        // Build the SOAP request XML payload using the builder class
        String xmlPayload = SOAPRequestBuilder.AddDcoumentSOAPRequest(session);

        // Set the XML payload in the session
        return session.set("xmlPayload", xmlPayload);
    })
        .exec(
          http("DARTS - GateWay - Soap - AddDocument - User")
            .post(SERVICE)
            .headers(soapHeaders)
            .body(StringBody("#{xmlPayload}")) // Use XML payload from session
            .check(status().is(200))
            .check(xpath("//return/code").saveAs("statusCode"))
            .check(xpath("//return/message").saveAs("message"))
          );      
  
      setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}
