var lineWidth = [""];

var lineChartOption = {
    title: {
        text: '',
        left: 'right'
    },
    tooltip: {
        trigger: 'axis',

    },
    legend: {
        data: ['实际用气量', '预测用气量', '最低气温', '最高气温', '预测最低气温', '预测最高气温'],
    },
    grid: {
        left: '5%',
        right: '7%',
        bottom: '6%',
    },

    xAxis: {
        type: 'category',
        boundaryGap: false,
    },
    yAxis: [

        {
            type: 'value',
            name: '温度',
            // interval: 2,
            axisLabel: {
                formatter: '{value} °C',
                onZero: false
            }
        },
        {
            type: 'value',
            name: '用气量',
            axisLabel: {
                formatter: '{value} 万方'
            }
        }
    ],
    series: [
        {
            name: '实际用气量',
            type: 'line',
            yAxisIndex: 1,
            trigger: 'item',
            symbol: "none"
        },
        {
            name: '预测用气量',
            type: 'line',
            yAxisIndex: 1,
            trigger: 'item',
            symbol: "none"
        },
        {
            name: '最高气温',
            type: 'line',
            stack: '总量',
            trigger: 'item',
            symbol: "none",
        },
        {
            name: '最低气温',
            type: 'line',
            trigger: 'item',
            symbol: "none",
        },
        {
            name: '预测最低气温',
            type: 'line',
            trigger: 'item',
            symbol: "none",
        },
        {
            name: '预测最高气温',
            type: 'line',
            trigger: 'item',
            symbol: "none",
        }
    ]
};


/**
 *
 * @param param
 */
function queryForPage(param) {
    chart.showLoading(
        {
            text: '数据获取中',
            effect: 'ring'
        }
    );
    $.ajax({
        url: "/drawCharts",
        contentType: "application/json; charset=utf-8",
        async: true,
        type: "post",
        success: success,
        dataType: 'json',
        data: JSON.stringify(param)
    });
}


function initEvent() {
    // echarts 初始化
    if ($("#line-chart")[0]) {
        chart = echarts.init($("#line-chart")[0]);
        $("#line-chart").resize(function () {
            chart.resize();
        });
    }
    // 时间旋转框初始化
    if ($(".date-picker")) {
        $("#beginDate").datepicker({
            autoclose: true,
            format: "yyyy-mm-dd",
            language: "zh-CN",
            endDate: new Date(),
            maxViewMode: 2,
        }).on('changeDate', function (e) {
            var startTime = e.date;
            $('#endDate').datepicker('setStartDate', startTime);
        });
        // var date = new Date();
        $("#endDate").datepicker({
            autoclose: true,
            format: "yyyy-mm-dd",
            language: "zh-CN",
            // endDate: date,
            todayHighlight: true,
            maxViewMode: 2
        });
        var date = new Date();
        $("#beginDate").datepicker("setDate", new Date(date.getTime() - 1000 * 60 * 60 * 24 * 32));
        $("#endDate").datepicker("setDate", new Date(date.getTime() - 1000 * 60 * 60 * 24));

    }

    // 绑定点查询按钮击事件
    if ($("#query")) {
        $("#query").unbind("click").bind("click", function () {
            var city = $("#area").val();
            var businessType = $("#business-type").val();
            var username = $("#user-name").val();
            var beginDate = $("#beginDate").val();
            var endDate = $("#endDate").val();
            if (!beginDate || !endDate) {

            }
            var param = {
                'city': city,
                'beginDate': new Date(beginDate),
                'endDate': new Date(endDate),
                'username': username,
                'businessType': businessType
            };
            queryForPage(param);
        });
    }
    initSelect();
}

function initSelect() {
    var param = {};
    $('#area').bind("change", function () {
        $('#user-name').val('');
    })
    $('#area').selectpicker();

    // $('#company').selectpicker();
    post("/areaList", param, function (res) {
        if (res && res.success && res.data) {
            var areas = res.data.areas;
            var ele = $("#area");
            var option;
            for (var i = 0; i < areas.length; i++) {
                var area = areas[i];
                option = "<option value='" + area + "'>" + area + "</option>";
                ele.append(option);
            }
            $('#area').selectpicker('refresh');
        }
    });


    $('#user-name').typeahead({
        source: function (query, process) {
            var area = $('#area').val();
            var param = {
                'words': query,
                'area': area
            };
            post("/getNameListByAreaAndName", param, function (res) {
                debugger
                if (res && res.data && res.data.list) {
                    process(res.data.list);
                }
            });
        },
        items: 10,
        // fitToElement:true
    });
}


/**
 *
 * @param res
 */
function success(res) {
    var usname = res.data.name;
    var city = res.data.area;
    var businessType = res.data.businessType;

    if ($("#title-before")) {
        var titleBefore = "";
        if (usname) {
            titleBefore += usname;
            if (businessType) {
                titleBefore += "(" + businessType + "行业)"
            }
        } else if (city) {
            titleBefore += city + "  ";
        }
        $("#title-before").html(titleBefore);
    }


    var minTemp = res.data.y2Data;
    var maxTemp = res.data.y1Data;
    var userTemp = res.data.y3Data;
    var xData = res.data.xData;
    var title = res.data.title;


    if (title) {
        lineChartOption.title.text = "供气单位:" + title;
    } else {
        lineChartOption.title.text = "供气单位:";
    }

    lineChartOption.xAxis.data = xData;
    lineChartOption.series[0].data = userTemp;
    lineChartOption.series[1].data = res.data.y6Data;
    lineChartOption.series[2].data = maxTemp;
    lineChartOption.series[3].data = minTemp;
    lineChartOption.series[4].data = res.data.y5Data;
    lineChartOption.series[5].data = res.data.y4Data;


    chart.hideLoading();
    chart.setOption(lineChartOption);

}