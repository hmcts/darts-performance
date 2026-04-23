package simulations.Scripts.DartsApi;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.GetAudioRequestScenario;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.HttpUtil;

import static io.gatling.javaapi.core.CoreDsl.*;


public class AudioRequestGetSimulation extends Simulation {
    {

        final HttpProtocolBuilder httpProtocol =
                HttpUtil.getHttpProtocol()
                        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
                        .inferHtmlResources();

        final ScenarioBuilder scn1 = scenario("Audio Requests:GET")
                .exec(GetApiTokenScenario.getApiToken())
                .repeat(3)
                .on(
                        uniformRandomSwitch().on(
                                exec(GetAudioRequestScenario.GetAudioRequestDownload()),
                                exec(GetAudioRequestScenario.GetAudioRequestPlayBack())
                        )
                );
        setUp(
                scn1.injectOpen(atOnceUsers(1)).protocols(httpProtocol)
        );
    }
}