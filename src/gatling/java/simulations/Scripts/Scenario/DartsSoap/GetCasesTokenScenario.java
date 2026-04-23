package simulations.Scripts.Scenario.DartsSoap;

import io.gatling.javaapi.core.ChainBuilder;
import simulations.Scripts.Headers.Headers;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import simulations.Scripts.Utilities.Feeders;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public final class GetCasesTokenScenario {

    private GetCasesTokenScenario() {
    }

    public static ChainBuilder GetCaseToken() {
        return group("GetCase SOAP Request Group")
                .on(feed(Feeders.createCourtHouseAndCourtRooms())
                        .exec(session -> {
                            String xmlPayload = SOAPRequestBuilder.getCasesTokenRequest(session);
                            return session.set("xmlPayload", xmlPayload);
                        })
                        .exec(http("DARTS - GateWay - Soap - GetCase - Token")
                                .post(SoapServiceEndpoint.DARTSService.getEndpoint())
                                .headers(Headers.SoapHeaders)
                                .body(StringBody(session -> session.get("xmlPayload")))
                                .check(status().is(200))
                                .check(xpath("//return/code").saveAs("statusCode"))
                                .check(xpath("//return/message").saveAs("message"))
                        ));
    }
}
