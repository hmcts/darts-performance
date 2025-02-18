package simulations.Scripts.Scenario.DartsPortal;

import io.gatling.javaapi.core.ChainBuilder;
import simulations.Scripts.Headers.Headers;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.UserInfoLogger;
import simulations.Scripts.Utilities.NumberGenerator;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalInternalLoginScenario {

    private DartsPortalInternalLoginScenario() {}

    public static ChainBuilder DartsPortalInternalLoginRequest() {

        return group("Darts Portal Internal Login")
            .on(   
                exec(
                    http("Darts-Portal - Login")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/login")
                        .headers(Headers.getHeaders(0))
                        )
                .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Login"))
                
                .exitHereIfFailed()
                .exec(
                    http("Darts-Portal - App - Config")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/App/Config")
                        .headers(Headers.getHeaders(0))
                    )
                .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - App - Config"))
                .exitHereIfFailed() 

                ///This Gets changed and causes internal users to fail log in. If this happens manually go into the portal with 1 user and grab it from the cookies. 
                .exec(addCookie(Cookie("Cookie", "Cookie: brcap=0; MicrosoftApplicationsTelemetryDeviceId=cdc9ea8a-5a9c-45e1-830b-295ed79e94f0; ESTSSSOTILES=1; AADSSOTILES=1; MSFPC=GUID=3cf27633ddc2408d81f8de91dc12c921&HASH=3cf2&LV=202403&V=4&LU=1711447933369; esctx-MRM8ciqf6yk=AQABCQEAAABVrSpeuWamRam2jAF1XRQEFkBLXUJlc28QowloaqpX4piKpxR03dp8BKiQXnPdJcYL03rkfUqnMonkyN6gREoEC_N6U9dH8Woc3fyCV4kfgb3ihZ_znLvA2L_p8Lw2qs_34b5Tb8AFTPon7V2vo0is2cVRqp4X735C1bwTQt3NNSAA; x-ms-gateway-slice=estsfd; stsservicecookie=estsfd; AADSSO=NA|NoExtension; esctx-2YSAQFXcrY=AQABCQEAAABVrSpeuWamRam2jAF1XRQEo-XYstz5S3lKW7lJdMpuR8bW5YZP9TJqe68_lDaU2PnuvmYVyQ4YEVKeN7NG4r-WiWmfFRDbGuQXg0O71efBUYBZNMCApiaMsnhGFZWp7KoGyxF92xStQ-Ht0fiz1Ht5srJsrMH-SSIv_Pe41O-hUSAA; esctx=PAQABBwEAAABVrSpeuWamRam2jAF1XRQElNo6mNyOYRj1HOXL-hk3kw9ACguLICOapalEaGoR1INOuWYNpGQRuwIEGZc53xOGmyOMCPkB8OtFzDwCRWwXetpjXr8hoqPrWGIu1NT9-YEA4GFjb1QCtcyn1VYPLYkb34xtzc177-EUOsPFdAfuNnGWQyhd5EJukazoXu_c3ysgAA; esctx-QWSwGhXVSQ=AQABCQEAAABVrSpeuWamRam2jAF1XRQEUoapHByqOKQ5OPpoD9dRA96nwF3PmcTGzXgwhfUpiTr4tDYTBi90Qq8vBTlx8RL1AIlvHAcOELlajWUSWmMNB5l7pO_NJDIqngkNeEcn7OT89TgrB4zcDU38jSM79myWFOP7fNwS32Jaj32XjFd2cSAA; ai_session=7XBGmJVU0QuZKyf9hOZD5g|1739352370994|1739352370994; wlidperf=FR=L&ST=1739352382820; ESTSAUTHPERSISTENT=1.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM7AA.AgABFwQAAABVrSpeuWamRam2jAF1XRQEAwDs_wUA9P9QCKD-GWs_0DBE3V3K_lBsZ_zQ6gymLOQ3E4Xo6Ioin46_ssqQJCPujU_46EOVnLtJaZtb0tmkHIAMSals-AewxsKj0VvF96qxJlelL340weTCDSi72Kq_RcDTyDFcsY9EmXoJLx360nuWhjrbGN7O0ahjKGqYg-rlEitC-ivBAqqLKuycIEIxOPfdn20eds0R7UN4u4IKmccDOHUVd3L3ST69ryKnUYv-IEAb892H25iNHb3-ax_ekQSS8Lf399ya1lPNPueem1h84f_JnKgEy_MsOx-kbrkoY7yxZh6BSxZgMf2Pfv5tBtGs1YHnQ90Lm9GUYG16_WciBIf7M-0_IFbObA89Y3YmqQpdfMAj6qxddV3f7mkSn2kWdceEHLuHSE16HHNxmI99JuKQbFvgeFYrUSVQrWEqZBhjXf8GQDpgK9eCwv2sui7vy0FTiYVP40cvLCLG5dDKK-5p5VAVgxDZZW8hVzXDw_SZLXjGl89UY7XEeN9McPOERd6C_c7qZIpXyY6uGURFQvaN8ZA6FO2tkEzQQ8ZHWyhAz8u1UKwzy05Q_jCJJYrLlAverrQXRw5V-_zlqUOaAWi5TD4iibryks36tvExrLVzwDi_favR8LbNesHQCxN3E0h3e2LYjrB-MJ_TRi5mo68e3N4wq-XhI-4tlAPVoCnonfP4joiUtNsqPN6vY1lspTA6nemNDV1kY5zyMLHNgYmhbbyMxvPwFl-6bDMjitbomSGsacYe-BT-BkIhqe7g5d_cVJDky1C5-bTChDYhb3qZ7t9IqAsdt95EnFF2pv3Gf-wBVobB36EpnOB8Ii4DeMx-8RrjooRnmQMDgauzTofma_LYVlmd0RJ0R0CtqzWIxG5nLJGh9jVmVGyQXv04JKVyS88pD5o4iSaTttb_zzflYicfPpy9YPQzsVVz62m5wjvAVrXaEXe20LtteVDSaVL5c2fHE7f3CNbTBVbkMS3x82ZUowrAWWQ18nPjqPPC5Bah8Y4IxaGRCGw7llJralEeNz2UADO9KLoy2dEoziAoMhirij18VyRGNTk-Ci-Sos9DJiK11kV8rR0XC8GfR-FnmcU0Q3WzoApNrMRbbojikDgJZpGYz3s33PWnSkP1DEVmwHg7S1zJ1d7HbzMMeby-ZMNZrZuJXmXqiCCDEr4jcBF5_W_0LjTGbs0SD1GILHDchcH-0H7sUyJsHVgF7_CLaSlJNMLo4-FbZDuXluavdzM4B_t47VLZLijaT6R4WIVXC9GlIDjPHfjCc2DJnfOvSvOcxArgEmEQizWr80ALS-gMnrPL0Sl4CFTHrN5fcOZK_1KGhc0spgTHM8rShA2p-AQAHatGhyRNH0XFaNbSjeHKGxamedTLOkznW3_q_0XKqy0BSjr83HD9nnowxSleIYoXPSm_LIgpflwrJ9AtEg886wEI9OdFka9jdmj2Dmr6nobgA1Upq5QAGTbbxCzPq3kX3lTwJvyZI3VKflI-MX3QG5lpDGQqRFOmb82JvVLFknwBfayvQ52o21VTJGq5Wj7-Pu8oKEQjAhTWFs50OSrGmfB0tYOY5NWUufuzfc5n6GcRRnPAVFYeDdAKAnghZGQH9mYCKV_CB0Q6y00_ntectQ0qApXgni0oiihfHdKL-wOMV6hWF2YytV6OqDV1x3BvmX9HrjmELtSaE4NhFkzjTFCLzV_OtFiATpP80rUeyV8Bak7bLMOZzCyic5ah21AXjxFWZuf2-MgEXqe6gi03UYIDXIs506FLQEd32-W4YiBAJ-bAmmvh0RNenw; ESTSAUTH=1.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM7AA.AgABFwQAAABVrSpeuWamRam2jAF1XRQEAwDs_wUA9P_4u3Nce4Q0o7-yJ4QkEpf3-3XfE3z28MGpVjN60ywmsdyfUV2YrHp4MKfIYjaWnulok5ifN1kCZ8XxY_oIKOojBAi-YcZeVSjeMAwPywWrPFCx48wrFM-oUC0irt1K9uHDT--TX7_nZx8DjzoMb_pa4mohNAzhm_6j0oxkOoZbsTVh76lZOzzEhnvnZu8vNHvrsQsxl_pwJraig427jQ5sNk5E5R7xD4dzCPo-fMZWJmhjA2jSkIZ2zMYf6ywhAn_Y5yHg4TJ8Y57L0MTlx6a534PYL_ygKChJiI8qbv6R1znvOymREBTbikqyFkOf9zFKkBhy-Nck3wCxquiQRHGdW4XxUsLIynwkVM3gwyvGyfLATswjP6AAvsaNFnCZNWdMrLA1O2IGc_6lOU9onAZdCZMb4dDsLKyzJTnjA1_pLkzho0SpWXTBasurra4Ho91kNWZaezWxa0E0wNaAHGlq4Bv1W6bpeNHCB4UkbITVH3ugSu0f0jKSlhIsCzw6-1SfrvwRkS3T0f4y9_bQm4AnhFQx2BkdLE5sXCXW4GH6tL7XSsj3KFyRsLCKvUfYbEzR27g7C_QC_F0AC_LMqP3ng0LNL3U; ESTSAUTHLIGHT=+00206129-ccaf-04e1-720f-27700bb39d7d+b49609ec-70ae-4270-9f5e-d3c2ae22bcc4+a07a7602-1e09-45d8-b18b-e937f7e92587; buid=1.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM7AA.AQABGgEAAABVrSpeuWamRam2jAF1XRQEkqZ897ItOXUSmdEbwS8nKDTKkaKHC1ITyma0d0h9i1UWCN4t_cIELwYGC1ryXrU23kFiNZFL_8cQAkf2B0EUJ5sgB6gIfjbCmdfqyE_y2rFz3qmNQi-5VIeiuIm5KRTRj5zJfpjKYcMSpzbM7FJBdH7UK9zyaoOF_jJjxPJSEDcgAA; CCState=Q3JZQkNoZGhjMmhzWlhrdVkyOXZjR1Z5UUdodFkzUnpMbTVsZEJJQkFTSUpDWUFDUmRXek05eElLZ2tKbzY4cHg2aE4zVWd5S2dvU0NoRDhwTzRIV1ZhU1NaQTNFQUxVSzZJRkVna0pLbHJHTEM5TTNVZ2FDUW03QzNKZzFFbmRTRElxQ2hJS0VJTkFTOFN3TzhGSnRIMlhUbFBMM3p3U0NRbkxjM0ZZQmszZFNCb0pDZlo0RUl5clN0MUlPQUJJQUZJU0NoQnQrUjlUNlFvcVJvMHR2c2ZBdENDQ1doSUtFRDdiRDFva1laWkpyc2Y0NXFVelhJb1NFZ29RRU00VEREVFdyMGFsdEwrdVZ0bnFVaG9KQ2ZaNEVJeXJTdDFJTW40Q0FBRWZBUUFBQUZXdEtsNjVacVpGcWJhTUFYVmRGQVFEQU96L0JRRDAvK2RxQ0l4RjE4OXp5VzR0QmRTRUxzSVJCZnNLTFRDc3lwVEtpRDVSSnNBNVNBN29ENDBqT0tpQTU3dGF6OVFMTUE5Q0RNQndUUkgwblUvTmlwUUxoQ0ZaNWgvTDN4V3pLeFJuUmFUdCsvcUp5ZWQ3TWNSYzlYTDZqRW93aWJ3PQ==; SignInStateCookie=CAgABFgIAAABVrSpeuWamRam2jAF1XRQEAwDs_wUA9P9xZuwItviJrRYDCVDF4EBsDg6O0Xxw6NeWZHZlwDCAA7m559UZYxmnYKIz-HCHeDc33J5N_frFBqB26_Jp0NnoS9qj7uQ-wMzOAFWl7NpAdCgq4c_IPqAgmkjhsThaNxhCdkPe3S4SIONoBxF8yf-VTDTN3-niR553gBjN0j1ZBzygD86NSX8QueFcJ-ofuwGi5XpBa8akiNIsXEemaTyG_A_z16mGN74ink0b7NqBrOfrsF1ZdWjRB89qcgH7Yl1acBEbQN8I1YxPYS-aGg15NzJtdHf99QaEe-Xe6IjvvNNrWYwBcIvSXS1BqDODDhIIIvhY9AwmAlKFQHQI7We_zOZm2kgkOJqC0nPzG_xMl0mdkCxFMJAhWg31TSfhK-OOSVnb3Rp8e3UddA; fpc=AmYGlHXwPXpIrnlKfA4l2HfRXcakAQAAAC9gPt8OAAAAuoTrKQEAAAA-YD7fDgAAAA")))
               


                .exec(http("Darts-Portal - Auth - Internal - Login")
                .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/internal/login")
                .headers(Headers.getHeaders(0))
                .check(regex("(?s).*\"sessionId\":\"([^\"]+)\".*").findRandom().saveAs("sessionId"))
                .check(regex("(?s).*client-request-id=([a-fA-F0-9-]+).*").findRandom().optional().saveAs("clientRequestId"))
                .check(regex("(?s).*\"sCtx\":\"([^\"]+)\".*").findRandom().optional().saveAs("sCtx"))
                .check(regex("(?s).*\"FlowToken\":\"([^\"]+)\".*").findRandom().optional().saveAs("flowToken"))
                .check(regex("(?s).*\"canary\":\"([^\"]+)\".*").findRandom().optional().saveAs("canary"))
                .check(status().is(200))
                .check(status().saveAs("status"))
             )
             .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Internal - Login"))

            .exitHereIfFailed() 
            .exec(session -> {
                String sessionId = session.getString("sessionId");
                String clientRequestId = session.getString("clientRequestId");
                String sCtx = session.getString("sCtx");
                String email = session.getString("Email");

                if (sessionId != null) {
                    System.out.println("Session ID: " + sessionId);
                    if (clientRequestId != null) {
                        System.out.println("Client Request ID: " + clientRequestId);
                    } else {
                        System.out.println("Client Request ID not found in response.");
                    }
                    if (sCtx != null) {
                        System.out.println("sCtx: " + sCtx);
                    } else {
                        System.out.println("sCtx not found in response. User:" + email);
                    }
                } else {
                    System.out.println("No value saved for sessionId using saveAs.");
                }
                return session;
            })
            .exec(session -> {
                String xmlPayload = RequestBodyBuilder.buildGetCredentialType(session);
                return session.set("xmlPayload", xmlPayload);
            })
            .exec(http("Darts-Portal - Login Microsoftonline - Common - GetCredentialType")
                .post("https://login.microsoftonline.com/common/GetCredentialType?mkt=en-US")
                .headers(Headers.getHeaders(1))
                .body(StringBody("#{xmlPayload}")).asJson()
                .check(status().is(200))
                .check(status().saveAs("status"))
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Login Microsoftonline - Common - GetCredentialType"))

            .exitHereIfFailed() 
            .pause(2, 10)

            .exec(addCookie(Cookie("Cookie", "Cookie: brcap=0; MicrosoftApplicationsTelemetryDeviceId=cdc9ea8a-5a9c-45e1-830b-295ed79e94f0; ESTSSSOTILES=1; AADSSOTILES=1; MSFPC=GUID=3cf27633ddc2408d81f8de91dc12c921&HASH=3cf2&LV=202403&V=4&LU=1711447933369; x-ms-gateway-slice=estsfd; stsservicecookie=estsfd; AADSSO=NA|NoExtension; esctx-OwcoSblnh4=AQABCQEAAAApTwJmzXqdR4BN2miheQMYfeUx_kVD5d-6lRtA3Fs057OIF91osTzMuia5jOiYkUzGCxjTNie1NDFoFepMHt-g1OiqnBZ5bsdMUv7yA9g786P-V5IT2tmf-yZP1CC4eJsIP7absVm1wuSzDoccM0MBfdswSnGL3wohWMNBvrs9ZSAA; esctx-yVfWqRmiW3w=AQABCQEAAAApTwJmzXqdR4BN2miheQMY4rHr6WKdwnO3RFXUNyzuGwk3llBsgZ5ozQAOubwTIBwzaY1phQQR79gGbXoZTeTEGkJzAL4Xf9psVaRT-OvY8LZLYpSNX29yAoWUijsdhc-B1sz3mxQFWLyh5MwTaIbXcuoGa1gQUceRyY0cOEhSRyAA; esctx-3dYIzQHmzAw=AQABCQEAAAApTwJmzXqdR4BN2miheQMY7HRfRswMgahMfwI66hZGjR0FVWMe_E6CJqRjbfMD0aqoZ9gGbSGNerJh2FIUJdvPt5cI434lO-i5v-UOhHFOI7DwYJamvoMu9JkA967CHXCww5VAgxXLawnZxmfG7tKh27ubaGLHCwTDoys9digHoSAA; esctx-ZgJP6HffC0=AQABCQEAAAApTwJmzXqdR4BN2miheQMYb2rodAQYmY77Mbu14xuvJtn7L_PTiz9ZBkQ2Cm3zB-wkUEFngdwkxsnxFbxIl1Mz4nYL1jBg07HmJAaRpmi8sfch67AdBrqQZdZoQicev0wjpbZ-juqVaENejq9XTW3QTBER7Bk0GcP3t2MiKEhhhSAA; esctx-ZTJkc1VdLM=AQABCQEAAAApTwJmzXqdR4BN2miheQMYvQBI70Rd_V-Vcz9tZo7ubMoi9xngCXuGhbJAvx3iWQ1EWEOeF0-kxJiH69VJzoTJ9Ku_AGwfnLPGQ7oyZb-F_uCqfI_AFML6N1PDRWkufFrS532lHMI3a8s2sl0ROVQDYkbsuf55SXkrootQCBZc2yAA; esctx-bvDTieYbU8I=AQABCQEAAAApTwJmzXqdR4BN2miheQMY-5QLQW95t7FIO1isP_OQMQHVcQLgJeN3NucFwQfz9yuen3EPR1ojvauoiQZRFVsO6uvlKqsULiDMSf_tA7w18HZ3QRhAchDVQRVXI11YYbdwBNJK-RjbFAklL2uDtiBrr67NsBffwx5XQ8WLMeTI4CAA; esctx-5hvzFR2qyo=AQABCQEAAAApTwJmzXqdR4BN2miheQMYeNrt5MJFtEX4dZoGe_E9pJv8RZp7tB3t9ou3QWUTr1NfFHBDBQgXgKuyO6rmvdeZYgMFgxZVEV6KNDWzAkMeOd9Xy7-bEn423lK0aweU9px8rgGG6JrxitQJO9kQNEGUXnAlFeLu17mBSWAVEQe4vCAA; ai_session=7mEJXarEblaRJbQ5iA7De2|1725283483712|1725285680311; esctx-B0Mw4KqfvE=AQABCQEAAAApTwJmzXqdR4BN2miheQMYJ_bx5a4TxzdutwI1YMMQ1c9wg2TFERhgxF7OY6TbJfijtK5UmQeIxLguX1N1JPGfoXvPFOskN25FjBeHNMwn4XenQqK0K_BTYW61YeV5n68FbraOahA0CYqd3pG4LYjMG1fa0Y2dQXuhIVj8ZwgyDiAA; esctx-pcMKN7W5i4M=AQABCQEAAAApTwJmzXqdR4BN2miheQMYWFsveD_GGg3Sdv4HPDkzL9ZmJx7kK_dPmvhKhlbnYX19S4AZR2FJKfDxFTw8atDSuBQasoFa5e0rwNuvZFvV_ADko4h0SDHAqMIp7mGMS18fQRqJ60DgaF11M_kqZApH0VodZyrZtUSYlDLw9w7n4CAA; CCState=Q25JS0ZXUmhjblJ6TG1wMVpHZGxRR2h0WTNSekxtNWxkQklCQURJcUNoSUtFQUlBQUFBQUFQRVB6Z0FBQUFBQUFBQVNDUW53TkNrVDNKSGNTQm9KQ2NlaHhVYUJqOXhJT0FCSUFGSVNDaEJ0K1I5VDZRb3FSbzB0dnNmQXRDQ0NXaElLRUYyZEdORVA4RVJDZ1dnNUs1d1NqQkFLdGdFS0YyRnphR3hsZVM1amIyOXdaWEpBYUcxamRITXVibVYwRWdFQklna0pnQUpGMWJNejNFZ3FDUW1ISWhsRVJNN2NTRElxQ2hJS0VJTkFTOFN3TzhGSnRIMlhUbFBMM3p3U0NRazFkRldmZ3NyY1NCb0pDVW5IOU5JbnlOeElNaW9LRWdvUS9LVHVCMWxXa2ttUU54QUMxQ3VpQlJJSkNldElZNVJKeTl4SUdna0pCL01IeU83STNFZzRBVWdBVWhJS0VHMzVIMVBwQ2lwR2pTMit4OEMwSUlKYUVnb1FQdHNQV2lSaGxrbXV4L2ptcFROY2loSVNDaENsWTFPUitQSVlUNEpPbDFlWWdsWWlHZ2tKQi9NSHlPN0kzRWd5aFFFQ0FBRWZBUUFBQUNsUEFtYk5lcDFIZ0UzYWFLRjVBeGdEQU96L0JRRDAvMUJHUEJxMHN6WUY3UTZERmRqUlZ5eEJKZUJHZlVUWTU5bHF6QW5nS3VXb3diWGlkQzRyUWFIbXVIQUV6blY2dEdPd3dTN0pDZ2VWa1NtN3lick8wRVk2Z0tqN3ROTWdQYzhUakRaZ3ZnWkpobEcwQXhCekU0RFRlWGNIT1I1SnBDNFN1dE5Z; SignInStateCookie=CAgABFgIAAAApTwJmzXqdR4BN2miheQMYAwDs_wUA9P_KwnoCJ6d_E2pn220e-gOCiBbfyGkKu1laKwh9soBeHb8pydixhfpSSOYlC3dlmYWB2HwyxZNCSa-dGQoH2O7XkcshyuNj0VTMLk1odwfpNu1CQlE6spg9i76Mlyeuk9NJWkkKdf8dFFZlOLUehEZMOZrBs8x_-HApOA3RMKig0_fGAU0y-XAj74VSqX_xuCwUwFUHK2ykWNy1lmHhSHeekbORU4zazQFd9Vl3sLDumNVR6TsRYmkjIcqrEWPosiAEXZIThaGoyaCbU0gPsWisCm4OVtLfZt5m4vrC3-xtPs_p51s; ESTSAUTHLIGHT=+7abcd326-b462-4e9c-a2c5-e2ea95ea0d94; ESTSAUTHPERSISTENT=0.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM.AgABFwQAAAApTwJmzXqdR4BN2miheQMYAwDs_wUA9P9rUg4F3_CM_jsqGU5eGy0gAAopJBuYtg7S7Ilv0PB4-J0IxAWpAunrmEXRU6qVUDqP8o56myaipNrH9cIOnBRhrQ3LxEqILfiszGWzANUUtTvrPYXlmTUzysf68FG8zJ2UZoy6Q_xgMusYZbS0KJ3uejOHjmCqaHK38jc7fQ8fr3K7OHYZ317xfkxN8QLScNWTRSDU7KBNbQcdJpyGyNzm06ib4JldQUJvQ2jPv9ciu2BYZ-PFE3esb-WZMF4XPajwziBggvhmcqf4x4CDv7vWPlndYoFYL1T0od6mVJ3BsYC5Hk6qkoiIsoKvS-Sotconabe1mej9L_qxV9P-8efmrHdg9ASk29sc5caJY9RFUur8w7seza3OSIqldTYUXC8E48lVYfL1jQE9dLjOxKYqMdSfUyIyK-nW96F13Rvzq__uySl-OtTuj2pV_o_Hgir4-kezxpnKehxtbulowqTgKkWbPKNcOpcUWGTeGqn6uPE8ysvasQlk6x035IdAxp5bKHgtqRQNdHNyANavp8B5N3Ks-j9VM86fNlWoGo_Q8sAFeTIIFsNUHATddBkJ1i1O7bhnAlHEDgYeuXgbWVmNf4fxgy8_HhnpwghzkS_BpAPRoA-8ToXqpfEDHQPNRQ-fWoclOZ6GUQR7WkK19FQFBbRu-EIjIh8lrNs222NrgD7QJX3VYChQyP5NKnDY0vbGt5dJHzV230h4pOFTiNVuBU8rNrUj8-UsDivS0JaMRfHqDmC0kXh0Oem3QIFVHonqzijjqAjEHtY6xUZBq2CuY2-CEZB-Ole6as7smQZGSRbRg7D0-ry3dA1gVp9MaKCziiPtWm2Xdwhl0nkDrht75mLhuQ0ppypGPFNAcBdohcMZ48FsD3aiWSF8oGNJy_HE-qwDVGXxqMLtAiTWX-QWruIBtnuHej-0VVSzuak7LLiM4SLFDnnY-X877oMD7bi1fANhMx883ropE4Y4mAvcKuRUH5TQYGRILjWeeAnh0ONv1kH0v3j7avO-tG0J6xKjyEqMPlwLPXgbFvQQMzhJLiYOywtn08CDdig1iBLzETUc36D7V40fvbB4kfQwCfS3Mse_xQjLyXV9RY-vvfFAfRGePatv-igv_kkhJBYN2GRtZgZwd91oStKyCx8ACUJr61LZQPN50rBUe-yKVGBJ4CbZayy7qnR902TZ-LTHx_j9uReUQMDBigDhyKKH9GvXS20F3i2fyN-bE8UkCQgQce-nn_KFsK1X9KvYhWfAoiQIqj_bNGNN_R_ev0RKbHPIeA; ESTSAUTH=0.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM.AgABFwQAAAApTwJmzXqdR4BN2miheQMYAwDs_wUA9P9ZqBDjTEKXRlAVBUsEfiOgUeH14aO8jkqcHf3lBgkIxb4UvUVuM_XCEfvXnMJPOc-taoq9QwNHiXHfqQdBaN1udCLkljCPq8YCj8cV54yk-fwysVX8dLypTElW352MbL3pBi42QGP7NK-RMxb6xFrn8GxK-hIGaZ91WLvCE-Ok3exE8wFTcbpdvb85tsHrG56R4NPcAmN3BykP_DLPRzfSStUHSE415L6JH4W7CoYZc88x1bP6YkLuhrmDJvw-of2RecT6VAJbz59DzfdddnISiPkFwiazDTDo4-XvI0qBDUrvYj63n2Dpv5d-kiuBGM4ztWNTMpwwYP0_iLoqndvJ4k3wBWC90_beyAEY8w5hk7FNerh9D8yGcjA_Oag-sDoUoXNQgNDezjLvhMFok3vn9A-rLNTOZ04YGey5vn5bhtE0WTMghcbU10b-U47DBAwdlFNmbkV_EqUFqrJ7M9IoNgeHKTE5tgjZY-fSVSkteW6fpzAF2zNrUFv1UE_QsGqrULkk9DxAEGFKs4J3bBweUNkPvZ-PYPxUewdWQhEBlUS03AKbvynYBsCmBtq9qXJmbT5pu9MXAnq2O8Gjre9GZDg1Nysn10ibJnvJum0Kty6K57BdwQ; buid=0.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM.AQABGgEAAAApTwJmzXqdR4BN2miheQMYUBrCsSXS39L3d1fW-DzB6q1TdbVTVwqGswAq7EhiBwFOjHRC-RHIStfFJGo4Rqu70WhMfSs97focX5cuSYQyrgfyUKfg6GZvhb8BnEuOD4tzbTgjYWeuAIygnNMqlDxguaIalXKPZiLO0CL1xZDjmEb1MCniS6y03DMMVOBG0gggAA; esctx=PAQABBwEAAAApTwJmzXqdR4BN2miheQMYNiK80FA6uuXUULEgcVSJKjSJDoLK59cATlaLGOs16FGjWXQYVbagfplp6TQ9ntI54bjXLS2U25ox7jVVy5XvVQCBkkQFC0ty8-DoQmNlK0-V9-S3maFP746YPRznaKiYsR_1temDJnGT2sFVERI97qP9nfD5CJYO_1mo33OgblkgAA; esctx-HB9GduVtev8=AQABCQEAAAApTwJmzXqdR4BN2miheQMYH41CayY7AGp60PqTKcM5B0BFGX49MwJ-6IcGO1MI0syDbzInlSVC0Jfrwn9owxDCjK0B4WMh6FpNKoQ9liydnuFS9Ivlma9qT4iRUa_I-pf6ip-4Y5vAelGs93f3ViMZjneY0B80hH_Vc0KM-6QSCyAA; fpc=AmYGlHXwPXpIrnlKfA4l2He8Ae7AAQAAAC-8Z94OAAAAHWQgHQMAAAB1vWfeDgAAAJljI3gCAAAAfL1n3g4AAAA; wlidperf=FR=L&ST=1725286199049")))

            .exec(
                http("Darts-Portal - Login")
                .post("/e575f663-b30a-4786-89ad-319842dfe853/login")
                .headers(Headers.getHeaders(2))
                .formParam("i13", "0")
                .formParam("login", "#{Email}")
                .formParam("loginfmt", "#{Email}")
                .formParam("type", "11")
                .formParam("LoginOptions", "3")
                .formParam("lrt", "")
                .formParam("lrtPartition", "")
                .formParam("hisRegion", "")
                .formParam("hisScaleUnit", "")
                .formParam("passwd", "#{Password}")
                .formParam("ps", "2")
                .formParam("psRNGCDefaultType", "")
                .formParam("psRNGCEntropy", "")
                .formParam("psRNGCSLK", "")
                .formParam("canary", "#{canary}")
                .formParam("ctx", "#{sCtx}")
                .formParam("hpgrequestid", "#{sessionId}")
                .formParam("flowToken", "#{flowToken}")
                .formParam("PPSX", "")
                .formParam("NewUser", "1")
                .formParam("FoundMSAs", "")
                .formParam("fspost", "0")
                .formParam("i21", "0")
                .formParam("CookieDisclosure", "0")
                .formParam("IsFidoSupported", "1")
                .formParam("isSignupPost", "0")
                .formParam("DfpArtifact", "")
                .formParam("i19", "42591")
                .check(regex("name=\"code\" value=\"([^\"]*)\"").saveAs("codeToken"))
                .check(regex("name=\"session_state\" value=\"([^\"]*)\"").saveAs("sessionState"))
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Some Request", "code=\"([^\"]*)\"", "#{responseBody}"))


            .exitHereIfFailed() 
            .exec(   
                http("Darts-Portal - Auth - Internal - Callback")
                    .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/internal/callback")
                    .headers(Headers.getHeaders(4))
                    .formParam("code", "#{codeToken}")
                    .formParam("session_state", "#{sessionState}")
                    .check(status().is(200))
                    .check(status().saveAs("status"))
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Internal - Callback"))

            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - App - Config")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/app/config")
                    .headers(Headers.getHeaders(15))
                    .check(status().is(200))
                    .check(status().saveAs("status"))
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - App - Config"))

            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - Auth - Is-authenticated")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
                    .headers(Headers.getHeaders(14))
                )

            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - Auth - Is-authenticated")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
                    .headers(Headers.getHeaders(14))
                )

            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - User - Profile")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/profile")
                    .headers(Headers.getHeaders(15))
                    .check(Feeders.saveUserId())
            )
            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
                    .headers(Headers.getHeaders(15))
                    )
            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - Api - Courthouses")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/courthouses")
                    .headers(Headers.getHeaders(15))
                    .check(status().is(200))
                    .check(status().saveAs("status"))
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Courthouses"))

            .exitHereIfFailed()
        );
    }
}
