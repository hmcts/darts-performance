package simulations.Scripts.Scenario.DartsApi;

import lombok.extern.slf4j.Slf4j;
import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

@Slf4j
public final class RunCaseExpiryDeletionScenario {

    
    private RunCaseExpiryDeletionScenario() {}
    public static ChainBuilder RunCaseExpiryDeletion() {

     

        return group("Remove Duplicate Events")
        .on(exec(http("DARTS - Api - AutomatedTasksRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/admin/automated-tasks/22/run") 
                .headers(Headers.getHeaders(24))
                .check(status().saveAs("statusCode"))
                .check(status().is(202))
        ))
        .exec(session -> {
                log.info("Automated Tasks 18 has been ran for Case Expiry Deletion");
            return session;
        });       
    }       
}