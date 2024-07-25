package simulations.Scripts.Scenario.DartsPortal;

import io.gatling.javaapi.core.ChainBuilder;
import scala.util.Random;
import simulations.Scripts.Headers.Headers;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.UserInfoLogger;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalInternalLoginScenario {

    private DartsPortalInternalLoginScenario() {}

    private static final Random randomNumber = new Random();


    public static ChainBuilder DartsPortalInternalLoginRequest() {

        return group("Darts Portal Internal Login")
            .on(   
                exec(
                    http("Darts-Portal - Login")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/login")
                        .headers(Headers.portalLoginHeaders(Headers.PortalCommonHeaders))
                )
                .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Login"))
                
                .exitHereIfFailed()
                .exec(
                    http("Darts-Portal - App - Config")
                        .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/App/Config")
                        .headers(Headers.portalLoginHeaders(Headers.PortalCommonHeaders))
                )
                .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - App - Config"))
                .exitHereIfFailed() 

                .exec(addCookie(Cookie("Cookie", "brcap=0; MicrosoftApplicationsTelemetryDeviceId=cdc9ea8a-5a9c-45e1-830b-295ed79e94f0; ESTSSSOTILES=1; AADSSOTILES=1; MSFPC=GUID=3cf27633ddc2408d81f8de91dc12c921&HASH=3cf2&LV=202403&V=4&LU=1711447933369; x-ms-gateway-slice=estsfd; stsservicecookie=estsfd; AADSSO=NA|NoExtension; flight-optionalscopes=true; esctx-I89rmV2J74s=AQABCQEAAAApTwJmzXqdR4BN2miheQMYeP9396mVKHbRtPUsXKGASMQkKuoA4WbnPuSrg794iHtfSsPu1lJGb7GkyXVnzIIiG0817iKeRt3-KaKK3zh-7LAewYMLy5e_ZbdLZDjk8QIbNz__6pDwQlh8LWGmMRjulmwG3blD9PRno7IimTkk9yAA; esctx-D2YjnHPyCwc=AQABCQEAAAApTwJmzXqdR4BN2miheQMYxQfSlBHf9mWxO1kplvlUQ12AXs1WypsOIQYyZpaogmFw3nZaiJT5b-TefynCf0dJiUr1NXeW_bxOolcym61T9t35YjX2GrrPVJ0OGDQGxiQUJonf0IcZSLWjVWxfq6arD7bKvXnmRaXNDJKsG81apyAA; esctx-CaDDHqylVQ=AQABCQEAAAApTwJmzXqdR4BN2miheQMYBRDb-6uvn8uzRLMGoAxUPG5UXmg70AdotoWZyy_eSPg9dTiXjXYAxLc4dOIaHEsDKSiHC1An8XMQYhA0aVGwKM2beGluR0dcdrFKAJH2gbaIvsXXnpB7Jr4UXwmsCnCnISoZpB3xmfDqpbLPrEC8giAA; esctx-n9JFaJDam4Q=AQABCQEAAAApTwJmzXqdR4BN2miheQMYTkoUR_gfu1QXWIaN6MCwkhwhc2WMKo0afLvxsISLJeoo9k80g-5m84sEcJyCOSk3vPfRtTZEL28f79jeH-DAuNncsCbnPZi-RKnmoPLtrs2XRbX0eTdYuZb_d02sUfKiHNPWLHE7vYLRFLxpMLvblSAA; esctx-3rO8kwGoTp0=AQABCQEAAAApTwJmzXqdR4BN2miheQMY8qO3GWqP4hCkZqZsug6RfUD6xhgR-FVvmaqwNiCZcINycBj5kRsZSRN4OyjovvtgC8pANxf5uvJu0eBNJQfAvAL_13_hY0KYybifptdSaftAL0Kw7mOaExWJ2cr61EARj7eecQnFADt3OZxDPnvUQiAA; esctx-RonC1u44PVo=AQABCQEAAAApTwJmzXqdR4BN2miheQMYtOadC-Q6Cmpza7QGA94JTNA9qTFEvPRhpWIBB0fC403kK-4VR_NKqdUO1Eu_e0BBtQrehKgGz3pLrh_gl7udjS6Ym-5qhl-PPpB2_9UQOS4RmIGO_3PWfiCX1j2cC1m_RIWVsZ1erwMHRZAzBPXVeCAA; esctx-hxMNRMqp1A0=AQABCQEAAAApTwJmzXqdR4BN2miheQMYyY_nzFgrhVKsy0mhtAqsQPZieZ5D69fCmEUjTJVQi70Zbp-ESeE2sBAO9E9pa93uw6Sb5G84iYnAX_xR0q1Pn4pRd8RfqRB61x_btvf03VD6Xa_d6DhfxkF-DYi1B7hAAgLntTZc0hyNpfA3OuvOPSAA; esctx-TnesEHjW8iY=AQABCQEAAAApTwJmzXqdR4BN2miheQMYuQESmfDRyql2fu4Sd4fbI_HH-33Bpy6IeHaiEheYphiTj_V-4425-rCjsJInXDcpg-yyEPoD44UCHlHMRowwrYvEAQmLIQ3bZZqMf8Uq2kCiNctzloUiQGgyV_bGCAYpERnAFcaJy8OdbALa2GkjnyAA; esctx-T9ZC9W0Gp74=AQABCQEAAAApTwJmzXqdR4BN2miheQMYPalc9A7eAJ0ju-kTsv8DtcP4Uo47KDl_v1FPZPRoMDnl6tiywYL9j82simfS6Xj_putKOrsIvVfizl9zXzS1XAlxAdbLysLZ9M2M89P0fyPVrqqxDwtcZNA7x45o6hcJ6caSn7OK1CIQVEDgJvAfTSAA; wlidperf=FR=L&ST=1720627053825; esctx=PAQABBwEAAAApTwJmzXqdR4BN2miheQMY4l-x0JY2urUSAWSLEj8vW1yR4ZTACLQvVajpVIfeyLArZ3CzeWRzODWWF0PFlN4_CF9mo0fARpJBlwkqUhMU86xbrxWW4nrc6kLMrazTL49gxDTs5FH3RtcO9v2nGU8QDcduOE8ismjSEBXjlZoUZp88dOCW3cskdF12w2Far9ogAA; esctx-UY59Vmt2lE4=AQABCQEAAAApTwJmzXqdR4BN2miheQMYMc3NUSw1ID-Luu48cPDYaOguTIWOpgDLTbd5vGJ-IfxyJOBAHQJXkwYBhLOr_J05tvOjfU73nfoH5yvnben9rEezfE1FWs6p0hvlC1ra6gronWgaY0ryzKu8p4bFE1nHLyT4Po6f7cVTx7d4zByY2CAA; ESTSAUTHPERSISTENT=0.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM.AgABFwQAAAApTwJmzXqdR4BN2miheQMYAgDs_wUA9P85ZFnHzMrdjLvD4tsTmd2GxoadwyI8K-N4Myspqpu514thqfRltVOuT1O8tynHGC-Kob3AkUEcnKstrqX2evYEY-Iuv6XdURGSYRHUBOYzBanqnj0q2uyoPYJbO8cRMduHaUDvioaI4ZHwAMGnoSizhpKLfoRu2fAnecgzA1H3rPbm3uHOYf-o5Gm1X4GRYVqKn7vP3Oc9-G4qRT4ADnCkJ5CUwtmZ6og0h9U4NZHeBjUKHzohiTYxXmsuJxgO39uVBL5jPpEkQqkQ3Zup_cjRYI69iAZPONRFFvQc2xvvdpAwcXEuTEth7YDZxQqYlPCG-YlPkbyEvoMxNUMdsSHFjEQ36uWMGi0FA-Oq6lpT9GTsB6j5m-K8aNmDOy1qJBPiwM7PJWdzq4cpR-D1gSl8UELMnzHBxlI2YZrQLavYw_trWBZ5W19DK0DifT7CVL4wafrHJ-6CeGzP8SIJwm6Rr-hifcm6bNRmYtIfnD6-2-CpjQy3j4xULj4xDZ4P190eD_vkhsbFH55KsMWQXvsCxxiet25ogeOAlNv3cc9Vh1GTMBog4SS57jgkTJ5r9TcMCrdtXJmdWJ3HWBUducyQMk-9spYBndqTWkX9BwYQhI6PgqchmvH6wBQ9zmlcmRM6K5wdvKdud3tshKGcHqgrs7WFmR5gI7SoN4fhzjcEByu0Dmgcn2dMt4SWEoCoym1_7PbyFG-n_lBM3VLHLivhGv3vm7lck0Q8lJoso_w8Smgrl751sFP9jqbhgHV3OAQQK-1EhbFmvXTaWsnBJiID8w9YvEzyb7Wh3q6SVNiTZzaoKzdqiS--PqacFlIZlIzI8r8AZsNdjhDmPPCawDzZkpcAaAFT8hWyH82t-VvZdE2sIiyBvxRkF6jZH4Dpz6B6g_P52xWmtuUlB6E0WuuRP6JRZDe4bwuB0kQUGrlf2BnwK46lWH1jS8rtxmrFUnX45SFRVkvw6WuyDoldpvn4z6YaRqV5_EWsrGutCfOmMt65DE-WvtOjZQ7PQpYz4pWyxrm6oAOf8Xy7qOajy7B-gigDDw3N2mQVKpxBvwpPcpS3O4zyEAYRRExc77lM2UPNVUJHlivrfR1riD5cePLAwldVD4sC94h0R3hLv-pREJTf6BOQyS70Cl5XfuqTaNPtvF2VXqPQreSCpavNgN_NGUQs1fbh7G_IHlk0X0evNVDYdTFwlBKaCvwdZT2R7-uAl1PL3XjSpJJpVXrfWLqLyLYQtQFgKsAkgrITrkLKQndMKL9NdTREtFdmVHHq4wWdGf3MGatJ-r57KtqCRWivgbfWZW4p0uiE31TOeqy7MQDK6N-WgHj_Jxkrg81ifv5HSVAIrd0PyQ7VynubpbIYOFoDKnHKw-Vbkl7x0djQjLgbiJVwp2g91_Kc-qnehMpQE_hPI75UhmKf209I4diOEIvM9vaMpqKt3_fM0sjVIslQeD4fRqSlXjBLoVTAGtPSIzPVr6gGp1tkPUqaqjEWdZGHIUzVHyCcRijdmXCd81fHQBPLHAA0utJz-5p-L0afXWIqoSJ0T-5zGCy_-tLgCGgK-AUcpSzpf-dMzIIqiVs6Q4A9XBBG3ENAOrdDRlakiq2RmE_ht7eQDw-fabUzlcLI0AmFMxPt4kMSnGi5X5bI6thzO-z_rMqvGb_3Mm7edC5PBs9m18ZpIKzlHpoPH4qqZVtSbVeig5sR45jgmViKjqVlFVO2uB7dMqWt6CAPy51ObiYAMD2VINiCpGtrBdjte1GXxnDEvbT1BTCp-vakdN0le_VoI10XIIftHJnU6wCUorJcPmY1ST3AQDgbky5RGj3B66WvbUasFqZ5AZXloXEt; ESTSAUTH=0.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM.AgABFwQAAAApTwJmzXqdR4BN2miheQMYAgDs_wUA9P-VDfvHMKdl97fgPMsCxJCm8aItn6GBaDvquUerZJslXsNuPIjRhr_Mmwi--ZCOKrpT7i0AuaIPlg; ESTSAUTHLIGHT=+33f3cd5b-1098-4d65-8747-67da11611dfa+9a5811fa-f3e6-41d0-bd46-731341da203c+02a63aed-108d-4a00-b121-9d596a6b3c2c; buid=0.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM.AQABGgEAAAApTwJmzXqdR4BN2miheQMYdaD_ZZyWZoQWnEjZ4OQwOL-EfcMSvxQDY2bZ02g2JT_nQs8oZ_z2sjGcy5BAZ3OFRG55lv3eokcy8OyK66grTNI-ca9VbrpfgeOi9oqc9WSgls4HnNmKBVtHr7BkuJP1mLTRDwUnjiq0I1toDV-xamSSL2C3mlcBYi_Su_jTGMEgAA; CCState=Q25JS0ZXUmhjblJ6TG1wMVpHZGxRR2h0WTNSekxtNWxkQklCQURJcUNoSUtFQUlBQUFBQUFQRVB6Z0FBQUFBQUFBQVNDUW53TkNrVDNKSGNTQm9KQ2NlaHhVYUJqOXhJT0FCSUFGSVNDaEJ0K1I5VDZRb3FSbzB0dnNmQXRDQ0NXaElLRUYyZEdORVA4RVJDZ1dnNUs1d1NqQkFLNGdFS0YyRnphR3hsZVM1amIyOXdaWEpBYUcxamRITXVibVYwRWdFQklna0pnQUpGMWJNejNFZ3FDUW1EM1RVZVVxUGNTRElxQ2hJS0VBSUFBQUFBQVBFUHpnQUFBQUFBQUFBU0NRbmszd05Xb2FIY1NCb0pDWmYrbjRsR245eElNaW9LRWdvUS9LVHVCMWxXa2ttUU54QUMxQ3VpQlJJSkNhZFp4WEcxb2R4SUdna0piQlZycFZxZjNFZ3lLZ29TQ2hDRFFFdkVzRHZCU2JSOWwwNVR5OTg4RWdrSnN0WXlZSENpM0VnYUNRbkZUTlNURmFEY1NEZ0JTQUJTRWdvUWJma2ZVK2tLS2thTkxiN0h3TFFnZ2xvU0NoQSsydzlhSkdHV1NhN0grT2FsTTF5S0VoSUtFS1ZqVTVINDhoaFBnazZYVjVpQ1ZpSWFDUW5GVE5TVEZhRGNTREtGQVFJQUFSOEJBQUFBS1U4Q1pzMTZuVWVBVGRwb29Ya0RHQUlBN1A4RkFQVC9wN1dGdlVSNTdyUmNiTFNHZW4rL203VjZhOWNyei9rK2kyL3ljMENDVXkvbXg2UGpXL0VINEQ3ZU9qUHVGT2ZWV1JOMDloTXg0WEx4bEV1dFRKZkVQNXZLWnRac1dncXhnak04QXA0aEl6cHJZNnZYakRrT1haOHNyVUJWTWhRVU0zbjl0Mlk9; SignInStateCookie=CAgABFgIAAAApTwJmzXqdR4BN2miheQMYAgDs_wUA9P_K1-0NR0dH6K-2YlWGQ_yaDQR_qJKy_ic7LkVUeK7OpLsofz9Zd-CyV65QxSppDLfQkYVpq13JldMuUEcjNcoxJcc8Wlo80vywwV_JHG5hO6cj9okWBjVk1POg5sOmGfjU0T5Ck7gmfwt5jC11cFHAZY8jkIq2xeFWRv5GOSrvv3si8DtYlUozHLUmHRfRkMmhAZu4uAn2Ad2FjWH_ktxkoQFPeEgazd5GkLvDloaVDogEn7IZlwkYDxHe2Z3qVu7ftd2iDewG6p-78awj4j5l2LcYTGUFOAPMFg3LllBw6k1OeO2Q9--hZPUcwsMHWOS5w6e828ja6z50nZ_VwNWRD5BAqV-K2G5Txgq4g5kOdXyQGWr5PtdAmSmvFK834JZgowjHQd4FnjJWBpFkmH35NKlxbZmKtP1LcvLBiyl4hckjDKlfHpGEGENtdXDCpisxV4XNd0_Yc2IZYUPJgB9P3boEBJO5nj-8hjU6T20pE4CSGO_iXwOfCESfTq1EFHb9LEVSr8Bd511BgtRTE_cGuaZ2iRVX9wWrv4QYzog2GMG1BDSDuAqZ5IEePC1RsDNJBArcJTR-ah2zVh39568dOBAi87sI-cN49zFgQcgRpMSyUzwpvJ_pXOnWvo_Mhhucqvd5sZf5JxG3lsz3f8XnGXByiyUJaCXU2-_2ubW-5J6G6DTqFC3rfRtC4L9Oxb_yMi2jfVaMtc1VQBsQ6wPu_eQ4TknVwlP7HfD2MXpuAxskIipP_r4kmpM4IVsYE2LKiQHqky1XT8UVolZpuDgH5DR_crZV3kKF4JZ2zgZ2075HFVXFw6kEr7SqD7n9ey5H2t0FhrQAxC_61nD6DTS0qmmH2ozvjXGqV28W9IdnUaBvcOP-qaH91ZYXxLMvHois9FjwEtiFxTR3WkCVlTWoO2Av2k2QUTgVk1EhHqxPRFzMVrX_HUtUby3EPNMFnZAMff0UXqzjNJwoK3DvCGKbDq3aVxTUwAyNNN22P4yT-DyYX3jUusj0t_N5jVvX3StBbSijIaJU8qfSAszfJYKs26C90OY5jRgTm4MmT6e6JR0TweNvzZZUvjDDeCTTLdqRM_4G1NXh-1r5rSU8G8qRA1yeYGZrNcj-7IhG0nz-qsMIG1Sl52ZlZcLwosteHzWwDwZNxoIpMRLf-MtT9Vwt4oF9Ii0SyLC8opwvSeJW_NyEo-5fNkmcCK-mjRBZb-VJ53lGYH-HLsooBlug2HrWuNgNqhsRAylntFe6SOqPFVB5ZclX8IzHw8AOHnqmtxAs9Ni_K8cDdNeDf3eS4xKV7-UkyNI; fpc=AmYGlHXwPXpIrnlKfA4l2HcdOoMDAQAAAHGmIN4OAAAAPw61OQEAAABopSDeDgAAAONjZZwCAAAAraUg3g4AAAA")))
               
                .exec(http("Darts-Portal - Auth - Internal - Login")
                .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/internal/login")
                .headers(Headers.getHeaders(0))
                .check(regex("(?s).*\"sessionId\":\"([^\"]+)\".*").findRandom().saveAs("sessionId"))
                .check(regex("(?s).*client-request-id=([a-fA-F0-9-]+).*").findRandom().optional().saveAs("clientRequestId"))
                .check(regex("(?s).*\"sCtx\":\"([^\"]+)\".*").findRandom().optional().saveAs("sCtx"))
                .check(regex("(?s).*\"FlowToken\":\"([^\"]+)\".*").findRandom().optional().saveAs("flowToken"))
                .check(regex("(?s).*\"canary\":\"([^\"]+)\".*").findRandom().optional().saveAs("canary"))
                .check(status().is(200))
             )
             .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Internal - Login"))

            .exitHereIfFailed() 
            .exec(session -> {
                String sessionId = session.getString("sessionId");
                String clientRequestId = session.getString("clientRequestId");
                String sCtx = session.getString("sCtx");

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
                        System.out.println("sCtx not found in response.");
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
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Login Microsoftonline - Common - GetCredentialType"))

            .exitHereIfFailed() 
            .pause(7)
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
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Internal - Callback"))

            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - App - Config")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/app/config")
                    .headers(Headers.DartsPortalHeaders4)
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - App - Config"))

            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - Auth - Is-authenticated")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
                    .headers(Headers.DartsPortalHeaders4)
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Is-authenticated"))

            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - Auth - Is-authenticated")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
                    .headers(Headers.DartsPortalHeaders4)
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Is-authenticated"))

            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - User - Profile")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/profile")
                    .headers(Headers.DartsPortalHeaders4)
                    .check(Feeders.saveUserId())
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - User - Profile"))

            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
                    .headers(Headers.DartsPortalHeaders4)
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-requests - Not-accessed-count"))

            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - Api - Courthouses")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/courthouses")
                    .headers(Headers.DartsPortalHeaders5)
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Courthouses"))

            .exitHereIfFailed()
        );
    }
}
