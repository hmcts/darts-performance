SELECT darts.transformed_media.trm_id, darts.transformed_media.mer_id, darts.media_request.hea_id, darts.media_request.request_status, darts.media_request.request_type
FROM darts.transformed_media
INNER JOIN
    darts.media_request
    ON 
    darts.transformed_media.mer_id = darts.media_request.mer_id
WHERE darts.media_request.request_type = 'PLAYBACK'
AND darts.media_request.request_status = 'COMPLETED'
AND darts.media_request.request_status != 'EXPIRED'
ORDER BY trm_id DESC LIMIT 1000;