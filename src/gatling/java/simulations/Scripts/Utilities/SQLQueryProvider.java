package simulations.Scripts.Utilities;

import io.burt.jmespath.antlr.v4.runtime.atn.SemanticContext.AND;
import jodd.csselly.selector.PseudoFunctions.NOT;

public class SQLQueryProvider {

    public static String getHearingQuery() {
        return """
            WITH deduplicated_hearings AS (
                SELECT
                    h.hea_id,
                    m.start_ts,
                    m.end_ts,
                    sguae.usr_id,
                    ROW_NUMBER() OVER (PARTITION BY h.hea_id ORDER BY RANDOM()) AS row_num
                FROM darts.hearing h
                INNER JOIN darts.courtroom cr ON h.ctr_id = cr.ctr_id
                INNER JOIN darts.courthouse ch ON cr.cth_id = ch.cth_id
                INNER JOIN darts.court_case cc ON cc.cas_id = h.cas_id
                INNER JOIN darts.hearing_media_ae hma ON hma.hea_id = h.hea_id
                INNER JOIN darts.media m ON m.med_id = hma.med_id
                INNER JOIN darts.security_group_courthouse_ae sgcae ON ch.cth_id = sgcae.cth_id
                INNER JOIN darts.security_group_user_account_ae sguae ON sgcae.grp_id = sguae.grp_id
                WHERE h.hearing_date BETWEEN DATE '2023-02-26' AND DATE '2024-05-27'
                    AND m.is_current = true
                    AND sguae.usr_id NOT IN (-100, -99, -69, -68, -67, -48, -44, -4, -3, -2, -1, 0, 1, -101, 221, 241, 1141)
                    AND NOT EXISTS (
                        SELECT 1
                        FROM darts.media_request_aud mra
                        WHERE mra.hea_id = h.hea_id
                    )
            )
            SELECT
                hea_id,
                TO_CHAR(start_ts AT TIME ZONE 'UTC', 'YYYY-MM-DD\"T\"HH24:MI:SS.MS\"z\"') AS start_ts,
                TO_CHAR(end_ts AT TIME ZONE 'UTC', 'YYYY-MM-DD\"T\"HH24:MI:SS.MS\"z\"') AS end_ts,
                usr_id
            FROM deduplicated_hearings
            WHERE row_num = 1
            ORDER BY RANDOM()
            LIMIT 1000;
        """;
    }
    


    public static String getTransformedMediaIdForDownloadQuery() {
        return "SELECT darts.transformed_media.trm_id, "
            + "darts.transformed_media.mer_id, "
            + "darts.media_request.hea_id, "
            + "darts.media_request.request_status, "
            + "darts.media_request.request_type "
            + "FROM darts.transformed_media "
            + "INNER JOIN "
            + "    darts.media_request "
            + "    ON "
            + "    darts.transformed_media.mer_id = darts.media_request.mer_id "
            + "INNER JOIN " 
            + "        darts.transient_object_directory "
            + "        ON 	 "
            + "        darts.transformed_media.trm_id = darts.transient_object_directory.trm_id "
            + "WHERE darts.media_request.request_type = 'DOWNLOAD' "
            + "AND darts.media_request.request_status = 'COMPLETED' "
            + "AND darts.media_request.request_status != 'EXPIRED' "
            + "AND darts.transient_object_directory.ors_id = 2 "
            + "AND darts.transient_object_directory.external_location IS NOT NULL "
            + "ORDER BY trm_id ASC LIMIT 1000;";
    }
    
    public static String getTransformedMediaIdForPlayBackQuery() {
        return "SELECT darts.transformed_media.trm_id, "
            + "darts.transformed_media.mer_id, "
            + "darts.media_request.hea_id, "
            + "darts.media_request.request_status, "
            + "darts.media_request.request_type "
            + "FROM darts.transformed_media "
            + "INNER JOIN "
            + "    darts.media_request "
            + "    ON "
            + "    darts.transformed_media.mer_id = darts.media_request.mer_id "
            + "INNER JOIN " 
            + "        darts.transient_object_directory "
            + "        ON 	 "
            + "        darts.transformed_media.trm_id = darts.transient_object_directory.trm_id "
            + "WHERE darts.media_request.request_type = 'PLAYBACK' "
            + "AND darts.media_request.request_status = 'COMPLETED' "
            + "AND darts.media_request.request_status != 'EXPIRED' "
            + "AND darts.transient_object_directory.ors_id = 2 "
            + "AND darts.transient_object_directory.external_location IS NOT NULL "
            + "ORDER BY trm_id ASC LIMIT 1000;";
    }


