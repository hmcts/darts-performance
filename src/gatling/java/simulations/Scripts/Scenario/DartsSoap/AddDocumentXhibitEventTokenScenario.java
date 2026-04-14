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
public final class AddDocumentXhibitEventTokenScenario {

    private AddDocumentXhibitEventTokenScenario() {
    }

    public static ChainBuilder AddDocumentXhibitEventToken() {
        return
                // group("AddDocument - Xhibit Event SOAP Requests")
                //  .on(
                feed(Feeders.createCourtHouseAndCourtRooms())
                        .exec(session -> {
                            String xmlPayload = SOAPRequestBuilder.addDocumentXhibitEventTokenRequest(session);
                            return session.set("xmlPayload", xmlPayload);
                        })
                        .exec(http("DARTS - GateWay - Soap - AddDocument - Xhibit Event - Token")
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
                            //    log.info("Raw response body for AddDocument - Xhibit Event request: " + responseBody);
                            return session;
                        })
                        .exec(session -> {
                            String statusCode = session.getString("statusCode");
                            String message = session.getString("message");

                            if (statusCode == null) {
                                log.info("Status code is null for AddDocument - Xhibit Event request. Response might be missing the <return/code> element.");
                            }

                            if (message == null) {
                                log.info("Message is null for AddDocument - Xhibit Event request. Response might be missing the <return/message> element.");
                            }

                            if (statusCode != null && statusCode.equals("ERROR")) {
                                // Mark the request as failed if there's an error status code
                                session.markAsFailed();
                                log.info("Error detected with status code for AddDocument - Xhibit Event request: " + statusCode);
                            }

                            if (message != null && message.toLowerCase().contains("error")) {
                                // Mark the request as failed if there's an error message
                                session.markAsFailed();
                                log.info("Error detected with message for AddDocument - Xhibit Event request: " + message);
                            }

                            return session;
                        })
                        .exec(session -> {
                            Object messageId = session.get("messageId");
                            String xmlPayload = session.get("xmlPayload");
                            if (messageId != null) {
                                log.info("messageId for AddDocument - Xhibit Event request: " + messageId);
                            } else {
                                log.info("Created AddDocument - Xhibit Event request.");
                                log.info("AddDocument - Xhibit Event payload: " + xmlPayload);
                            }
                            return session;
                        });
    }
}