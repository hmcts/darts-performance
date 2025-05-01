package simulations.Scripts.ScenarioBuilder;

import simulations.Scripts.Scenario.DartsApi.DeleteAudioRequestScenario;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Utilities.AppConfig;
import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;

public class DeleteAudioRequestScenarioBuild {
   public static ScenarioBuilder build(String scenarioName) {
    return scenario(scenarioName)
        .group("Delete Audio Request Scenario")
        .on(exec(GetApiTokenScenario.getApiToken())
            .repeat(AppConfig.DELETE_AUDIO_REQUEST_PEAK_REPEATS)
            .on(exec(DeleteAudioRequestScenario.DeleteAudioRequest())));
    }
}