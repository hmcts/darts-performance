package simulations.Scripts.NightlyRun;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsApi.GetAudioRequestScenario;
import simulations.Scripts.Scenario.DartsApi.PostAudioRequestScenario;
import simulations.Scripts.Scenario.DartsSoap.AddCaseUserScenario;
import simulations.Scripts.Scenario.DartsSoap.AddCourtlogUserScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentCPPDailyListTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentCPPEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentXhibitDailyListTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentXhibitEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.GetCasesUserScenario;
import simulations.Scripts.Scenario.DartsSoap.GetCourtlogTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;
import simulations.Scripts.Scenario.DartsApi.DeleteAudioRequestScenario;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class NightlyRunSimulation extends Simulation {
    
    public static Boolean isFixed = true;


    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public NightlyRunSimulation() {
        HttpProtocolBuilder httpProtocolSoap = http
                .inferHtmlResources()
                .acceptEncodingHeader("gzip,deflate")
                .contentTypeHeader("text/xml;charset=UTF-8")
                .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)")
                .baseUrl(EnvironmentURL.PROXY_BASE_URL.getUrl());

        HttpProtocolBuilder httpProtocolApi = http

                .inferHtmlResources()
                .baseUrl(EnvironmentURL.B2B_Login.getUrl());
        setUpScenarios(httpProtocolSoap, httpProtocolApi);
    }

    private void setUpScenarios(HttpProtocolBuilder httpProtocolSoap, HttpProtocolBuilder httpProtocolApi) {
        // Main SOAP scenario setup
        ScenarioBuilder mainScenario = scenario("Main Scenario")
         //Register with different VIQ
         .group("Register With VIQ External Username")
         .on(
             exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl()))
            .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl()))
            .repeat(AppConfig.NIGHTLY_RUN_REPEATS)
                .on(exec(AddCaseUserScenario.addCaseUser(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl())))
            .repeat(AppConfig.NIGHTLY_RUN_REPEATS)
                .on(exec(GetCasesUserScenario.GetCaseSOAPUser(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl()))))
            .repeat(AppConfig.NIGHTLY_RUN_REPEATS)
                .on(exec(AddCourtlogUserScenario.addCourtLogUser(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl())))
            
        //Register with different CPP
        .group("Register With CPP External Username")
        .on(
            exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()))
            .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()))
            .repeat(AppConfig.NIGHTLY_RUN_REPEATS)
                .on(exec(AddDocumentCPPEventTokenScenario.AddDocumentCPPEventToken()))
            .repeat(AppConfig.NIGHTLY_RUN_REPEATS) 
                .on(exec(AddDocumentCPPDailyListTokenScenario.AddDocumentCPPDailyListToken()))
        )

        //Register with different XHIBIT
        .group("Register With XHIBIT External Username")
        .on(
            exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
            .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
            .repeat(AppConfig.NIGHTLY_RUN_REPEATS)
                    .on(exec(AddDocumentXhibitEventTokenScenario.AddDocumentXhibitEventToken()))
                .repeat(AppConfig.NIGHTLY_RUN_REPEATS)
                    .on(exec(AddDocumentXhibitDailyListTokenScenario.AddDocumentXhibitDailyListToken()))
        );

        // API scenario setups
        ScenarioBuilder postAudioScenario = scenario("Post Audio Request Scenario")
            .exec(GetApiTokenScenario.getApiToken())
            .repeat(AppConfig.NIGHTLY_RUN_REPEATS)
                .on(exec(PostAudioRequestScenario.PostaudioRequest())
                .exec(GetAudioRequestScenario.GetAudioRequest())
                .exec(DeleteAudioRequestScenario.DeleteAudioRequest())

                //  .uniformRandomSwitch().on(
                //     exec(GetAudioRequestScenario.GetAudioRequestDownload()),
                //     exec(GetAudioRequestScenario.GetAudioRequestPlayBack()))

            );      

        // Set up all scenarios together
        setUp(
            mainScenario.injectOpen(atOnceUsers(AppConfig.NIGHTLY_RUN_USERS))
            .protocols(httpProtocolSoap)
            
            // postAudioScenario.injectOpen(atOnceUsers(AppConfig.NIGHTLY_RUN_USERS))
            // .protocols(httpProtocolApi)
        ).assertions(
            global().responseTime().max().lt(50000),
            global().successfulRequests().percent().gt(95.0)
        );
    }

    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }
}
