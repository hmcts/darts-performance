package simulations.Scripts.Scenario.DartsSoap;

import io.gatling.javaapi.core.ChainBuilder;
import lombok.extern.slf4j.Slf4j;
import simulations.Scripts.Headers.Headers;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import simulations.Scripts.Utilities.Feeders;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

@Slf4j
public final class AddCourtlogUserScenario {

    private AddCourtlogUserScenario() {
    }

    public static ChainBuilder addCourtLogUser(String userName, String password) {
        return group("CourtLog SOAP Request Group")
                .on(feed(Feeders.createCourtHouseAndCourtRooms())
                        .exec(session -> {
                            String xmlPayload = SOAPRequestBuilder.addCourtLogUserRequest(session, userName, password);
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

                            if (statusCode.equals("500")) {
                                // Mark as failed if statusCode is 500
                                session.markAsFailed();
                                if (message == null) {
                                    log.info("Error detected for Add CourtLog request: 500 response Code");
                                } else {
                                    log.info("Error detected for Add CourtLog request: " + message);
                                }
                            }
                            return session;
                        })
                        .exec(session -> {
                            Object messageId = session.get("messageId");
                            if (messageId != null) {
                                log.info("messageId for Add CourtLog request: " + messageId);
                            } else {
                                log.info("Created Add CourtLog request.");
                            }
                            return session;
                        })
                );
    }
}

