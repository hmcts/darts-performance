package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;
import java.util.UUID;

public final class AddAudioTokenScenario {

    private static final FeederBuilder<String> feeder = csv(AppConfig.COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH).random();
    private static final String randomAudioFile = AppConfig.getRandomAudioFile();
    private static final String boundary = UUID.randomUUID().toString();

    private AddAudioTokenScenario() {}

    public static ChainBuilder addAudioToken() {
        return group("AddAudio SOAP Request Group")
            .on(exec(feed(feeder))
            .exec(session -> {
                String xmlPayload = SOAPRequestBuilder.AddAudioTokenRequest(session);
                return session.set("xmlPayload", xmlPayload);
            })
            .exec(http("DARTS - GateWay - Soap - AddAudio - Token")
                    .post(SoapServiceEndpoint.StandardService.getEndpoint())
                    .headers(Headers.SoapHeaders)  
                    .header("Content-Type", "multipart/related; type=\"application/xop+xml\"; start=\"<rootpart@soapui.org>\"; start-info=\"text/xml\"; boundary=" + boundary)
                    .header("User-Agent", "Apache-HttpClient/4.5.5 (Java/16.0.2)")              
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