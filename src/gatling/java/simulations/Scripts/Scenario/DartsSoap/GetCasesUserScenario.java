package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;
import simulations.Scripts.Utilities.*;

public final class GetCasesUserScenario {

    private GetCasesUserScenario() {}
    public static ChainBuilder GetCaseSOAPUser(String USERNAME, String PASSWORD) {
        return group("AddDocument SOAP Request Group")
            .on(exec(feed(Feeders.createCourtHouseAndCourtRooms()))
                .exec(session -> {
                    String xmlPayload = SOAPRequestBuilder.GetCasesUserRequest(session, USERNAME, PASSWORD);
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
