package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;

public final class AddDocumentCPPDailyListTokenScenario {

    private AddDocumentCPPDailyListTokenScenario() {}
    public static ChainBuilder AddDocumentCPPDailyListToken() {
        return group("AddDocument - CPP DailyList SOAP Requests")
            .on(feed(Feeders.createCourtHouseAndCourtRooms())   
            .exec(session -> {
                    String xmlPayload = SOAPRequestBuilder.AddDocumentCPPDailyListTokenRequest(session);  
                    return session.set("xmlPayload", xmlPayload);  
                })
                .exec(http("DARTS - GateWay - Soap - AddDocument - CPP DailyList - Token")
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
                                System.out.println("Error detected for CPP DailyList: " + message);
                            }
                            return session;
                        })
                        .exec(session -> {
                            Object messageId = session.get("messageId");
                            if (messageId != null) {
                                System.out.println("messageId: " + messageId.toString());
                            } else {
                                System.out.println("No value for messageId on the AddDocument - CPP DailyList request.");
                            }
                            return session;
                        })
                    );
            } 
        }