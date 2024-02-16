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
        "total": "114",
        "ok": "393",
        "ko": "114"
    },
    "maxResponseTime": {
        "total": "637",
        "ok": "637",
        "ko": "114"
    },
    "meanResponseTime": {
        "total": "454",
        "ok": "492",
        "ko": "114"
    },
    "standardDeviation": {
        "total": "134",
        "ok": "76",
        "ko": "0"
    },
    "percentiles1": {
        "total": "448",
        "ok": "451",
        "ko": "114"
    },
    "percentiles2": {
        "total": "531",
        "ok": "531",
        "ko": "114"
    },
    "percentiles3": {
        "total": "610",
        "ok": "613",
        "ko": "114"
    },
    "percentiles4": {
        "total": "632",
        "ok": "632",
        "ko": "114"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 9,
    "percentage": 90
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
    "percentage": 10
},
    "meanNumberOfRequestsPerSecond": {
        "total": "2",
        "ok": "1.8",
        "ko": "0.2"
    }
},
contents: {
"group_adddocument-soa-1a38a": {
          type: "GROUP",
name: "AddDocument SOAP Request Group",
path: "AddDocument SOAP Request Group",
pathFormatted: "group_adddocument-soa-1a38a",
stats: {
    "name": "AddDocument SOAP Request Group",
    "numberOfRequests": {
        "total": "10",
        "ok": "9",
        "ko": "1"
    },
    "minResponseTime": {
        "total": "114",
        "ok": "393",
        "ko": "114"
    },
    "maxResponseTime": {
        "total": "637",
        "ok": "637",
        "ko": "114"
    },
    "meanResponseTime": {
        "total": "454",
        "ok": "492",
        "ko": "114"
    },
    "standardDeviation": {
        "total": "134",
        "ok": "76",
        "ko": "0"
    },
    "percentiles1": {
        "total": "448",
        "ok": "451",
        "ko": "114"
    },
    "percentiles2": {
        "total": "531",
        "ok": "531",
        "ko": "114"
    },
    "percentiles3": {
        "total": "610",
        "ok": "613",
        "ko": "114"
    },
    "percentiles4": {
        "total": "632",
        "ok": "632",
        "ko": "114"
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
        "total": "2",
        "ok": "1.8",
        "ko": "0.2"
    }
},
contents: {
"req_darts---gateway-9a38d": {
        type: "REQUEST",
        name: "DARTS - GateWay - Soap - AddDocument - User",
path: "AddDocument SOAP Request Group / DARTS - GateWay - Soap - AddDocument - User",
pathFormatted: "req_adddocument-soa-3078c",
stats: {
    "name": "DARTS - GateWay - Soap - AddDocument - User",
    "numberOfRequests": {
        "total": "10",
        "ok": "9",
        "ko": "1"
    },
    "minResponseTime": {
        "total": "114",
        "ok": "393",
        "ko": "114"
    },
    "maxResponseTime": {
        "total": "637",
        "ok": "637",
        "ko": "114"
    },
    "meanResponseTime": {
        "total": "454",
        "ok": "492",
        "ko": "114"
    },
    "standardDeviation": {
        "total": "134",
        "ok": "76",
        "ko": "0"
    },
    "percentiles1": {
        "total": "448",
        "ok": "451",
        "ko": "114"
    },
    "percentiles2": {
        "total": "531",
        "ok": "531",
        "ko": "114"
    },
    "percentiles3": {
        "total": "610",
        "ok": "613",
        "ko": "114"
    },
    "percentiles4": {
        "total": "632",
        "ok": "632",
        "ko": "114"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 9,
    "percentage": 90
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
    "percentage": 10
},
    "meanNumberOfRequestsPerSecond": {
        "total": "2",
        "ok": "1.8",
        "ko": "0.2"
    }
}
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
