package simulations.Scripts.Scenario.DartsSoap;

import io.gatling.javaapi.core.ChainBuilder;
import lombok.extern.slf4j.Slf4j;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import simulations.Scripts.Utilities.Feeders;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

@Slf4j
public final class AddCaseTokenScenario {

    private AddCaseTokenScenario() {
    }

    public static ChainBuilder addCaseToken() {
        return group("AddCase SOAP Request Group")
                .on(feed(Feeders.createCourtHouseAndCourtRooms())
                        .exec(session -> {
                            String xmlPayload = SOAPRequestBuilder.addCaseTokenRequest(session);
                            return session.set("xmlPayload", xmlPayload);
                        })
                        .exec(http("DARTS - GateWay - Soap - AddCase - Token")
                                .post(SoapServiceEndpoint.DARTSService.getEndpoint())
                                //.headers(Headers.SoapHeaders)
                                .header("SOAPAction", "\"\"")
                                .header("Client-Type", "SOAPUI Gateway Suite")
                                .body(StringBody(session -> session.get("xmlPayload")))
                                .check(status().is(200))
                                .check(xpath("//messageId/text()").find().optional().saveAs("messageId"))
                                .check(xpath("//return/code").saveAs("statusCode"))
                                .check(xpath("//return/message").saveAs("message"))
                        )
                        .exec(session -> {
                            Object messageId = session.get("messageId");
                            if (messageId != null) {
                                log.info("messageId for AddCase request: " + messageId);
                            } else {
                                log.info("Created AddCase request.");
                            }
                            return session;
                        })
                );
    }
}