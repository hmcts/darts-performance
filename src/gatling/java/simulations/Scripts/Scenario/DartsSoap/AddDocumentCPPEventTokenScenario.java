package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;

public final class AddDocumentCPPEventTokenScenario {

    private AddDocumentCPPEventTokenScenario() {}
    public static ChainBuilder AddDocumentCPPEventToken() {
        return 
        //group("AddDocument - CPP Event SOAP Requests")
           // .on(
           feed(Feeders.createCourtHouseAndCourtRooms())   
            .exec(session -> {
                    String xmlPayload = SOAPRequestBuilder.AddDocumentCPPEventTokenRequest(session);
                    return session.set("xmlPayload", xmlPayload);
                })
                .exec(http("DARTS - GateWay - Soap - AddDocument - CPP Event - Token")
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
                            //System.out.println("Raw response body for AddDocument - CPP Event request: " + responseBody);

                            if (statusCode.equals("ERROR") || (message != null && message.toLowerCase().contains("error"))) {
                                // Mark the request as failed if there's an error message
                                session.markAsFailed();
                                System.out.println("Error detected for CPP Event: " + message);
                            }
                            return session;
                        })
                        .exec(session -> {
                            Object messageId = session.get("messageId");
                            if (messageId != null) {
                                System.out.println("messageId for AddDocument - CPP Event request: " + messageId.toString());
                            } else {
                                System.out.println("Created AddDocument - CPP Event request.");
                            }
                            return session;
                        }) 
                   // )
                    ;
            } 
        }