$(function () {
    init();

});

function modelAnalysis() {
    var param = {};
    var values = [];
    var modelIndex = [];
    if ($("#customModelIndex")[0].checked) {
        $("#tableBody").find("tr").each(function (i, e) {
            e = $(e);
            var name = $(e.find("td")[0]).html();
            var value = $($(e.find("td")[1]).find("input")).val();
            values.push(Number(value));
            modelIndex.push(name);
        });
        values.push(1);
        param.specialMetric = modelIndex;
        param.Weights = values;
        param.num_class = 4;
        param.Category_dict = {}
    }
    $("#loading").removeClass("off");
    $("#loadingContent").removeClass("off");
    post("/executeModelAnalysis", param, function (res) {
        $("#loading").addClass("off");
        $("#loadingContent").addClass("off");
        if (res && !res.success) {
            alert("模型正在运行,请稍后再试.");
        } else {
            $('#tale-grid').bootstrapTable('refresh', {url: '/queryForPage'});
        }
    });


}

function init() {

    post("/getModelScoreRadio", {}, function (res) {
        if (res && res.success) {
            debugger
            var value = res.data;
            if (null != value && !isNaN(value)) {
                $("#scoreRadio").html(value + "%");
            }
        }
    });


    // 模型执行分析执行
    $("#executeAnalysis").unbind("click").bind("click", modelAnalysis);
    $("#customModelIndex").off("ifChanged").on("ifChanged", function () {
        $("#customModelIndexPanel").toggle();
    });
    // ---------模型自定义checkbox美化begin--------
    $('#customModelIndex').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%'
    });
    // ---------模型自定义checkbox美化end--------

    // ------下拉初始化----------
    $("#type-select").selectpicker();
    $("#business_type").selectpicker();
    $("#model-select").selectpicker();
    $("#cluster-select").selectpicker();
    $("#industry-select").selectpicker();
    initArea();
    // -----下拉初始化END--------

    // -----模型参数自定义按钮点击事件begin--------
    $("#paramSetting").unbind("click").bind("click", function () {
        post("/getModelScoreRadio", {}, function (res) {
            if (res && res.success) {
                var value = res.data;
                if (null != value && !isNaN(value)) {
                    $("#modelScoreRadio").val(value);
                }
            }
        });
        post("/getSpecialMetrics", {}, function (res) {
            if (res && res.success) {
                var data = res.data;
                var metrecs = eval(data);
                var metrec;
                var template = '<tr><td>${key}</td><td><input type="number" min="1" value="${value}" style="width: 50px;border:1px solid #d3d3d3"/></td><td><span name="value">${radio}</span>%</td><td><a href="#" onclick="up(this)">&#8593</a> | <a href="#" onclick="down(this)">&#8595</a></td></tr>'
                var table = $("#tableBody");
                if (metrecs.length > 0) {
                    table.html("");
                }
                var sum = 0;
                for (var i = 0; i < metrecs.length; i++) {
                    metrec = metrecs[i];
                    var tr = template.replace("${key}", metrec.key).replace("${value}", metrec.value).replace("${radio}", metrec.radio);
                    table.append(tr);
                }
                bindChangeFunc();
            }
        });
        $('#myModal').modal("show");
        $('#myModal').modal({backdrop: 'static', keyboard: false});
    });


    // -----模型参数自定义按钮点击事件end--------

    // 查询按钮事件
    $('#search_btn').click(function () {
        $('#tale-grid').bootstrapTable('refresh', {url: '/queryForPage'});
        var modelIndex = $("#model-select").val();
        $('#tale-grid').bootstrapTable();
        if (modelIndex) {
            switch (modelIndex) {
                case '1':
                    $('#tale-grid').bootstrapTable("showColumn", 'score');
                    $('#tale-grid').bootstrapTable("hideColumn", 'guaranteedSupplyScore');
                    $('#tale-grid').bootstrapTable("hideColumn", 'overFlowSupplyScore');
                    $('#tale-grid').bootstrapTable("hideColumn", 'insufficientSupplyScore');
                    break;
                case '2':
                    $('#tale-grid').bootstrapTable("showColumn", 'guaranteedSupplyScore');
                    $('#tale-grid').bootstrapTable("hideColumn", 'score');
                    $('#tale-grid').bootstrapTable("hideColumn", 'overFlowSupplyScore');
                    $('#tale-grid').bootstrapTable("hideColumn", 'insufficientSupplyScore');
                    break;
                case '3':
                    $('#tale-grid').bootstrapTable("hideColumn", 'guaranteedSupplyScore');
                    $('#tale-grid').bootstrapTable("hideColumn", 'score');
                    $('#tale-grid').bootstrapTable("showColumn", 'overFlowSupplyScore');
                    $('#tale-grid').bootstrapTable("hideColumn", 'insufficientSupplyScore');
                    break;
                case '4':
                    $('#tale-grid').bootstrapTable("hideColumn", 'guaranteedSupplyScore');
                    $('#tale-grid').bootstrapTable("hideColumn", 'score');
                    $('#tale-grid').bootstrapTable("hideColumn", 'overFlowSupplyScore');
                    $('#tale-grid').bootstrapTable("showColumn", 'insufficientSupplyScore');
                    break;
                default:
                    return;
            }
        }

    });

    // 初始化表格
    $('#tale-grid').bootstrapTable({
        method: 'post',
        contentType: "application/json",// 必须要有！！！！
        url: "/queryForPage",// 要请求数据的文件路径
        striped: true, // 是否显示行间隔色
        dataField: "rows",
        // rows:'resultList', // 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
        pageNumber: 1, // 初始化加载第一页，默认第一页
        pagination: true,// 是否分页
        queryParamsType: 'limit',// 查询参数组织方式
        queryParams: queryParams,// 请求服务器时所传的参数
        sidePagination: 'server',// 指定服务器端分页
        pageSize: 10,//单页记录数
        pageList: [5, 10, 20, 30],// 分页步进值
        // showRefresh: true,// 刷新按钮
        // showColumns: true,
        clickToSelect: false,// 是否启用点击选中行
        toolbarAlign: 'right', // 工具栏对齐方式
        buttonsAlign: 'right',// 按钮对齐方式
        height: toHeight(),
        columns: [
            {
                title: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户名称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
                field: 'customName',
                // width: '40',
                visible: true
            },
            {
                title: '客户类型',
                field: 'category',
                // width: '10',
                // formatter: numberFomater,
                sortable: true
            },
            {
                title: '模型打分',
                field: 'score',
                // width: '10',
                formatter: numberFomater,
                sortable: true
            },
            {
                title: '保供打分',
                field: 'guaranteedSupplyScore',
                // width: '10',
                formatter: numberFomater,
                sortable: true
            },
            {
                title: '供大于求打分',
                field: 'overFlowSupplyScore',
                // width: '10',
                formatter: numberFomater,
                sortable: true
            },
            {
                title: '供不应求打分',
                field: 'insufficientSupplyScore',
                // width: '10',
                formatter: numberFomater,
                sortable: true
            },
            {
                title: '人工打分',
                field: 'artificialScore',
                // width: '10',
                formatter: numberFomater,
                sortable: true
            },
            {
                title: '综合打分',
                field: 'compositeScore',
                // width: '10',
                formatter: numberFomater,
                // sortable: true
            },
            {
                title: '用气量比率',
                field: 'gasUsedRadio',
                // width: '10',
                formatter: numberFomater,
                sortable: true
            },
            {
                title: '用气价格',
                field: 'price',
                // width: '10',
                formatter: numberFomater,
                sortable: true
            },
            {
                title: '用气计划符合度',
                field: 'gasPlanMatch',
                formatter: numberFomater,
                // width: '10'
            },
            {
                title: '用气合同匹配度',
                // width: '10',
                formatter: numberFomater,
                field: 'gasContractMatch'
            },
            {
                title: '用气增长量',
                // width: '15',
                formatter: numberFomater,
                field: 'gasUsedGrowth',
                sortable: true
            },
            {
                title: '用气增长率',
                // width: '10',
                field: 'gasUsedGrowthRadio',
                formatter: numberFomater,
                align: 'center',
                // 列数据格式化
                // formatter: operateFormatter
            },
            {
                title: '可承受气价能力',
                field: 'acceptablePrice',
                // width: '10',
                align: 'center',
                // 列数据格式化
                formatter: numberFomater
            },
            {
                title: '生产经营发展评分',
                // width: '15',
                formatter: numberFomater,
                field: 'developScore',
                sortable: true,
                align: 'center'
            },
            {
                title: '行业气价偏离系数',
                // width: '10',
                formatter: numberFomater,
                field: 'divergeRadio',
                align: 'center'
            },
            {
                title: '聚类类别',
                // width: '20',
                field: 'clusterCategory',
                align: 'center'
            },
            {
                title: '行业标识',
                // width: '20',
                field: 'customIndustry',
                align: 'center'
            },
            {
                title: '用气稳定性',
                // width: '10',
                formatter: numberFomater,
                field: 'stabilization',
                align: 'center'
            },
            {
                title: '温度变化对用气量变化的敏感系数',
                // width: '10',
                formatter: numberFomater,
                field: 'temperatureInfluenceRadio',
                align: 'center'
            }
        ],
        locale: 'zh-CN',//中文支持,
        esponseHandler: function (res) {
            // 在ajax获取到数据，渲染表格之前，修改数据源
            return res;
        }
    });
    $('#tale-grid').bootstrapTable("hideColumn", 'guaranteedSupplyScore');
    $('#tale-grid').bootstrapTable("hideColumn", 'overFlowSupplyScore');
    $('#tale-grid').bootstrapTable("hideColumn", 'insufficientSupplyScore');

}


