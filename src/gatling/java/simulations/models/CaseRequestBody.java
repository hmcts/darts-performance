package simulations.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.ALWAYS)
public class CaseRequestBody {

    @JsonProperty("courthouse")
    private String courthouse;

    @JsonProperty("case_number")
    private String caseNumber;

    @JsonProperty("case_type")
    private String caseType;

    @JsonProperty("defendants")
    private List<String> defendants;

    @JsonProperty("judges")
    private List<String> judges;

    @JsonProperty("prosecutors")
    private List<String> prosecutors;

    @JsonProperty("defenders")
    private List<String> defenders;
}
