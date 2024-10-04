package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;

public final class AddCourtlogUserScenario {

    private AddCourtlogUserScenario() {}

    public static ChainBuilder addCourtLogUser(String USERNAME, String PASSWORD) {
        return group("CourtLog SOAP Request Group")
            .on(feed(Feeders.createCourtHouseAndCourtRooms()) 
            .exec(session -> {
                    String xmlPayload = SOAPRequestBuilder.AddCourtLogUserRequest(session, USERNAME, PASSWORD);
                    return session.set("xmlPayload", xmlPayload);
                })
                .exec(http("DARTS - GateWay - Soap - Add CourtLog - User")
                        .post(SoapServiceEndpoint.DARTSService.getEndpoint())
                        .headers(Headers.SoapHeaders)
                        .body(StringBody(session -> session.get("xmlPayload")))
                        .check(status().is(200))  // Check only for a 200 status code since that's what you're receiving
                        .check(xpath("//return/code").saveAs("statusCode"))  // Extract status code
                        .check(xpath("//return/message").optional().saveAs("message"))  // Extract message, if available
                        .check(bodyString().saveAs("responseBody")) // Capture the entire response body
                        )  
                        .exec(session -> {
                            String statusCode = session.getString("statusCode");
                            String message = session.getString("message");

                            String responseBody = session.getString("responseBody");
                            System.out.println("Raw response body for AddDocument - Add CourtLog request: " + responseBody);

                            if (statusCode.equals("500")) {
                                // Mark as failed if statusCode is 500
                                session.markAsFailed();
                                if (message == null) {
                                    System.out.println("Error detected for Add CourtLog request: 500 response Code");
                                } else {
                                    System.out.println("Error detected for Add CourtLog request: " + message);
                                }
                            }
                            return session;
                        })
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

