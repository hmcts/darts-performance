package simulations.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.ALWAYS)
public class TranscriptionsRequestBody {

    @JsonProperty("case_id")
    private String caseId;

    @JsonProperty("hearing_id")
    private String hearingId;

    @JsonProperty("transcription_type_id")
    private Integer transcriptionTypeId;

    @JsonProperty("transcription_urgency_id")
    private Integer transcriptionUrgencyId;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("start_date_time")
    private String startDateTime;

    @JsonProperty("end_date_time")
    private String endDateTime;
}
