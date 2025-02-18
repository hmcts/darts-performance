package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class RunProcessDETSToArmResponseTaskScenario {

    
    private RunProcessDETSToArmResponseTaskScenario() {}
    public static ChainBuilder RunProcessDETSToArmResponseTask() {

     

        return group("Process DETS To Arm Response Task")
        .on(exec(http("DARTS - Api - AutomatedTasksRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/admin/automated-tasks/24/run") 
                .headers(Headers.getHeaders(24))
                .check(status().saveAs("statusCode"))
                .check(status().is(202))
        ))
        .exec(session -> {
                System.out.println("Automated Tasks 18 has been ran for Process DETS To Arm Response Task");
            return session;
        });       
    }       
}