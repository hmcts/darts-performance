package simulations.Scripts.PerformanceTests.NewMainTests;

import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import simulations.Scripts.ScenarioBuilder.DeleteAudioRequestScenarioBuild;
import simulations.Scripts.ScenarioBuilder.GetAudioRequestScenarioBuild;
import simulations.Scripts.ScenarioBuilder.PostAudioRequestScenarioBuild;
import simulations.Scripts.ScenarioBuilder.SoapGatewayUsersScenario;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.HttpUtil;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;

public class SoapTestSimulation extends Simulation {

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
                SoapGatewayUsersScenario.build(SOAP_GATEWAY_REQUESTS)
                        .injectOpen(atOnceUsers(AppConfig.getSoapUsers()))
                        .protocols(httpProtocolSoap),

                PostAudioRequestScenarioBuild.build(API_REQUESTS_POST_AUDIO_REQUEST)
                        .injectOpen(atOnceUsers(AppConfig.getPostAudioUsers()))
                        .protocols(httpProtocolApi),

                GetAudioRequestScenarioBuild.build(API_REQUESTS_GET_AUDIO_REQUEST)
                        .injectOpen(atOnceUsers(AppConfig.getGetAudioUsers()))
                        .protocols(httpProtocolApi),

                DeleteAudioRequestScenarioBuild.build(API_REQUESTS_DELETE_AUDIO_REQUEST)
                        .injectOpen(atOnceUsers(AppConfig.getDeleteAudioUsers()))
                        .protocols(httpProtocolApi)
        );
    }

    private HttpProtocolBuilder configureSoapHttp() {
        return HttpUtil.getHttpProtocol()
                .contentTypeHeader("text/xml;charset=UTF-8")
                .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)")
                .baseUrl(AppConfig.EnvironmentURL.PROXY_BASE_URL.getUrl());
    }

    private HttpProtocolBuilder configureSoapAddDocumentHttp() {
        return
                HttpUtil.getHttpProtocol()
                        .baseUrl(EnvironmentURL.GATEWAY_BASE_URL.getUrl())
                        .inferHtmlResources()
                        .acceptEncodingHeader("gzip,deflate")
                        .contentTypeHeader("text/xml;charset=UTF-8")
                        .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");
    }

    private HttpProtocolBuilder configureApiHttp() {
        return
                HttpUtil.getHttpProtocol()
                        .contentTypeHeader("text/xml;charset=UTF-8")
                        .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)")
                        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
                        .inferHtmlResources();
    }
}
