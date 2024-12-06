package simulations.Scripts.ScenarioBuilder;

import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.GetAudioRequestScenario;
import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;

public class GetAudioRequestScenarioBuild {
   public static ScenarioBuilder build(String scenarioName, int getAudioRequestRepeats) {
    return scenario(scenarioName)
        .group("Get Audio Request Scenario")
        .on(exec(GetApiTokenScenario.getApiToken())
            .repeat(getAudioRequestRepeats)
            .on(uniformRandomSwitch().on(
                exec(GetAudioRequestScenario.GetAudioRequestDownload()),
                exec(GetAudioRequestScenario.GetAudioRequestPlayBack()))));
    }
}