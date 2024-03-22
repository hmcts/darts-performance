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
        Map.entry("Referer", AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/case/#{getCaseId.case_id}/hearing/#{getHearings.id}")
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
        Map.entry("SOAPAction", "")
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
    Map.entry("Host", AppConfig.EnvironmentURL.B2B_Login.getUrl()),
    Map.entry("Connection", "keep-alive"),
    Map.entry("Content-Length", "82"),
    Map.entry("sec-ch-ua", "\"Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121\""),
    Map.entry("X-CSRF-TOKEN", "#{csrf}"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36"),
    Map.entry("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"),
    Map.entry("Accept", "application/json, text/javascript, */*; q=0.01"),
    Map.entry("X-Requested-With", "XMLHttpRequest"),
    Map.entry("sec-ch-ua-platform", "\"Windows\""),
    Map.entry("Origin", AppConfig.EnvironmentURL.B2B_Login.getUrl()),
    Map.entry("Sec-Fetch-Site", "same-origin"),
    Map.entry("Sec-Fetch-Mode", "cors"),
    Map.entry("Sec-Fetch-Dest", "empty"),
    Map.entry("Referer", AppConfig.EnvironmentURL.B2B_Login.getUrl() + "/"+ AppConfig.EnvironmentURL.DARTS_PORTAL_Auth_LOGIN.getUrl() + "?client_id="+ AppConfig.EnvironmentURL.AZURE_AD_B2C_CLIENT_ID.getUrl() +"&redirect_uri="+ AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "%2Fauth%2Fcallback&scope=openid&prompt=login&response_mode=form_post&response_type=code"),
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
        Map.entry("X-CSRF-TOKEN", "NW1xRG1PN2NyYTh3REI1Tm5sRkFCM3dNeVQrMExvSVYzRHhyK3hEd1YxdXBoa2EyVHRuV1Y5SG1Fb0dKY2I5R2NtbnBScjIwQ0E4NWxSTzZ4dVlhN2c9PTsyMDI0LTAzLTE1VDE0OjU1OjI5LjUzMTE2MTZaO211K1RzbWc5TDBaZm0yKzlzTllqV3c9PTt7Ik9yY2hlc3RyYXRpb25TdGVwIjoxfQ=="),
        Map.entry("X-Requested-With", "XMLHttpRequest"),
        Map.entry("sec-ch-ua", "Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122"),
        Map.entry("sec-ch-ua-mobile", "?0"),
        Map.entry("sec-ch-ua-platform", "Windows")
        );
}