    public static String getCaseDetailsForRetentionQuery() {
        return "WITH random_cases AS ("
            + "    SELECT DISTINCT ON (cth_id) cas_id, cth_id "
            + "    FROM darts.court_case "
            + "    ORDER BY cth_id, RANDOM()"
            + "), "
            + "case_with_courtrooms AS ("
            + "    SELECT rc.cas_id, cc.case_number, rc.cth_id, ch.courthouse_name, "
            + "           (SELECT cr.courtroom_name "
            + "            FROM darts.courtroom cr "
            + "            WHERE cr.cth_id = rc.cth_id "
            + "            ORDER BY RANDOM() LIMIT 1) AS courtroom_name "
            + "    FROM random_cases rc "
            + "    JOIN darts.court_case cc ON rc.cas_id = cc.cas_id "
            + "    JOIN darts.courthouse ch ON rc.cth_id = ch.cth_id "
            + ") "
            + "SELECT cas_id, case_number, cth_id, courthouse_name, courtroom_name "
            + "FROM case_with_courtrooms "
            + "ORDER BY cth_id;";
        }

    public static String getTransformedMediaToDeleteQuery() {
        return  "SELECT darts.transformed_media.trm_id, "
                + "darts.transformed_media.mer_id, "
                + "darts.media_request.hea_id, "
                + "darts.media_request.request_status, "
                + "darts.media_request.request_type "
            + "FROM darts.transformed_media "
            + "INNER JOIN "
                + "darts.media_request "
            + "ON "
                + "darts.transformed_media.mer_id = darts.media_request.mer_id "
            + "WHERE darts.media_request.request_type = 'DOWNLOAD' "
            + "AND darts.media_request.request_status != 'DELETED' "
            + "ORDER BY trm_id ASC LIMIT 500;"; 
        } 
        
        
    public static String getCaseRetentionForChildObjectQuery() {
        return "WITH random_cases AS ( "
                + "    SELECT DISTINCT ON (cth_id) cas_id, cth_id "
                + "    FROM darts.court_case "
                + "    ORDER BY cth_id, RANDOM() "
                + "), "
                + "case_with_courtrooms AS ( "
                + "    SELECT rc.cas_id, cc.case_number, rc.cth_id, ch.courthouse_name, "
                + "           (SELECT cr.courtroom_name "
                + "            FROM darts.courtroom cr "
                + "            WHERE cr.cth_id = rc.cth_id "
                + "            ORDER BY RANDOM() LIMIT 1) AS courtroom_name "
                + "    FROM random_cases rc "
                + "    JOIN darts.court_case cc ON rc.cas_id = cc.cas_id "
                + "    JOIN darts.courthouse ch ON rc.cth_id = ch.cth_id "
                + ") "
                + "SELECT "
                + "    cwc.cas_id, "
                + "    cwc.case_number, "
                + "    cwc.cth_id, "
                + "    cwc.courthouse_name, "
                + "    cwc.courtroom_name, "
                + "    ( "
                + "        SELECT count(*) "
                + "        FROM darts.hearing h "
                + "        JOIN darts.hearing_media_ae hm ON hm.hea_id = h.hea_id "
                + "        JOIN darts.media med ON med.med_id = hm.med_id "
                + "        WHERE h.cas_id = cwc.cas_id "
                + "        AND med.is_current = TRUE "
                + "    ) as audio_count, "
                + "    ( "
                + "        SELECT count(*) "
                + "        FROM darts.hearing h "
                + "        JOIN darts.hearing_transcription_ae ht ON ht.hea_id = h.hea_id "
                + "        JOIN darts.transcription tra ON tra.tra_id = ht.tra_id "
                + "        JOIN darts.transcription_document trd ON trd.tra_id = tra.tra_id "
                + "        WHERE h.cas_id = cwc.cas_id "
                + "        AND tra.is_current = TRUE "
                + "        AND tra.is_deleted = FALSE "
                + "    ) as transcription_doc_count, "
                + "    ( "
                + "        SELECT count(*) "
                + "        FROM darts.hearing h "
                + "        JOIN darts.hearing_annotation_ae ha ON ha.hea_id = h.hea_id "
                + "        JOIN darts.annotation ann ON ann.ann_id = ha.ann_id "
                + "        JOIN darts.annotation_document ado ON ado.ann_id = ann.ann_id "
                + "        WHERE h.cas_id = cwc.cas_id "
                + "        AND ann.is_deleted = FALSE "
                + "        AND ado.is_deleted = FALSE "
                + "    ) as annotation_doc_count, "
                + "    ( "
                + "        SELECT count(*) "
                + "        FROM darts.case_document cad "
                + "        WHERE cad.cas_id = cwc.cas_id "
                + "        AND cad.is_deleted = FALSE "
                + "    ) as case_doc_count "
                + "FROM case_with_courtrooms cwc "
                + "WHERE EXISTS ( "
                + "    SELECT 1 "
                + "    FROM darts.hearing h "
                + "    JOIN darts.hearing_media_ae hm ON hm.hea_id = h.hea_id "
                + "    JOIN darts.media med ON med.med_id = hm.med_id "
                + "    WHERE h.cas_id = cwc.cas_id "
                + "    AND med.is_current = TRUE "
                + "    AND med.is_deleted = FALSE "
                + ") OR EXISTS ( "
                + "    SELECT 1 "
                + "    FROM darts.hearing h "
                + "    JOIN darts.hearing_transcription_ae ht ON ht.hea_id = h.hea_id "
                + "    JOIN darts.transcription tra ON tra.tra_id = ht.tra_id "
                +                 "    JOIN darts.transcription_document trd ON trd.tra_id = tra.tra_id "
                + "    WHERE h.cas_id = cwc.cas_id "
                + "    AND tra.is_current = TRUE "
                + "    AND tra.is_deleted = FALSE "
                + ") OR EXISTS ( "
                + "    SELECT 1 "
                + "    FROM darts.hearing h "
                + "    JOIN darts.hearing_annotation_ae ha ON ha.hea_id = h.hea_id "
                + "    JOIN darts.annotation ann ON ann.ann_id = ha.ann_id "
                + "    JOIN darts.annotation_document ado ON ado.ann_id = ann.ann_id "
                + "    WHERE h.cas_id = cwc.cas_id "
                + "    AND ann.is_deleted = FALSE "
                + "    AND ado.is_deleted = FALSE "
                + ") OR EXISTS ( "
                + "    SELECT 1 "
                + "    FROM darts.case_document cad "
                + "    WHERE cad.cas_id = cwc.cas_id "
                + "    AND cad.is_deleted = FALSE "
                + ") "
                + "ORDER BY "
                + "    audio_count DESC, "
                + "    transcription_doc_count DESC, "
                + "    annotation_doc_count DESC, "
                + "    case_doc_count DESC, "
                + "    cth_id;";
    }        

