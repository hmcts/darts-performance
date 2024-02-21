
import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class GETAudioPreview extends Simulation {

  {
    HttpProtocolBuilder httpProtocol = http
      .baseUrl("https://darts-api.staging.platform.hmcts.net")
      .inferHtmlResources()
      .acceptEncodingHeader("gzip, deflate")
      .acceptLanguageHeader("en-US,en;q=0.9")
      .userAgentHeader("Fiddler/5.0.20211.51073 (.NET 4.8; WinNT 10.0.19045.0; en-GB; 12xAMD64; Auto Update; Full Instance; Extensions: APITesting, AutoSaveExt, EventLog, FiddlerOrchestraAddon, HostsFile, RulesTab2, SAZClipboardFactory, SimpleFilter, Timeline)");
    
    Map<CharSequence, String> headers_0 = new HashMap<>();
    headers_0.put("Accept-Language", "en-GB");
    headers_0.put("Connection", "close");
    headers_0.put("Pragma", "no-cache");
    
    Map<CharSequence, String> headers_1 = new HashMap<>();
    headers_1.put("Accept-Encoding", "gzip, deflate, br");
    headers_1.put("Sec-Fetch-Dest", "empty");
    headers_1.put("Sec-Fetch-Mode", "cors");
    headers_1.put("Sec-Fetch-Site", "same-origin");
    headers_1.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");
    headers_1.put("accept", "text/event-stream");
    headers_1.put("authorization", "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6Ilg1ZVhrNHh5b2pORnVtMWtsMll0djhkbE5QNC1jNTdkTzZRR1RWQndhTmsiLCJ0eXAiOiJKV1QifQ.eyJpZHAiOiJMb2NhbEFjY291bnQiLCJvaWQiOiI4ZmIwMDRmMy00NzRiLTQ4MzItYjk1YS03YzRiNjhjZTJjNmQiLCJzdWIiOiI4ZmIwMDRmMy00NzRiLTQ4MzItYjk1YS03YzRiNjhjZTJjNmQiLCJnaXZlbl9uYW1lIjoiRGFydHMiLCJmYW1pbHlfbmFtZSI6IlRyYW5zY3JpYmVyIiwiZW1haWxzIjpbImRhcnRzLnRyYW5zY3JpYmVyQGhtY3RzLm5ldCJdLCJ0ZnAiOiJCMkNfMV9yb3BjX2RhcnRzX3NpZ25pbiIsInNjcCI6IkZ1bmN0aW9uYWwuVGVzdCIsImF6cCI6Ijg1MTE4ZWUyLTY1NDQtNDE4MC1hMTZlLTk5NjE1MzY1ZjIwOSIsInZlciI6IjEuMCIsImlhdCI6MTcwODUwNDk1MiwiYXVkIjoiODUxMThlZTItNjU0NC00MTgwLWExNmUtOTk2MTUzNjVmMjA5IiwiZXhwIjoxNzA4NTA4NTUyLCJpc3MiOiJodHRwczovL2htY3Rzc3RnZXh0aWQuYjJjbG9naW4uY29tLzhiMTg1ZjhiLTY2NWQtNGJiMy1hZjRhLWFiN2VlNjFiOTMzNC92Mi4wLyIsIm5iZiI6MTcwODUwNDk1Mn0.U4rkybfkTUGkwzOKlQSaLt8VAe13LDKWIUn6nG7cJmEqyK7-CQ1l70pMUyMlvwCFIVPVfJsIMw7LXmQQzZrDyEjZMpRx95N87Nifzbjbp041y7aBJob1unol1EdIRaPsERDg0MNCCZtBI0nrsqywJj1dokZX-rtgB1jntitQ916a5y0SxvgpEK77s_oe3GG_hZ3cQJE_SShF2dYebMBJkpnFInCd_CcsrPET5Hed3R4L0JDJ0LS_lxtXEITrwwYPmJRAR-FcvfT5snIeGOeMtsrtq2EWkgOXibVnWiyCxb1t9aQk5QtHinXYw1mwQUK3Pyi4JDzTXLAv6L0WqBbACQ");
    headers_1.put("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121");
    headers_1.put("sec-ch-ua-mobile", "?0");
    headers_1.put("sec-ch-ua-platform", "Windows");
    headers_1.put("x-dtpc", "4$131465890_612h44vWMAOGOERRPCCVUFTFCMGWCUQKTNNBAHM-0e0");
    
    String uri2 = "https://www.fiddler2.com/UpdateCheck.aspx";

    ScenarioBuilder scn = scenario("GETAudioPreview")
      .exec(
        http("request_0")
          .get(uri2 + "?isBeta=False")
          .headers(headers_0)
      )
      .pause(93)
      .exec(
        http("request_1")
          .get("/audio/preview/13488")
          .headers(headers_1)
      );

	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
