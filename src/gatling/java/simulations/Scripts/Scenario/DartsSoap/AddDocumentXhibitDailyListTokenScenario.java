package simulations.Scripts.Scenario.DartsSoap;

import io.gatling.javaapi.core.ChainBuilder;
import lombok.extern.slf4j.Slf4j;
import simulations.Scripts.Headers.Headers;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.NumberGenerator;
import simulations.Scripts.Utilities.Util;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

@Slf4j
public final class AddDocumentXhibitDailyListTokenScenario {


    private static final NumberGenerator generator = new NumberGenerator(11);

    private AddDocumentXhibitDailyListTokenScenario() {
    }

    public static ChainBuilder AddDocumentXhibitDailyListToken() {
        return
                //  group("AddDocument - Xhibit DailyList SOAP Requests")
                //   .on(
                feed(Feeders.createCourtHouseAndCourtRooms())
                        .pause(Util.getDurationFromSeconds(1))
                        .exec(session -> {
                            String xmlPayload = SOAPRequestBuilder.addDocumentXhibitDailyListTokenRequest(session, generator);
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
                                .check(bodyString().saveAs("responseBody")) // Capture the entire response body
                        )
                        .exec(session -> {
                            String statusCode = session.getString("statusCode");
                            String message = session.getString("message");

                            if (statusCode.equals("ERROR") || (message != null && message.toLowerCase().contains("error"))) {
                                // Mark the request as failed if there's an error message
                                session.markAsFailed();
                                log.info("Error detected for Xhibit DailyList: " + message);
                            }
                            return session;
                        })
                        .exec(session -> {
                            Object messageId = session.get("messageId");
                            if (messageId != null) {
                                log.info("messageId for AddDocument - Xhibit DailyList request: " + messageId);
                            } else {
                                log.info("Created AddDocument - Xhibit DailyList request.");
                            }
                            return session;
                        })
                // )
                ;
    }
}