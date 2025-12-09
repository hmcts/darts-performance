package simulations.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventRequestBody {

    @JsonProperty("event_id")
    private Object eventId;

    @JsonProperty("message_id")
    private Object messageId;

    @JsonProperty("event_text")
    private String eventText;

    @JsonProperty("type")
    private String type;

    @JsonProperty("sub_type")
    private String subType;

    @JsonProperty("courthouse")
    private String courthouse;

    @JsonProperty("courtroom")
    private String courtroom;

    @JsonProperty("case_numbers")
    private List<String> caseNumbers;

    @JsonProperty("date_time")
    private String dateTime;

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("end_time")
    private String endTime;

    @JsonProperty("retention_policy")
    private RetentionPolicy retentionPolicy;

    @JsonProperty("is_mid_tier")
    private Boolean isMidTier;
}
