<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">×</span>
    </button>
    <div class="modal-title">会员修改</div>
</div>
<div class="modal-body">
    <div>
        <form id="member_update_form" role="form" class="form-horizontal" action="/admin/member/update" method="post">
            <input type="hidden" name="id" value="${memberInfo.id}">
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>会员名</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control " disabled id="username"  name="username" type="text"  placeholder="请输入会员名" value="${memberInfo.username}">
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>新密码</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control "  name="password" type="password"  placeholder="请输入新密码，不输入则密码不修改" >
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>昵称</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control validate"  name="nickname" type="text" value="${memberInfo.nickname}"  placeholder="请输入昵称"  data-options="required:true,messages:{required:'请输入昵称'}">
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>邮箱</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control validate"  name="email" type="text" value="${memberInfo.email}" placeholder="请输入邮箱"  data-options="required:true,email:true,messages:{required:'请输入邮箱',email:'邮箱格式不正确'}">
                </div>
            </div>
        </form>
    </div>
</div>
<div class="modal-footer">
    <div class="pull-right">
        <button class="btn btn-primary " onclick="validateAndSubmitForm('member_update_form', this);">保存</button>
        <button class="btn btn-default" type="button" data-dismiss="modal">取消</button>
    </div>
</div>
