package simulations.Scripts.Headers;

import java.util.Map;

import simulations.Scripts.Utilities.AppConfig;

import java.util.HashMap;

public class Headers {

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

    public static final Map<String, String> AddDocHeaders = Map.ofEntries(
        Map.entry("sec-ch-ua", "Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122"),
        Map.entry("sec-ch-ua-platform", "Windows"),
        Map.entry("sec-ch-ua-mobile", "?0"),
        Map.entry("Request-Context", "appId=cid-v1:b3dee2d6-8fe5-407e-b65f-c7d24670531d"),
        Map.entry("Sec-Fetch-Dest", "empty"),
        Map.entry("Sec-Fetch-Mode", "cors"),
        Map.entry("Sec-Fetch-Site", "same-origin"),
        Map.entry("Referer", "https://darts.test.apps.hmcts.net/work/#{getTranscriptionId}")
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
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    break;
                case 9:
                    headers.put("Content-Type", "application/json");
                    headers.put("Origin", AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl());
                    headers.put("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128");
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
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
                    break;
                case 11:
                    headers.put("Origin", AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl());
                    headers.put("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128");
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    break; 
                case 12:
                    headers.put("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128");
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    break;
                case 13:
                    headers.put("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128");
                    headers.put("Accept", "*/*");
                    headers.put("Sec-Fetch-Dest", "audio");
                    headers.put("Sec-Fetch-Mode", "no-cors");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    headers.put("Range", "bytes=0-");
                    break;
                case 14:
                    headers.put("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128");
                    headers.put("accept", "application/json, text/plain, */*");
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    headers.put("Accept-Encoding", "gzip, deflate, br, zstd");
                    headers.put("Accept-Language", "en-US,en;q=0.9");
                    break;
                case 15:
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    break;
                case 16:
                    headers.put("Sec-Fetch-Dest", "document");
                    headers.put("Sec-Fetch-Mode", "navigate");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("Sec-Fetch-User", "?1");
                    headers.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
                    headers.put("Upgrade-Insecure-Requests", "1");
                    headers.put("return-client-request-id", "true");
                    headers.put("tb-aad-device-family", "3");
                    headers.put("tb-aad-env-id", "10.0.19041.4474");
                    headers.put("x-ms-RefreshTokenCredential", "NA");
                    break;
                case 17:
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Site", "cross-site");
                    headers.put("Sec-Fetch-User", "?1");
                    headers.put("Sec-Fetch-User", "?1");
                    headers.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    headers.put("Accept", "*/*");
                    headers.put("Origin", AppConfig.EnvironmentURL.B2B_Login.getUrl());
                    break;
                case 18:
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("sec-ch-ua", "Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    break;
                case 19:
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("Sec-Fetch-User", "?1");
                    headers.put("Sec-Fetch-User", "?1");
                    headers.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    headers.put("Accept", "*/*");
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Origin", AppConfig.EnvironmentURL.B2B_Login.getUrl());
                    headers.put("X-CSRF-TOKEN", "#{csrf}");
                    headers.put("X-Requested-With", "XMLHttpRequest");
                    headers.put("Referer", "https://hmctsstgextid.b2clogin.com/hmctsstgextid.onmicrosoft.com/B2C_1_darts_externaluser_signin/oauth2/v2.0/authorize?client_id=363c11cb-48b9-44bf-9d06-9a3973f6f413&redirect_uri=https%3A%2F%2Fdarts.test.apps.hmcts.net%2Fauth%2Fcallback&scope=openid&prompt=login&response_mode=form_post&response_type=code");
                    break;
                case 20:   
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("sec-ch-ua", "Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
                    headers.put("Cache-Control", "max-age=0");
                    headers.put("Origin", AppConfig.EnvironmentURL.B2B_Login.getUrl());
                    headers.put("Sec-Fetch-Dest", "document");
                    headers.put("Sec-Fetch-Mode", "navigate");
                    headers.put("Sec-Fetch-Site", "cross-site");
                    headers.put("Upgrade-Insecure-Requests", "1");
                    break;
                case 21: 
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("X-CSRF-TOKEN", "#{csrf}");
                    headers.put("X-Requested-With", "XMLHttpRequest");
                    headers.put("sec-ch-ua", "Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    break;
                case 22:
                    headers.put("Sec-Fetch-Dest", "empty");
                    headers.put("Sec-Fetch-Mode", "cors");
                    headers.put("Sec-Fetch-Site", "same-origin");
                    headers.put("sec-ch-ua", "Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24");
                    headers.put("sec-ch-ua-mobile", "?0");
                    headers.put("sec-ch-ua-platform", "Windows");
                    headers.put("Accept", "application/json, text/plain, */*");
                    headers.put("Content-Type", "application/json");
                    headers.put("Origin", AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl());
                    headers.put("Referer", AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/case/#{getCaseId}/hearing/#{getHearings.id}");
            }
            return headers;
        }
}    
