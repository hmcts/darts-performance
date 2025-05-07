package simulations.Scripts.ScenarioBuilder;

import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.GetAudioRequestScenario;
import simulations.Scripts.Utilities.AppConfig;
import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;

public class GetAudioRequestScenarioBuild {
   public static ScenarioBuilder build(String scenarioName) {
    return scenario(scenarioName)
        .group("Get Audio Request Scenario")
        .on(exec(GetApiTokenScenario.getApiToken())
            .repeat(AppConfig.GET_AUDIO_REQUEST_PEAK_REPEATS)
            .on(exec(GetAudioRequestScenario.GetAudioRequestDownload())));

            // .on(uniformRandomSwitch().on(
            //     exec(GetAudioRequestScenario.GetAudioRequestDownload()),
            //     exec(GetAudioRequestScenario.GetAudioRequestPlayBack()))));
    }
}