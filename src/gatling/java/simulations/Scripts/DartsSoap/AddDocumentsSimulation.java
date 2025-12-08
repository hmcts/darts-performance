package simulations.Scripts.DartsSoap;

import lombok.extern.slf4j.Slf4j;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentCPPEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentXhibitEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.Utilities.HttpUtil;

@Slf4j
public class AddDocumentsSimulation extends Simulation {


    @Override
    public void before() {
        log.info("Simulation is about to start!");
    }

    public AddDocumentsSimulation() {
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
        ScenarioBuilder mainScenario = scenario("Main Scenario")
        //Register with different CPP
        .group("Register With CPP External Username")
        .on(
            exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()))
            .exec(RegisterWithTokenScenario.registerWithToken(EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()))
            .repeat(1)
                .on(exec(AddDocumentCPPEventTokenScenario.AddDocumentCPPEventToken()))                
        )

        //Register with different XHIBIT
        .group("Register With XHIBIT External Username")
        .on(
            exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
            .exec(RegisterWithTokenScenario.registerWithToken(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
            .repeat(1)
                    .on(exec(AddDocumentXhibitEventTokenScenario.AddDocumentXhibitEventToken()))
        );
      

        // Set up all scenarios together
        setUp(
            mainScenario.injectOpen(atOnceUsers(50)).protocols(httpProtocolSoap)
        );
    }

    @Override
    public void after() {
        log.info("Simulation is finished!");
    }
}
