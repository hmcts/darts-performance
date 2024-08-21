package simulations.Scripts.Headers;

import java.util.Map;

import simulations.Scripts.Utilities.AppConfig;

import java.util.HashMap;

public class Headers {
    public static final Map<CharSequence, String> StandardHeaders = Map.ofEntries(
        Map.entry("Sec-Fetch-Dest", "empty"),
        Map.entry("Sec-Fetch-Mode", "cors"),
        Map.entry("Sec-Fetch-Site", "same-origin"),
        Map.entry("sec-ch-ua", "Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24"),
        Map.entry("sec-ch-ua-mobile", "?0"),
        Map.entry("sec-ch-ua-platform", "Windows"),
        Map.entry("x-dtpc", "3$353367900_249h39vFJVCICPLERLNMNMRQIEOQRIAHARLRUKW-0e0")
    );

    public static final Map<CharSequence, String> StandardHeaders2 = Map.ofEntries(
        Map.entry("Sec-Fetch-Dest", "empty"),
        Map.entry("Sec-Fetch-Mode", "cors"),
        Map.entry("Sec-Fetch-Site", "same-origin"),
        Map.entry("sec-ch-ua", "Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24"),
        Map.entry("sec-ch-ua-mobile", "?0"),
        Map.entry("sec-ch-ua-platform", "Windows"),
        Map.entry("Accept", "application/json, text/plain, */*"),
        Map.entry("Content-Type", "application/json"),
        Map.entry("Origin", AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl()),
        Map.entry("Referer", AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/case/#{getCaseId}/hearing/#{getHearings.id}")
    );

    public static final Map<String, String> AuthorizationHeaders = new HashMap<>();

    // Populate AuthorizationHeaders
    static {
        AuthorizationHeaders.put("Sec-Fetch-Dest", "empty");
        AuthorizationHeaders.put("Sec-Fetch-Mode", "cors");
        AuthorizationHeaders.put("Sec-Fetch-Site", "same-origin");
        AuthorizationHeaders.put("accept-language", "en-US,en;q=0.9");
        AuthorizationHeaders.put("authorization", "Bearer #{bearerToken}");
        AuthorizationHeaders.put("origin", AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl());
        AuthorizationHeaders.put("sec-ch-ua", "Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24");
        AuthorizationHeaders.put("sec-ch-ua-mobile", "?0");
        AuthorizationHeaders.put("sec-ch-ua-platform", "Windows");
        AuthorizationHeaders.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36");
        AuthorizationHeaders.put("x-dtpc", "3$353367900_249h39vFJVCICPLERLNMNMRQIEOQRIAHARLRUKW-0e0");
    }

    public static final Map<String, String> CourthouseHeaders = new HashMap<>();

    static {
        CourthouseHeaders.put("Content-Type", "application/json");
        CourthouseHeaders.put("authorization", "Bearer #{bearerToken}");
    }

    // Method to add an additional header conditionally
    public static Map<String, String> addAdditionalHeader(Map<String, String> headers, boolean userId, boolean eventStream) {
        Map<String, String> updatedHeaders = new HashMap<>(headers);
    
        if (userId) {
            updatedHeaders.put("user_id", "#{usr_id}");
        }
    
        // Add or update the "accept" header based on the condition
        updatedHeaders.put("accept", eventStream ? "text/event-stream" : "*/*");
    
        return updatedHeaders;
    }

    public static final Map<CharSequence, String> ApiHeaders = Map.ofEntries(
        Map.entry("Accept", "*/*"),
        Map.entry("Cache-Control", "no-cache"),
        Map.entry("accept-encoding", "gzip, deflate, br"),
        Map.entry("user-agent", "application/x-www-form-urlencoded"),
        Map.entry("Content-Type", "application/x-www-form-urlencoded")
      );



          // SoapHeaders 
    public static final Map<CharSequence, String> SoapHeaders = Map.ofEntries(
        Map.entry("SOAPAction", "\"\"")
    );


    public static final Map<String, String> PortalCommonHeaders = Map.ofEntries(
        Map.entry("Sec-Fetch-Dest", "empty"),
        Map.entry("Sec-Fetch-Mode", "cors"),
        Map.entry("Sec-Fetch-Site", "same-origin"),
        Map.entry("sec-ch-ua", "Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122"),
        Map.entry("sec-ch-ua-mobile", "?0"),
        Map.entry("sec-ch-ua-platform", "Windows")
    );  

    public static Map<String, String> portalLoginHeaders(Map<String, String> headers) {
        Map<String, String> updatedHeaders = new HashMap<>(PortalCommonHeaders);
            
        updatedHeaders.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        updatedHeaders.put("Sec-Fetch-Dest", "document");
        updatedHeaders.put("Sec-Fetch-Mode", "navigate");
        updatedHeaders.put("Sec-Fetch-User", "?1");
        updatedHeaders.put("Upgrade-Insecure-Requests", "1");
        return updatedHeaders;
    }

    public static Map<String, String> portalLogOutHeaders(Map<String, String> headers) {
        Map<String, String> updatedHeaders = new HashMap<>(PortalCommonHeaders);
            
        updatedHeaders.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        updatedHeaders.put("Sec-Fetch-Dest", "document");
        updatedHeaders.put("Sec-Fetch-Mode", "navigate");
        updatedHeaders.put("Sec-Fetch-User", "?1");
        updatedHeaders.put("Upgrade-Insecure-Requests", "1");
        updatedHeaders.put("return-client-request-id", "true");
        updatedHeaders.put("tb-aad-device-family", "3");
        updatedHeaders.put("tb-aad-env-id", "10.0.19041.4474");
        updatedHeaders.put("x-ms-RefreshTokenCredential", "NA");
        return updatedHeaders;
    }

    public static Map<String, String> AzureadB2cLoginHeaders(Map<String, String> headers) {
        Map<String, String> updatedHeaders = new HashMap<>(PortalCommonHeaders);
        
        updatedHeaders.put("Accept", "*/*");
        updatedHeaders.put("Origin", AppConfig.EnvironmentURL.B2B_Login.getUrl());
        updatedHeaders.put("Sec-Fetch-Site", "cross-site");
        return updatedHeaders;
    }
    
    public static Map<String, String> PerftraceHeaders(Map<String, String> headers) {
        Map<String, String> updatedHeaders = new HashMap<>(PortalCommonHeaders);
        
        updatedHeaders.put("Accept", "application/json, text/javascript, */*; q=0.01");
        updatedHeaders.put("Content-Type", "application/json; charset=UTF-8");
        updatedHeaders.put("Origin", AppConfig.EnvironmentURL.B2B_Login.getUrl());
        updatedHeaders.put("X-CSRF-TOKEN", "#{csrf}");
        updatedHeaders.put("X-Requested-With", "XMLHttpRequest");
        updatedHeaders.put("Referer", "https://hmctsstgextid.b2clogin.com/hmctsstgextid.onmicrosoft.com/B2C_1_darts_externaluser_signin/oauth2/v2.0/authorize?client_id=363c11cb-48b9-44bf-9d06-9a3973f6f413&redirect_uri=https%3A%2F%2Fdarts.test.apps.hmcts.net%2Fauth%2Fcallback&scope=openid&prompt=login&response_mode=form_post&response_type=code");
        return updatedHeaders;
    }

    public static final Map<CharSequence, String> DartsPortalHeaders21 = Map.ofEntries(   
    Map.entry("Connection", "keep-alive"),
    Map.entry("Content-Length", "82"),
    Map.entry("sec-ch-ua", "\"Not A(Brand\";v=\"8\", \"Google Chrome\";v=\"126\", \"Chromium\";v=\"126\""),
    Map.entry("X-CSRF-TOKEN", "#{csrf}"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36"),
    Map.entry("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"),
    Map.entry("Accept", "application/json, text/javascript, */*; q=0.01"),
    Map.entry("X-Requested-With", "XMLHttpRequest"),
    Map.entry("sec-ch-ua-platform", "\"Windows\""),
    Map.entry("Origin", AppConfig.EnvironmentURL.B2B_Login.getUrl()),
    Map.entry("Sec-Fetch-Site", "same-origin"),
    Map.entry("Sec-Fetch-Mode", "cors"),
    Map.entry("Sec-Fetch-Dest", "empty"),
    Map.entry("Referer", AppConfig.EnvironmentURL.B2B_Login.getUrl() + "/"+ AppConfig.EnvironmentURL.DARTS_PORTAL_Auth_LOGIN.getUrl() + "?client_id="+ AppConfig.EnvironmentURL.EXTERNAL_CLIENT_ID.getUrl() +"&redirect_uri="+ AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "%2Fauth%2Fcallback&scope=openid&prompt=login&response_mode=form_post&response_type=code"),
    Map.entry("Accept-Encoding", "gzip, deflate, br, zstd"),
    Map.entry("Accept-Language", "en-US,en;q=0.9")
    );

    public static Map<String, String> DartsPortalHeaders3(Map<String, String> headers) {
        Map<String, String> updatedHeaders = new HashMap<>(PortalCommonHeaders);
        
        updatedHeaders.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        updatedHeaders.put("Cache-Control", "max-age=0");
        updatedHeaders.put("Origin", AppConfig.EnvironmentURL.B2B_Login.getUrl());
        updatedHeaders.put("Sec-Fetch-Dest", "document");
        updatedHeaders.put("Sec-Fetch-Mode", "navigate");
        updatedHeaders.put("Sec-Fetch-Site", "cross-site");
        updatedHeaders.put("Upgrade-Insecure-Requests", "1");
        return updatedHeaders;
    }

    public static final Map<CharSequence, String> DartsPortalHeaders4 = Map.ofEntries(
    Map.entry("Sec-Fetch-Dest", "empty"),
    Map.entry("Sec-Fetch-Mode", "cors"),
    Map.entry("Sec-Fetch-Site", "same-origin"),
    Map.entry("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "Windows"),
    Map.entry("x-dtpc", "1$315192570_228h2vRUPELAAMUTHGMGAVQKEMCVNGFKSNLOCP-0e0")
    );    
    
    public static final Map<CharSequence, String> DartsPortalHeaders5 = Map.ofEntries(
    Map.entry("Sec-Fetch-Dest", "empty"),
    Map.entry("Sec-Fetch-Mode", "cors"),
    Map.entry("Sec-Fetch-Site", "same-origin"),
    Map.entry("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "Windows"),
    Map.entry("x-dtpc", "1$315192570_228h10vRUPELAAMUTHGMGAVQKEMCVNGFKSNLOCP-0e0"),
    Map.entry("x-dtreferer", AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/")
    );  


    public static final Map<String, String> CommonHeaders = Map.ofEntries(
        Map.entry("Request-Context", "appId=cid-v1:b3dee2d6-8fe5-407e-b65f-c7d24670531d"),
        Map.entry("Request-Id", "|43d8376d8efc4a2c96aafc203b3ee232.16ba10280ab1428c"),
        Map.entry("Sec-Fetch-Dest", "empty"),
        Map.entry("Sec-Fetch-Mode", "cors"),
        Map.entry("Sec-Fetch-Site", "same-origin"),
        Map.entry("sec-ch-ua", "Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122"),
        Map.entry("sec-ch-ua-mobile", "?0"),
        Map.entry("sec-ch-ua-platform", "Windows"),
        Map.entry("traceparent", "00-43d8376d8efc4a2c96aafc203b3ee232-16ba10280ab1428c-01"),
        Map.entry("x-dtpc", "5$336731829_286h95vSEKMHRRHHHPUKSBIGWAIQRROWWRCFGGR-0e0")
    );  

    public static final Map<String, String> AddDocHeaders = Map.ofEntries(
        Map.entry("sec-ch-ua", "Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122"),
        Map.entry("sec-ch-ua-platform", "Windows"),
        Map.entry("traceparent", "00-43d8376d8efc4a2c96aafc203b3ee232-16ba10280ab1428c-01"),
        Map.entry("sec-ch-ua-mobile", "?0"),
        Map.entry("Request-Id", "|43d8376d8efc4a2c96aafc203b3ee232.16ba10280ab1428c"),
        Map.entry("Request-Context", "appId=cid-v1:b3dee2d6-8fe5-407e-b65f-c7d24670531d"),
        Map.entry("Sec-Fetch-Dest", "empty"),
        Map.entry("Sec-Fetch-Mode", "cors"),
        Map.entry("Sec-Fetch-Site", "same-origin"),
        Map.entry("Referer", "https://darts.test.apps.hmcts.net/work/#{getTranscriptionId}")
    ); 
    
    // public static final Map<String, String> headers_6 = Map.ofEntries(
    // Map.entry("Content-Type", "application/json"),
    // Map.entry("Origin", "https://darts.test.apps.hmcts.net"),
    // Map.entry("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128"),
    // Map.entry("Request-Id", "|597bfa8201c241449ff1bff600c82581.c6401b1f193f49e5"),
    // Map.entry("Sec-Fetch-Dest", "empty"),
    // Map.entry("Sec-Fetch-Mode", "cors"),
    // Map.entry("Sec-Fetch-Site", "same-origin"),
    // Map.entry("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123"),
    // Map.entry("sec-ch-ua-mobile", "?0"),
    // Map.entry("sec-ch-ua-platform", "Windows"),
    // Map.entry("traceparent", "00-597bfa8201c241449ff1bff600c82581-c6401b1f193f49e5-01")
    // );

        // Define headers for each case
        // Method to add an additional header conditionally
        public static Map<String, String> searchCaseHeaders(Map<String, String> headers) {
            Map<String, String> updatedHeaders = new HashMap<>(CommonHeaders);
                
            updatedHeaders.put("Content-Type", "application/json");
            updatedHeaders.put("Origin", AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl());
            return updatedHeaders;
        }

        public static Map<String, String> searchReferer(Map<String, String> headers) {
            Map<String, String> updatedHeaders = new HashMap<>(CommonHeaders);
                
            updatedHeaders.put("x-dtreferer", AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/search");
            return updatedHeaders;
        }
        public static Map<String, String> caseReferer(Map<String, String> headers) {
            Map<String, String> updatedHeaders = new HashMap<>(CommonHeaders);
                
            updatedHeaders.put("x-dtreferer", AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/case/29709");
            return updatedHeaders;
        }   

        public static final Map<String, String> headers_0 = Map.ofEntries(
        Map.entry("Sec-Fetch-Dest", "empty"),
        Map.entry("Sec-Fetch-Mode", "cors"),
        Map.entry("Sec-Fetch-Site", "same-origin"),
        Map.entry("X-CSRF-TOKEN", "#{csrf}"),
        Map.entry("X-Requested-With", "XMLHttpRequest"),
        Map.entry("sec-ch-ua", "Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122"),
        Map.entry("sec-ch-ua-mobile", "?0"),
        Map.entry("sec-ch-ua-platform", "Windows")
        );


        public static Map<String, String> getHeaders(int headerType) {
            Map<String, String> headers = new HashMap<>();
            switch (headerType) {
                case 0:
                    headers.put("Sec-Fetch-Dest", "document");
                    headers.put("Sec-Fetch-Mode", "navigate");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("Sec-Fetch-User", "?1");
                    headers.put("Upgrade-Insecure-Requests", "1");
                    headers.put("sec-ch-ua", "\"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126\"");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
                    headers.put("Accept-Encoding", "gzip, deflate, br, zstd");
                    headers.put("Accept-Language", "en-US,en;q=0.9");
                    headers.put("Connection", "keep-alive");
                    break;
                case 1:
                    headers.put("Accept", "application/json");
                    headers.put("Content-type", "application/json; charset=UTF-8");
                    headers.put("Origin", "https://login.microsoftonline.com");
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("canary", "#{canary}");
                    headers.put("client-request-id", "#{clientRequestId}");
                    headers.put("hpgact", "1800");
                    headers.put("hpgid", "1104");
                    headers.put("hpgrequestid", "#{sessionId}");
                    headers.put("sec-ch-ua", "\"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126\"");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    break;
                case 2:
                    headers.put("Cache-Control", "max-age=0");
                    headers.put("Origin", "https://login.microsoftonline.com");
                    headers.put("Sec-Fetch-Dest", "document");
                    headers.put("Sec-Fetch-Mode", "navigate");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("Sec-Fetch-User", "?1");
                    headers.put("Upgrade-Insecure-Requests", "1");
                    headers.put("sec-ch-ua", "\"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126\"");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    break;
                case 3:
                    headers.put("Cache-Control", "max-age=0");
                    headers.put("Sec-Fetch-Dest", "document");
                    headers.put("Sec-Fetch-Mode", "navigate");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("Sec-Fetch-User", "?1");
                    headers.put("sec-ch-ua", "Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    break;
                case 4:
                    headers.put("Cache-Control", "max-age=0");
                    headers.put("Sec-Fetch-Dest", "document");
                    headers.put("Sec-Fetch-Mode", "navigate");
                    headers.put("Sec-Fetch-Site", "cross-site");
                    headers.put("sec-ch-ua", "Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    break;
                case 5:
                    headers.put("Sec-Fetch-Dest", "iframe");
                    headers.put("Sec-Fetch-Mode", "navigate");
                    headers.put("Sec-Fetch-Site", "cross-site");
                    headers.put("Upgrade-Insecure-Requests", "1");
                    headers.put("sec-ch-ua", "Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    break;
                case 6:
                    headers.put("Accept", "application/json, text/plain, */*");
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("sec-ch-ua", "Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    headers.put("Referer", "https://darts.test.apps.hmcts.net/auth/internal/logout-callback?sid=#{sessionId}");
                    break;
                case 7:
                    headers.put("Accept", "application/json, text/plain, */*");
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("sec-ch-ua", "Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    headers.put("Referer", "https://darts.test.apps.hmcts.net/login");
                    break;
                case 8:
                    headers.put("Content-Type", "application/json");
                    headers.put("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128");
                    headers.put("Request-Id", "|597bfa8201c241449ff1bff600c82581.c6401b1f193f49e5");
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    headers.put("traceparent", "00-597bfa8201c241449ff1bff600c82581-c6401b1f193f49e5-01");
                    break;
                case 9:
                    headers.put("Content-Type", "application/json");
                    headers.put("Origin", "https://darts.test.apps.hmcts.net");
                    headers.put("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128");
                    headers.put("Request-Id", "|597bfa8201c241449ff1bff600c82581.c6401b1f193f49e5");
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    headers.put("traceparent", "00-597bfa8201c241449ff1bff600c82581-c6401b1f193f49e5-01");
                    break;
                case 10:
                    headers.put("sec-ch-ua", "\"Not)A;Brand\";v=\"99\", \"Google Chrome\";v=\"127\", \"Chromium\";v=\"127\"");
                    headers.put("accept", "*/*");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36");
                    headers.put("sec-ch-ua-platform", "\"Windows\"");
                    headers.put("Origin", "https://darts-api.test.platform.hmcts.net");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Referer", "https://darts-api.test.platform.hmcts.net/swagger-ui/index.html");
                    headers.put("Accept-Encoding", "gzip, deflate, br, zstd");
                    headers.put("Accept-Language", "en-US,en;q=0.9");        
            }
            return headers;
        }
}
