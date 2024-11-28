package simulations.Scripts.DartsBaseLineNormalTests;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsApi.GetAudioRequestScenario;
import simulations.Scripts.Scenario.DartsApi.PostAudioRequestScenario;
import simulations.Scripts.Scenario.DartsSoap.AddCaseUserScenario;
import simulations.Scripts.Scenario.DartsSoap.AddCourtlogUserScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentCPPDailyListTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentCPPEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentXhibitDailyListTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentXhibitEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.GetCasesUserScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;
import simulations.Scripts.Scenario.DartsApi.DeleteAudioRequestScenario;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class SoapBaseLineNormalTestSimulation extends Simulation {


    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public SoapBaseLineNormalTestSimulation() {
        HttpProtocolBuilder httpProtocolSoap = http
                .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
                .inferHtmlResources()
                .acceptEncodingHeader("gzip,deflate")
                .contentTypeHeader("text/xml;charset=UTF-8")
                .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)")
                .baseUrl(EnvironmentURL.PROXY_BASE_URL.getUrl());

        HttpProtocolBuilder httpProtocolApi = http
                .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
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
            .repeat(AppConfig.ADD_CASES_BASELINE_NORMAL_REPEATS)
            .on(exec(AddCaseUserScenario.addCaseUser(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl())))
            .repeat(AppConfig.GET_CASES_BASELINE_NORMAL_REPEATS)
            .on(exec(GetCasesUserScenario.GetCaseSOAPUser(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl())))
            .repeat(AppConfig.ADD_LOG_ENTRY_BASELINE_NORMAL_REPEATS)
            .on(exec(AddCourtlogUserScenario.addCourtLogUser(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl()))))
            
        //Register with different CPP
        .group("Register With CPP External Username")
        .on(
            exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()))
            .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()))
            .repeat(AppConfig.CPP_EVENTS_BASELINE_NORMAL_REPEATS)
                .on(exec(AddDocumentCPPEventTokenScenario.AddDocumentCPPEventToken()))
            .repeat(AppConfig.CPP_DailyList_BASELINE_NORMAL_REPEATS) 
                .on(exec(AddDocumentCPPDailyListTokenScenario.AddDocumentCPPDailyListToken()))
        )

        //Register with different XHIBIT
        .group("Register With XHIBIT External Username")
        .on(
            exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
            .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
            .repeat(AppConfig.XHIBIT_EVENTS_BASELINE_NORMAL_REPEATS)
                .on(exec(AddDocumentXhibitEventTokenScenario.AddDocumentXhibitEventToken()))
            .repeat(AppConfig.XHIBIT_DailyList_BASELINE_NORMAL_REPEATS)
                .on(exec(AddDocumentXhibitDailyListTokenScenario.AddDocumentXhibitDailyListToken()))
        );

        // API scenario setups
        ScenarioBuilder postAudioScenario = scenario("Post Audio Request Scenario")
            .exec(GetApiTokenScenario.getApiToken())
            .repeat(1)
            .on(exec(PostAudioRequestScenario.PostaudioRequest()));

        ScenarioBuilder getAudioScenario = scenario("Get Audio Request Scenario")
            .exec(GetApiTokenScenario.getApiToken())
            .repeat(1)
            .on(exec(GetAudioRequestScenario.GetAudioRequestDownload()));

        ScenarioBuilder deleteAudioScenario = scenario("Delete Audio Request Scenario")
            .exec(GetApiTokenScenario.getApiToken())
            .repeat(1)
            .on(exec(DeleteAudioRequestScenario.DeleteAudioRequest()));

        // Set up all scenarios together
        setUp(
         //   mainScenario.injectOpen(atOnceUsers(195)).protocols(httpProtocolSoap),
      //      postAudioScenario.injectOpen(atOnceUsers(1)).protocols(httpProtocolApi),
      //      getAudioScenario.injectOpen(atOnceUsers(1)).protocols(httpProtocolApi),
            deleteAudioScenario.injectOpen(atOnceUsers(1)).protocols(httpProtocolApi)
        );
    }

    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }
}
