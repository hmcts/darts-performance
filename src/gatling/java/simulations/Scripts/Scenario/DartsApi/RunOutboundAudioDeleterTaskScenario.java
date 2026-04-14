package simulations.Scripts.Scenario.DartsApi;

import io.gatling.javaapi.core.ChainBuilder;
import lombok.extern.slf4j.Slf4j;
import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;

import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.group;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

@Slf4j
public final class RunOutboundAudioDeleterTaskScenario {


    private RunOutboundAudioDeleterTaskScenario() {
    }

    public static ChainBuilder RunOutboundAudioDeleterTask() {

        return group("Outbound Audio Deleter")
                .on(exec(http("DARTS - Api - AutomatedTasksRequest:POST")
                        .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/admin/automated-tasks/3/run")
                        .headers(Headers.getHeaders(24))
                        .check(status().saveAs("statusCode"))
                        .check(status().is(202))
                ))
                .exec(session -> {
                    log.info("Automated Tasks 3 has been ran for Outbound Audio Deleter");
                    return session;
                });
    }
}