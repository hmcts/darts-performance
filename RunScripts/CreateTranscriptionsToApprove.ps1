# Define the SQL query
$sqlQuery = @"
do $$
declare
	transcriptionPerManager INTEGER := 10;
	shouldApprove boolean := TRUE;
	trtId INTEGER := 1; -- (1 = SENTENCING_REMARKS)
	truId INTEGER := 1; -- (1 = STANDARD)
	trsId INTEGER := 1; -- (1 = REQUESTED)
	userId INTEGER := 8440; 
    transcriptionCountPerManager int;
	-- get all manager users
	managerUsers cursor for select distinct
			ua.usr_id as usr_id,
    		ua.user_email_address as user_email_address,
    		ch.cth_id as cth_id,
			ch.display_name as display_name
		from darts.user_account ua
    	JOIN
        	darts.security_group_user_account_ae sgua ON sgua.usr_id = ua.usr_id
    	JOIN
        	darts.security_group_courthouse_ae sgch ON sgch.grp_id = sgua.grp_id
    	JOIN
        	darts.courthouse ch ON sgch.cth_id = ch.cth_id
    	WHERE
       		ua.user_name LIKE '%PerfCourtManager%';
	managerUser RECORD;
	transcriptionDetails RECORD;
	transcriptionId INTEGER;
begin
	OPEN managerUsers;
	LOOP
		-- For each user
        FETCH NEXT FROM managerUsers INTO managerUser;
        EXIT WHEN NOT FOUND;
		-- Create X number of translations
		
		
		select ua.usr_id
		INTO userId
		from darts.user_account ua
    	JOIN
        	darts.security_group_user_account_ae sgua ON sgua.usr_id = ua.usr_id
    	JOIN
        	darts.security_group_courthouse_ae sgch ON sgch.grp_id = sgua.grp_id
    	JOIN
        	darts.courthouse ch ON sgch.cth_id = ch.cth_id
    	WHERE
       		ua.user_name LIKE '%PerfCourtClerk%'
			AND ch.cth_id = managerUser.cth_id
			limit 1;

		if (userId is null) THEN
			raise notice 'Clerk user not found. Skipping';
			CONTINUE;
		end if;

		raise notice 'Using clerk %', userId;	
		for transcriptionPerManage in 1..transcriptionPerManager loop
			raise notice 'Processing user % count %', managerUser.usr_id, transcriptionPerManage;	
			-- Get the hearing details for the transcription request
			select 
				h.hea_id, 
				h.cas_id, 
				h.ctr_id, 
				c.cth_id 
			INTO transcriptionDetails
			from darts.hearing h 
			join darts.hearing_media_ae hm
				on hm.hea_id = h.hea_id
			join darts.courtroom c 
				on c.ctr_id = h.ctr_id 
			left join darts.hearing_transcription_ae ht
				on ht.hea_id = h.hea_id
			where 
				ht is null
			and c.cth_id = managerUser.cth_id;
			
			if (transcriptionDetails is null) THEN
				raise notice 'Skipped as no data found';
				EXIT;
			end if;
			
			SELECT nextval('darts.tra_seq') into transcriptionId;	
			
			raise notice 'Creating Transcription with ID: % (hea_id: %, cas_id: % ctr_id: %, cth_id: %)', transcriptionId, transcriptionDetails.hea_id, transcriptionDetails.cas_id, transcriptionDetails.ctr_id, transcriptionDetails.cth_id;	


			-- Create Transcription
			INSERT INTO darts.transcription (tra_id, ctr_id, trt_id, transcription_object_id, start_ts, end_ts, created_ts, last_modified_ts, last_modified_by, version_label, created_by, tru_id, trs_id, hearing_date, is_manual_transcription, hide_request_from_requestor, chronicle_id, antecedent_id, is_deleted, deleted_by, deleted_ts, transcription_object_name, requested_by, is_current) 
			VALUES(
				transcriptionId,				--tra_id
				transcriptionDetails.ctr_id, 	--ctr_id
				trtId, 							--trt_id 
				NULL, 							--transcription_object_id
				NULL, 							--start_ts
				NULL, 							--end_ts
				'2024-07-03 09:49:00.802', 		--created_ts
				'2024-08-02 12:20:00.426', 		--last_modified_ts
				userId, 						--last_modified_by
				NULL, 							--version_label
				userId, 	 					--created_by
				truId,  						--tru_id 
				trsId,  						--trs_id 
				NULL,  							--hearing_date
				true,  							--is_manual_transcription
				false,  						--hide_request_from_requestor
				NULL,  							--chronicle_id
				NULL,  							--antecedent_id
				false,  						--is_deleted
				NULL,  							--deleted_by
				NULL, 							--deleted_ts
				NULL, 							--transcription_object_name
				NULL,  							--requested_by
				true 							--is_current
			);
			-- Associate case
			INSERT INTO darts.case_transcription_ae (cas_id, tra_id)
			 VALUES(transcriptionDetails.cas_id, transcriptionId);
   			-- Associate hearing
			INSERT INTO darts.hearing_transcription_ae (hea_id, tra_id) 
			VALUES(transcriptionDetails.hea_id, transcriptionId);
			-- Create transcriptionWorkflowEntities for creation
			INSERT INTO darts.transcription_workflow (trw_id, tra_id, trs_id, workflow_actor, workflow_ts) 
			VALUES(nextval('darts.trw_seq'), transcriptionId, trsId, userId, '2024-07-24 09:47:46.668');
			-- Create transcriptionWorkflowEntities for approval (trsId = 2 (AWAITING_AUTHORISATION))
			INSERT INTO darts.transcription_workflow (trw_id, tra_id, trs_id, workflow_actor, workflow_ts) 
			VALUES(nextval('darts.trw_seq'), transcriptionId, 2, userId, '2024-07-24 09:47:46.668');
		
			IF (shouldApprove) THEN
				raise notice 'Approving transcription %', transcriptionId;	
				INSERT INTO darts.transcription_workflow (trw_id, tra_id, trs_id, workflow_actor, workflow_ts) 
				VALUES(nextval('darts.trw_seq'), transcriptionId, 3, managerUser.usr_id, '2024-07-24 09:47:46.668');
			END IF;
		end loop;
    END LOOP;
end $$;

"@

# Database connection parameters
$postgresHost = "darts-api-test.postgres.database.azure.com"
$port = "5432" # Default is 5432
$database = "darts"
$user = "pgadmin"
$password = "oIYRDeLXDMLKahVUjP0D"

# Ensure PGPASSWORD environment variable is set
$env:PGPASSWORD = $password

# Full path to psql executable (update this to the actual path if needed)
$psqlPath = "C:\Program Files\PostgreSQL\16\bin\psql.exe"


Write-Host "Query executed and completed"