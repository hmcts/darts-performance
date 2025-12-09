package simulations.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TranscriptionRequestBody {

    @JsonProperty("hearing_id")
    private String hearingId;

    @JsonProperty("case_id")
    private String caseId;

    @JsonProperty("transcription_urgency_id")
    private String transcriptionUrgencyId;

    @JsonProperty("transcription_type_id")
    private String transcriptionTypeId;

    @JsonProperty("comment")
    private String comment;
}

