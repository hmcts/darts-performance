package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;

public final class AddDocumentXhibitDailyListTokenScenario {

    private AddDocumentXhibitDailyListTokenScenario() {}
    public static ChainBuilder AddDocumentXhibitDailyListToken() {
        return
       //  group("AddDocument - Xhibit DailyList SOAP Requests")
         //   .on(
                feed(Feeders.createCourtHouseAndCourtRooms())   
            .exec(session -> {
                    String xmlPayload = SOAPRequestBuilder.AddDocumentXhibitDailyListTokenRequest(session);  
                    return session.set("xmlPayload", xmlPayload);  
                })
                .exec(http("DARTS - GateWay - Soap - AddDocument - Xhibit DailyList - Token")
                        .post(SoapServiceEndpoint.DARTSService.getEndpoint())
                        .headers(Headers.SoapHeaders)
                        .body(StringBody(session -> session.get("xmlPayload")))
                        .check(status().is(200))
                        .check(xpath("//messageId/text()").find().optional().saveAs("messageId"))
                        .check(xpath("//return/code").saveAs("statusCode"))
                        .check(xpath("//return/message").saveAs("message"))
                        )
                        .exec(session -> {
                            String statusCode = session.getString("statusCode");
                            String message = session.getString("message");
                            if (statusCode.equals("ERROR") || (message != null && message.toLowerCase().contains("error"))) {
                                // Mark the request as failed if there's an error message
                                session.markAsFailed();
                                System.out.println("Error detected for Xhibit DailyList: " + message);
                            }
                            return session;
                        })
                        .exec(session -> {
                            Object messageId = session.get("messageId");
                            if (messageId != null) {
                                System.out.println("messageId for AddDocument - Xhibit DailyList request: " + messageId.toString());
                            } else {
                                System.out.println("Created AddDocument - Xhibit DailyList request.");
                            }
                            return session;
                        })
                   // )
                   ;
            } 
        }