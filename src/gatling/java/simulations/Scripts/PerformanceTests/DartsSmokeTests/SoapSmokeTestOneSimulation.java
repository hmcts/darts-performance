package simulations.Scripts.PerformanceTests.DartsSmokeTests;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import lombok.extern.slf4j.Slf4j;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.GetAudioRequestScenario;
import simulations.Scripts.Scenario.DartsApi.PostAudioRequestScenario;
import simulations.Scripts.Scenario.DartsSoap.*;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.HttpUtil;

import static io.gatling.javaapi.core.CoreDsl.*;

@Slf4j
public class SoapSmokeTestOneSimulation extends Simulation {


    @Override
    public void before() {
        log.info("Simulation is about to start!");
    }

    public SoapSmokeTestOneSimulation() {
        HttpProtocolBuilder httpProtocolSoap =
                HttpUtil.getHttpProtocol()
                        .inferHtmlResources()
                        .acceptEncodingHeader("gzip,deflate")
                        .contentTypeHeader("text/xml;charset=UTF-8")
                        .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)")
                        .baseUrl(EnvironmentURL.PROXY_BASE_URL.getUrl());

        HttpProtocolBuilder httpProtocolApi =
                HttpUtil.getHttpProtocol()
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
                                .exec(RegisterWithTokenScenario.registerWithToken(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl()))
                                .repeat(AppConfig.ADD_CASES_SMOKE_REPEATS)
                                .on(exec(AddCaseUserScenario.addCaseUser(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl())))
                                .repeat(AppConfig.GET_CASES_SMOKE_REPEATS)
                                .on(exec(GetCasesUserScenario.GetCaseSOAPUser(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl()))))

                //Register with different CPP
                .group("Register With CPP External Username")
                .on(
                        exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()))
                                .exec(RegisterWithTokenScenario.registerWithToken(EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()))
                                .repeat(AppConfig.CPP_EVENTS_SMOKE_REPEATS)
                                .on(exec(AddDocumentCPPEventTokenScenario.AddDocumentCPPEventToken()))
                                .repeat(AppConfig.CPP_DailyList_SMOKE_REPEATS)
                                .on(exec(AddDocumentCPPDailyListTokenScenario.AddDocumentCPPDailyListToken()))
                )

                //Register with different XHIBIT
                .group("Register With XHIBIT External Username")
                .on(
                        exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
                                .exec(RegisterWithTokenScenario.registerWithToken(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
                                .repeat(AppConfig.XHIBIT_EVENTS_SMOKE_REPEATS)
                                .on(exec(AddDocumentXhibitEventTokenScenario.AddDocumentXhibitEventToken()))
                                .repeat(AppConfig.XHIBIT_DailyList_SMOKE_REPEATS)
                                .on(exec(AddDocumentXhibitDailyListTokenScenario.AddDocumentXhibitDailyListToken()))
                );

        // API scenario setups
        ScenarioBuilder postAudioScenario = scenario("Post Audio Request Scenario")
                .exec(GetApiTokenScenario.getApiToken())
                .repeat(AppConfig.POST_AUDIO_REQUEST_SMOKE_REPEATS)
                .on(exec(PostAudioRequestScenario.PostaudioRequest()));

        ScenarioBuilder getAudioScenario = scenario("Get Audio Request Scenario")
                .exec(GetApiTokenScenario.getApiToken())
                .repeat(AppConfig.GET_AUDIO_REQUEST_SMOKE_REPEATS)
                .on(uniformRandomSwitch().on(
                        exec(GetAudioRequestScenario.GetAudioRequestDownload()),
                        exec(GetAudioRequestScenario.GetAudioRequestPlayBack())));

        // Set up all scenarios together
        setUp(
                mainScenario.injectOpen(atOnceUsers(95)).protocols(httpProtocolSoap),
                postAudioScenario.injectOpen(atOnceUsers(AppConfig.POST_AUDIO_USERS_COUNT)).protocols(httpProtocolApi),
                getAudioScenario.injectOpen(atOnceUsers(AppConfig.GET_AUDIO_USERS_COUNT)).protocols(httpProtocolApi)
        );
    }

    @Override
    public void after() {
        log.info("Simulation is finished!");
    }
}
