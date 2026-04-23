package simulations.Scripts.DartsSoap;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import lombok.extern.slf4j.Slf4j;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentCPPDailyListTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentXhibitDailyListTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.HttpUtil;

import static io.gatling.javaapi.core.CoreDsl.*;

@Slf4j
public class DailyListsSimulation extends Simulation {

    @Override
    public void before() {
        log.info("Simulation is about to start!");
    }

    public DailyListsSimulation() {
        HttpProtocolBuilder httpProtocolSoap =
                HttpUtil.getHttpProtocol()
                        .inferHtmlResources()
                        .acceptEncodingHeader("gzip,deflate")
                        .contentTypeHeader("text/xml;charset=UTF-8")
                        .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)")
                        .baseUrl(EnvironmentURL.PROXY_BASE_URL.getUrl());

        setUpScenarios(httpProtocolSoap);
    }

    private void setUpScenarios(HttpProtocolBuilder httpProtocolSoap) {
        // Main SOAP scenario setup
        ScenarioBuilder mainScenario = scenario("VIQ External Requests")
                // Register with different CPP
                .group("Register With CPP External Username")
                .on(
                        exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()))
                                .exec(RegisterWithTokenScenario.registerWithToken(EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()))
                )
                .group("Add Document CPP")
                .on(
                        repeat(5)
                                //    .on(exec(AddDocumentCPPEventTokenScenario.AddDocumentCPPEventToken()))
                                // .repeat(AppConfig.CPP_DailyList_PEAK_REPEATS) 
                                .on(exec(AddDocumentCPPDailyListTokenScenario.AddDocumentCPPDailyListToken()))
                )

                // Register with different XHIBIT
                .group("Register With XHIBIT External Username")
                .on(
                        exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
                                .exec(RegisterWithTokenScenario.registerWithToken(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
                )
                // .group("Add Document Xhibit")
                // .on(
                //     repeat(AppConfig.XHIBIT_EVENTS_PEAK_REPEATS)
                //         .on(exec(AddDocumentXhibitEventTokenScenario.AddDocumentXhibitEventToken()))
                .repeat(5)
                .on(exec(AddDocumentXhibitDailyListTokenScenario.AddDocumentXhibitDailyListToken()));
        //);


        // Set up all scenarios together
        setUp(
                mainScenario.injectOpen(atOnceUsers(1)).protocols(httpProtocolSoap)
        );
    }

    @Override
    public void after() {
        log.info("Simulation is finished!");
    }
}