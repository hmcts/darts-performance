package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class RunUnstructuredAudioDeleterTaskScenario {

    
    private RunUnstructuredAudioDeleterTaskScenario() {}
    public static ChainBuilder RunUnstructuredAudioDeleterTask() {

     
        return group("Unstructured Audio Deleter")
        .on(exec(http("DARTS - Api - AutomatedTasksRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/admin/automated-tasks/7/run") 
                .headers(Headers.getHeaders(24))
                .check(status().saveAs("statusCode"))
                .check(status().is(202))
        ))
        .exec(session -> {
                System.out.println("Automated Tasks 7 has been ran for Unstructured Audio Deleter");
            return session;
        });       
    }       
}