package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;

public final class AddCaseUserScenario {

    private static final FeederBuilder<String> feeder = csv(AppConfig.COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH).random();

    private AddCaseUserScenario() {}

    public static ChainBuilder addCaseUser(String USERNAME, String PASSWORD) {
        return group("AddCase SOAP Request Group")
            .on(exec(feed(feeder))
            .exec(session -> {
                String xmlPayload = SOAPRequestBuilder.AddCaseUserRequest(session, USERNAME, PASSWORD);
                return session.set("xmlPayload", xmlPayload);
            })
            .exec(http("DARTS - GateWay - Soap - AddCase - User")
                    .post(SoapServiceEndpoint.StandardService.getEndpoint())
                    .headers(Headers.SoapHeaders)
                    .bodyPart(StringBodyPart("metadata", session -> session.get("xmlPayload"))
                            .contentType("application/xop+xml; charset=UTF-8; type=\"text/xml")
                            .transferEncoding("8bit")
                            .contentId("<rootpart@soapui.org>"))
                    .bodyPart(RawFileBodyPart("file", "C:\\Users\\a.cooper\\Desktop\\Performance.Testing\\DARTS\\Gatling_Base\\user-files\\Data\\sample.mp2")
                        .contentType("application/octet-stream")
                        .transferEncoding("binary")
                        .contentId("<sample6.mp2>")
                        .dispositionType("attachment")
                        .fileName("sample6.mp2")                        
                    )
                        .check(status().is(200))
                        .check(xpath("//return/code").saveAs("statusCode"))
                        .check(xpath("//return/message").saveAs("message"))
            ));
    }
}