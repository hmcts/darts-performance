var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "11",
        "ok": "2",
        "ko": "9"
    },
    "minResponseTime": {
        "total": "44",
        "ok": "262",
        "ko": "44"
    },
    "maxResponseTime": {
        "total": "393",
        "ok": "393",
        "ko": "198"
    },
    "meanResponseTime": {
        "total": "117",
        "ok": "328",
        "ko": "70"
    },
    "standardDeviation": {
        "total": "111",
        "ok": "66",
        "ko": "46"
    },
    "percentiles1": {
        "total": "56",
        "ok": "328",
        "ko": "51"
    },
    "percentiles2": {
        "total": "136",
        "ok": "360",
        "ko": "62"
    },
    "percentiles3": {
        "total": "328",
        "ok": "386",
        "ko": "148"
    },
    "percentiles4": {
        "total": "380",
        "ok": "392",
        "ko": "188"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 2,
    "percentage": 18
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
    "count": 9,
    "percentage": 82
},
    "meanNumberOfRequestsPerSecond": {
        "total": "1.571",
        "ok": "0.286",
        "ko": "1.286"
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
        "total": "393",
        "ok": "393",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "393",
        "ok": "393",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "393",
        "ok": "393",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "0",
        "ok": "0",
        "ko": "-"
    },
    "percentiles1": {
        "total": "393",
        "ok": "393",
        "ko": "-"
    },
    "percentiles2": {
        "total": "393",
        "ok": "393",
        "ko": "-"
    },
    "percentiles3": {
        "total": "393",
        "ok": "393",
        "ko": "-"
    },
    "percentiles4": {
        "total": "393",
        "ok": "393",
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
        "total": "0.143",
        "ok": "0.143",
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
        "total": "10",
        "ok": "1",
        "ko": "9"
    },
    "minResponseTime": {
        "total": "44",
        "ok": "262",
        "ko": "44"
    },
    "maxResponseTime": {
        "total": "262",
        "ok": "262",
        "ko": "198"
    },
    "meanResponseTime": {
        "total": "89",
        "ok": "262",
        "ko": "70"
    },
    "standardDeviation": {
        "total": "72",
        "ok": "0",
        "ko": "46"
    },
    "percentiles1": {
        "total": "54",
        "ok": "262",
        "ko": "51"
    },
    "percentiles2": {
        "total": "70",
        "ok": "262",
        "ko": "62"
    },
    "percentiles3": {
        "total": "233",
        "ok": "262",
        "ko": "148"
    },
    "percentiles4": {
        "total": "256",
        "ok": "262",
        "ko": "188"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 1,
    "percentage": 10
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
    "count": 9,
    "percentage": 90
},
    "meanNumberOfRequestsPerSecond": {
        "total": "1.429",
        "ok": "0.143",
        "ko": "1.286"
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
