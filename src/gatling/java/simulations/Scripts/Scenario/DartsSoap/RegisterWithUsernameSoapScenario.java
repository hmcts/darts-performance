package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;

public final class RegisterWithUsernameSoapScenario {

    private static final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();
    private RegisterWithUsernameSoapScenario() {}
    public static ChainBuilder RegisterWithUsernameSoap() {
        return group("Register With Username SOAP Request Group")
            .on(exec(feed(feeder))
                .exec(session -> {
                    String xmlPayload = SOAPRequestBuilder.RegisterWithUsernameSOAPRequest(session);
                    return session.set("xmlPayload", xmlPayload);
                })
                .exec(http("DARTS - GateWay - Soap - RegisterWithUsername")
                        .post(SoapServiceEndpoint.ContextRegistryService.getEndpoint())
                        .headers(Headers.SoapHeaders)
                        .body(StringBody(session -> session.get("xmlPayload")))
                        .check(status().is(200))
                        .check(Feeders.saveRegistrationToken()) 
            ));
    } 
}
