package simulations.Scripts.PerformanceTests.DartsBaseLinePeakTests;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentCPPDailyListTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentCPPEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentXhibitDailyListTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentXhibitEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.Duration;

public class AddDocumnetPeakTestSimulation extends Simulation {

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public AddDocumnetPeakTestSimulation() {      

        HttpProtocolBuilder httpProtocolSoapGateway = http
        //    .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
            .baseUrl(EnvironmentURL.GATEWAY_BASE_URL.getUrl())
            .inferHtmlResources()
            .acceptEncodingHeader("gzip,deflate")
            .contentTypeHeader("text/xml;charset=UTF-8")
            .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");

        setUpScenarios(httpProtocolSoapGateway);
    }

    private void setUpScenarios(HttpProtocolBuilder httpProtocolSoapGateway) {
        ScenarioBuilder soapGatewayAddDocument = scenario("Soap Gateway Scenario")  

        .group("Register With CPP External Username")
        .on(
            exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()))
            .exec(RegisterWithTokenScenario.registerWithToken(EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()))
        )
        .group("Add Document CPP").on(
            repeat(1).on(
                exec(
                    group("Simulate XHIBIT to App delay").on(
                        pause(Duration.ofMillis(500), Duration.ofMillis(1000))
                    )
                )
                .exec(
                    group("AddDocumentCPPEventToken").on(
                        AddDocumentCPPEventTokenScenario.AddDocumentCPPEventToken()
                    )
                )
                .exec(
                    group("Simulate App to DAR PC delay").on(
                        pause(Duration.ofMillis(500), Duration.ofMillis(1000))
                    )
                )
            )
            .repeat(1).on(
                exec(
                    group("AddDocumentCPPDailyListToken").on(
                        AddDocumentCPPDailyListTokenScenario.AddDocumentCPPDailyListToken()
                    )
                )
            )
        )
        
        .group("Register With XHIBIT External Username")
        .on(
            exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
            .exec(RegisterWithTokenScenario.registerWithToken(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
        )
        .group("Add Document Xhibit").on(
            repeat(1).on(
                exec(
                    group("Simulate XHIBIT to App delay").on(
                        pause(Duration.ofMillis(500), Duration.ofMillis(1000))
                    )
                )
                .exec(
                    group("AddDocumentXhibitEventToken").on(
                        AddDocumentXhibitEventTokenScenario.AddDocumentXhibitEventToken()
                    )
                )
                .exec(
                    group("Simulate App to DAR PC delay").on(
                        pause(Duration.ofMillis(500), Duration.ofMillis(1000))
                    )
                )
            )
            .repeat(1).on(
                exec(
                    group("AddDocumentXhibitDailyListToken").on(
                        AddDocumentXhibitDailyListTokenScenario.AddDocumentXhibitDailyListToken()
                    )
                )
            )
        );

        setUp(
           soapGatewayAddDocument.injectOpen(atOnceUsers(1)).protocols(httpProtocolSoapGateway)
        );
    }

    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }
}
