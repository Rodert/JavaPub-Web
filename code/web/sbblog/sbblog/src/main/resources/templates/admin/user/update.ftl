<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">×</span>
    </button>
    <div class="modal-title">管理员修改</div>
</div>
<div class="modal-body">
    <div>
        <form id="user_update_form" role="form" class="form-horizontal" action="/admin/user/update" method="post">
            <input type="hidden" name="id" value="${adminInfo.id}">
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>用户名</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control"  name="username" type="text" value="${adminInfo.username}" disabled  placeholder="1-10位英文、数字、下划线，区分大小写">
                </div>
            </div>

            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>原密码</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control validate"  name="oldpassword" type="password" placeholder="6-20位英文、数字、符号，区分大小写" >
                </div>
            </div>

            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>新密码</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control validate"  name="newpassword" type="password">
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>昵称</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control"  name="nickname" type="text" value="${adminInfo.nickname}"  placeholder="1-10位英文、数字、下划线、汉字，区分大小写">
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>邮箱</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control" name="email" value="${adminInfo.email}" type="email"  >
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>角色</label>
                </div>
                <div class="col-md-9 controls">
                    <label class="radio-inline">
                        <input type="radio" name="isSupper"  value="0" <#if adminInfo.isSupper == 0> checked </#if>>普通管理员
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="isSupper"  value="1" <#if adminInfo.isSupper == 1> checked </#if> >超级管理员
                    </label>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="modal-footer">
    <div class="pull-right">
        <button class="btn btn-primary " onclick="validateAndSubmitForm('user_update_form', this);">保存</button>
        <button class="btn btn-default" type="button" data-dismiss="modal">取消</button>
    </div>
</div>
<script type="text/javascript">
    //validation后台验证，由于代码较长，不便于在HTML中使用，暂时使用js执行。
    $(function(){
        var validator = $("#user_update_form").validate({
            rules: {
                oldpassword:{
                    required:true,
                    minlength:5
                },
                newpassword:{
                    required:true,
                    minlength:5
                },
                nickname:"required",
                email:{
                    required:true,
                    email: true
                },
            },
            messages: {
                oldpassword:{
                    required:'请输入原密码',
                    minlength: '密码长度不能少于5'
                },
                newpassword:{
                    required:'请输入新密码',
                    minlength:'密码长度不能少于5'
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
