package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;
import simulations.Scripts.Utilities.*;

public final class GetCasesUserScenario {

    private GetCasesUserScenario() {}
    public static ChainBuilder GetCaseSOAPUser(String USERNAME, String PASSWORD) {
        return group("AddDocument SOAP Request Group")
            .on(exec(feed(Feeders.createCourtHouseAndCourtRooms()))
                .exec(session -> {
                    String xmlPayload = SOAPRequestBuilder.GetCasesUserRequest(session, USERNAME, PASSWORD);
                    return session.set("xmlPayload", xmlPayload);
                })
                .exec(http("DARTS - GateWay - Soap - GetCase - User")
                        .post(SoapServiceEndpoint.DARTSService.getEndpoint())
                        .headers(Headers.SoapHeaders)
                        .body(StringBody(session -> session.get("xmlPayload")))
                        .check(status().is(200))
                        .check(xpath("//return/code").saveAs("statusCode"))
                        .check(xpath("//return/message").saveAs("message"))
                        .check(bodyString().saveAs("responseBody")) // Capture the entire response body
                        )  
                        .exec(session -> {
                            String statusCode = session.getString("statusCode");
                            String message = session.getString("message");

                            if (statusCode.equals("ERROR") || (message != null && message.toLowerCase().contains("error"))) {
                                // Mark the request as failed if there's an error message
                                session.markAsFailed();
                                System.out.println("Error detected for GetCase request: " + message);
                            }
                            return session;
                        })
                        .exec(session -> {
                            String statusCode = session.getString("statusCode");
                            String message = session.getString("message");

                            if (statusCode.equals("500")) {
                                // Mark as failed if statusCode is 500
                                session.markAsFailed();
                                if (message == null) {
                                    System.out.println("Error detected for Add GetCase request: 500 response Code");
                                } else {
                                    System.out.println("Error detected for Add GetCase request: " + message);
                                }
                            }
                            return session;
                        })
                        .exec(session -> {
                            Object messageId = session.get("messageId");
                            if (messageId != null) {
                                System.out.println("messageId for GetCase request: " + messageId.toString());
                            } else {
                                System.out.println("Created GetCase request.");
                            }
                            return session;
                        })
                    );
            } 
        }