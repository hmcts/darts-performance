package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;

public final class AddAudioUserScenario {

    private static final FeederBuilder<String> feeder = csv(AppConfig.COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH).random();
    private static final String randomAudioFile = AppConfig.getRandomAudioFile();

    private AddAudioUserScenario() {}

    public static ChainBuilder addAudioUser() {
        return group("AddAudio SOAP Request Group")
            .on(exec(feed(feeder))
            .exec(session -> {
                String xmlPayload = SOAPRequestBuilder.AddAudioUserRequest(session);
                return session.set("xmlPayload", xmlPayload);
            })
            .exec(http("DARTS - GateWay - Soap - AddAudio - User")
                    .post(SoapServiceEndpoint.StandardService.getEndpoint())
                    .headers(Headers.SoapHeaders)                    
                    .bodyPart(StringBodyPart("metadata", session -> session.get("xmlPayload"))
                            .contentType("application/xop+xml; charset=UTF-8; type=\"text/xml")
                            .transferEncoding("8bit")
                            .contentId("<rootpart@soapui.org>"))
                    .bodyPart(RawFileBodyPart("file", AppConfig.CSV_FILE_COMMON_PATH + randomAudioFile)
                        .contentType("application/octet-stream")
                        .transferEncoding("binary")
                        .contentId("<"+ randomAudioFile+ ">")
                        .dispositionType("attachment; name=\""+ randomAudioFile + "")
                    )
                        .check(status().is(200))
                        .check(xpath("//return/code").saveAs("statusCode"))
                        .check(xpath("//return/message").saveAs("message"))
            ));
    }
}