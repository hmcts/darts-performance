package simulations.Scripts.Scenario.DartsPortal;

import io.gatling.javaapi.core.ChainBuilder;
import scala.util.Random;
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

                .exec(addCookie(Cookie("Cookie", "Cookie: brcap=0; MicrosoftApplicationsTelemetryDeviceId=cdc9ea8a-5a9c-45e1-830b-295ed79e94f0; ESTSSSOTILES=1; AADSSOTILES=1; MSFPC=GUID=3cf27633ddc2408d81f8de91dc12c921&HASH=3cf2&LV=202403&V=4&LU=1711447933369; x-ms-gateway-slice=estsfd; stsservicecookie=estsfd; AADSSO=NA|NoExtension; esctx-oKesD4LAPIg=AQABCQEAAAApTwJmzXqdR4BN2miheQMYIlixvaR8k98-V7heJ88OcpUX-2zw9QiTGa7qHWwusC74MgMEJbb8eSfShFYIU4XK20MJGYT-OwFveCsaLtk6fBEhEo9kM8uMvhzHmf9VX0Zh2FulCqTqNNRDZnunnIyDm0mVcmNE9qn1lP2aPZFMeiAA; esctx-jB85Qb7b3A0=AQABCQEAAAApTwJmzXqdR4BN2miheQMYzIt4EMM4JthaaOf2u2Uog7tjn7n67YCccc2ojXfeerUokkkDp84bHbiuiXnz4lNSQMJlQMipqpo6rJ9OtCM_YDd1eI6Gk68cySeYvkb7LV5eScPMZRM4zootDIORyXsrO7amtVM_b5unF7K9g3dHRSAA; esctx-XAa3ptTMaKE=AQABCQEAAAApTwJmzXqdR4BN2miheQMYwy9_eqAxfEx9xbmWJOOK5hGWvU6iwcYc48CYwpviGXgUvl0PpenkIswYHcQR4a8PV0mX-3MjCvcUKCHKqmE8HyH1UuAqLcMyeO84xVAKyKiwMyIzVnQ7VLKS9CK4M_c-P1irxYCTGav6JmahrBhvCiAA; wlidperf=FR=L&ST=1725279930187; CCState=Q25JS0ZXUmhjblJ6TG1wMVpHZGxRR2h0WTNSekxtNWxkQklCQURJcUNoSUtFQUlBQUFBQUFQRVB6Z0FBQUFBQUFBQVNDUW53TkNrVDNKSGNTQm9KQ2NlaHhVYUJqOXhJT0FCSUFGSVNDaEJ0K1I5VDZRb3FSbzB0dnNmQXRDQ0NXaElLRUYyZEdORVA4RVJDZ1dnNUs1d1NqQkFLdGdFS0YyRnphR3hsZVM1amIyOXdaWEpBYUcxamRITXVibVYwRWdFQklna0pnQUpGMWJNejNFZ3FDUW1ISWhsRVJNN2NTRElxQ2hJS0VJTkFTOFN3TzhGSnRIMlhUbFBMM3p3U0NRazFkRldmZ3NyY1NCb0pDVW5IOU5JbnlOeElNaW9LRWdvUS9LVHVCMWxXa2ttUU54QUMxQ3VpQlJJSkNldElZNVJKeTl4SUdna0pCL01IeU83STNFZzRBVWdBVWhJS0VHMzVIMVBwQ2lwR2pTMit4OEMwSUlKYUVnb1FQdHNQV2lSaGxrbXV4L2ptcFROY2loSVNDaENsWTFPUitQSVlUNEpPbDFlWWdsWWlHZ2tKQi9NSHlPN0kzRWd5aFFFQ0FBRWZBUUFBQUNsUEFtYk5lcDFIZ0UzYWFLRjVBeGdEQU96L0JRRDAvMVpwWTVwWTFBYnlvMlVKZzRkNmxDcU5IYWRYc1U0WTNtR0t1TmdKV2NNcEpMKzdWNlJMYUxNZWZxVUo2OExhME80YkZFQWgwOGJSb0tIVzE1VldZWkpycCtRNFN3dUpaaFdBczc2eXFLMzg3QXBJdXljOW0yalBXcTlrbWg0NzRIWU9yUEtP; SignInStateCookie=CAgABFgIAAAApTwJmzXqdR4BN2miheQMYAwDs_wUA9P9m7_-EtjmNg664HvUAS1KKgGLAAcd7iPe5UvF2vGVxPKeHFX8TI8hZG19jIHkU744Ny3WInrsmpVue1TrIO0kIfz96jJ0_sEt2Y7O3TkLM0BRAazDyKD54Udkt4kDwqC-NkkvWzvhichfUhIzSwe2UpddKZjEPuKcvL-vTXoOizE3GYVa3rpN6CPjDlEvzuvss5G__bRfGus0VZ3tek9s68CJr9Pr8GKLEY4WNqoAxIGdmh517CtHQwaWEnMxj-jBHcnXB8i7sGt0ORkyH6mrLVM9wWCVy2E5oofgFgGlY3VXPfzs; ESTSAUTHLIGHT=+7abcd326-b462-4e9c-a2c5-e2ea95ea0d94; ESTSAUTHPERSISTENT=0.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM.AgABFwQAAAApTwJmzXqdR4BN2miheQMYAwDs_wUA9P8_WTHT2Yl8VrBeGRFbNKZ7nnPHagJ8ThjX_ZWh7VfFVu5W4HnUmo0Yt1HVZWAexXcd20WquUzNX4WF6akZoRRHCXoOYt6M0t6mmdmVyKnwbzD00nVfr9pKw0SMG_8Na7V44JdisIIk-4uChAKdDgyCW-463GmsqKLMAUIgdKAYwboPSuM4bpRa6_kJpeRSQP2JCB7Kf8abLlKK3QjlGIGMzb9IM9p886wzpdv9of4eo7sTTS-CBF76v2-_B0MRCX20O47-7Ffr8aoI43pzCy3WAowdDWVWgeYO7GKqkukYNDApNLsQNGVhp4K6fU02eUUaI0eGsV-Fut8vtxL4JfkorydalKyEFSSEGW-1cVkMfoqQly3KtpvEY6Htznse6sQQo48pXWBSyECTJqbpdbulLKUr8OPpwurGA8pbaBdNl-a5wkYfxqA7tg-jtG3kEyJQ8738QT_VqhNFMJfuKOLpqB5RHrtPESrAJDH6OMEDOqE6_3_81wMhR4Hkxy8656gi_V3X1ssziLFrEclkTLnrZX8EY1w5IZw3lIUIfZTvompzR3hifXay6UcZIrpYSxY6KhwdN9Y5JCWAgfHStUw4o04XuUUkkkNWNhPj6QRst5NGV7BuFvsuN9CM9dGcvo_FL50HYq84HLfVnntU2Qv3a5-NcsEw7evFPWK8lmwfx543sfEu8_MJrDjXU9uHumM6VcLGemlKxyjoxC3bWrWTvHRmY5HyIV4ITM46AyL4bzOjSD0OLXChQFehcIaZSQkueESFG__zOAF3PQlANV3FQ0fGieRTI4Y8_4bySRCbqE1bVgX47HjTuYLqU7yW42AwEiSM3a8am3c_4LxNLoUiVNEtKnKJLnoKowE7gct0UGa_sPJGn15TrvVp7XZCm8Wk8q-cGVg_JDnR4miZNagiW5KnqXHmnJhvthyxqSFJ15XQRTV9WarUN01BLaw5s2dJGC5nxCMlj2MLAqGxQJ6B-6yGsP-b6hElaxqCSNCVPoxzAMc-7qwc7-17RFil737heDEcgJf77CXBhb_B3LY-sH7cIqG10u-owN8dpXrciTuoRmGl6-TcYs7VbLYH6kXUKnUAMpW0Npbiydj4JaFJ4rzurZkZSfcdf7zeqCg_CnJvAt2MusJzWjaYnv64aVVw8LjSTGp2dqkZL2bhAy2Pvdfvgis0hB6zByE3qRZTT7oxX0jIJhEvGV4_zrYrSjBAPpNe8VWK1y6gOEU-J1LOJpxdiXnYGgd9hf1WCboU6wP7INQY1v4OFDqCWkBWlgIOSQ; ESTSAUTH=0.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM.AgABFwQAAAApTwJmzXqdR4BN2miheQMYAwDs_wUA9P8W8tZn1c8tCYs_bVUWsqV3Gc1rcl0uph4pRFRe9BSRpHmQuo0MlW2EhUdbeOiwyv5gOxujcfEUWpfgGYqQWTxq5itg7rL_0hs0pz84VCEqBy6sJKpkOR6KLsFblONjycVc7nzYFdYqPLbCP475Sg3466Yq1Lp7SFj9BViZo4rQstZcDvbF3q9mLV8F8KrdYo9iXbs6fx9ztGY1KZS3byOmeJ1Gbyy-iSmcDDUPVFoaPOtCAE3Xas3yPJKOrGYDU1CMz5l0iSACO7vjwMjXdh9B3sFhe5MHSNb225CEB65HfqaLqrGQPazKTJmqDWyFereq0q3rYxg9t8-q2PPTdbdvilvMBpmJMYR_7rzqwSX1lLSubJhO3rkIxrZCgiGioqwxvj1-RpVk667teP9uS7si2Ao0haZZfQX4TS7QJqX7lYkZAhaHabVRmFsRdEWXwst9FG29iBcblXTEvyBEhf6cOLbIor9YM-Lx0fDP8bMYeVok1hxjNyWWGC4nFvaGHDFVXo0T65WrIVYTPn97_FTwjhxLXzpz9gX9fbYXVqtNTl7MEuHq2RkL4r644hJRGXnckdBTM0i5rncipjeALEmw4s0JHfYhbWnQnQ4dfydPyzkPnQ; buid=0.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM.AQABGgEAAAApTwJmzXqdR4BN2miheQMYQjApSHIqv0lFa5LNOLRtgIdA5QQtoAK15SWf-sHk20Vejrh3qzsZSsDHIRQl-lwS9g-KTONjgCRnlGdj9pOcy_PfmOX8nveOwGEKRbxvILBhAfy3P7-ierunKpF8cK3fBdIZwqhWG4OwQsTkj0cGlDy7lHUQ_VxLsDRrJPoWKxUgAA; esctx=PAQABBwEAAAApTwJmzXqdR4BN2miheQMYh6403MguUR-P5UWydJc4fqbUIdeSallJ5ugDB_6yt3UxaqPYsqIHk2UVjb7ucb9jWTdrQvRDHA3y5uDvd5_1MjrHoi4q3nFTq8R-u5bK0WSry_3Ozq2-YYIDZu2mUXLhYP9f3oe3wgsJWsxvUeSkvSV1_NZcsVUTurILpYByR48gAA; esctx-OwcoSblnh4=AQABCQEAAAApTwJmzXqdR4BN2miheQMYfeUx_kVD5d-6lRtA3Fs057OIF91osTzMuia5jOiYkUzGCxjTNie1NDFoFepMHt-g1OiqnBZ5bsdMUv7yA9g786P-V5IT2tmf-yZP1CC4eJsIP7absVm1wuSzDoccM0MBfdswSnGL3wohWMNBvrs9ZSAA; fpc=AmYGlHXwPXpIrnlKfA4l2HcdZCAdAQAAAJmzZ94OAAAA; ai_session=7mEJXarEblaRJbQ5iA7De2|1725283483712|1725283483712")))
               
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
                    .headers(Headers.DartsPortalHeaders4)
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
                    .headers(Headers.DartsPortalHeaders4)
                    .check(Feeders.saveUserId())
            )
            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
                    .headers(Headers.DartsPortalHeaders4)
            )
            .exitHereIfFailed() 
            .exec(
                http("Darts-Portal - Api - Courthouses")
                    .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/courthouses")
                    .headers(Headers.DartsPortalHeaders4)
                    .check(status().is(200))
                    .check(status().saveAs("status"))
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Courthouses"))

            .exitHereIfFailed()
        );
    }
}
