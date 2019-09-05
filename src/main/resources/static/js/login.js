function login() {
    var userName = $("#userName").val();
    var password = $("#password").val();
    var param = {loginName: userName, password: password}

    $.ajax({
        url: "/login/userLogin",
        contentType: "application/json; charset=utf-8",
        async: true,
        type: "post",
        success: callback,
        dataType: 'json',
        data: JSON.stringify(param)
    });
}

function callback(res) {
    if (res && !res.success) {
        alert("用户名或密码错误");
    } else {
        $.cookie("userName", res.data.loginName);
        window.location.href = '/';
    }
}