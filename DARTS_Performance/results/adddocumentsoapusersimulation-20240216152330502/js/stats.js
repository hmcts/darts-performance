var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "9",
        "ok": "0",
        "ko": "9"
    },
    "minResponseTime": {
        "total": "26",
        "ok": "-",
        "ko": "26"
    },
    "maxResponseTime": {
        "total": "83",
        "ok": "-",
        "ko": "83"
    },
    "meanResponseTime": {
        "total": "38",
        "ok": "-",
        "ko": "38"
    },
    "standardDeviation": {
        "total": "18",
        "ok": "-",
        "ko": "18"
    },
    "percentiles1": {
        "total": "31",
        "ok": "-",
        "ko": "31"
    },
    "percentiles2": {
        "total": "34",
        "ok": "-",
        "ko": "34"
    },
    "percentiles3": {
        "total": "71",
        "ok": "-",
        "ko": "71"
    },
    "percentiles4": {
        "total": "81",
        "ok": "-",
        "ko": "81"
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
    "count": 9,
    "percentage": 100
},
    "meanNumberOfRequestsPerSecond": {
        "total": "9",
        "ok": "-",
        "ko": "9"
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
        "total": "9",
        "ok": "0",
        "ko": "9"
    },
    "minResponseTime": {
        "total": "26",
        "ok": "-",
        "ko": "26"
    },
    "maxResponseTime": {
        "total": "83",
        "ok": "-",
        "ko": "83"
    },
    "meanResponseTime": {
        "total": "38",
        "ok": "-",
        "ko": "38"
    },
    "standardDeviation": {
        "total": "18",
        "ok": "-",
        "ko": "18"
    },
    "percentiles1": {
        "total": "31",
        "ok": "-",
        "ko": "31"
    },
    "percentiles2": {
        "total": "34",
        "ok": "-",
        "ko": "34"
    },
    "percentiles3": {
        "total": "71",
        "ok": "-",
        "ko": "71"
    },
    "percentiles4": {
        "total": "81",
        "ok": "-",
        "ko": "81"
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
    "count": 9,
    "percentage": 100
},
    "meanNumberOfRequestsPerSecond": {
        "total": "9",
        "ok": "-",
        "ko": "9"
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
