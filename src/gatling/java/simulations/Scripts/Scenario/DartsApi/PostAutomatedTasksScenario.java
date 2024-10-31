package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class PostAutomatedTasksScenario {

    private PostAutomatedTasksScenario() {}
    public static ChainBuilder PostAutomatedTasksRequest() {
        return group("Automated Tasks Request POST")
        .on(exec(feed(Feeders.createCaseHouseRoomsHearingDetails()))           
            .exec(http("DARTS - Api - AutomatedTasksRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/admin/automated-tasks/11/run") 
                .headers(Headers.getHeaders(24))
                .check(status().saveAs("statusCode"))
                .check(status().is(202))
            ));
    }       
}