    public static String getCaseRetentionForChildObjectQuery2() {
        return "WITH random_cases AS ( "
               + "    SELECT cas_id, cth_id "
               + "    FROM darts.court_case "
               + "    ORDER BY RANDOM() "
               + "    LIMIT 1000 "
               + "), "
               + "case_with_courtrooms AS ( "
               + "    SELECT rc.cas_id, cc.case_number, rc.cth_id, ch.courthouse_name, "
               + "           (SELECT cr.courtroom_name "
               + "            FROM darts.courtroom cr "
               + "            WHERE cr.cth_id = rc.cth_id "
               + "            ORDER BY RANDOM() LIMIT 1) AS courtroom_name "
               + "    FROM random_cases rc "
               + "    JOIN darts.court_case cc ON rc.cas_id = cc.cas_id "
               + "    JOIN darts.courthouse ch ON rc.cth_id = ch.cth_id "
               + "), "
               + "SELECT "
               + "    cwc.cas_id, "
               + "    cwc.case_number, "
               + "    cwc.cth_id, "
               + "    cwc.courthouse_name, "
               + "    cwc.courtroom_name, "
               + "    ( "
               + "        SELECT count(*) "
               + "        FROM darts.hearing h "
               + "        JOIN darts.hearing_media_ae hm ON hm.hea_id = h.hea_id "
               + "        JOIN darts.media med ON med.med_id = hm.med_id "
               + "        WHERE h.cas_id = cwc.cas_id "
               + "        AND med.is_current = TRUE "
               + "    ) as audio_count, "
               + "    ( "
               + "        SELECT count(*) "
               + "        FROM darts.hearing h "
               + "        JOIN darts.hearing_transcription_ae ht ON ht.hea_id = h.hea_id "
               + "        JOIN darts.transcription tra ON tra.tra_id = ht.tra_id "
               + "        JOIN darts.transcription_document trd ON trd.tra_id = tra.tra_id "
               + "        WHERE h.cas_id = cwc.cas_id "
               + "        AND tra.is_current = TRUE "
               + "        AND tra.is_deleted = FALSE "
               + "    ) as transcription_doc_count, "
               + "    ( "
               + "        SELECT count(*) "
               + "        FROM darts.hearing h "
               + "        JOIN darts.hearing_annotation_ae ha ON ha.hea_id = h.hea_id "
               + "        JOIN darts.annotation ann ON ann.ann_id = ha.ann_id "
               + "        JOIN darts.annotation_document ado ON ado.ann_id = ann.ann_id "
               + "        WHERE h.cas_id = cwc.cas_id "
               + "        AND ann.is_deleted = FALSE "
               + "        AND ado.is_deleted = FALSE "
               + "    ) as annotation_doc_count, "
               + "    ( "
               + "        SELECT count(*) "
               + "        FROM darts.case_document cad "
               + "        WHERE cad.cas_id = cwc.cas_id "
               + "        AND cad.is_deleted = FALSE "
               + "    ) as case_doc_count "
               + "FROM case_with_courtrooms cwc "
               + "WHERE EXISTS ( "
               + "    SELECT 1 "
               + "    FROM darts.hearing h "
               + "    JOIN darts.hearing_media_ae hm ON hm.hea_id = h.hea_id "
               + "    JOIN darts.media med ON med.med_id = hm.med_id "
               + "    WHERE h.cas_id = cwc.cas_id "
               + "    AND med.is_current = TRUE "
               + "    AND med.is_deleted = FALSE "
               + ") OR EXISTS ( "
               + "    SELECT 1 "
               + "    FROM darts.hearing h "
               + "    JOIN darts.hearing_transcription_ae ht ON ht.hea_id = h.hea_id "
               + "    JOIN darts.transcription tra ON tra.tra_id = ht.tra_id "
               + "    JOIN darts.transcription_document trd ON trd.tra_id = tra.tra_id "
               + "    WHERE h.cas_id = cwc.cas_id "
               + "    AND tra.is_current = TRUE "
               + "    AND tra.is_deleted = FALSE "
               + ") OR EXISTS ( "
               + "    SELECT 1 "
               + "    FROM darts.hearing h "
               + "    JOIN darts.hearing_annotation_ae ha ON ha.hea_id = h.hea_id "
               + "    JOIN darts.annotation ann ON ann.ann_id = ha.ann_id "
               + "    JOIN darts.annotation_document ado ON ado.ann_id = ann.ann_id "
               +                "    WHERE h.cas_id = cwc.cas_id "
               + "    AND ann.is_deleted = FALSE "
               + "    AND ado.is_deleted = FALSE "
               + ") OR EXISTS ( "
               + "    SELECT 1 "
               + "    FROM darts.case_document cad "
               + "    WHERE cad.cas_id = cwc.cas_id "
               + "    AND cad.is_deleted = FALSE "
               + ") "
               + "ORDER BY "
               + "    audio_count DESC, "
               + "    transcription_doc_count DESC, "
               + "    annotation_doc_count DESC, "
               + "    case_doc_count DESC, "
               + "    cth_id;";
    }    
}