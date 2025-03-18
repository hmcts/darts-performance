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
                .exec(addCookie(Cookie("brcap", "0; MicrosoftApplicationsTelemetryDeviceId=cdc9ea8a-5a9c-45e1-830b-295ed79e94f0; ESTSSSOTILES=1; AADSSOTILES=1; MSFPC=GUID=3cf27633ddc2408d81f8de91dc12c921&HASH=3cf2&LV=202403&V=4&LU=1711447933369; x-ms-gateway-slice=estsfd; stsservicecookie=estsfd; AADSSO=NA|NoExtension; esctx-oRnXUGi6fLA=AQABCQEAAABVrSpeuWamRam2jAF1XRQEBlHxM-V7QmA40AymJxpGE8Af_OMrwnBcgpHW9mAH7riurc1ckhKhTim8XowaCHL6DnKr2wmJYQrzaq6NNFXn5cTpA0l3PPN-O3JuM1E2IBBfWkIOOICuUwizsL3enpOQQfg9wuKpHEt_D7DmVX0StCAA; esctx-hasNv3N71JE=AQABCQEAAABVrSpeuWamRam2jAF1XRQEWbzPSHJc0XtRTD5fZFKb8-zI8lpLV2KlDSbgpuSAdvyoN_haw-Ic1Zjk9EpnM90xYQ6re_fBpdk0kPnePq4r1xmFFZ8wVbKEHf8Qxh7NDY1f6Y6YMZkwTBAKBXo0x2NS2Zsh-K-z1zpLAWgvgzyVgSAA; esctx-FIGhrvsiQdU=AQABCQEAAABVrSpeuWamRam2jAF1XRQEia8fvUSx1a6d2IhZ-0XlTRxdr1nmpZgZSw-n-7GqTnAjW58WgL9UCgP_wbIu4UxUyJb76x1mJNKeklpt5dcKBbDTvqvFa8xA8j3LksZ4K8MVwN5B1LoXic5Y4_8qMgVrJ2Fxs_5uWSftx9UARttmGyAA; esctx-omnYSPHNiMs=AQABCQEAAABVrSpeuWamRam2jAF1XRQEcoJPKw3U1zpYsYHNMXmEFx-d3GGPneOYTxtzMKYtqmniM_YhNfuYGK-m0OftS8kbjrbXk_N-An4oUXUGaIvl8XYyBUIdmtu_z_mIjEI5Si2TEsnO0RhdqML4mcyADiJki7jilzrdpOEsE3GoYZxjCCAA; esctx-ssSmZ6cB9zs=AQABCQEAAABVrSpeuWamRam2jAF1XRQE7GVoR_Yo6gNpwfsWrKYVtKPlE0wcc1JsFtTaZuAen0ztALKtKRqwzkMXFh4VsNqPKtRKBM95HHLApL4t4ZSR74tELxg_HybeKNf314yXIFLUIfR5WIeGRfKKZvbgkD6ZtdUh_c2i42cb1L_44nKV1yAA; esctx-MAgm3FzOXs=AQABCQEAAABVrSpeuWamRam2jAF1XRQEYDBDWCzI1iHwMWZGI03vSuSIh6gy6rjPTsESEBwZ_LE05Wq-QzCrCm1jmpOwcsP1w7R9fTCqihOcY_o_AWhOI7WhYUotsCImku21yUN247bVr0DjJdK6DugwAKqYXEDoAfNcD83vehCmrItND9nqbiAA; esctx-nVuH0Xm0KQs=AQABCQEAAABVrSpeuWamRam2jAF1XRQEVXZXYRGhGeck792YOJvq7to82GRtckdygoysCgFlASOiSjIy441LjYf1MzFhgIcQuyrma2RGOJJATVo2tRqFSyKk22HmROqalW7ht4vx4UIzpmtWMTPW_-jkMGJuGjoJq_P7_zbQVtOp1JeD1xqWziAA; esctx-zqT2itgiPyA=AQABCQEAAABVrSpeuWamRam2jAF1XRQEwfkxa-plO7PLUsAEMwIUhv-ILsXaU3I5OItjdrAC24B4o39GBxfK1iMqUy2-lGsry1-BJ2ktKtqbNISQ744tDrYx_1OWyTF8QRJCqaEGGVH_Su204S4f6-Swp8fo2ofRQcc1tq9xZ9vm1vltDSuAsyAA; esctx-dxarhphnaNw=AQABCQEAAABVrSpeuWamRam2jAF1XRQExTeQv5eU0Fy4N1v4TxiUN-Nkzn3nUabVvTQST_OS7fWf7Jv5poUaUbQA1nkvdcrou3-qT96VL54asYvenrQzo8O05rE65BvVsdVv6ZLoHImrnm2WGbzKu0NQFkzdVIt1BJ-c-Phq7CopqKnEBnntqyAA; wlidperf=FR=L&ST=1741949868380; esctx=PAQABBwEAAABVrSpeuWamRam2jAF1XRQEqcxX-UMVOR4m0XLT9jhrs4RrH3FUlDhfDGot_sq7LFQLoHFxcP32240HU9sPC2MxfoTcHxwwupwB7V4ELIWcfqaJ2dqh4Gf_HREOBWRU5sl_iOaSS_oMOOBhuzROFHB4FwQyUg7IFuzw3bSXcDLPSshZsMVJ5KQsYBj_C0EcyCMgAA; esctx-0eY2VlhFWAs=AQABCQEAAABVrSpeuWamRam2jAF1XRQE5amQbSct3--kG3c7XyRlog0--Gr05exV11XTgSJkA7MeVgPG46Q_mpi7yICWXOmSspo18q7oxZb78wKk61fIBM0RC4pgfbJzx6s57qtVJe04AnDcSoXofcZjovy6TYn1tzOQZ3p6uF3HdPbn_PZvBiAA; CCState=Q3JZQkNoZGhjMmhzWlhrdVkyOXZjR1Z5UUdodFkzUnpMbTVsZEJJQkFTSUpDWUFDUmRXek05eElLZ2tKREp3MklwOW4zVWd5S2dvU0NoRDhwTzRIV1ZhU1NaQTNFQUxVSzZJRkVna0p0VCs5V3Y1aTNVZ2FDUW02YkdpT28yRGRTRElxQ2hJS0VJTkFTOFN3TzhGSnRIMlhUbFBMM3p3U0NRbjRFN04zZFdUZFNCb0pDWkdGVnFzYVl0MUlPQUJJQUZJU0NoQnQrUjlUNlFvcVJvMHR2c2ZBdENDQ1doSUtFRDdiRDFva1laWkpyc2Y0NXFVelhJb1NFZ29RRU00VEREVFdyMGFsdEwrdVZ0bnFVaG9KQ1pHRlZxc2FZdDFJTW40Q0FBRWZBUUFBQUZXdEtsNjVacVpGcWJhTUFYVmRGQVFEQU96L0JRRDAvd3RWSTNLeDRCZHdqbXlNQ0srdUthZVJlUDFMdmVxNzdmbzRWZkt6VHg5OWlpK1BIY1FYcllUbVJrVTl0Mzc2WHdmVTU4QWF0TkYvcVhhclBnNTlrZysvT0xmdnVoa2VSRjlPYm9qYURJOFJreVRoM09DVHFsaytxYkw4Z3JRPQ==; fpc=AmYGlHXwPXpIrnlKfA4l2Hc; SignInStateCookie=CAgABFgIAAABVrSpeuWamRam2jAF1XRQEAwDs_wUA9P9DgOTxiKhyIuA7JVyeoo-BSc_xUSY33_0NMeWwtVcJZ5RTR_d-jMUV5CwqZ8JzuyDrO8cKemGdDucm8LCecZ0OD47KGVvr0YHyQTlViPEymCOVejz-8t0tjc6Nx7169eMQNQNELuQPkll2rfIqMx2jvYtcUMrXb2jckmmTTFvFdEe3ty8U7PogzNmn2cFje74CbwvWJki4d6mOQS2p6_qeccYIwkKSqRsOxVCib6MThdrbx0yVsU741EEdFnv508zOud9ArvcbZIfLlYBVP5QzSuw2KbDRtgBfPX53lhPHK6NlVADLiRDdFaZ6raRx8AyWCYCHExEt2nanIShOYHSS-IYNPuxPjxm63f6C3iqMO0m9bz8vXpTzS-Z6pSdlal2Md6957lHBdpCQLyoGF8HRMi1Ah9iyNC9tzKBYv9olqFAEVe8P6y7c1iMcEN4OQwsoJEC5pC_1Fex1cOMEsSgxDp6Rp0zisAyCq3-u4WyeLHPqMPLOL1kx70SWw5uPxzFolN6dVgjZBBiWOz6KzBIhczlXn2GjlkFgpXnpls9t7TTDgMnl07CblVS6KqVVZsrXE-25MDTEydVa90AK_rcZDwfuI1xgkdhLC8PaX6H455vCyYudxw2-H9CxnCztzv74AQwcxY1MjsL-hDFitpBPWKwgCvkgp8rftZrLxHM0nGyUfYZGTAnReY6s4XVuTKqwFi7VplJ3V5dbFAufH1Uy_H2CscDDUtwpQNSqDIoPKiUVywPgB4TEQW-8U_sgVpLvrj4cawJx7zt3FR1oDyMwb3kyoNXVIoXTeTNCFDGQBLAN7lQKn3vi6i8NjvvQEA3boZBESXhB3yWEySZF4KtVGqHc3g; ESTSAUTHPERSISTENT=1.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM7AA.AgABFwQAAABVrSpeuWamRam2jAF1XRQEAwDs_wUA9P_y_wNORBihe4Mlc26BssIMgbFDLfuPXr0sBcjYsgQXuuRLFCJ6M3aeKTFCPL7HWqBGPrBhrXgN52gZaMjfgzxU0lJHdQL20lgLu__frsllYx_vHgQjkkMLE_nbarjaAVPo5bwSwLwBUdODeV9G7iTboSgWxzS3bBBC9B-cZ5FJ8iwraA_3VlIZ4Sk-lDh4OxBcjIkFTDnHXr2PPNsCSf4mOS_MFle-4tfBsc5ZKutwZi_ueE8WWMjl597Dv_TMg0ogeFIUmlPShfgd-CAdgogOGNEBADbtIBMWli7WjXkQeMZqcnduVVzzxVX4UpFKeR3OY9BUsBqphiBtT57eRJvg35PWkY32AGL_c7Iam7lPDxLczKolMmsd89DOrGjdkbzoe5ztrHx80vo2yXt0qms2XflfL7xwcmBGarLri9FZx9E0PDgKEZtmoSGuOO4S11rAiHo0qMV3Jrb-5vbGCG57GfDfD99TuBxZFYP4UnikNpI146BVoE9fs0JM2bLnr0i0eRzCsGm6YJVcKUWuTd7v09NEJ7FpH8UyASRxlPgsPa_F0tLHCHHIsjSd7evl_2EdNnEZFmMYMZf0l-kWRxTji2m8HxJ9WCFJ8Bp7g4f2FdzCBCHruGKWcHRE9CZj7kqY0Ce7Yzli5jsmmorHozRgvKTn2ly2tfbPHaHrQgR86BVk1p2hgbkfodXTKT0kzlleJox__8oIW5d-IcEdsh-fOKJChZSmnmNqB6t-ZxqcCRMPw8JNjJBeqV1vd-4XSZG8aupvt0w6Cr16pq8RaMl6GmnoIsqMc8Q51ry3bVRdqGF_UONh18wvJXAVCm0UrEuIUjF9JgVdLySgdejDrQAyNNiDZGsiPolrEkPlQCHne1CngYkhkaiWULCCFiq9ySE6xnq4BR_rf3E_cL5OYKpZM5AGel1KVdJ7MuhWdl2kdWDM3g2nxgw-xTxzWCA0HnfE_VE9mmf6lEQ8fSSRJvIdT8LFaTao0ROHPffLjp6DxMKcKWiMwOWQKvHYDZQh1dyqjIABKyW_tPA5LzdmUc4U8D4ujM9JIvjEyzjpdpGGvN5akOQXWY2hYMp-mbXBzOXz_waTbtlLHEaZWJBs_neAVBIqPgpWdm8Xev93dWt5VE8n0jRetvgTAPH-QuScMpLt_809LoM77jnmCtM0BZTe6jZmUGAiolp4_8Gi9UUCaQCkDO8Ohsx9Ib-Eq5c53vVsOrS5AjYg_cZa9n3OZ49346AHkDb9zxpFbuNiRYMzsdghuZhdVPL1hAy_olGn0AxlLId91U9T63iWPUtWPA2TwfLhUacoM1ytO231bOAOLSaPfDd9zb3eV1WgZE14zxpa3lnXWWbKwmM-K8HoD3HokzXblfVZODlz5bw4D0qR9EEtgQEKH1Zb8XkPbf_tPSjtM-h5jZVjyO7VLpRFKHynR22yW8oqFH-eTN_RwYVSCNtxf-j2ZAvr1NYwrb8STB_MW9mbMh7_5bJ_QTKW74xMP-dRqa32Kt_k0Nd0_9A5XbyN-WnXPfxcRKSLnwRgzKiD8woSO-DITEmcKX_2RADzfUYSmMG28UZp0MG5AS_0d9NyMIsRKUDqoD_lty83CHEoz8tOmhKZzQypNB5X-STbDE3zwIyFa2ZfB1OTnYCuyyu1P1BXwlBtipNuxDWBZ-4DbTkf_CvQkxXCbKs9MV-e_0Coqmvl4mQ3pyLRyWG2FUJk1p-Dx7Fhw7tqZ7eehTIEzq6QYC7GGddjekKVvQNyg5g2MuVQGCalkhIG1w74VXIUoaqNwk9WDw; ESTSAUTH=1.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM7AA.AgABFwQAAABVrSpeuWamRam2jAF1XRQEAwDs_wUA9P9FmU7RHTZMsAlbTxifsquHNXOkGAW8yTuhV5-umrKwXusf4pW0P9TE_mDE58D1uTnCDsiyf0D-Sg; ESTSAUTHLIGHT=+b49609ec-70ae-4270-9f5e-d3c2ae22bcc4+a07a7602-1e09-45d8-b18b-e937f7e92587; buid=1.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM7AA.AQABGgEAAABVrSpeuWamRam2jAF1XRQEeH9fy86RYv9Qa8ZHYHaYrOYpWFaCl9ovbrGRTvuKcTdS83iIfW9X6-xHOQLnnaur31jjUQAXrKjuManw6UOEfikFaDBK-XIso8-E2rVHzbTzMhCnw1x8PZOLgCvU2F1-NM293PqELHRekz7pM271UHxnAAoYbWfdliycctwAtQogAA; ai_session=zgodk4MY6mSgJgKhsZAhnL|1741952704175|1741952704175")))



                .exec(http("Darts-Portal - Auth - Internal - Login")
                .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/internal/login")
                .headers(Headers.getHeaders(27))
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
                .headers(Headers.getHeaders(28))
                .body(StringBody("#{xmlPayload}")).asJson()
                .check(status().is(200))
                .check(status().saveAs("status"))
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Login Microsoftonline - Common - GetCredentialType"))

            .exitHereIfFailed() 
            .pause(2, 10)

            .exec(addCookie(Cookie("brcap", "0; MicrosoftApplicationsTelemetryDeviceId=cdc9ea8a-5a9c-45e1-830b-295ed79e94f0; ESTSSSOTILES=1; AADSSOTILES=1; MSFPC=GUID=3cf27633ddc2408d81f8de91dc12c921&HASH=3cf2&LV=202403&V=4&LU=1711447933369; x-ms-gateway-slice=estsfd; stsservicecookie=estsfd; AADSSO=NA|NoExtension; esctx-ksEB7mZsv1w=AQABCQEAAABVrSpeuWamRam2jAF1XRQERyMt-u8KWqV-5qwikKFhMaAdtloJFfqYFxyuNSMIjilv10w0vjf6s6hkqoBm_ishw9HZtpl7RwTKJj8bxSkBz18mC9YaJYWYyvFp9PmS2WupTlhWuMt607raDOjzQkoOB_rrbbdJT6kWhJLZQNxIpiAA; esctx-fbpyCOkRqMo=AQABCQEAAABVrSpeuWamRam2jAF1XRQEAgfVB-SCFuNNn5UnVBhcBktZNGxEjLcRWu9auDwWa4opkZ0odrb2lXM5AnruJwy0RI1EtyZR2KQsXk9OICFMWLu83ERgF__ToQy-A4yWRFgXMIfqU4EN7RHpLNiBc6zT742X0vlaaIYWPpZrbYD9qCAA; esctx-L1obyvOmMQM=AQABCQEAAABVrSpeuWamRam2jAF1XRQEucY9hpFSauX61ZFzkpDhS2gLcb0CY0vsbBG8K8vG_aNPnDm3Kvw0TdZ0OeBTYH0LfzZmk0xb3ujn4Utbb3cbtB2sDnQASQtnOCUw1IKf2zZfsimDQ0yIlaGPaySziwy2GvG9zly4h6QX2gxvqlm8fiAA; esctx-FSjbM953wI4=AQABCQEAAABVrSpeuWamRam2jAF1XRQEpNoVDe_vCeNKC98ADdWteht4P0UXO5Wmip67ixXI1FgISW5eK94i1qeeTGPq3KHPEwbUbuQ97ME9ZauIJ14L1U4fDdoUuspxSS7-2uCaJCKdouteg1dDqB2RD_f9rCBhb7h4nVXerF2jTag0aFAAKCAA; esctx-FGCTNJ6KEI=AQABCQEAAABVrSpeuWamRam2jAF1XRQE1_B1Wc5Nt4fXl2NbUsS85s6KjN9f1Y460qtEETocZko4VQ0UX3ZHSK9m9qL-VJOunvqoqm-Ftgo-IBTnbMwGdhOAcPkX_BC0rOBIY_5rBWvi4kIRuWpT0UGgHRUu6akzWEBUxz2uiCA7vQYHu60c0yAA; esctx-1ddUxnqpVQ=AQABCQEAAABVrSpeuWamRam2jAF1XRQEww429I44P3LOTn_xk_KAUjtDQjH3awIVm4LLpexqYsVtC8G8q6Z4OOLs_xqhOPJ2JP4o3mkZAekZBvBfINYQgCM2tbeSHwa2LGwO8SKww_ZQJ9Gi7xDEA3x-1L4T0gn4Ljo7AxLrc8PcVBvMm8oCeCAA; esctx-k9tr9nDorLc=AQABCQEAAABVrSpeuWamRam2jAF1XRQElveNl4eQaDebhV4jTI2pto6deDuwUc71vQYmmDmSskxjQqEgQdUWuVDSC4DWmnR63OxJCyi3N36NlmGvXHckktXNRfbLKP7tzHVPD_hAZUu3V1CJKxAKsn-o5BHVZHA5Li6LKX-IPtKPndTrZPhD9CAA; esctx-pVKh5Se7Sw=AQABCQEAAABVrSpeuWamRam2jAF1XRQEhmQx5QSQKreF30cXRCIGPzmi_DufOge5lH0nk5Rki6bAlWeAEt9inD8KtR9Rdy6hAMMJscpDdwvGIllgr4_ZIqmxbKz6TGxFGM8Bd1y8vEpHgDvFNkNnrLof10PDCjkpJicSBxorPCJoyNkQa1ZVviAA; CCState=Q3JZQkNoZGhjMmhzWlhrdVkyOXZjR1Z5UUdodFkzUnpMbTVsZEJJQkFTSUpDWUFDUmRXek05eElLZ2tKREp3MklwOW4zVWd5S2dvU0NoRDhwTzRIV1ZhU1NaQTNFQUxVSzZJRkVna0p0VCs5V3Y1aTNVZ2FDUW02YkdpT28yRGRTRElxQ2hJS0VJTkFTOFN3TzhGSnRIMlhUbFBMM3p3U0NRbjRFN04zZFdUZFNCb0pDWkdGVnFzYVl0MUlPQUJJQUZJU0NoQnQrUjlUNlFvcVJvMHR2c2ZBdENDQ1doSUtFRDdiRDFva1laWkpyc2Y0NXFVelhJb1NFZ29RRU00VEREVFdyMGFsdEwrdVZ0bnFVaG9KQ1pHRlZxc2FZdDFJTW40Q0FBRWZBUUFBQUZXdEtsNjVacVpGcWJhTUFYVmRGQVFEQU96L0JRRDAvNngwL2QxVE5SWms3VTJVOXNtSnRmOTY1ZEpKZitlVlZMdWhwSGFZcWJ4NEliOTVWTUpUNVRrVlZMMWM3SG4rSXRDMldJa2hMeG9hVVU1SklyNGdobmhNaVkrM2IvT0wxTmhtb00vQWI5Z2tERi8yOUR0UmpadFk3RS9pTzRnPQ==; SignInStateCookie=CAgABFgIAAABVrSpeuWamRam2jAF1XRQEAwDs_wUA9P-I0a9fHCm3gDbUP86b_wiXAJn_nAXPPFzl27e2cIOJoQb3lpgiyE798eQy8wsJyStxjh4ZUa5ZrXFG_IJ0pjaQSxBjzdOxd0rx05LghJHUwDK1RQu-1T8Llril671WnIv4gtEoh2dUgVEstpfCiec1tr33AuHqlsDxgY5vnEuHFnv4WBLurHJIDWxN9plhobSONijPChxiJu3fekImIXgd3vgHRYnJcCY3F7XmNlUkEKbZas2Py5zCOdkD37SJyY_PcdoQnoNjitp0zPrxbyAkvQxHpMckzM0I6z0ql8lQZaYW9n-3UuJeUeILI2Q14Ahf6d534PGpr9cxum8Y29Ve4evJvcB1icm4J3xN-mX4rbUccz5M9dcZi3HcfQ; ESTSAUTHLIGHT=+003066b9-2857-c9d7-bc48-c5c3189debc4+a07a7602-1e09-45d8-b18b-e937f7e92587; esctx-scW7jlofbc=AQABCQEAAABVrSpeuWamRam2jAF1XRQE8OiDGe_e7-mOBo0elak_xZkjC6spSBfQn1e5NKPEvFe6jT_P3_yStsm5x_W8bVoYsw2TfKq5z2Zcx_EzEDj9fxQ6mKJ5W-x-R1VoKvNQAA6YyTdlmDY6v3iEoHvuDmSFiTm3lnGSpvrSVKMDNE9eDSAA; ESTSAUTHPERSISTENT=1.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM7AA.AgABFwQAAABVrSpeuWamRam2jAF1XRQEAwDs_wUA9P-9V5gZhCPWzsSn5XDTXiUy99i39Ed3tQBFTOThC87munZh4YjQXueQvRmDIjxhftz1qYIOuGUMVL-tDClR6x_eRa3QQilIheZgwpGTdNU5WwtL9qM2bn7k1t5J3Xb3Vlq_K1kEoxwn1XH29i0iCqYAOz9dcnebU8y4TY-maol-NtYhpbQ2nxcusmVNcCP7M0DuaitXB4WsIriUm8N6MacS4CR7Vizg0EdnweVfllyy6FCVzcaHeqGSdn50mTnuIVtFfprvx7XxxnTnRdougZS-3ELbWqsjl76rwmwNuQRk9avebUIbDOfwno5aeenG4__MJ7PUtpzgDBj7ESKNf6BENgjYo04mWASlQQq0qqd9ri1IY00r0_vHdQezy9WFY1wspTZVKIpJ8ssUpdG_3qcKkVJraEMFp-3O35wS1oXAm4JW4RY7V8DDKYS3aWF2pvacktFkRtkVGAGuYrcwlT5AXC3U7JRz8KQ-xOTYd7NnlaKeVhpGCYdvmfJ5E2PFDQWf9DKIHKh2KmgJOw07TQIHA-RPC5gmD0leBaxlZ0Oc1AZGdFRZt9UOks5b2pRjTT6AKL_GWcS-U7oekVSDPFr2O4LVqrRszm43x2PQalXBkMZ6WIcdOTMI9e0AgUOewtAAoENr5TuQ-9VkoWemCHzzkdszlNDilhtxg-mXFRos3EiaD-soiO3AjX6kyrtNAMyIkdzSuJ5Ywa7K5WyhdDmsX_nKZJv5PxTnz42utF18jaJmyDBSlhFgkzsHN96yIBlxk7v4_8jUwCqW58tdYP26RweveYqnQL9__b4QHjQ4OwX8ymVoyyBMGVF5ysRsWkkXcOWlym8U-cUYxHTx_0A_6H6MCvixWYX-I1I9kE1aSt9Fb_rljH3H7B1ndjLblqvZNNuxStKoNqiUb1dVcA7Td93U5hR6GSxCErZnL7gZk5myvsdYcWfZXM9TfbCga6ksKPpEPHAePeAH2KRxS_O2WRFgb3zOG2tlOFgwWX410b6Bq_7EXxIpwzUQI19sRcC3E96s1Vi1q7YG0VkwN93CFDMskyeIv9BkvPRotAhjZRSEi5fVTRejzUdIUOFpB17nMDzQAAE0fD5elVXd8wys_k9HjM2SKFfOVSCRfeoxV-FG9bJ7i7b-x74nmJ6xVLsuZnd1Zhjyejco6WHXRGOOA6ypcgnx0bXo5C4R-mHotQQMLyhb53dnZkzaWD_K4s6eufyvCasjTeI-aBzD-zW3uIm46a4q7k-3F5C0u_Z2Ur51NgouNDwlxOICFZQCGaBzEeeHib70PZs2Aoo3Gr-WumVV1CF3NryAbK-oNW88_ybpZ-Nrj6c5WC2Jhc8NFfKzrnGeKBTCfLyeZsnpJh9WuYw5waBcks546BlPFx-0PVjur9JEAip74WxK-mWwWTCF2-W669cY4RVui7Pt9AXfUMx2kULuOwp9EtQpc5alcN2rEVQ7UXHaWTMKGZh-KT8PjDvES2VXDT6RUyYRB5aBV1lgjwl-wJk0qoYqENQQldpn9XwjFW0ey_6egK6wTqJsw7tSwWQeAvED_HqEmOdpumxE8EZA3rw0aVZU; ESTSAUTH=1.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM7AA.AgABFwQAAABVrSpeuWamRam2jAF1XRQEAwDs_wUA9P-Esdb7cq4XxHbWvDGUxlo3zaI5Gma2LBvDmMuZgXWcCG3goAorkjw6Mw_KsYn28cKJhvRyNV_iFM2v30tndJ-004D8nv50b40hagNGWqWWMQqeAxXov6giTPRQIaQ0FwR2ezKhRgpxYeLdOIlX6Z4ffouWJX5ikPYdn5yf9cGH-oSwAd4BG7dW-MrMZgWCTgoVwJ9u84Y2EOGB13zUqmhWraYYq9uYbpJ2LjDYWrV4PvpBA_G2ZjRvPZPQIcOTjLdSpC6gNijhUDZLqmdcZ_3Mp6VrC3Fe17KZYb99yL8znvAYgmeNqnOInN3NOZUg0VZfJ9AHPQEUbfbMLIsvfsLZh1Mv9L7Ga-8fyCH9voVI8VL41Po0RyD60tfEVVvCuwA-yZA77h1dFA8Mlax6V1ugR_EP3u1hR69d-ZWgmvRIqyB-fNqNdrKV5UM8V-gSTflfBYzGuAeaO_dDCNciy097EXD1lqH5oCiCnofhtQT3FUUOkyIhB9kFxVrkTkNQLBN10NaDhxZ55SjoDDHeQJbFK_trvaCYqYnoN59k56exzlx_1W0_XkcSYr85rm5urRnKp-2yB0I3su5uMAjo5Vo0IpLhScYuEA1KZubgafDpQYVPtA; buid=1.ATsAY_Z15QqzhkeJrTGYQt_oU0oNk22s_s5BjQilufY_mYAgAOM7AA.AQABGgEAAABVrSpeuWamRam2jAF1XRQE0Waa7MkmZUzhmu6reG3g2mKld_yG1wyiaNdqbnm0W9m74LBRwy1MCdIY8exqTOs2k610hrN1hNEHm8IrJILpM5aXY7MBvRq0WLkPHqCk8c-oUuEDQKVFlfrLmIQkT4lLSCskDiyX6FXyXwLK7VT9BNihLVOz-1-l5raloP2059sgAA; esctx=PAQABBwEAAABVrSpeuWamRam2jAF1XRQEqQxuuj5dGWSdU9AQaLkQQLeOcktkz6z4o5T3rTnykO6tHdOmYrzKFUjI1MEqU1yvh32KUjVVMBxzKRF0Wl9JTq6CajE6wz30NcUsYNXfawcTmUHsA1hsAZv_x1FrBHVG6mK0W2zFyNBPsvn4JJp5kYlMiBhtiY-lG_DhbZT6FJYgAA; esctx-9dcJfOZ8VPI=AQABCQEAAABVrSpeuWamRam2jAF1XRQEROgNsJ8NldpyhQbaIiJQjyLsRDFVfE5o4Ma49Q2YqlXboARqgaXnYKAuG6avELyWxP8xU3G1jo5eiVRlCwDvSsWCixayYXzoA-1grgLJQsHEclAYaujHS2foNWoUldHS-OsqW8ykNmYaPTfhExODWSAA; fpc=AmYGlHXwPXpIrnlKfA4l2HeZYyN4BAAAALLlad8OAAAAJRSmvQMAAABU5mnfDgAAANFdxqQCAAAA2OZp3w4AAAA; ai_session=wVz8rcwNWWmPKeP3DC1yys|1742201178081|1742204981427; wlidperf=FR=L&ST=1742204982623")))

            .exec(
                http("Darts-Portal - Login")
                .post("/e575f663-b30a-4786-89ad-319842dfe853/login")
                .headers(Headers.getHeaders(29))
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
                .formParam("i19", "3306")
                .check(regex("name=\"code\" value=\"([^\"]*)\"").saveAs("codeToken"))
                .check(regex("name=\"session_state\" value=\"([^\"]*)\"").saveAs("sessionState"))
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Login", "code=\"([^\"]*)\"", "#{responseBody}"))


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
