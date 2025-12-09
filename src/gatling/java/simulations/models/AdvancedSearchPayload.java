package simulations.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdvancedSearchPayload {

    @JsonProperty("case_number")
    private String caseNumber;

    @JsonProperty("courthouse_ids")
    private List<Integer> courthouseIds;

    @JsonProperty("courtroom")
    private String courtroom;

    @JsonProperty("judge_name")
    private String judgeName;

    @JsonProperty("defendant_name")
    private String defendantName;

    @JsonProperty("date_from")
    private String dateFrom;

    @JsonProperty("date_to")
    private String dateTo;

    @JsonProperty("event_text_contains")
    private String eventTextContains;
}
