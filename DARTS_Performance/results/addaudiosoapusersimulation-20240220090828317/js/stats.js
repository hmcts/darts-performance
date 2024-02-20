var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "1",
        "ok": "0",
        "ko": "1"
    },
    "minResponseTime": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "maxResponseTime": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "meanResponseTime": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "standardDeviation": {
        "total": "0",
        "ok": "-",
        "ko": "0"
    },
    "percentiles1": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "percentiles2": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "percentiles3": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "percentiles4": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
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
        "total": "0.5",
        "ok": "-",
        "ko": "0.5"
    }
},
contents: {
"group_addaudio-soap-r-530b8": {
          type: "GROUP",
name: "AddAudio SOAP Request Group",
path: "AddAudio SOAP Request Group",
pathFormatted: "group_addaudio-soap-r-530b8",
stats: {
    "name": "AddAudio SOAP Request Group",
    "numberOfRequests": {
        "total": "1",
        "ok": "0",
        "ko": "1"
    },
    "minResponseTime": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "maxResponseTime": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "meanResponseTime": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "standardDeviation": {
        "total": "0",
        "ok": "-",
        "ko": "0"
    },
    "percentiles1": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "percentiles2": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "percentiles3": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "percentiles4": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
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
        "total": "0.5",
        "ok": "-",
        "ko": "0.5"
    }
},
contents: {
"req_darts---gateway-39ae6": {
        type: "REQUEST",
        name: "DARTS - GateWay - Soap - AddAudio - User",
path: "AddAudio SOAP Request Group / DARTS - GateWay - Soap - AddAudio - User",
pathFormatted: "req_addaudio-soap-r-0172e",
stats: {
    "name": "DARTS - GateWay - Soap - AddAudio - User",
    "numberOfRequests": {
        "total": "1",
        "ok": "0",
        "ko": "1"
    },
    "minResponseTime": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "maxResponseTime": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "meanResponseTime": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "standardDeviation": {
        "total": "0",
        "ok": "-",
        "ko": "0"
    },
    "percentiles1": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "percentiles2": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "percentiles3": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
    },
    "percentiles4": {
        "total": "1196",
        "ok": "-",
        "ko": "1196"
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
        "total": "0.5",
        "ok": "-",
        "ko": "0.5"
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
