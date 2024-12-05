package simulations.Scripts.ScenarioBuilder;

import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.RunInboundToUnstructuredDataStoreScenario;
import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;

public class InboundtoUnstructuredDatastoreTaskScenario {
   public static ScenarioBuilder build(String scenarioName) {
    return scenario(scenarioName)
        .group("Run Inbound To Unstructured DataStore Task")
        .on(exec(GetApiTokenScenario.getApiToken())
                .repeat(1)    
                .on(exec(RunInboundToUnstructuredDataStoreScenario.RunInboundToUnstructuredDataStore())));
    }
}
        