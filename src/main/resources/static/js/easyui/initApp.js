/**
 */

var chart;
$(function () {
    $('body').app({
        onTaskBlankContextMenu: onTaskBlankContextMenu,
        onAppContextMenu: onAppContextMenu,
        onWallContextMenu: onWallContextMenu,
        onStartMenuClick: onStartMenuClick,
        onElementLoaded: onElementLoaded,
        iconSize: 145,
        title: "基于大数据处理平台的天然气管网调度与实时销售信息的智能联动优化系统"
        // onBeforeClose:onBeforeClose
    });

    function onStartMenuClick(item) {
        debugger;
        console.log(item);
        if (item && item.iconCls =='icon-shutdown') {
            logOut();
        }
    }

    function logOutSuccess(res) {
        if (res && res.success) {
            $.cookie("userName", null);
            window.location.href = "/login.html";
        }
    }

    function logOut() {
        var param = {};
        $.ajax({
            url: "/login/logOut",
            contentType: "application/json; charset=utf-8",
            async: true,
            type: "post",
            success: logOutSuccess,
            dataType: 'json',
            data: JSON.stringify(param)
        });
    }

    var appListMenuData = [
        {
            "text": "打开"
        },
        {
            "text": "关闭"
        },
        {
            "text": "关闭其他"
        },
        {
            "text": "关闭所有"
        },
        {
            "text": "任务管理器"
        },
        {
            "text": "属性"
        }
    ];

    // var appListMenu = $('body').app('createMenu', {data: appListMenuData});

    function onTaskBlankContextMenu(e, appid) {
        // if (appid) {
        //     alert(appid);
        //     appListMenu.menu('findItem', '任务管理器').target.style.display = 'none';
        //     appListMenu.menu('findItem', '属性').target.style.display = 'none';
        //     appListMenu.menu('findItem', '打开').target.style.display = 'block';
        //     appListMenu.menu('findItem', '关闭').target.style.display = 'block';
        //     appListMenu.menu('findItem', '关闭其他').target.style.display = 'block';
        //     appListMenu.menu('findItem', '关闭所有').target.style.display = 'block';
        // } else {
        //     appListMenu.menu('findItem', '任务管理器').target.style.display = 'block';
        //     appListMenu.menu('findItem', '属性').target.style.display = 'block';
        //     appListMenu.menu('findItem', '打开').target.style.display = 'none';
        //     appListMenu.menu('findItem', '关闭').target.style.display = 'none';
        //     appListMenu.menu('findItem', '关闭其他').target.style.display = 'none';
        //     appListMenu.menu('findItem', '关闭所有').target.style.display = 'none';
        // }

        // appListMenu.menu('show', {
        //     left: e.pageX,
        //     top: e.pageY
        // });
        e.preventDefault();
    }


    var wallMenuData = [
        // {
        //     "text": "属性",
        //     "href": ""
        // },
        // '-',
        // {
        //     "text": "关于",
        //     "href": ""
        // }
    ];
    var appMenuData = [
        {
            "text": "打开"
        }
        // '-',
        // {
        //     "text": "属性"
        // }
    ];

    var wallMenu = $('body').app('createMenu', {data: wallMenuData, opt: {onClick: onStartMenuClick}});
    var appMenu = $('body').app('createMenu', {data: appMenuData, opt: {onClick: onAppContextMenuClick}});

    var APPID;

    function onAppContextMenu(e, appid) {
        appMenu.menu('show', {
            left: e.pageX,
            top: e.pageY
        });
        APPID = appid;
    }

    function onAppContextMenuClick(item) {
        if (item.text == '打开') {
            $("li[app_id='" + APPID + "']").dblclick();
        }
    }

    function onWallContextMenu(e) {
        wallMenu.menu('show', {
            left: e.pageX,
            top: e.pageY
        });
    }

    function onElementLoaded(item) {
        if (!chart) {
            chart = echarts.init($("#line-chart")[0]);
        }
        chart.setOption(lineChartOption, true);
    }

    function onBeforeClose() {
        chart.dispose();
        chart = null;
    }
});

function post(url, param, callback) {
    $.ajax({
        url: url,
        contentType: "application/json; charset=utf-8",
        async: true,
        type: "post",
        success: callback,
        dataType: 'json',
        data: JSON.stringify(param)
    });
}
