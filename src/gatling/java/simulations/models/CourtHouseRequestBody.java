package simulations.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourtHouseRequestBody {

    @JsonProperty("courthouse_name")
    private String courthouseName;

    @JsonProperty("display_name")
    private String displayName;
}
