package simulations.Scripts.ParallelSimulations;

import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsApi.GetAudioRequestScenario;

import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class NEWAudioRequestGetSimulation extends Simulation {

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public NEWAudioRequestGetSimulation() {
        HttpProtocolBuilder httpProtocolApi = http
           //     .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
                .inferHtmlResources()
                .baseUrl(EnvironmentURL.B2B_Login.getUrl());
        setUpScenarios(httpProtocolApi);
    }

    private void setUpScenarios(HttpProtocolBuilder httpProtocolApi) {
        // Main SOAP scenario setup
        ScenarioBuilder mainScenario = scenario("Audio Requests:GET")
         //Register with different VIQ
         .group("Audio Requests:GET")
         .on(exec(GetApiTokenScenario.getApiToken())
         .repeat(10)
         .on(
             uniformRandomSwitch().on(
                 exec(GetAudioRequestScenario.GetAudioRequestDownload()),
                 exec(GetAudioRequestScenario.GetAudioRequestPlayBack())
             )        
        ));
      
        setUp(
            mainScenario.injectOpen(atOnceUsers(1)).protocols(httpProtocolApi)
        );
    }

    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }
}
