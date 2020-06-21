$(function () {
    $("#login").click(function () {
        var html = '';

        html += ' <div class="login-window">' +
            ' <div class="login-panel">' +
            '<div class="row">' +
            '<div class="col-xs-12">' +
            '<div class="login-title text-center">' +
            '欢迎登录博客系统' +
            ' </div>' +
            '</div>' +
            ' </div>' +
            ' <hr class="login-hr">' +
            ' <form class="login-form">' +
            ' <div class="row login-group">' +
            ' <label class="col-xs-4 text-right login-label">用户名</label>' +
            '<div class="col-xs-8">' +
            ' <input type="text" name="username" class="login-input">' +
            ' </div>' +
            '</div>' +
            ' <div class="row login-group">' +
            ' <label class="col-xs-4 text-right login-label">密码</label>' +
            ' <div class="col-xs-8">' +
            ' <input type="password" name="password" class="login-input">' +
            ' </div>' +
            ' </div>' +
            ' <div class="row ">' +
            ' <div class="col-xs-12 text-center login-button">' +
            ' <button type="button" onclick="login()">登录</button>' +
            ' <button type="button" onclick="remove()">关闭</button>' +
            ' </div>' +
            ' </div>' +
            ' </form>' +
            ' </div>' +
            ' </div>';
        $("body").append(html);
        return false;
    });

    $("#logout").click(function () {
        layer.confirm('确认退出吗？',{
            title:'退出操作',
            icon:3
        },function (index) {
            layer.close(index)
            $.ajax({
                url:'/logout',
                type:'post',
                dataType: 'json',
                success:function (data) {
                    layer.msg(data.message,{
                        time:500
                    },function () {
                        window.location.reload();
                    });

                }
            });
        });
        return false
    });

    $("#search_button").click(function () {
        var content =  $.trim($("#search_content").val());
        if(content) {
            location.href = '/search/'+ content;
        }

    });
});

function remove() {
    $(".login-window").remove();
    return false;
}
function login(){
    var username=$("input[name=username]");
    var password=$("input[name=password]");
    if(!(username && username.val() != '')) {
        layer.msg('用户名不能为空',{time:2000});
        username.focus();
        return false;
    }
    if(!(password && password.val() != '')) {
        layer.msg('密码不能为空',{time:2000});
        password.focus();
        return false;
    }
    //ajax请求
    $.ajax({
        url:'/login',
        type:'post',
        data:{username:username.val(),password:password.val()},
        dataType:'json',
        success:function (data) {
            if(data.success) {
                layer.msg(data.message,{time:500},function () {
                   window.location.reload();
                });
            } else {
                layer.msg(data.message,{time:2000});
            }
        }
    });
    return false;
}