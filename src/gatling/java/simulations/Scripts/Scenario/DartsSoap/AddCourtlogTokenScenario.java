package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;

public final class AddCourtlogTokenScenario {

    private AddCourtlogTokenScenario() {}
    public static ChainBuilder addCourtLogToken() {
        return group("Add CourtLog SOAP Request Group")
            .on(feed(Feeders.createCourtHouseAndCourtRooms()) 
            .exec(session -> {
                    String xmlPayload = SOAPRequestBuilder.addCourtLogTokenRequest(session);
                    return session.set("xmlPayload", xmlPayload);
                })
                .exec(http("DARTS - GateWay - Soap - Add CourtLog - Token")
                        .post(SoapServiceEndpoint.DARTSService.getEndpoint())
                        .headers(Headers.SoapHeaders)
                        .body(StringBody(session -> session.get("xmlPayload")))
                        .check(status().is(200))
                        .check(xpath("//return/code").saveAs("statusCode"))
                        .check(xpath("//return/message").saveAs("message"))
                        )
                        .exec(session -> {
                            Object messageId = session.get("messageId");
                            if (messageId != null) {
                                System.out.println("messageId for Add CourtLog request: " + messageId.toString());
                            } else {
                                System.out.println("Created Add CourtLog request.");
                            }
                            return session;
                        })
                    );
            } 
        }