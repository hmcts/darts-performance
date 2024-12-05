package simulations.Scripts.PerformanceTests.DartsBaseLinePeakTests;

import simulations.Scripts.ScenarioBuilder.*;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class SoapBaseLinePeakTestSimulationcopy extends Simulation {   

    private static final String SOAP_REQUESTS = "DARTS - Soap - Requests";
    private static final String API_REQUESTS = "DARTS - API - Requests";


    public SoapBaseLinePeakTestSimulationcopy() {
        HttpProtocolBuilder httpProtocolSoap = configureSoapHttp();
        HttpProtocolBuilder httpProtocolApi = configureApiHttp();

        setUpScenarios(httpProtocolSoap, httpProtocolApi);
    }

    private void setUpScenarios(HttpProtocolBuilder httpProtocolSoap, HttpProtocolBuilder httpProtocolApi) {

        setUp(           
            SoapUsersScenario.build(SOAP_REQUESTS,
                AppConfig.getAddCasesRepeats(),
                AppConfig.getGetCasesRepeats(), 
                AppConfig.getAddLogEntryRepeats(), 
                AppConfig.getCppEventsRepeats(), 
                AppConfig.getCppDailyListRepeats(), 
                AppConfig.getXhibitEventsRepeats(), 
                AppConfig.getXhibitDailyListRepeats())
                    .injectOpen(atOnceUsers(1))
                    .protocols(httpProtocolSoap),
            PostAudioRequestScenarioBuild.build(API_REQUESTS,
                AppConfig.getPostAudioRequestRepeats())
                    .injectOpen(atOnceUsers(3))
                    .protocols(httpProtocolApi), 
            GetAudioRequestScenarioBuild.build(API_REQUESTS,
                AppConfig.getGetAudioRequestRepeats())
                    .injectOpen(atOnceUsers(3))
                    .protocols(httpProtocolApi), 
            DeleteAudioRequestScenarioBuild.build(API_REQUESTS,
                AppConfig.getPostAudioRequestRepeats())
                    .injectOpen(atOnceUsers(3))
                    .protocols(httpProtocolApi)                        
        );
    }   

    private HttpProtocolBuilder configureSoapHttp() {
        return http
            .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
            .contentTypeHeader("text/xml;charset=UTF-8")
            .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)")
            .baseUrl(AppConfig.EnvironmentURL.PROXY_BASE_URL.getUrl());
    }

    private HttpProtocolBuilder configureApiHttp() {
        return http
            .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
            .contentTypeHeader("text/xml;charset=UTF-8")
            .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)")
            .baseUrl(EnvironmentURL.B2B_Login.getUrl())
            .inferHtmlResources();
    }
}
