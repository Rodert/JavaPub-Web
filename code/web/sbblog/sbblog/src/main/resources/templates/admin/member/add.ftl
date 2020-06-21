<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">×</span>
    </button>
    <div class="modal-title">会员添加</div>
</div>
<div class="modal-body">
    <div>
        <form id="member_add_form" role="form" class="form-horizontal" action="/admin/member/add" method="post">

            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>会员名</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control" id="username"  name="username" type="text"  placeholder="请输入会员名">
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>密码</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control validate"  name="password" type="password"  placeholder="请输入密码" data-options="required:true,messages:{required:'请输入密码'}">
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>昵称</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control validate"  name="nickname" type="text"  placeholder="请输入昵称"  data-options="required:true,messages:{required:'请输入昵称'}">
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>邮箱</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control validate"  name="email" type="text"  placeholder="请输入邮箱"  data-options="required:true,email:true,messages:{required:'请输入邮箱',email:'邮箱格式不正确'}">
                </div>
            </div>
        </form>
    </div>
</div>
<div class="modal-footer">
    <div class="pull-right">
        <button class="btn btn-primary " onclick="validateAndSubmitForm('member_add_form', this);">保存</button>
        <button class="btn btn-default" type="button" data-dismiss="modal">取消</button>
    </div>
</div>
<script type="text/javascript">
    //validation后台验证，由于代码较长，不便于在HTML中使用，暂时使用js执行。
    $(function(){
        var validator = $("#member_add_form").validate({
            rules: {
                username : {
                    required : true,
                    remote: {
                        type:'POST',
                        dataType: 'json',
                        url:'/admin/member/check',
                        data:{username:function () {
                                return $("#username").val();
                            }}
                    }
                }
            },
            messages: {
                username: {
                    required: '请输入会员名',
                    remote:'会员名已存在!'
                },
            }
        });

    });

</script>
