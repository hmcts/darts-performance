package simulations.Scripts.PerformanceTests.NewMainTests;

import simulations.Scripts.ScenarioBuilder.*;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class SoapTestSimulation extends Simulation {   

    private static final String SOAP_PROXY_REQUESTS = "DARTS - Soap - Proxy Requests";
    private static final String SOAP_GATEWAY_REQUESTS = "DARTS - Soap - Gateway Requests";

    private static final String API_REQUESTS_POST_AUDIO_REQUEST = "DARTS - API - Post Audio Requests";
    private static final String API_REQUESTS_GET_AUDIO_REQUEST = "DARTS - API - Get Audio Requests";
    private static final String API_REQUESTS_DELETE_AUDIO_REQUEST = "DARTS - API - Delete Audio Requests";


    public SoapTestSimulation() {
        HttpProtocolBuilder httpProtocolSoap = configureSoapHttp();
        HttpProtocolBuilder httpProtocolSoapAddDocument = configureSoapAddDocumentHttp();        
        HttpProtocolBuilder httpProtocolApi = configureApiHttp();

        setUpScenarios(httpProtocolSoap, httpProtocolSoapAddDocument, httpProtocolApi);
    }

    private void setUpScenarios(HttpProtocolBuilder httpProtocolSoap, HttpProtocolBuilder httpProtocolSoapAddDocument, HttpProtocolBuilder httpProtocolApi) {

        setUp(           
            SoapProxyUsersScenario.build(SOAP_PROXY_REQUESTS,
            AppConfig.getAddCasesRepeats(),
            AppConfig.getGetCasesRepeats(), 
            AppConfig.getAddLogEntryRepeats())
                .injectOpen(atOnceUsers(1))
                .protocols(httpProtocolSoap).andThen
                (SoapGatewayUsersScenario.build(SOAP_GATEWAY_REQUESTS,                   
                AppConfig.getCppEventsRepeats(),
                AppConfig.getCppDailyListRepeats(),
                AppConfig.getXhibitEventsRepeats(),
                AppConfig.getXhibitDailyListRepeats())
                .injectOpen(atOnceUsers(1))
                .protocols(httpProtocolSoapAddDocument)),
    
            PostAudioRequestScenarioBuild.build(API_REQUESTS_POST_AUDIO_REQUEST,
                    AppConfig.getPostAudioRequestRepeats())
                .injectOpen(atOnceUsers(AppConfig.getPostAudioUsers()))
                .protocols(httpProtocolApi), 
            
            GetAudioRequestScenarioBuild.build(API_REQUESTS_GET_AUDIO_REQUEST,
                    AppConfig.getGetAudioRequestRepeats())
                .injectOpen(atOnceUsers(AppConfig.getGetAudioUsers()))
                .protocols(httpProtocolApi), 
    
            DeleteAudioRequestScenarioBuild.build(API_REQUESTS_DELETE_AUDIO_REQUEST,
                    AppConfig.getDeleteAudioRequestRepeats())
                .injectOpen(atOnceUsers(AppConfig.getDeleteAudioUsers()))
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

    private HttpProtocolBuilder configureSoapAddDocumentHttp() {
        return http
       .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
            .baseUrl(EnvironmentURL.GATEWAY_BASE_URL.getUrl())
            .inferHtmlResources()
            .acceptEncodingHeader("gzip,deflate")
            .contentTypeHeader("text/xml;charset=UTF-8")
            .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");
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
