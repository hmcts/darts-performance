package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.NumberGenerator;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;

public final class AddDocumentCPPDailyListTokenScenario {

    private static final NumberGenerator generator = new NumberGenerator(11);

    private AddDocumentCPPDailyListTokenScenario() {}
    public static ChainBuilder AddDocumentCPPDailyListToken() {
        return
        // group("AddDocument - CPP DailyList SOAP Requests")
            //.on(
            feed(Feeders.createCourtHouseAndCourtRooms())   
            .pause(1)
            .exec(session -> {
                    String xmlPayload = SOAPRequestBuilder.AddDocumentCPPDailyListTokenRequest(session, generator);  
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
                        .check(bodyString().saveAs("responseBody")) // Capture the entire response body
                        )
                        .exec(session -> {
                            String statusCode = session.getString("statusCode");
                            String message = session.getString("message");

                            String responseBody = session.getString("responseBody");
                           // System.out.println("Raw response body for AddDocument - CPP DailyList request: " + responseBody);

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
                                System.out.println("messageId for AddDocument - CPP DailyList request: " + messageId.toString());
                            } else {
                                System.out.println("Created AddDocument - CPP DailyList request.");
                            }
                            return session;
                        })
                    //)
                    ;
            } 
        }