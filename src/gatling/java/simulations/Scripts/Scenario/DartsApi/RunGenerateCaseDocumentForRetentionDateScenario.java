package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class RunGenerateCaseDocumentForRetentionDateScenario {

    
    private RunGenerateCaseDocumentForRetentionDateScenario() {}
    public static ChainBuilder RunGenerateCaseDocumentForRetentionDate() {

     

        return group("Generate Case Document For Retention Date")
        .on(exec(http("DARTS - Api - AutomatedTasksRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/admin/automated-tasks/21/run") 
                .headers(Headers.getHeaders(24))
                .check(status().saveAs("statusCode"))
                .check(status().is(202))
        ))
        .exec(session -> {
                System.out.println("Automated Tasks 21 has been ran for Generate Case Documents for For Retention Date");
            return session;
        });       
    }       
}