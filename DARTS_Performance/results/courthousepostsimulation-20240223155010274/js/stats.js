var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "2",
        "ok": "0",
        "ko": "2"
    },
    "minResponseTime": {
        "total": "151",
        "ok": "-",
        "ko": "151"
    },
    "maxResponseTime": {
        "total": "247",
        "ok": "-",
        "ko": "247"
    },
    "meanResponseTime": {
        "total": "199",
        "ok": "-",
        "ko": "199"
    },
    "standardDeviation": {
        "total": "48",
        "ok": "-",
        "ko": "48"
    },
    "percentiles1": {
        "total": "199",
        "ok": "-",
        "ko": "199"
    },
    "percentiles2": {
        "total": "223",
        "ok": "-",
        "ko": "223"
    },
    "percentiles3": {
        "total": "242",
        "ok": "-",
        "ko": "242"
    },
    "percentiles4": {
        "total": "246",
        "ok": "-",
        "ko": "246"
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
    "count": 2,
    "percentage": 100
},
    "meanNumberOfRequestsPerSecond": {
        "total": "2",
        "ok": "-",
        "ko": "2"
    }
},
contents: {
"group_courthouse-api--33678": {
          type: "GROUP",
name: "Courthouse Api Request Group",
path: "Courthouse Api Request Group",
pathFormatted: "group_courthouse-api--33678",
stats: {
    "name": "Courthouse Api Request Group",
    "numberOfRequests": {
        "total": "2",
        "ok": "0",
        "ko": "2"
    },
    "minResponseTime": {
        "total": "151",
        "ok": "-",
        "ko": "151"
    },
    "maxResponseTime": {
        "total": "247",
        "ok": "-",
        "ko": "247"
    },
    "meanResponseTime": {
        "total": "199",
        "ok": "-",
        "ko": "199"
    },
    "standardDeviation": {
        "total": "48",
        "ok": "-",
        "ko": "48"
    },
    "percentiles1": {
        "total": "199",
        "ok": "-",
        "ko": "199"
    },
    "percentiles2": {
        "total": "223",
        "ok": "-",
        "ko": "223"
    },
    "percentiles3": {
        "total": "242",
        "ok": "-",
        "ko": "242"
    },
    "percentiles4": {
        "total": "246",
        "ok": "-",
        "ko": "246"
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
    "count": 2,
    "percentage": 100
},
    "meanNumberOfRequestsPerSecond": {
        "total": "2",
        "ok": "-",
        "ko": "2"
    }
},
contents: {
"req_darts---api---c-18cbf": {
        type: "REQUEST",
        name: "DARTS - Api - CourtHouse:Post",
path: "Courthouse Api Request Group / DARTS - Api - CourtHouse:Post",
pathFormatted: "req_courthouse-api--b3a65",
stats: {
    "name": "DARTS - Api - CourtHouse:Post",
    "numberOfRequests": {
        "total": "2",
        "ok": "0",
        "ko": "2"
    },
    "minResponseTime": {
        "total": "151",
        "ok": "-",
        "ko": "151"
    },
    "maxResponseTime": {
        "total": "247",
        "ok": "-",
        "ko": "247"
    },
    "meanResponseTime": {
        "total": "199",
        "ok": "-",
        "ko": "199"
    },
    "standardDeviation": {
        "total": "48",
        "ok": "-",
        "ko": "48"
    },
    "percentiles1": {
        "total": "199",
        "ok": "-",
        "ko": "199"
    },
    "percentiles2": {
        "total": "223",
        "ok": "-",
        "ko": "223"
    },
    "percentiles3": {
        "total": "242",
        "ok": "-",
        "ko": "242"
    },
    "percentiles4": {
        "total": "246",
        "ok": "-",
        "ko": "246"
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
    "count": 2,
    "percentage": 100
},
    "meanNumberOfRequestsPerSecond": {
        "total": "2",
        "ok": "-",
        "ko": "2"
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
