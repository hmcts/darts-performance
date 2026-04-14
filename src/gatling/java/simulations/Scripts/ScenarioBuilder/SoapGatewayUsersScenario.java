package simulations.Scripts.ScenarioBuilder;

import io.gatling.javaapi.core.ScenarioBuilder;
import simulations.Scripts.Scenario.DartsSoap.*;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;

import static io.gatling.javaapi.core.CoreDsl.*;

public class SoapGatewayUsersScenario {

    public static ScenarioBuilder build(
            String scenarioName
    ) {
        return scenario(scenarioName)
                .group("VIQ External Requests")
                .on(
                        repeat(AppConfig.ADD_CASES_PEAK_REPEATS)
                                .on(exec(AddCaseUserScenario.addCaseUser(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl())))
                                .repeat(AppConfig.GET_CASES_PEAK_REPEATS)
                                .on(exec(GetCasesUserScenario.GetCaseSOAPUser(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl())))
                                .repeat(AppConfig.ADD_LOG_ENTRY_PEAK_REPEATS)
                                .on(exec(AddCourtlogUserScenario.addCourtLogUser(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl())))
                )

                // Register with different CPP
                .group("Register With CPP External Username")
                .on(
                        exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()))
                                .exec(RegisterWithTokenScenario.registerWithToken(EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()))
                )
                .group("Add Document CPP")
                .on(
                        repeat(AppConfig.CPP_EVENTS_PEAK_REPEATS)
                                .on(exec(AddDocumentCPPEventTokenScenario.AddDocumentCPPEventToken()))
                                .repeat(AppConfig.CPP_DailyList_PEAK_REPEATS)
                                .on(exec(AddDocumentCPPDailyListTokenScenario.AddDocumentCPPDailyListToken()))
                )

                // Register with different XHIBIT
                .group("Register With XHIBIT External Username")
                .on(
                        exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
                                .exec(RegisterWithTokenScenario.registerWithToken(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
                )
                .group("Add Document Xhibit")
                .on(
                        repeat(AppConfig.XHIBIT_EVENTS_PEAK_REPEATS)
                                .on(exec(AddDocumentXhibitEventTokenScenario.AddDocumentXhibitEventToken()))
                                .repeat(AppConfig.XHIBIT_DailyList_PEAK_REPEATS)
                                .on(exec(AddDocumentXhibitDailyListTokenScenario.AddDocumentXhibitDailyListToken()))
                );
    }
}