// 三个参数，value代表该列的值
function operateFormatter(value, row, index) {
    if (value == 2) {
        return '<i class="fa fa-lock" style="color:red"></i>'
    } else if (value == 1) {
        return '<i class="fa fa-unlock" style="color:green"></i>'
    } else {
        return '数据错误'
    }
}

function numberFomater(value, row, index) {
    if ((value || 0 == value) && !isNaN(value)) {
        return value.toFixed(4);
    } else {
        return value;
    }
}

// 请求服务数据时所传参数
function queryParams(params) {
    if (!params.sort) {
        params.sort = 'gasUsedRadio';
        params.order = 'desc';
    }
    return {
        //每页多少条数据
        "limit": params.limit,
        //请求第几页
        "offset": params.offset,
        "name": $('#search_name').val(),
        "type": $("#type-select").val(),
        "businessType": $("#business_type").val(),
        "loc": $("#loc").val(),
        "clusterCategory": $("#cluster-select").val(),
        "industryCategory": $("#industry-select").val(),
        "sort": params.sort,
        "order": params.order,
        "model": $("#model-select").val()
    }
}

function toHeight() {
    return $(window).height() - 200;
}

function initArea() {
    $("#loc").selectpicker();
    // 地区下拉
    var param = {};
    post("/areaList", param, function (res) {
        if (res && res.success && res.data) {
            var areas = res.data.areas;
            var ele = $("#loc");
            var option;
            for (var i = 0; i < areas.length; i++) {
                var area = areas[i];
                option = "<option value='" + area + "'>" + area + "</option>";
                ele.append(option);
            }
            $('#loc').selectpicker('refresh');
        }
    });
}

