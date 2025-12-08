package simulations.Scripts.Scenario.DartsSoap;

import lombok.extern.slf4j.Slf4j;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.Utilities.HttpUtil;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;
import simulations.Scripts.Utilities.*;

@Slf4j
public final class AddCaseUserScenario {
    
    private AddCaseUserScenario() {}

    public static ChainBuilder addCaseUser(String USERNAME, String PASSWORD) {
        return group("AddCase SOAP Request Group")
            .on(exec(feed(Feeders.createCourtHouseAndCourtRooms()))
            .exec(session -> {
                //String randomAudioFile = Feeders.getRandomAudioFile();
                String xmlPayload = SOAPRequestBuilder.addCaseUserRequest(session, USERNAME, PASSWORD);
               // return session.set("randomAudioFile", randomAudioFile)
               return session.set("xmlPayload", xmlPayload);
            })
           // .exec(http(session -> "DARTS - GateWay - Soap - AddCase - User: File - " + session.get("randomAudioFile"))
            .exec(http("DARTS - GateWay - Soap - AddCase - User")
                    .post(SoapServiceEndpoint.DARTSService.getEndpoint())
                    //.headers(Headers.SoapHeaders)
                    .header("SOAPAction", "\"\"")
                    .header("Client-Type", "SOAPUI Gateway Suite") 
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
                            log.info("Error detected for AddCase: " + message);
                        }
                        return session;
                    })
                    .exec(session -> {
                        Object messageId = session.get("messageId");
                        Object responseBody = session.get("responseBody");
                        Object xmlPayload = session.get("xmlPayload");

                        if (messageId != null) {
                            log.info("messageId for AddCase request: " + messageId.toString());
                            log.info("AddCase response Body: " + responseBody.toString());
                            log.info("AddCase payload: " + xmlPayload.toString());
                        } else {
                            log.info("Created AddCase request.");
                        }
                        return session;
                    })
                );
        } 
    }