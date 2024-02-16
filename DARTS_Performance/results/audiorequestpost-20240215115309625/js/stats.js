var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "2",
        "ok": "2",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "714",
        "ok": "714",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "5168",
        "ok": "5168",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "2941",
        "ok": "2941",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "2227",
        "ok": "2227",
        "ko": "-"
    },
    "percentiles1": {
        "total": "2941",
        "ok": "2941",
        "ko": "-"
    },
    "percentiles2": {
        "total": "4055",
        "ok": "4055",
        "ko": "-"
    },
    "percentiles3": {
        "total": "4945",
        "ok": "4945",
        "ko": "-"
    },
    "percentiles4": {
        "total": "5123",
        "ok": "5123",
        "ko": "-"
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
    "count": 1,
    "percentage": 50
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.182",
        "ok": "0.182",
        "ko": "-"
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
        "total": "5168",
        "ok": "5168",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "5168",
        "ok": "5168",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "5168",
        "ok": "5168",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "0",
        "ok": "0",
        "ko": "-"
    },
    "percentiles1": {
        "total": "5168",
        "ok": "5168",
        "ko": "-"
    },
    "percentiles2": {
        "total": "5168",
        "ok": "5168",
        "ko": "-"
    },
    "percentiles3": {
        "total": "5168",
        "ok": "5168",
        "ko": "-"
    },
    "percentiles4": {
        "total": "5168",
        "ok": "5168",
        "ko": "-"
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
    "count": 1,
    "percentage": 100
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.091",
        "ok": "0.091",
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
        "ok": "1",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "714",
        "ok": "714",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "714",
        "ok": "714",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "714",
        "ok": "714",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "0",
        "ok": "0",
        "ko": "-"
    },
    "percentiles1": {
        "total": "714",
        "ok": "714",
        "ko": "-"
    },
    "percentiles2": {
        "total": "714",
        "ok": "714",
        "ko": "-"
    },
    "percentiles3": {
        "total": "714",
        "ok": "714",
        "ko": "-"
    },
    "percentiles4": {
        "total": "714",
        "ok": "714",
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
        "total": "0.091",
        "ok": "0.091",
        "ko": "-"
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
