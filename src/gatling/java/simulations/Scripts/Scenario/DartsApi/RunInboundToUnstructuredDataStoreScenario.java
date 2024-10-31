package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class RunInboundToUnstructuredDataStoreScenario {

    
    private RunInboundToUnstructuredDataStoreScenario() {}
    public static ChainBuilder RunInboundToUnstructuredDataStore() {

     

        return group("Inbound To Unstructured DataStore")
        .on(exec(http("DARTS - Api - AutomatedTasksRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/admin/automated-tasks/6/run") 
                .headers(Headers.getHeaders(24))
                .check(status().saveAs("statusCode"))
                .check(status().is(202))
        ))
        .exec(session -> {
                System.out.println("Automated Tasks 18 has been ran for Inbound To Unstructured DataStore");
            return session;
        });       
    }       
}