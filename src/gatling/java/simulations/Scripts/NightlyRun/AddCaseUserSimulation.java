package simulations.Scripts.NightlyRun;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;

import simulations.Scripts.Scenario.DartsSoap.AddCaseUserScenario;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AddCaseUserSimulation extends Simulation {


    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public AddCaseUserSimulation() {
        HttpProtocolBuilder httpProtocolSoap = http
                .inferHtmlResources()
                .acceptEncodingHeader("gzip,deflate")
                .contentTypeHeader("text/xml;charset=UTF-8")
                .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)")
                .baseUrl(EnvironmentURL.PROXY_BASE_URL.getUrl());
                setUpScenarios(httpProtocolSoap);
    }

    private void setUpScenarios(HttpProtocolBuilder httpProtocolSoap) {
        // Main SOAP scenario setup
        ScenarioBuilder mainScenario = scenario("DARTS - GateWay - Soap - AddCase:POST")
         //Register with different VIQ
         .group("DARTS - GateWay - Soap - AddCase:POST")
         .on(
             repeat(AppConfig.NIGHTLY_RUN_REPEATS)
            .on(exec(AddCaseUserScenario.addCaseUser(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl())))
         );

        // Set up all scenarios together
        setUp(
            mainScenario.injectOpen(atOnceUsers(AppConfig.NIGHTLY_RUN_USERS)).protocols(httpProtocolSoap)
          
        );
    }

    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }
}
