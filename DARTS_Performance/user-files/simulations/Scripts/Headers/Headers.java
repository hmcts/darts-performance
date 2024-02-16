package Headers;

import java.util.Map;

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

    // AuthorizationHeaders
    public static final Map<CharSequence, String> AuthorizationHeaders = Map.ofEntries(
        Map.entry("Sec-Fetch-Dest", "empty"),
        Map.entry("Sec-Fetch-Mode", "cors"),
        Map.entry("Sec-Fetch-Site", "same-origin"),
        Map.entry("accept-language", "en-US,en;q=0.9"),
        Map.entry("authorization", "Bearer #{bearerToken}"),
        Map.entry("origin", "BASE_URL"),
        Map.entry("sec-ch-ua", "Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24"),
        Map.entry("sec-ch-ua-mobile", "?0"),
        Map.entry("sec-ch-ua-platform", "Windows"),
        Map.entry("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36"),
        Map.entry("x-dtpc", "3$353367900_249h39vFJVCICPLERLNMNMRQIEOQRIAHARLRUKW-0e0")
    );

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
}
