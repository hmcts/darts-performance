WITH random_cases AS (
                SELECT DISTINCT ON (cth_id) cas_id, cth_id
                FROM darts.court_case
                ORDER BY cth_id, RANDOM()
            ), 
            case_with_courtrooms AS (
                SELECT rc.cas_id, cc.case_number, rc.cth_id, ch.courthouse_name, 
                       (SELECT cr.courtroom_name 
                        FROM darts.courtroom cr
                        WHERE cr.cth_id = rc.cth_id 
                        ORDER BY RANDOM() LIMIT 1) AS courtroom_name 
                FROM random_cases rc 
                JOIN darts.court_case cc ON rc.cas_id = cc.cas_id 
                JOIN darts.courthouse ch ON rc.cth_id = ch.cth_id 
            ) 
            SELECT cas_id, case_number, cth_id, courthouse_name, courtroom_name 
            FROM case_with_courtrooms
            ORDER BY cth_id;";