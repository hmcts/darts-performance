package simulations.Scripts.ScenarioBuilder;

import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.PostAudioRequestScenario;
import simulations.Scripts.Utilities.AppConfig;
import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;

public class PostAudioRequestScenarioBuild {
   public static ScenarioBuilder build(String scenarioName) {
    return scenario(scenarioName)
        .group("Post Audio Request Scenario")
        .on(exec(GetApiTokenScenario.getApiToken())
            .repeat(AppConfig.POST_AUDIO_REQUEST_PEAK_REPEATS)
            .on(exec(PostAudioRequestScenario.PostaudioRequest())));
    }
}        