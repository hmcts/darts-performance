package Headers;

import java.util.Map;
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

    public static final Map<String, String> AuthorizationHeaders = new HashMap<>();

    // Populate AuthorizationHeaders
    static {
        AuthorizationHeaders.put("Sec-Fetch-Dest", "empty");
        AuthorizationHeaders.put("Sec-Fetch-Mode", "cors");
        AuthorizationHeaders.put("Sec-Fetch-Site", "same-origin");
        AuthorizationHeaders.put("accept-language", "en-US,en;q=0.9");
        AuthorizationHeaders.put("authorization", "Bearer #{bearerToken}");
        AuthorizationHeaders.put("origin", "BASE_URL");
        AuthorizationHeaders.put("sec-ch-ua", "Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24");
        AuthorizationHeaders.put("sec-ch-ua-mobile", "?0");
        AuthorizationHeaders.put("sec-ch-ua-platform", "Windows");
        AuthorizationHeaders.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36");
        AuthorizationHeaders.put("x-dtpc", "3$353367900_249h39vFJVCICPLERLNMNMRQIEOQRIAHARLRUKW-0e0");
    }

    // Method to add an additional header conditionally
    public static Map<String, String> addAdditionalHeader(Map<String, String> headers, boolean condition) {
        if (condition) {
            Map<String, String> updatedHeaders = new HashMap<>(headers);
            updatedHeaders.put("user_id", "#{usr_id}");
            return updatedHeaders;
        } else {
            return headers;
        }
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
}
