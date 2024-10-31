package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class RunCloseOldUnfinishedTranscriptionsTaskScenario {

    
    private RunCloseOldUnfinishedTranscriptionsTaskScenario() {}
    public static ChainBuilder RunCloseOldUnfinishedTranscriptionsTask() {

     

        return group("Close Old Unfinished Transcriptions")
        .on(exec(http("DARTS - Api - AutomatedTasksRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/admin/automated-tasks/2/run") 
                .headers(Headers.getHeaders(24))
                .check(status().saveAs("statusCode"))
                .check(status().is(202))
        ))
        .exec(session -> {
                System.out.println("Automated Tasks 2 has been ran for Close Old Unfinished Transcriptions");
            return session;
        });       
    }      
}