<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">×</span>
    </button>
    <div class="modal-title">管理员信息</div>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-hover">
        <tr>
            <td>用户名</td>
            <td>${adminInfo.username }</td>
        </tr>
        <tr>
            <td>昵称</td>
            <td>${adminInfo.nickname }</td>
        </tr>
        <tr>
            <td>邮箱</td>
            <td>
                ${adminInfo.email }
            </td>
        </tr>
        <tr>
            <td>角色</td>
            <td>
                <#if adminInfo.isSupper == 1>超级管理员</#if>
                <#if adminInfo.isSupper == 0>普通管理员</#if>
            </td>
        </tr>
        <tr>
            <td>注册时间</td>
            <td>${adminInfo.createTime }</td>
        </tr>
    </table>
</div>
<div class="modal-footer">
    <div>
        <button class="btn btn-default pull-right " type="button" data-dismiss="modal">关闭</button>
    </div>
</div>
