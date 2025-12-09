package simulations.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RetentionPolicy {

    @JsonProperty("case_retention_fixed_policy")
    private String caseRetentionFixedPolicy;

    @JsonProperty("case_total_sentence")
    private String caseTotalSentence;
}
