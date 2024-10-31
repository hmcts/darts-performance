package simulations.Scripts.DartsGroupTest;

import simulations.Scripts.DartsGroupTest.Scenarios.*;
import simulations.Scripts.Utilities.AppConfig;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class PortalandSoapBaselinePeakTestSimulation extends Simulation {   

    private static final String BASE_LINE_PEAK_JUDGE_USERS = "Baseline Peak - DARTS - Portal - Judge Users";
    private static final String BASE_LINE_PEAK_COURT_CLERK_USERS = "Baseline Peak - DARTS - Portal - Court Clerk Users";
    private static final String BASE_LINE_PEAK_COURT_MANAGER_USERS = "Baseline Peak - DARTS - Portal - Court Manager Users";
    private static final String BASE_LINE_PEAK_TRANSCRIBER_USERS = "Baseline Peak - DARTS - Portal - Transcriber Users";
    private static final String BASE_LINE_PEAK_LANGUAGE_USERS = "Baseline Peak - DARTS - Portal - Language Shop Users";
    private static final String BASE_LINE_PEAK_SOAP_REQUESTS = "Baseline Peak - DARTS - Soap - Requests";

    public PortalandSoapBaselinePeakTestSimulation() {
        HttpProtocolBuilder httpProtocolInternal = configureInternalHttp();
        HttpProtocolBuilder httpProtocolExternal = configureExternalHttp();
        HttpProtocolBuilder httpProtocolSoap = configureSoapHttp();

        setUpScenarios(httpProtocolExternal, httpProtocolInternal, httpProtocolSoap);
    }

    private void setUpScenarios(HttpProtocolBuilder httpProtocolExternal, HttpProtocolBuilder httpProtocolInternal, HttpProtocolBuilder httpProtocolSoap) {
        setUp(
            CourtClerkUsersScenario.build(BASE_LINE_PEAK_COURT_CLERK_USERS)
                .injectOpen(rampUsers(AppConfig.COURT_CLERK_RAMP_UP_USERS_PEAK)
                .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_COURT_CLERK)))
                .protocols(httpProtocolInternal),

            CourtManagerUsersScenario.build(BASE_LINE_PEAK_COURT_MANAGER_USERS)
                .injectOpen(rampUsers(AppConfig.COURT_MANAGER_RAMP_UP_USERS_PEAK)
                .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_COURT_MANAGER)))
                .protocols(httpProtocolInternal),

            TranscriberUsersScenario.build(BASE_LINE_PEAK_TRANSCRIBER_USERS)
                .injectOpen(rampUsers(AppConfig.TRANSCRIBER_RAMP_UP_USERS_PEAK)
                .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_TRANSCRIBER)))
                .protocols(httpProtocolExternal),

            JudgeUserScenario.build(BASE_LINE_PEAK_JUDGE_USERS)
                .injectOpen(rampUsers(AppConfig.JUDGE_RAMP_UP_USERS_PEAK)
                .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_JUDGES)))
                .protocols(httpProtocolInternal),

            LanguageShopUserScenario.build(BASE_LINE_PEAK_LANGUAGE_USERS)
                .injectOpen(rampUsers(AppConfig.LANGUAGE_SHOP_RAMP_UP_USERS_PEAK)
                .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_LANGUAGE_SHOP)))
                .protocols(httpProtocolExternal),

            SoapUsersScenario.build(BASE_LINE_PEAK_SOAP_REQUESTS)
                .injectOpen(atOnceUsers(95))
                .protocols(httpProtocolSoap)
        );
    }

    private HttpProtocolBuilder configureInternalHttp() {
        return http
            .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
            .baseUrl("https://login.microsoftonline.com")
            .acceptHeader("application/json, text/plain, */*")
            .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
    }

    private HttpProtocolBuilder configureExternalHttp() {
        return http
            .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
            .baseUrl(AppConfig.EnvironmentURL.B2B_Login.getUrl())
            .acceptHeader("application/json, text/plain, */*")
            .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
    }

    private HttpProtocolBuilder configureSoapHttp() {
        return http
            .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
            .contentTypeHeader("text/xml;charset=UTF-8")
            .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)")
            .baseUrl(AppConfig.EnvironmentURL.PROXY_BASE_URL.getUrl());
    }
}
