// package simulations.Scripts.DartsApi;

// import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
// import simulations.Scripts.Scenario.DartsApi.GetAudioRequestScenario;
// import simulations.Scripts.Utilities.AppConfig;
// import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
// import io.gatling.javaapi.core.*;
// import io.gatling.javaapi.http.*;

// import static io.gatling.javaapi.core.CoreDsl.*;
// import static io.gatling.javaapi.http.HttpDsl.*;


// public class AudioRequestGetDownloadSimulation2 extends Simulation {   
//   {
//     final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();

//     final HttpProtocolBuilder httpProtocol = http
//         .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT).httpsPort(AppConfig.PROXY_PORT))
//         .baseUrl(EnvironmentURL.B2B_Login.getUrl())
//         .inferHtmlResources();

//     final ScenarioBuilder scn1 = scenario("Audio Requests:GET Download")
//         .exec(GetApiTokenScenario.getApiToken())
//         .repeat(10)    
//         .on(exec(GetAudioRequestScenario.GetAudioRequestDownload().feed(feeder))    
//         );

//     setUp(
//         scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
//     }    
// }




// package simulations.Scripts.DartsSoap;

// import simulations.Scripts.Utilities.AppConfig;
// import simulations.Scripts.Utilities.Feeders;
// import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
// import simulations.Scripts.Scenario.DartsSoap.AddDocumentDailyListTokenScenario;
// import simulations.Scripts.Scenario.DartsSoap.AddDocumentEventTokenScenario;
// import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
// import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;
// import io.gatling.javaapi.core.*;
// import io.gatling.javaapi.http.*;
// import java.time.Duration;

// import static io.gatling.javaapi.core.CoreDsl.*;
// import static io.gatling.javaapi.http.HttpDsl.*;


// public class AddDocumentTokenSimulation extends Simulation {
   
//     @Override
//     public void before() {
//       System.out.println("Simulation is about to start!");
//     }

//     {
//         HttpProtocolBuilder httpProtocol = http
//             .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
//             .baseUrl(EnvironmentURL.GATEWAY_BASE_URL.getUrl())
//             .inferHtmlResources()
//             .acceptEncodingHeader("gzip,deflate")
//             .contentTypeHeader("text/xml;charset=UTF-8")
//             .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");


//         // Scenario 1: AddDocumentEventTokenScenario
//         final ScenarioBuilder baselineScenario = scenario("DARTS - GateWay - Soap - AddDocument:POST - Event Token")
//             .feed(Feeders.createCourtHouseAndCourtRooms())
//             .exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
//             .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
//             .repeat(AppConfig.SOAP_BASELINE_REPEATS)
//             .on( 
//                 doIf(session -> session.get("messageId") != null)
//                     .then(
//                         exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
//                             .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
//                     )
//             .exec(AddDocumentEventTokenScenario.AddDocumentEventToken().pace(Duration.ofMillis(AppConfig.SOAP_BASELINE_PACE_DURATION_MILLIS))));


//         // Scenario 2: Ramp up Test
//         final ScenarioBuilder rampUpScenario = scenario("Ramp Up Test")
//             .feed(Feeders.createCourtHouseAndCourtRooms())
//             .exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
//             .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
//             .repeat(AppConfig.SOAP_RAMPUP_REPEATS)
//             .on( 
//                 doIf(session -> session.get("messageId") != null)
//                     .then(
//                         exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
//                             .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
//                     )
//             .exec(AddDocumentEventTokenScenario.AddDocumentEventToken().pace(Duration.ofMillis(AppConfig.SOAP_RAMPUP_PACE_DURATION_MILLIS))));

//         // Scenario 3: Spike Test
//         final ScenarioBuilder spikeScenario = scenario("Spike Test")
//         .feed(Feeders.createCourtHouseAndCourtRooms())
//         .exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
//         .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
//         .repeat(AppConfig.SOAP_SPIKE_REPEATS)
//         .on( 
//             doIf(session -> session.get("messageId") != null)
//                 .then(
//                     exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
//                         .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
//                 )
//         .exec(AddDocumentEventTokenScenario.AddDocumentEventToken().pace(Duration.ofMillis(AppConfig.SOAP_SPIKE_PACE_DURATION_MILLIS))));
        
//         // Setting up of scenarios
//         setUp(
//             baselineScenario.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.BASELINE_TEST_DURATION_MINUTES))).protocols(httpProtocol)
//             .andThen(rampUpScenario.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.RAMP_TEST_DURATION_MINUTES))).protocols(httpProtocol))
//             .andThen(spikeScenario.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SPIKE_TEST_DURATION_MINUTES))).protocols(httpProtocol))
//             );
//     }

//     @Override
//     public void after() {
//     System.out.println("Simulation is finished!");
//     }
// }
