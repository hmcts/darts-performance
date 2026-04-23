package simulations.Scripts.Utilities;

import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.http.HttpDsl.Proxy;

public class HttpUtil {

    public static HttpProtocolBuilder getHttpProtocol() {

        HttpProtocolBuilder builder = HttpDsl.http;
        if (AppConfig.ENABLE_PROXY) {
            builder = builder.proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT));
        }
        return builder;
    }
}
