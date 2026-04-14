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
public final class GetCasesUserScenario {

    private GetCasesUserScenario() {
    }

    public static ChainBuilder GetCaseSOAPUser(String userName, String password) {
        return group("GetCase SOAP Request Group")
                .on(exec(feed(Feeders.createCourtHouseAndCourtRooms()))
                        .exec(session -> {
                            String xmlPayload = SOAPRequestBuilder.getCasesUserRequest(session, userName, password);
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
                                log.info("Error detected for GetCase request: " + message);
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
                                    log.info("Error detected for Add GetCase request: 500 response Code");
                                } else {
                                    log.info("Error detected for Add GetCase request: " + message);
                                }
                            }
                            return session;
                        })
                        .exec(session -> {
                            Object messageId = session.get("messageId");
                            if (messageId != null) {
                                log.info("messageId for GetCase request: " + messageId);
                            } else {
                                log.info("Created GetCase request.");
                            }
                            return session;
                        })
                );
    }
}