var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "20",
        "ok": "10",
        "ko": "10"
    },
    "minResponseTime": {
        "total": "232",
        "ok": "549",
        "ko": "232"
    },
    "maxResponseTime": {
        "total": "873",
        "ok": "873",
        "ko": "402"
    },
    "meanResponseTime": {
        "total": "493",
        "ok": "690",
        "ko": "297"
    },
    "standardDeviation": {
        "total": "211",
        "ok": "87",
        "ko": "63"
    },
    "percentiles1": {
        "total": "476",
        "ok": "696",
        "ko": "268"
    },
    "percentiles2": {
        "total": "695",
        "ok": "721",
        "ko": "350"
    },
    "percentiles3": {
        "total": "777",
        "ok": "828",
        "ko": "393"
    },
    "percentiles4": {
        "total": "854",
        "ok": "864",
        "ko": "400"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 9,
    "percentage": 45
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 1,
    "percentage": 5
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
    "percentage": 50
},
    "meanNumberOfRequestsPerSecond": {
        "total": "1.818",
        "ok": "0.909",
        "ko": "0.909"
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
        "total": "10",
        "ok": "10",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "549",
        "ok": "549",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "873",
        "ok": "873",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "690",
        "ok": "690",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "87",
        "ok": "87",
        "ko": "-"
    },
    "percentiles1": {
        "total": "696",
        "ok": "696",
        "ko": "-"
    },
    "percentiles2": {
        "total": "721",
        "ok": "721",
        "ko": "-"
    },
    "percentiles3": {
        "total": "828",
        "ok": "828",
        "ko": "-"
    },
    "percentiles4": {
        "total": "864",
        "ok": "864",
        "ko": "-"
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
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.909",
        "ok": "0.909",
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
        "ok": "0",
        "ko": "10"
    },
    "minResponseTime": {
        "total": "232",
        "ok": "-",
        "ko": "232"
    },
    "maxResponseTime": {
        "total": "402",
        "ok": "-",
        "ko": "402"
    },
    "meanResponseTime": {
        "total": "297",
        "ok": "-",
        "ko": "297"
    },
    "standardDeviation": {
        "total": "63",
        "ok": "-",
        "ko": "63"
    },
    "percentiles1": {
        "total": "268",
        "ok": "-",
        "ko": "268"
    },
    "percentiles2": {
        "total": "350",
        "ok": "-",
        "ko": "350"
    },
    "percentiles3": {
        "total": "393",
        "ok": "-",
        "ko": "393"
    },
    "percentiles4": {
        "total": "400",
        "ok": "-",
        "ko": "400"
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
        "total": "0.909",
        "ok": "-",
        "ko": "0.909"
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
