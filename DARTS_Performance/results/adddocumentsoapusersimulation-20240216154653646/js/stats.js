var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "10",
        "ok": "9",
        "ko": "1"
    },
    "minResponseTime": {
        "total": "74",
        "ok": "407",
        "ko": "74"
    },
    "maxResponseTime": {
        "total": "1047",
        "ok": "1047",
        "ko": "74"
    },
    "meanResponseTime": {
        "total": "506",
        "ok": "554",
        "ko": "74"
    },
    "standardDeviation": {
        "total": "232",
        "ok": "191",
        "ko": "0"
    },
    "percentiles1": {
        "total": "459",
        "ok": "459",
        "ko": "74"
    },
    "percentiles2": {
        "total": "593",
        "ok": "607",
        "ko": "74"
    },
    "percentiles3": {
        "total": "858",
        "ok": "879",
        "ko": "74"
    },
    "percentiles4": {
        "total": "1009",
        "ok": "1013",
        "ko": "74"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 8,
    "percentage": 80
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 1,
    "percentage": 10
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
    "percentage": 10
},
    "meanNumberOfRequestsPerSecond": {
        "total": "1.667",
        "ok": "1.5",
        "ko": "0.167"
    }
},
contents: {
"req_darts---gateway-9a38d": {
        type: "REQUEST",
        name: "DARTS - GateWay - Soap - AddDocument - User",
path: "DARTS - GateWay - Soap - AddDocument - User",
pathFormatted: "req_darts---gateway-9a38d",
stats: {
    "name": "DARTS - GateWay - Soap - AddDocument - User",
    "numberOfRequests": {
        "total": "10",
        "ok": "9",
        "ko": "1"
    },
    "minResponseTime": {
        "total": "74",
        "ok": "407",
        "ko": "74"
    },
    "maxResponseTime": {
        "total": "1047",
        "ok": "1047",
        "ko": "74"
    },
    "meanResponseTime": {
        "total": "506",
        "ok": "554",
        "ko": "74"
    },
    "standardDeviation": {
        "total": "232",
        "ok": "191",
        "ko": "0"
    },
    "percentiles1": {
        "total": "459",
        "ok": "459",
        "ko": "74"
    },
    "percentiles2": {
        "total": "593",
        "ok": "607",
        "ko": "74"
    },
    "percentiles3": {
        "total": "858",
        "ok": "879",
        "ko": "74"
    },
    "percentiles4": {
        "total": "1009",
        "ok": "1013",
        "ko": "74"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 8,
    "percentage": 80
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 1,
    "percentage": 10
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
    "percentage": 10
},
    "meanNumberOfRequestsPerSecond": {
        "total": "1.667",
        "ok": "1.5",
        "ko": "0.167"
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
