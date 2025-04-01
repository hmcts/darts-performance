package simulations.Scripts.ScenarioBuilder;

import io.gatling.javaapi.core.ScenarioBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;

import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.AddCaseUserScenario;
import simulations.Scripts.Scenario.DartsSoap.AddCourtlogUserScenario;
import simulations.Scripts.Scenario.DartsSoap.GetCasesUserScenario;

public class SoapProxyUsersScenario {
    
    public static ScenarioBuilder build(
        String scenarioName,
        int addCaseRepeats, 
        int getCaseRepeats, 
        int addLogRepeats
) {
        return scenario(scenarioName)
        .group("VIQ External Requests")
        .on(
            repeat(addCaseRepeats)
            .on(exec
                (AddCaseUserScenario.addCaseUser
                    (EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), 
                    EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl())
                )
            )
            .repeat(getCaseRepeats)
            .on(exec
                (GetCasesUserScenario.GetCaseSOAPUser
                    (EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), 
                    EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl()
                    )
                )
            )
            .repeat(addLogRepeats)
            .on(exec
                (AddCourtlogUserScenario.addCourtLogUser
                    (EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(),
                    EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl()
                    )
                )
            )
        ); 
    }
}
