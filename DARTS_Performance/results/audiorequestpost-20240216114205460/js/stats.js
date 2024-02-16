var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "10",
        "ok": "8",
        "ko": "2"
    },
    "minResponseTime": {
        "total": "57",
        "ok": "70",
        "ko": "57"
    },
    "maxResponseTime": {
        "total": "1185",
        "ok": "1185",
        "ko": "88"
    },
    "meanResponseTime": {
        "total": "233",
        "ok": "273",
        "ko": "73"
    },
    "standardDeviation": {
        "total": "324",
        "ok": "350",
        "ko": "16"
    },
    "percentiles1": {
        "total": "101",
        "ok": "144",
        "ko": "73"
    },
    "percentiles2": {
        "total": "204",
        "ok": "221",
        "ko": "80"
    },
    "percentiles3": {
        "total": "766",
        "ok": "859",
        "ko": "86"
    },
    "percentiles4": {
        "total": "1101",
        "ok": "1120",
        "ko": "88"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 7,
    "percentage": 70
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
    "count": 2,
    "percentage": 20
},
    "meanNumberOfRequestsPerSecond": {
        "total": "1.25",
        "ok": "1",
        "ko": "0.25"
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
        "total": "1185",
        "ok": "1185",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "1185",
        "ok": "1185",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "1185",
        "ok": "1185",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "0",
        "ok": "0",
        "ko": "-"
    },
    "percentiles1": {
        "total": "1185",
        "ok": "1185",
        "ko": "-"
    },
    "percentiles2": {
        "total": "1185",
        "ok": "1185",
        "ko": "-"
    },
    "percentiles3": {
        "total": "1185",
        "ok": "1185",
        "ko": "-"
    },
    "percentiles4": {
        "total": "1185",
        "ok": "1185",
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
    "count": 1,
    "percentage": 100
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
        "total": "0.125",
        "ok": "0.125",
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
        "total": "9",
        "ok": "7",
        "ko": "2"
    },
    "minResponseTime": {
        "total": "57",
        "ok": "70",
        "ko": "57"
    },
    "maxResponseTime": {
        "total": "254",
        "ok": "254",
        "ko": "88"
    },
    "meanResponseTime": {
        "total": "127",
        "ok": "143",
        "ko": "73"
    },
    "standardDeviation": {
        "total": "67",
        "ok": "67",
        "ko": "16"
    },
    "percentiles1": {
        "total": "98",
        "ok": "103",
        "ko": "73"
    },
    "percentiles2": {
        "total": "185",
        "ok": "198",
        "ko": "80"
    },
    "percentiles3": {
        "total": "236",
        "ok": "241",
        "ko": "86"
    },
    "percentiles4": {
        "total": "250",
        "ok": "251",
        "ko": "88"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 7,
    "percentage": 78
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
    "percentage": 22
},
    "meanNumberOfRequestsPerSecond": {
        "total": "1.125",
        "ok": "0.875",
        "ko": "0.25"
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
