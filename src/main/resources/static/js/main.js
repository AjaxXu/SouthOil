var interVal;
$(function () {
    // 先清空之前的定时任务
    if (interVal) {
        clearInterval(interVal);
    }
    interVal = setInterval(function () {
        LoginCheck();
    }, 5000);
});

/**
 * 登陆检测
 * @constructor
 */

function LoginCheck() {
    var userName = $.cookie("userName");
    if (userName && 'null' != userName) {
        $("#loginUser").html(userName);
        $("#login-div").show();
    }
    var param = {};
    $.ajax({
        url: "/login/loginCheck",
        contentType: "application/json; charset=utf-8",
        async: true,
        type: "post",
        success: loginCheckHandler,
        dataType: 'json',
        data: JSON.stringify(param)
    });
}

function loginCheckHandler(res) {
    if (res && !res.success) {
        $.cookie("userName", null);
        window.location.href = '/login.html'
    }
}