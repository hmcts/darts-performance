package simulations.Scripts.Utilities;

public class SQLQueryProvider {

    public static String getHearingQuery() {
        return "SELECT " +
            "   subquery.hea_id, " +
            "   subquery.start_ts, " +
            "   subquery.end_ts, " +
            "   subquery.usr_id " +
            "FROM ( " +
            "   SELECT " +
            "       h.hea_id, " +
            "       TO_CHAR(m.start_ts AT TIME ZONE 'UTC', 'YYYY-MM-DD\"T\"HH24:MI:SS.MS\"z\"') AS start_ts, " +
            "       TO_CHAR(m.end_ts AT TIME ZONE 'UTC', 'YYYY-MM-DD\"T\"HH24:MI:SS.MS\"z\"') AS end_ts, " +
            "       sguae.usr_id " +
            "   FROM " +
            "       darts.hearing h " +
            "   INNER JOIN " +
            "       darts.courtroom cr ON h.ctr_id = cr.ctr_id " +
            "   INNER JOIN " +
            "       darts.courthouse ch ON cr.cth_id = ch.cth_id " +
            "   INNER JOIN " +
            "       darts.court_case cc ON cc.cas_id = h.cas_id " +
            "   INNER JOIN " +
            "       darts.hearing_media_ae hma ON hma.hea_id = h.hea_id " +
            "   INNER JOIN " +
            "       darts.media m ON m.med_id = hma.med_id " +
            "   INNER JOIN " +
            "       darts.security_group_courthouse_ae sgcae ON ch.cth_id = sgcae.cth_id " +
            "   INNER JOIN " +
            "       darts.security_group_user_account_ae sguae ON sgcae.grp_id = sguae.grp_id " +
            "   WHERE " +
            "       h.hearing_date BETWEEN '2023-02-26' AND '2024-05-27' " +
            "   AND " +
            "       sguae.usr_id NOT IN (-100, -99, -69, -68, -67, -48, -44, -4, -3, -2, -1, 0, 1, -101, 221, 241, 1141) " +
            "   ORDER BY " +
            "       RANDOM() " +
            "   LIMIT 1000 " +
            ") subquery;";
    }


    public static String getTransformedMediaIdQuery() {
        return "SELECT darts.transformed_media.trm_id, " + 
            "darts.transformed_media.mer_id, " + 
            "darts.media_request.hea_id, " + 
            "darts.media_request.request_status, " + 
            "darts.media_request.request_type " + 
            "FROM darts.transformed_media " + 
            "INNER JOIN " + 
            "    darts.media_request " +  
            "    ON " + 
            "    darts.transformed_media.mer_id = darts.media_request.mer_id " + 
            "WHERE darts.media_request.request_type = 'DOWNLOAD' " + 
            "AND darts.media_request.request_status = 'COMPLETED' " + 
            "ORDER BY trm_id DESC LIMIT 1000;"; 
    }



    public static String getCaseDetailsForRetentionQuery() {
        return "WITH random_cases AS (" +
            "    SELECT DISTINCT ON (cth_id) cas_id, cth_id " +
            "    FROM darts.court_case " +
            "    ORDER BY cth_id, RANDOM()" +
            "), " +
            "case_with_courtrooms AS (" +
            "    SELECT rc.cas_id, cc.case_number, rc.cth_id, ch.courthouse_name, " +
            "           (SELECT cr.courtroom_name " +
            "            FROM darts.courtroom cr " +
            "            WHERE cr.cth_id = rc.cth_id " +
            "            ORDER BY RANDOM() LIMIT 1) AS courtroom_name " +
            "    FROM random_cases rc " +
            "    JOIN darts.court_case cc ON rc.cas_id = cc.cas_id " +
            "    JOIN darts.courthouse ch ON rc.cth_id = ch.cth_id " +
            ") " +
            "SELECT cas_id, case_number, cth_id, courthouse_name, courtroom_name " +
            "FROM case_with_courtrooms " +
            "ORDER BY cth_id;";
        }

    public static String getCaseDetailsForRetentionQuery() {
        return  "SELECT darts.transformed_media.trm_id, " +
                "darts.transformed_media.mer_id, " +
                "darts.media_request.hea_id, " +
                "darts.media_request.request_status, " +
                "darts.media_request.request_type " +
            "FROM darts.transformed_media " +
            "INNER JOIN " +
                "darts.media_request " +
            "ON " +
                "darts.transformed_media.mer_id = darts.media_request.mer_id " +
            "WHERE darts.media_request.request_type = 'DOWNLOAD' " +
            "AND darts.media_request.request_status != 'DELETED' " +
            "ORDER BY trm_id ASC LIMIT 500;"; 
        }

        String sql = 
        "SELECT darts.transformed_media.trm_id, " +
            "darts.transformed_media.mer_id, " +
            "darts.media_request.hea_id, " +
            "darts.media_request.request_status, " +
            "darts.media_request.request_type " +
        "FROM darts.transformed_media " +
        "INNER JOIN " +
            "darts.media_request " +
        "ON " +
            "darts.transformed_media.mer_id = darts.media_request.mer_id " +
        "WHERE darts.media_request.request_type = 'DOWNLOAD' " +
        "AND darts.media_request.request_status != 'DELETED' " +
        "ORDER BY trm_id ASC LIMIT 500;"; 
}