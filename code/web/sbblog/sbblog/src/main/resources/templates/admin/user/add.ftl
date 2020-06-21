<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">×</span>
    </button>
    <div class="modal-title">管理员添加</div>
</div>
<div class="modal-body">
    <div>
        <form id="user_add_form" role="form" class="form-horizontal" action="/admin/user/add" method="post">

            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>用户名</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control" id="add_username" name="username" type="text"  placeholder="1-10位英文、数字、下划线，区分大小写">
                </div>
            </div>

            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>密码</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control validate" id="add_password" name="password" type="password" placeholder="6-20位英文、数字、符号，区分大小写" >
                </div>
            </div>

            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>确认密码</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control validate" id="add_confirmpassword" name="confirmpassword" type="password">
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>昵称</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control" id="add_nickname" name="nickname" type="text"  placeholder="1-10位英文、数字、下划线、汉字，区分大小写">
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>邮箱</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control" name="email" type="email" id="add_email" >
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>角色</label>
                </div>
                <div class="col-md-9 controls">
                    <label class="radio-inline">
                        <input type="radio" name="isSupper"  value="0" checked="checked">普通管理员
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="isSupper"  value="1">超级管理员
                    </label>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="modal-footer">
    <div class="pull-right">
        <button class="btn btn-primary " onclick="validateAndSubmitForm('user_add_form', this);">保存</button>
        <button class="btn btn-default" type="button" data-dismiss="modal">取消</button>
    </div>
</div>
<script type="text/javascript">
    //validation后台验证，由于代码较长，不便于在HTML中使用，暂时使用js执行。
    $(function(){
        var validator = $("#user_add_form").validate({
            rules: {
                username : {
                    required : true,
                    remote: {
                        type:'POST',
                        dataType: 'json',
                        url:'/admin/user/check',
                        data:{
                            username:function() {
                                return $("#add_username").val();
                            }
                        }
                    }
                },
                password:{
                    required:true,
                    minlength:5
                },
                confirmpassword:{
                    required:true,
                    minlength:5,
                    equalTo:"#add_password"
                },
                nickname:"required",
                email:{
                    required:true,
                    email: true
                },
            },
            messages: {
                username: {
                    required: '请输入用户名',
                    remote:'用户名已存在!'
                },
                password:{
                    required:'请输入密码',
                    minlength: '密码长度不能少于5'
                },
                confirmpassword:{
                    required:'请输入确认密码',
                    minlength:'密码长度不能少于5',
                    equalTo:"确认密码和密码输入不一致！"
                },
                nickname: {
                    required:'请输入昵称',
                },
                email:{
                    required:'请输入邮箱',
                    email:'邮箱格式不正确'
                }

            }
        });

    });

</script>
