package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class RunUnstructuredToArmDataStoreScenario {

    
    private RunUnstructuredToArmDataStoreScenario() {}
    public static ChainBuilder RunUnstructuredToArmDataStore() {

     

        return group("Unstructured To Arm DataStore")
        .on(exec(http("DARTS - Api - AutomatedTasksRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/admin/automated-tasks/8/run") 
                .headers(Headers.getHeaders(24))
                .check(status().saveAs("statusCode"))
                .check(status().is(202))
        ))
        .exec(session -> {
                System.out.println("Automated Tasks 8 has been ran for Unstructured To Arm DataStore");
            return session;
        });       
    }       
}