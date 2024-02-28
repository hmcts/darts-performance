var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "10",
        "ok": "0",
        "ko": "10"
    },
    "minResponseTime": {
        "total": "188",
        "ok": "-",
        "ko": "188"
    },
    "maxResponseTime": {
        "total": "1340",
        "ok": "-",
        "ko": "1340"
    },
    "meanResponseTime": {
        "total": "379",
        "ok": "-",
        "ko": "379"
    },
    "standardDeviation": {
        "total": "323",
        "ok": "-",
        "ko": "323"
    },
    "percentiles1": {
        "total": "272",
        "ok": "-",
        "ko": "272"
    },
    "percentiles2": {
        "total": "283",
        "ok": "-",
        "ko": "283"
    },
    "percentiles3": {
        "total": "899",
        "ok": "-",
        "ko": "899"
    },
    "percentiles4": {
        "total": "1252",
        "ok": "-",
        "ko": "1252"
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
    "count": 10,
    "percentage": 100
},
    "meanNumberOfRequestsPerSecond": {
        "total": "2.5",
        "ok": "-",
        "ko": "2.5"
    }
},
contents: {
"group_register-with-u-32886": {
          type: "GROUP",
name: "Register With Username SOAP Request Group",
path: "Register With Username SOAP Request Group",
pathFormatted: "group_register-with-u-32886",
stats: {
    "name": "Register With Username SOAP Request Group",
    "numberOfRequests": {
        "total": "10",
        "ok": "0",
        "ko": "10"
    },
    "minResponseTime": {
        "total": "188",
        "ok": "-",
        "ko": "188"
    },
    "maxResponseTime": {
        "total": "1340",
        "ok": "-",
        "ko": "1340"
    },
    "meanResponseTime": {
        "total": "379",
        "ok": "-",
        "ko": "379"
    },
    "standardDeviation": {
        "total": "323",
        "ok": "-",
        "ko": "323"
    },
    "percentiles1": {
        "total": "272",
        "ok": "-",
        "ko": "272"
    },
    "percentiles2": {
        "total": "283",
        "ok": "-",
        "ko": "283"
    },
    "percentiles3": {
        "total": "899",
        "ok": "-",
        "ko": "899"
    },
    "percentiles4": {
        "total": "1252",
        "ok": "-",
        "ko": "1252"
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
    "count": 10,
    "percentage": 100
},
    "meanNumberOfRequestsPerSecond": {
        "total": "2.5",
        "ok": "-",
        "ko": "2.5"
    }
},
contents: {
"req_darts---gateway-38d41": {
        type: "REQUEST",
        name: "DARTS - GateWay - Soap - RegisterWithUsername",
path: "Register With Username SOAP Request Group / DARTS - GateWay - Soap - RegisterWithUsername",
pathFormatted: "req_register-with-u-818f0",
stats: {
    "name": "DARTS - GateWay - Soap - RegisterWithUsername",
    "numberOfRequests": {
        "total": "10",
        "ok": "0",
        "ko": "10"
    },
    "minResponseTime": {
        "total": "188",
        "ok": "-",
        "ko": "188"
    },
    "maxResponseTime": {
        "total": "1340",
        "ok": "-",
        "ko": "1340"
    },
    "meanResponseTime": {
        "total": "379",
        "ok": "-",
        "ko": "379"
    },
    "standardDeviation": {
        "total": "323",
        "ok": "-",
        "ko": "323"
    },
    "percentiles1": {
        "total": "272",
        "ok": "-",
        "ko": "272"
    },
    "percentiles2": {
        "total": "283",
        "ok": "-",
        "ko": "283"
    },
    "percentiles3": {
        "total": "899",
        "ok": "-",
        "ko": "899"
    },
    "percentiles4": {
        "total": "1252",
        "ok": "-",
        "ko": "1252"
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
    "count": 10,
    "percentage": 100
},
    "meanNumberOfRequestsPerSecond": {
        "total": "2.5",
        "ok": "-",
        "ko": "2.5"
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
