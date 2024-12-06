package simulations.Scripts.ScenarioBuilder;

import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.PostAudioRequestScenario;
import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;

public class PostAudioRequestScenarioBuild {
   public static ScenarioBuilder build(String scenarioName, int postAudioRequestRepeats) {
    return scenario(scenarioName)
        .group("Post Audio Request Scenario")
        .on(exec(GetApiTokenScenario.getApiToken())
            .repeat(postAudioRequestRepeats)
            .on(exec(PostAudioRequestScenario.PostaudioRequest())));
    }
}        