function removeFormTable(ele) {
    debugger;
    var text = $($(ele).parent().parent().find("td")[0]).html();
    $($(ele).parent().parent()).remove();
    $("#paramList").append('<li class="list-group-item cursor" onclick="liClickFunction(this)">' + text + '</li>');
}

function liClickFunction(ele) {
    var template = '<tr><td>${text}</td><td><input type="number" style="width: 50px;border:1px solid #d3d3d3"/></td><td><a href="#" onclick="removeFormTable(this)">x</td></tr>';
    var text = $(ele).html();
    template = template.replace("${text}", text);
    $("#tableBody").append(template);
    $(ele).remove();
}

function up(obj) {
    var objParentTR = $(obj).parent().parent();
    var prevTR = objParentTR.prev();
    if (prevTR.length > 0) {
        prevTR.insertAfter(objParentTR);
    }
}

function down(obj) {
    var objParentTR = $(obj).parent().parent();
    var nextTR = objParentTR.next();
    if (nextTR.length > 0) {
        nextTR.insertBefore(objParentTR);
    }
}

function saveParam() {
    var modelScoreRadio = $("#modelScoreRadio").val();
    var specialMetrics = [];
    if ($("#customModelIndex")[0].checked) {

        $("#tableBody").find("tr").each(function (i, e) {
            var temp = {};
            e = $(e);
            var name = $(e.find("td")[0]).html();
            var value = $($(e.find("td")[1]).find("input")).val();
            var radio = $($(e.find("td")[2]).find("span")).html();
            temp.key = name;
            temp.value = value;
            temp.radio = radio;
            specialMetrics.push(temp);
        });
    }
    post("/updateModelScoreRadio", {value: modelScoreRadio, specialMetrics: specialMetrics}, function () {
        if (modelScoreRadio && !isNaN(modelScoreRadio)) {
            $("#scoreRadio").html(modelScoreRadio + "%");
        }

    });
}

function bindChangeFunc() {
    $("#tableBody").find("tr").find("input").unbind("change").bind("change", function (e) {
        var sum = 0;
        var inputs = $("#tableBody").find("tr").find("input");
        for (var i = 0; i < inputs.length; i++) {
            var input = $(inputs[i]);
            sum += Number(input.val());
        }
        for (var j = 0; j < inputs.length; j++) {
            var input = $(inputs[j]);
            var selfVal = Number(input.val());
            var radio = (selfVal / sum) * 100;
            radio = radio.toFixed(2);
            $(input).parent().parent().find("span").html(radio);
        }
    });
}