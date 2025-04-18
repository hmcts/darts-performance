package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;

public final class RegisterWithTokenScenario 
{
    private RegisterWithTokenScenario() {}

    public static ChainBuilder registerWithToken(String userName, String password) {
        return group("Register With Token SOAP Request Group")
        .on(exec(session -> {
                String xmlPayload = SOAPRequestBuilder.registerWithTokenRequest(session, userName, password);
                return session.set("xmlPayload", xmlPayload);
            })
            .exec(http("DARTS - GateWay - Soap - RegisterWithToken")
                .post(SoapServiceEndpoint.ContextRegistryService.getEndpoint())
                .headers(Headers.SoapHeaders)
                .header("Accept-Encoding", "gzip,deflate")
                .header("Content-Type", "text/xml;charset=UTF-8")
                .header("User-Agent", "Apache-HttpClient/4.5.5 (Java/16.0.2)")
                .body(StringBody(session -> session.get("xmlPayload")))
                .check(status().is(200))
                .check(Feeders.saveRegistrationToken())
            )
        );    
    } 
}
