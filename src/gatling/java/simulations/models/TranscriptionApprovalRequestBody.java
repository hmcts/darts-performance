package simulations.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.ALWAYS)
public class TranscriptionApprovalRequestBody {

    @JsonProperty("transcription_status_id")
    private String transcriptionStatusId;

    @JsonProperty("workflow_comment")
    private String workflowComment;
}
