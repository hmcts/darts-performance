package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Utilities.*;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;

public final class AddCaseTokenScenario {

    private AddCaseTokenScenario() {}

    public static ChainBuilder addCaseToken() {
        return group("AddCase SOAP Request Group")
            .on(feed(Feeders.createCourtHouseAndCourtRooms())
            .exec(session -> {
                String xmlPayload = SOAPRequestBuilder.addCaseTokenRequest(session);
                return session.set("xmlPayload", xmlPayload);
            })
            .exec(http("DARTS - GateWay - Soap - AddCase - Token")
                .post(SoapServiceEndpoint.DARTSService.getEndpoint())
                //.headers(Headers.SoapHeaders)
                .header("SOAPAction", "\"\"")
                .header("Client-Type", "SOAPUI Gateway Suite") 
                .body(StringBody(session -> session.get("xmlPayload")))
                .check(status().is(200))
                .check(xpath("//messageId/text()").find().optional().saveAs("messageId"))
                .check(xpath("//return/code").saveAs("statusCode"))
                .check(xpath("//return/message").saveAs("message"))                
        )
        .exec(session -> {
            Object messageId = session.get("messageId");
            if (messageId != null) {
                System.out.println("messageId for AddCase request: " + messageId.toString());
            } else {
                System.out.println("Created AddCase request.");
            }
            return session;
            })
        );
    } 
}