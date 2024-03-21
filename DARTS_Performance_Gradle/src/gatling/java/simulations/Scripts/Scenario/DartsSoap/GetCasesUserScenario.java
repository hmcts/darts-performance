package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;

public final class GetCasesUserScenario {

    private static final FeederBuilder<String> feeder = csv(AppConfig.COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH).random();
    private GetCasesUserScenario() {}
    public static ChainBuilder GetCaseSOAPUser() {
        return group("AddDocument SOAP Request Group")
            .on(exec(feed(feeder))
                .exec(session -> {
                    String xmlPayload = SOAPRequestBuilder.GetCasesUserRequest(session);
                    return session.set("xmlPayload", xmlPayload);
                })
                .exec(http("DARTS - GateWay - Soap - GetCase - User")
                        .post(SoapServiceEndpoint.StandardService.getEndpoint())
                        .headers(Headers.SoapHeaders)
                        .body(StringBody(session -> session.get("xmlPayload")))
                        .check(status().is(200))
                        .check(xpath("//return/code").saveAs("statusCode"))
                        .check(xpath("//return/message").saveAs("message"))
            ));
    } 
}
