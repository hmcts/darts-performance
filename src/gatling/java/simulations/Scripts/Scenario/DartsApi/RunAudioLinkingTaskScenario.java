package simulations.Scripts.Scenario.DartsApi;

import lombok.extern.slf4j.Slf4j;
import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

@Slf4j
public final class RunAudioLinkingTaskScenario {

    
    private RunAudioLinkingTaskScenario() {}
    public static ChainBuilder RunAudioLinkingTask() {

     

        return group("Audio Linking")
        .on(exec(http("DARTS - Api - AutomatedTasksRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/admin/automated-tasks/27/run") 
                .headers(Headers.getHeaders(24))
                .check(status().saveAs("statusCode"))
                .check(status().is(202))
        ))
        .exec(session -> {
                log.info("Automated Tasks 27 has been ran for Audio Linking");
            return session;
        });       
    }       
}