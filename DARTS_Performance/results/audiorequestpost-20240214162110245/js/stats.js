var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "2",
        "ok": "1",
        "ko": "1"
    },
    "minResponseTime": {
        "total": "335",
        "ok": "752",
        "ko": "335"
    },
    "maxResponseTime": {
        "total": "752",
        "ok": "752",
        "ko": "335"
    },
    "meanResponseTime": {
        "total": "544",
        "ok": "752",
        "ko": "335"
    },
    "standardDeviation": {
        "total": "209",
        "ok": "0",
        "ko": "0"
    },
    "percentiles1": {
        "total": "544",
        "ok": "752",
        "ko": "335"
    },
    "percentiles2": {
        "total": "648",
        "ok": "752",
        "ko": "335"
    },
    "percentiles3": {
        "total": "731",
        "ok": "752",
        "ko": "335"
    },
    "percentiles4": {
        "total": "748",
        "ok": "752",
        "ko": "335"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 1,
    "percentage": 50
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 1,
    "percentage": 50
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.667",
        "ok": "0.333",
        "ko": "0.333"
    }
},
contents: {
"req_darts---api---t-9065a": {
        type: "REQUEST",
        name: "DARTS - Api - Token:GET",
path: "DARTS - Api - Token:GET",
pathFormatted: "req_darts---api---t-9065a",
stats: {
    "name": "DARTS - Api - Token:GET",
    "numberOfRequests": {
        "total": "1",
        "ok": "1",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "752",
        "ok": "752",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "752",
        "ok": "752",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "752",
        "ok": "752",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "0",
        "ok": "0",
        "ko": "-"
    },
    "percentiles1": {
        "total": "752",
        "ok": "752",
        "ko": "-"
    },
    "percentiles2": {
        "total": "752",
        "ok": "752",
        "ko": "-"
    },
    "percentiles3": {
        "total": "752",
        "ok": "752",
        "ko": "-"
    },
    "percentiles4": {
        "total": "752",
        "ok": "752",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 1,
    "percentage": 100
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.333",
        "ok": "0.333",
        "ko": "-"
    }
}
    },"req_darts---api---a-3f0a9": {
        type: "REQUEST",
        name: "DARTS - Api - AudioRequest:Post",
path: "DARTS - Api - AudioRequest:Post",
pathFormatted: "req_darts---api---a-3f0a9",
stats: {
    "name": "DARTS - Api - AudioRequest:Post",
    "numberOfRequests": {
        "total": "1",
        "ok": "0",
        "ko": "1"
    },
    "minResponseTime": {
        "total": "335",
        "ok": "-",
        "ko": "335"
    },
    "maxResponseTime": {
        "total": "335",
        "ok": "-",
        "ko": "335"
    },
    "meanResponseTime": {
        "total": "335",
        "ok": "-",
        "ko": "335"
    },
    "standardDeviation": {
        "total": "0",
        "ok": "-",
        "ko": "0"
    },
    "percentiles1": {
        "total": "335",
        "ok": "-",
        "ko": "335"
    },
    "percentiles2": {
        "total": "335",
        "ok": "-",
        "ko": "335"
    },
    "percentiles3": {
        "total": "335",
        "ok": "-",
        "ko": "335"
    },
    "percentiles4": {
        "total": "335",
        "ok": "-",
        "ko": "335"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 0,
    "percentage": 0
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 1,
    "percentage": 100
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.333",
        "ok": "-",
        "ko": "0.333"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
