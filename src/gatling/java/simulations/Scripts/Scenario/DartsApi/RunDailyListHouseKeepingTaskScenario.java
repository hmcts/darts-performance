package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class RunDailyListHouseKeepingTaskScenario {

    
    private RunDailyListHouseKeepingTaskScenario() {}
    public static ChainBuilder RunDailyListHouseKeepingTask() {

     

        return group("Deletes daily lists older than 30 days")
        .on(exec(http("DARTS - Api - AutomatedTasksRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/admin/automated-tasks/13/run") 
                .headers(Headers.AuthorizationHeaders)
                .check(status().saveAs("statusCode"))
                .check(status().is(202))
        ))
        .exec(session -> {
                System.out.println("Automated Tasks 13 has been ran for Daily List Housekeeping");
            return session;
        });       
    }      
}