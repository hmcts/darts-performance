//package user_files.simulations.Scripts.DartsSoap;

// import io.gatling.core.scenario.Simulation;
// import io.gatling.http.request.builder.HttpRequestBuilder;
// import io.gatling.http.HeaderValues;
// import io.gatling.http.protocol.HttpProtocolBuilder;

// import static io.gatling.http.HeaderValues.APPLICATION_X_WWW_FORM_URLENCODED;
// import static io.gatling.http.Predef.http;
// import static io.gatling.core.Predef.scenario;
// import static io.gatling.core.structure.ScenarioBuilder;

// import io.gatling.core.Predef.*;
// import io.gatling.http.Predef.*;

// import java.time.Duration;
// import java.util.*;

// import io.gatling.javaapi.core.*;
// import io.gatling.javaapi.http.*;
// import io.gatling.javaapi.jdbc.*;

// import static io.gatling.javaapi.core.CoreDsl.*;
// import static io.gatling.javaapi.http.HttpDsl.*;
// import static io.gatling.javaapi.jdbc.JdbcDsl.*;

// import java.util.UUID;

// public class AddAudioSOAPUser extends Simulation {

//     private static final String BASE_URL = "https://darts-gateway.staging.platform.hmcts.net";
//     private static final String uri = "/service/darts/DARTSService";
//     private static final String boundary = UUID.randomUUID().toString();
//     private static final String contentType = "multipart/related; type=\"application/xop+xml\"; boundary=\"" + boundary + "\"; start=\"<rootpart@soapui.org>\"; start-info=\"text/xml\"";
//     private static final String requestBody = "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:com=\"http://com.synapps.mojdarts.service.com\" xmlns:core=\"http://core.datamodel.fs.documentum.emc.com/\" xmlns:prop=\"http://properties.core.datamodel.fs.documentum.emc.com/\" xmlns:con=\"http://content.core.datamodel.fs.documentum.emc.com/\">\n"
//             + "   <s:Header>\n"
//             + "      <ServiceContext token=\"temporary/127.0.0.1-1694086218480-789961425\" xmlns=\"http://context.core.datamodel.fs.documentum.emc.com/\">\n"
//             + "         <Identities xsi:type=\"RepositoryIdentity\" userName=\"test\" password=\"test\" repositoryName=\"moj_darts\" domain=\"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n"
//             + "         <RuntimeProperties/>\n"
//             + "      </ServiceContext>\n"
//             + "   </s:Header>\n"
//             + "   <s:Body>\n"
//             + "      <com:addAudio>\n"
//             + "         <!--Optional:-->\n"
//             + "         <document>&lt;audio&gt;&lt;start Y=\"2023\" M=\"9\" D=\"7\" H=\"11\" MIN=\"26\" S=\"51\" /&gt;&lt;end Y=\"2023\" M=\"9\" D=\"7\" H=\"11\" MIN=\"29\" S=\"49\" /&gt;&lt;channel&gt;1&lt;/channel&gt;&lt;max_channels&gt;4&lt;/max_channels&gt;&lt;mediaformat&gt;mpeg2&lt;/mediaformat&gt;&lt;mediafile&gt;0001.a00&lt;/mediafile&gt;&lt;courthouse&gt;test&lt;/courthouse&gt;&lt;courtroom&gt;test&lt;/courtroom&gt;&lt;case_numbers&gt;&lt;case_number&gt;test&lt;/case_number&gt;&lt;case_number&gt;test&lt;/case_number&gt;&lt;/case_numbers&gt;&lt;/audio&gt;</document>\n"
//             + "      </com:addAudio>\n"
//             + "   </s:Body>\n"
//             + "</s:Envelope>";

//         private static final HttpRequestBuilder requestBuilder =
//         http("DARTS - SOAP - AddAudio - User")
//             .post(uri)
//             .header("Content-Type", contentType)
//             .bodyParts(
//                 HttpDsl.StringBodyPart(requestBody)
//                     .contentType("application/xop+xml; charset=UTF-8; charset=UTF-8; type=\\\"text/xml")
//                     .transferEncoding("8bit")
//                     .contentId("<rootpart@soapui.org>"),
//                 RawFileBodyPart("C://data/sample.mp2")
//                     .contentType("application/octet-stream")
//                     .transferEncoding("binary",boundary)
//                     .contentId("<sample6.mp2")
//                     .header("Content-Disposition", "attachment", "name=sample6.mp2")
//                     .fileName("sample.mp2")
//             )
//             .check(status().is(200))
//             .check(xpath("//return/code").saveAs("statusCode"))
//             .check(xpath("//return/message").saveAs("message"));
    
//     private static final ScenarioBuilder scn = scenario("AddAudioSOAPUser")
//           .exec(requestBuilder);

//     private static final HttpProtocolBuilder httpProtocol = 
//         http()
//             .baseUrl(BASE_URL)
//             .proxy(Proxy("127.0.0.1", 8888).httpsPort(8888))
//             .inferHtmlResources()
//             .acceptEncodingHeader("gzip,deflate")
//             .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");

//     {
//         setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol);
//     }
// }
