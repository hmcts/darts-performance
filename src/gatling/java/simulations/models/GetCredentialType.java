package simulations.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.ALWAYS)
public class GetCredentialType {

    @JsonProperty("username")
    private String username;

    @JsonProperty("isOtherIdpSupported")
    private boolean otherIdpSupported;

    @JsonProperty("checkPhones")
    private boolean checkPhones;

    @JsonProperty("isRemoteNGCSupported")
    private boolean remoteNGCSupported;

    @JsonProperty("isCookieBannerShown")
    private boolean cookieBannerShown;

    @JsonProperty("isFidoSupported")
    private boolean fidoSupported;

    @JsonProperty("originalRequest")
    private String originalRequest;

    @JsonProperty("country")
    private String country;

    @JsonProperty("forceotclogin")
    private boolean forceOtpLogin;

    @JsonProperty("isExternalFederationDisallowed")
    private boolean externalFederationDisallowed;

    @JsonProperty("isRemoteConnectSupported")
    private boolean remoteConnectSupported;

    @JsonProperty("federationFlags")
    private int federationFlags;

    @JsonProperty("isSignup")
    private boolean signup;

    @JsonProperty("flowToken")
    private String flowToken;

    @JsonProperty("isAccessPassSupported")
    private boolean accessPassSupported;

    @JsonProperty("isQrCodePinSupported")
    private boolean qrCodePinSupported;
}
