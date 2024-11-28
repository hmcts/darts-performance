package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;

public final class AddDocumentXhibitEventTokenScenario {

    private AddDocumentXhibitEventTokenScenario() {}

    public static ChainBuilder AddDocumentXhibitEventToken() {
        return 
       // group("AddDocument - Xhibit Event SOAP Requests")
          //  .on(
                feed(Feeders.createCourtHouseAndCourtRooms())   
            .exec(session -> {
                    String xmlPayload = SOAPRequestBuilder.AddDocumentXhibitEventTokenRequest(session);
                    return session.set("xmlPayload", xmlPayload);
                })
                .exec(http("DARTS - GateWay - Soap - AddDocument - Xhibit Event - Token")
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
                    //    System.out.println("Raw response body for AddDocument - Xhibit Event request: " + responseBody);
                    return session;
                })
                .exec(session -> {
                    String statusCode = session.getString("statusCode");
                    String message = session.getString("message");
                    
                    if (statusCode == null) {
                        System.out.println("Status code is null for AddDocument - Xhibit Event request. Response might be missing the <return/code> element.");
                    }
                    
                    if (message == null) {
                        System.out.println("Message is null for AddDocument - Xhibit Event request. Response might be missing the <return/message> element.");
                    }
                    
                    if (statusCode != null && statusCode.equals("ERROR")) {
                        // Mark the request as failed if there's an error status code
                        session.markAsFailed();
                        System.out.println("Error detected with status code for AddDocument - Xhibit Event request: " + statusCode);
                    }
                    
                    if (message != null && message.toLowerCase().contains("error")) {
                        // Mark the request as failed if there's an error message
                        session.markAsFailed();
                        System.out.println("Error detected with message for AddDocument - Xhibit Event request: " + message);
                    }
                    
                    return session;
                })
                .exec(session -> {
                    Object messageId = session.get("messageId");
                    if (messageId != null) {
                        System.out.println("messageId for AddDocument - Xhibit Event request: " + messageId.toString());
                    } else {
                        System.out.println("Created AddDocument - Xhibit Event request.");
                    }
                    return session;
                })
        //)
        ;
    }
}