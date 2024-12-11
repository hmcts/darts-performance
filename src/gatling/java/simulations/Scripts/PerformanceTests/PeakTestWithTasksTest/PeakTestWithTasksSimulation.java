package simulations.Scripts.PerformanceTests.PeakTestWithTasksTest;

import simulations.Scripts.ScenarioBuilder.*;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class PeakTestWithTasksSimulation extends Simulation {   

    private static final String BASE_LINE_PEAK_JUDGE_USERS = "Baseline Peak - DARTS - Portal - Judge Users";
    private static final String BASE_LINE_PEAK_COURT_CLERK_USERS = "Baseline Peak - DARTS - Portal - Court Clerk Users";
    private static final String BASE_LINE_PEAK_COURT_MANAGER_USERS = "Baseline Peak - DARTS - Portal - Court Manager Users";
    private static final String BASE_LINE_PEAK_TRANSCRIBER_USERS = "Baseline Peak - DARTS - Portal - Transcriber Users";
    private static final String BASE_LINE_PEAK_LANGUAGE_USERS = "Baseline Peak - DARTS - Portal - Language Shop Users";
    private static final String BASE_LINE_PEAK_SOAP_REQUESTS = "Baseline Peak - DARTS - Soap - Requests";

    public PeakTestWithTasksSimulation() {
        HttpProtocolBuilder httpProtocolInternal = configureInternalHttp();
        HttpProtocolBuilder httpProtocolExternal = configureExternalHttp();
        HttpProtocolBuilder httpProtocolSoap = configureSoapHttp();
        HttpProtocolBuilder httpProtocolApi = configureApiHttp();

        setUpScenarios(httpProtocolExternal, httpProtocolInternal, httpProtocolSoap, httpProtocolApi);
    }

    private void setUpScenarios(HttpProtocolBuilder httpProtocolExternal, HttpProtocolBuilder httpProtocolInternal, HttpProtocolBuilder httpProtocolSoap, HttpProtocolBuilder httpProtocolApi) {
        setUp(
            CourtClerkUsersScenarioBuild.build(BASE_LINE_PEAK_COURT_CLERK_USERS)
                .injectOpen(rampUsers(AppConfig.getCourtClerkUsers())
                .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_COURT_CLERK)))
                .protocols(httpProtocolInternal),

            CourtManagerUsersScenarioBuild.build(BASE_LINE_PEAK_COURT_MANAGER_USERS)
                .injectOpen(rampUsers(AppConfig.getCourtManagerUsers())
                .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_COURT_MANAGER)))
                .protocols(httpProtocolInternal),

            TranscriberUsersScenario.build(BASE_LINE_PEAK_TRANSCRIBER_USERS)
                .injectOpen(rampUsers(AppConfig.getTranscriberUsers())
                .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_TRANSCRIBER)))
                .protocols(httpProtocolExternal),

            JudgeUserScenario.build(BASE_LINE_PEAK_JUDGE_USERS)
                .injectOpen(rampUsers(AppConfig.getJudgeUsers())
                .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_JUDGES)))
                .protocols(httpProtocolInternal),

            LanguageShopUserScenario.build(BASE_LINE_PEAK_LANGUAGE_USERS)
                .injectOpen(rampUsers(AppConfig.getLanguageShopUsers())
                .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_LANGUAGE_SHOP)))
                .protocols(httpProtocolExternal),

                SoapUsersScenario.build(BASE_LINE_PEAK_SOAP_REQUESTS,
                AppConfig.getAddCasesRepeats(),
                AppConfig.getGetCasesRepeats(), 
                AppConfig.getAddLogEntryRepeats(), 
                AppConfig.getCppEventsRepeats(), 
                AppConfig.getCppDailyListRepeats(), 
                AppConfig.getXhibitEventsRepeats(), 
                AppConfig.getXhibitDailyListRepeats())
                .injectOpen(atOnceUsers(AppConfig.getSoapUsers()))
                .protocols(httpProtocolSoap),
            
            InboundtoUnstructuredDatastoreTaskScenario.build(BASE_LINE_PEAK_SOAP_REQUESTS)
                .injectOpen(atOnceUsers(1))
                .protocols(httpProtocolApi)            
        );
    }

    private HttpProtocolBuilder configureInternalHttp() {
        return http
   //         .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
            .baseUrl("https://login.microsoftonline.com")
            .acceptHeader("application/json, text/plain, */*")
            .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
    }

    private HttpProtocolBuilder configureExternalHttp() {
        return http
   //         .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
            .baseUrl(AppConfig.EnvironmentURL.B2B_Login.getUrl())
            .acceptHeader("application/json, text/plain, */*")
            .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
    }

    private HttpProtocolBuilder configureSoapHttp() {
        return http
   //         .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
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
