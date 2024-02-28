package Scenario.DartsSoap;

import Headers.Headers;
import Utilities.AppConfig;
import Utilities.AppConfig.SoapServiceEndpoint;
import Utilities.Feeders;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import SOAPRequestBuilder.SOAPRequestBuilder;

public final class RegisterWithTokenSoapScenario {

    private static final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();
    private RegisterWithTokenSoapScenario() {}
    public static ChainBuilder RegisterWithTokenSoap() {
        return group("Register With Token SOAP Request Group")
            .on(exec(feed(feeder))
                .exec(session -> {
                    String xmlPayload = SOAPRequestBuilder.RegisterWithTokenSOAPRequest(session);
                    return session.set("xmlPayload", xmlPayload);
                })
                .exec(http("DARTS - GateWay - Soap - RegisterWithToken")
                        .post(SoapServiceEndpoint.ContextRegistryService.getEndpoint())
                        .headers(Headers.SoapHeaders)
                        .body(StringBody(session -> session.get("xmlPayload")))
                        .check(status().is(200))
                        .check(Feeders.saveRegistrationToken()) 
            ));
    } 
}
