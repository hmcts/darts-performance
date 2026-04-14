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
public final class AddDocumentCPPEventTokenScenario {

    private AddDocumentCPPEventTokenScenario() {
    }

    public static ChainBuilder AddDocumentCPPEventToken() {
        return
                //group("AddDocument - CPP Event SOAP Requests")
                // .on(
                feed(Feeders.createCourtHouseAndCourtRooms())
                        .exec(session -> {
                            String xmlPayload = SOAPRequestBuilder.addDocumentCPPEventTokenRequest(session);
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

                            if (statusCode.equals("ERROR") || (message != null && message.toLowerCase().contains("error"))) {
                                // Mark the request as failed if there's an error message
                                session.markAsFailed();
                                log.info("Error detected for CPP Event: " + message);
                            }
                            return session;
                        })
                        .exec(session -> {
                            Object messageId = session.get("messageId");
                            String xmlPayload = session.get("xmlPayload");
                            if (messageId != null) {
                                log.info("messageId for AddDocument - CPP Event request: " + messageId);
                            } else {
                                log.info("Created AddDocument - CPP Event request.");
                                log.info("messageId for AddDocument - CPP Event request: " + xmlPayload);

                            }
                            return session;
                        })
                // )
                ;
    }
}