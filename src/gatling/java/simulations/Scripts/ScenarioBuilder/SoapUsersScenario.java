package simulations.Scripts.ScenarioBuilder;

import io.gatling.javaapi.core.ScenarioBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;

import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.AddCaseUserScenario;
import simulations.Scripts.Scenario.DartsSoap.AddCourtlogUserScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentCPPDailyListTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentCPPEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentXhibitDailyListTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentXhibitEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.GetCasesUserScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;

public class SoapUsersScenario {
    
    public static ScenarioBuilder build(
        String scenarioName,
        int addCaseRepeats, 
        int getCaseRepeats, 
        int addLogRepeats, 
        int addDocumentCPPEventRepeats, 
        int addDocumentCPPDailyListRepeats, 
        int addDocumentXhibitEventRepeats, 
        int addDocumentXhibitDailyListRepeats) {
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
        )
            
        // Register with different CPP
        .group("Register With CPP External Username")
        .on(
            exec(RegisterWithUsernameScenario.RegisterWithUsername
                (EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(),
                EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()
                )
            )
            .exec(RegisterWithTokenScenario.registerWithToken
                 (EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(),
                 EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()
                 )
            )
        )
        .group("Add Document CPP")
        .on(
            repeat(addDocumentCPPEventRepeats)
                .on(exec(AddDocumentCPPEventTokenScenario.AddDocumentCPPEventToken()))
            .repeat(addDocumentCPPDailyListRepeats) 
                .on(exec(AddDocumentCPPDailyListTokenScenario.AddDocumentCPPDailyListToken()))
        )

        // Register with different XHIBIT
        .group("Register With XHIBIT External Username")
        .on(
            exec(RegisterWithUsernameScenario.RegisterWithUsername
                (EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(),
                EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()
                )
            )
            .exec(RegisterWithTokenScenario.registerWithToken
                (EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(),
                EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()
                )
            )
        )
        .group("Add Document Xhibit")
        .on(
            repeat(addDocumentXhibitEventRepeats)
                .on(exec(AddDocumentXhibitEventTokenScenario.AddDocumentXhibitEventToken()))
            .repeat(addDocumentXhibitDailyListRepeats)
                .on(exec(AddDocumentXhibitDailyListTokenScenario.AddDocumentXhibitDailyListToken()))
        );

    }
}
