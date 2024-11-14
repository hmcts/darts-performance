SELECT 
    subquery.hea_id, 
    subquery.start_ts, 
    subquery.end_ts, 
    subquery.usr_id 
FROM ( 
    SELECT 
        h.hea_id, 
        TO_CHAR(m.start_ts AT TIME ZONE 'UTC', 'YYYY-MM-DD"T"HH24:MI:SS.MS"z"') AS start_ts, 
        TO_CHAR(m.end_ts AT TIME ZONE 'UTC', 'YYYY-MM-DD"T"HH24:MI:SS.MS"z"') AS end_ts, 
        sguae.usr_id 
    FROM 
        darts.hearing h 
    INNER JOIN 
        darts.courtroom cr ON h.ctr_id = cr.ctr_id 
    INNER JOIN 
        darts.courthouse ch ON cr.cth_id = ch.cth_id 
    INNER JOIN 
        darts.court_case cc ON cc.cas_id = h.cas_id 
    INNER JOIN 
        darts.hearing_media_ae hma ON hma.hea_id = h.hea_id 
    INNER JOIN 
        darts.media m ON m.med_id = hma.med_id 
    INNER JOIN 
        darts.security_group_courthouse_ae sgcae ON ch.cth_id = sgcae.cth_id 
    INNER JOIN 
        darts.security_group_user_account_ae sguae ON sgcae.grp_id = sguae.grp_id 
    WHERE 
        h.hearing_date BETWEEN '2023-02-26' AND '2024-05-27' 
    AND 
        sguae.usr_id NOT IN (-100, -99, -69, -68, -67, -48, -44, -4, -3, -2, -1, 0, 1, -101, 221, 241, 1141) 
    ORDER BY 
        RANDOM() 
    LIMIT 1000 
) subquery;