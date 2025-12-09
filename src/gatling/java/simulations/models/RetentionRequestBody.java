package simulations.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RetentionRequestBody {

    @JsonProperty("case_id")
    private String caseId;

    @JsonProperty("is_permanent_retention")
    private Boolean isPermanentRetention;

    @JsonProperty("comments")
    private String comments;
}
