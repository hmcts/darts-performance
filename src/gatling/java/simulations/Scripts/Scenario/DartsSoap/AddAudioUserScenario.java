package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;

public final class AddAudioUserScenario {

    private AddAudioUserScenario() {}

    public static ChainBuilder addAudioUser(String USERNAME, String PASSWORD) {
        return group("AddAudio SOAP Request Group")
            .on(exec(feed(Feeders.createCourtHouseAndCourtRooms()))
            .exec(session -> {
                String randomAudioFile = Feeders.getRandomAudioFile();
                String xmlPayload = SOAPRequestBuilder.addAudioTokenRequest(session, randomAudioFile);
                return session.set("randomAudioFile", randomAudioFile)
                .set("xmlPayload", xmlPayload);
            })
            .exec(http(session -> "DARTS - GateWay - Soap - AddAudio - User: File - " + session.get("randomAudioFile"))
                    .post(SoapServiceEndpoint.DARTSService.getEndpoint())
                    .headers(Headers.SoapHeaders)                    
                    .bodyPart(StringBodyPart("metadata", session -> session.get("xmlPayload"))
                            .contentType("application/xop+xml; charset=UTF-8; type=\"text/xml")
                            .transferEncoding("8bit")
                            .contentId("<rootpart@soapui.org>"))
                    .bodyPart(RawFileBodyPart("file",session ->  AppConfig.CSV_FILE_COMMON_PATH + session.get("randomAudioFile"))
                        .contentType("application/octet-stream")
                        .transferEncoding("binary")
                        .contentId(session ->"<"+ session.get("randomAudioFile")+ ">")
                        .dispositionType(session ->"attachment; name=\""+ session.get("randomAudioFile") + "")
                    )
                        .check(status().is(200))
                        .check(xpath("//return/code").saveAs("statusCode"))
                        .check(xpath("//return/message").saveAs("message"))
                        )
                    );
            }

            public static ChainBuilder addAudioUserBinary(String USERNAME, String PASSWORD) {
                return group("AddAudio SOAP Request Group")
                    .on(exec(feed(Feeders.createCourtHouseAndCourtRooms()))
                    .exec(session -> {
                        String randomAudioFile = Feeders.getRandomAudioFile();
                        String xmlPayload = SOAPRequestBuilder.addAudioTokenRequestBinary(session, randomAudioFile);
                        return session.set("randomAudioFile", randomAudioFile)
                                      .set("xmlPayload", xmlPayload);
                    })
                    .exec(http(session -> "DARTS - GateWay - Soap - AddAudio - User: File - " + session.get("randomAudioFile"))
                        .post(SoapServiceEndpoint.DARTSService.getEndpoint())
                        .headers(Headers.SoapHeaders)
                        .header("Content-Type", "multipart/related; type=\"application/xop+xml\"; start=\"<rootpart@soapui.org>\"; boundary=\"MIMEBoundary\"")
                        .bodyPart(StringBodyPart("root", session -> session.getString("xmlPayload"))
                            .contentType("application/xop+xml; charset=UTF-8; type=\"text/xml\"")
                            .transferEncoding("8bit")
                            .contentId("<rootpart@soapui.org>")
                        )
                        .bodyPart(RawFileBodyPart("file", session -> AppConfig.CSV_FILE_COMMON_PATH + session.getString("randomAudioFile"))
                            .contentType("application/octet-stream")
                            .transferEncoding("binary")
                            .contentId(session -> "<" + session.getString("randomAudioFile") + ">")
                            .dispositionType(session -> "attachment; name=\"" + session.getString("randomAudioFile") + "\"")
                        )
                        .check(status().is(200))
                        .check(xpath("//return/code").saveAs("statusCode"))
                        .check(xpath("//return/message").saveAs("message"))
                    ));
    }
}