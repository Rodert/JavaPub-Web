<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${sysInfo.webname}</title>
    <link href="/static/index/plugins/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="/static/index/plugins/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="/static/index/css/lee-blog.css" rel="stylesheet">
</head>
<body>
<div class="reg-window">
    <div class="reg-panel">
        <div class="row">
            <div class="col-xs-12">
                <div class="reg-title text-center">欢迎注册博客系统</div>
            </div>
        </div>
        <hr class="reg-hr">
        <form class="reg-form">
            <div class="row reg-group"><label class="col-xs-4 text-right reg-label">用户名</label>
                <div class="col-xs-8"><input type="text" name="username" class="reg-input"></div>
            </div>
            <div class="row reg-group"><label class="col-xs-4 text-right reg-label">密码</label>
                <div class="col-xs-8"><input type="password" name="password" class="reg-input"></div>
            </div>
            <div class="row reg-group"><label class="col-xs-4 text-right reg-label">确认密码</label>
                <div class="col-xs-8"><input type="password" name="confirmpassword" class="reg-input"></div>
            </div>
            <div class="row reg-group"><label class="col-xs-4 text-right reg-label">昵称</label>
                <div class="col-xs-8"><input type="nickname" name="nickname" class="reg-input"></div>
            </div>
            <div class="row reg-group"><label class="col-xs-4 text-right reg-label">邮箱</label>
                <div class="col-xs-8"><input type="email" name="email" class="reg-input"></div>
            </div>
            <div class="row ">
                <div class="col-xs-12 text-center reg-button">
                    <button type="button" id="reg_sub_btn">注册提交</button>
                    <button type="button" id="reg_back_btn">返回首页</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="/static/index/plugins/jquery/jquery-3.4.1.min.js"></script>
<script src="/static/index/plugins/bootstrap/js/bootstrap.js"></script>
<script src="/static/lib/layer/layer.js"></script>
<script>
    $(function () {
        $("#reg_back_btn").click(function () {
            location.href = '/';
            return false;
        });
        $("#reg_sub_btn").click(function () {
            $.ajax({
                url:'/register',
                type:'post',
                data:$('form').serialize(),
                dataType:'json',
                success:function (data) {
                    if(data.success) {
                        layer.msg(data.message,{time:500},function () {
                            location.href='/';
                        });
                    } else {
                        layer.msg(data.message,{time:2000});
                    }
                }
            });
            return false;
        });
    });

</script>
</body>
</html>