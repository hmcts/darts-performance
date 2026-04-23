package simulations.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.ALWAYS)
public class PerfTraceRequest {

    @JsonProperty("navigation")
    private PerfTraceNavigation navigation;

    @JsonProperty("timing")
    private PerfTraceTiming timing;

    @JsonProperty("entries")
    private List<PerfTraceEntry> entries;

    @JsonProperty("connection")
    private PerfTraceConnection connection;

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public static class PerfTraceNavigation {
        @JsonProperty("type")
        private Integer type;

        @JsonProperty("redirectCount")
        private Integer redirectCount;
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public static class PerfTraceTiming {
        @JsonProperty("connectStart")
        private Double connectStart;

        @JsonProperty("navigationStart")
        private Double navigationStart;

        @JsonProperty("secureConnectionStart")
        private Double secureConnectionStart;

        @JsonProperty("fetchStart")
        private Double fetchStart;

        @JsonProperty("domContentLoadedEventStart")
        private Double domContentLoadedEventStart;

        @JsonProperty("responseStart")
        private Double responseStart;

        @JsonProperty("domInteractive")
        private Double domInteractive;

        @JsonProperty("domainLookupEnd")
        private Double domainLookupEnd;

        @JsonProperty("responseEnd")
        private Double responseEnd;

        @JsonProperty("redirectStart")
        private Double redirectStart;

        @JsonProperty("requestStart")
        private Double requestStart;

        @JsonProperty("unloadEventEnd")
        private Double unloadEventEnd;

        @JsonProperty("unloadEventStart")
        private Double unloadEventStart;

        @JsonProperty("domLoading")
        private Double domLoading;

        @JsonProperty("domComplete")
        private Double domComplete;

        @JsonProperty("domainLookupStart")
        private Double domainLookupStart;

        @JsonProperty("loadEventStart")
        private Double loadEventStart;

        @JsonProperty("domContentLoadedEventEnd")
        private Double domContentLoadedEventEnd;

        @JsonProperty("loadEventEnd")
        private Double loadEventEnd;

        @JsonProperty("redirectEnd")
        private Double redirectEnd;

        @JsonProperty("connectEnd")
        private Double connectEnd;
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public static class PerfTraceEntry {
        @JsonProperty("name")
        private String name;

        @JsonProperty("entryType")
        private String entryType;

        @JsonProperty("startTime")
        private Double startTime;

        @JsonProperty("duration")
        private Double duration;

        @JsonProperty("initiatorType")
        private String initiatorType;

        @JsonProperty("deliveryType")
        private String deliveryType;

        @JsonProperty("nextHopProtocol")
        private String nextHopProtocol;

        @JsonProperty("renderBlockingStatus")
        private String renderBlockingStatus;

        @JsonProperty("workerStart")
        private Double workerStart;

        @JsonProperty("redirectStart")
        private Double redirectStart;

        @JsonProperty("redirectEnd")
        private Double redirectEnd;

        @JsonProperty("fetchStart")
        private Double fetchStart;

        @JsonProperty("domainLookupStart")
        private Double domainLookupStart;

        @JsonProperty("domainLookupEnd")
        private Double domainLookupEnd;

        @JsonProperty("connectStart")
        private Double connectStart;

        @JsonProperty("secureConnectionStart")
        private Double secureConnectionStart;

        @JsonProperty("connectEnd")
        private Double connectEnd;

        @JsonProperty("requestStart")
        private Double requestStart;

        @JsonProperty("responseStart")
        private Double responseStart;

        @JsonProperty("firstInterimResponseStart")
        private Double firstInterimResponseStart;

        @JsonProperty("responseEnd")
        private Double responseEnd;

        @JsonProperty("transferSize")
        private Integer transferSize;

        @JsonProperty("encodedBodySize")
        private Integer encodedBodySize;

        @JsonProperty("decodedBodySize")
        private Integer decodedBodySize;

        @JsonProperty("responseStatus")
        private Integer responseStatus;

        @JsonProperty("serverTiming")
        private List<PerfTraceServerTiming> serverTiming;

        @JsonProperty("unloadEventStart")
        private Double unloadEventStart;

        @JsonProperty("unloadEventEnd")
        private Double unloadEventEnd;

        @JsonProperty("domInteractive")
        private Double domInteractive;

        @JsonProperty("domContentLoadedEventStart")
        private Double domContentLoadedEventStart;

        @JsonProperty("domContentLoadedEventEnd")
        private Double domContentLoadedEventEnd;

        @JsonProperty("domComplete")
        private Double domComplete;

        @JsonProperty("loadEventStart")
        private Double loadEventStart;

        @JsonProperty("loadEventEnd")
        private Double loadEventEnd;

        @JsonProperty("type")
        private String type;

        @JsonProperty("redirectCount")
        private Integer redirectCount;

        @JsonProperty("activationStart")
        private Integer activationStart;

        @JsonProperty("criticalCHRestart")
        private Integer criticalCHRestart;
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public static class PerfTraceServerTiming {
        @JsonProperty("name")
        private String name;

        @JsonProperty("duration")
        private Double duration;

        @JsonProperty("description")
        private String description;
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public static class PerfTraceConnection {
        @JsonProperty("onchange")
        private Object onchange;

        @JsonProperty("effectiveType")
        private String effectiveType;

        @JsonProperty("rtt")
        private Double rtt;

        @JsonProperty("downlink")
        private Double downlink;

        @JsonProperty("saveData")
        private Boolean saveData;
    }
}
