package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;

public final class AddDocumentDailyListTokenScenario {

    private AddDocumentDailyListTokenScenario() {}
    public static ChainBuilder AddDocumentDailyListToken() {
        return group("AddDocument SOAP Request Group")
            .on(feed(Feeders.createCourtHouseAndCourtRooms())   
            .exec(session -> {
                    String xmlPayload = SOAPRequestBuilder.AddDocumentDailyListTokenRequest(session);  
                    return session.set("xmlPayload", xmlPayload);  
                })
                .exec(http("DARTS - GateWay - Soap - AddDocument - DailyList - Token")
                        .post(SoapServiceEndpoint.StandardService.getEndpoint())
                        .headers(Headers.SoapHeaders)
                        .body(StringBody(session -> session.get("xmlPayload")))
                        .check(status().is(200))
                        .check(xpath("//messageId/text()").find().optional().saveAs("messageId"))
                        .check(xpath("//return/code").saveAs("statusCode"))
                        .check(xpath("//return/message").saveAs("message"))
                )
                .exec(session -> {
                    Object messageId = session.get("messageId");
                    if (messageId != null) {
                        System.out.println("messageId: " + messageId.toString());
                    } else {
                        System.out.println("No value for messageId.");
                    }
                    return session;
                })
            );
    } 
}