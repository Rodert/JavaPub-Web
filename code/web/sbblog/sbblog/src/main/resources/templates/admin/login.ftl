<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Lee-Blog</title>

    <!-- Bootstrap Core CSS -->
    <link href="/static/admin/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <link href="/static/admin/plugins/jquery-validate/css/validate.css" rel="stylesheet" type="text/css">
    <link href="/static/admin/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">


    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<div class="container">
    <div class="row" style="margin-top: 50px">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">欢迎登录Lee-Blog后台管理系统</h3>
                </div>
                <div class="panel-body">
                    <form id="loginform" role="form" class="form-vertical form-vertical-small">
                            <div style="display: none" id="errorMsg" class="alert alert-danger">test</div>
                            <div class="form-group">
                                <input  class="form-control validate" placeholder="用户名" name="username" type="text" autofocus data-options="required:true,messages:{required:'请输入用户名'}">
                            </div>

                            <div class="form-group">
                                <input class="form-control validate" placeholder="密码" name="password" type="password" value="" data-options="required:true, messages:{required:'请输入密码'}">
                            </div>
                            <button type="submit" id="loginBtn" class="btn btn-lg btn-success btn-block">登录</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- jQuery -->
<script src="/static/admin/plugins/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/static/admin/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/admin/js/commons.js"></script>
<script src="/static/admin/plugins/bootstrap-growl/js/jquery.bootstrap-growl.min.js"></script>
<script src="/static/admin/plugins/jquery-validate/js/jquery.metadata.js"></script>
<script src="/static/admin/plugins/jquery-validate/js/jquery.validate.js"></script>

<script src="/static/admin/plugins/jquery-validate/js/messages_zh.js"></script>
<script src="/static/admin/plugins/jquery-validate/js/validate-methods.js"></script>
<script src="/static/admin/plugins/jquery-validate/js/jquery.form.js"></script>
<script src="/static/lib/layer/layer.js"></script>

<script type="text/javascript">
    $(function(){

        $("#loginBtn").click(function () {
            var isValid = validateForm("loginform");	//验证表单
            if(!isValid) {
                return false;
            }
            $.ajax({
                url:'/admin/login',
                type:'post',
                data:$("form").serialize(),
                dataType:'json',
                success:function (data) {
                    if(data.success) {
                        layer.msg(data.message,{
                            icon:6,
                            time:2000
                        },function () {
                            location.href = data.url;
                        });
                    } else {
                        layer.open({
                            title:'登录失败',
                            content:data.message,
                            icon:5
                        });
                    }
                }
            });
            return false;
        });
    });
</script>
</body>

</html>
