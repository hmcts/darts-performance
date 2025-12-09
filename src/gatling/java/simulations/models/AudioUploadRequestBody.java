package simulations.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AudioUploadRequestBody {

    @JsonProperty("started_at")
    private String startedAt;

    @JsonProperty("ended_at")
    private String endedAt;

    @JsonProperty("channel")
    private Integer channel;

    @JsonProperty("total_channels")
    private Integer totalChannels;

    @JsonProperty("format")
    private String format;

    @JsonProperty("filename")
    private String filename;

    @JsonProperty("courthouse")
    private String courthouse;

    @JsonProperty("courtroom")
    private String courtroom;

    @JsonProperty("file_size")
    private Double fileSize;

    @JsonProperty("checksum")
    private String checksum;

    @JsonProperty("cases")
    private List<String> cases;
}
