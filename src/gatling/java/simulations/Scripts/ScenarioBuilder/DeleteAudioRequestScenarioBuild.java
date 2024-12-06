package simulations.Scripts.ScenarioBuilder;

import simulations.Scripts.Scenario.DartsApi.DeleteAudioRequestScenario;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;

public class DeleteAudioRequestScenarioBuild {
   public static ScenarioBuilder build(String scenarioName, int deleteAudioRequestRepeats) {
    return scenario(scenarioName)
        .group("Delete Audio Request Scenario")
        .on(exec(GetApiTokenScenario.getApiToken())
            .repeat(deleteAudioRequestRepeats)
            .on(exec(DeleteAudioRequestScenario.DeleteAudioRequest())));
    }
}