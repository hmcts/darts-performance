package simulations.Scripts.ScenarioBuilder;

import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.RunApplyRetentionCaseAssociatedObjectsTaskScenario;
import simulations.Scripts.Scenario.DartsApi.RunApplyRetentionTaskScenario;
import simulations.Scripts.Scenario.DartsApi.RunInboundToUnstructuredDataStoreScenario;
import simulations.Scripts.Scenario.DartsApi.RunProcessArmResponseFilesTaskScenario;
import simulations.Scripts.Scenario.DartsApi.RunUnstructuredToArmDataStoreScenario;
import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;

public class AutomatedTaskScenario {
   public static ScenarioBuilder build(String scenarioName) {
    return scenario(scenarioName)
        .group("Automated Tasks")
        .on(exec(GetApiTokenScenario.getApiToken())
                .repeat(1)    
                .on(exec(RunInboundToUnstructuredDataStoreScenario.RunInboundToUnstructuredDataStore())
              //  .exec(RunApplyRetentionTaskScenario.RunApplyRetentionTask())
               // .exec(RunApplyRetentionCaseAssociatedObjectsTaskScenario.RunApplyRetentionCaseAssociatedObjectsTask())
             //   .exec(RunUnstructuredToArmDataStoreScenario.RunUnstructuredToArmDataStore())
             //   .exec(RunProcessArmResponseFilesTaskScenario.RunProcessArmResponseFilesTask())
            )
        );
    }
}
        