<#include "admin/common/_head.ftl">
<#include "admin/common/_left.ftl">
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="main-content-header">
                <div class="pull-left header-title">
                    <h4>系统设置</h4>
                    <div class="small-title">系统设置</div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div class="panel">
                <div class="panel-body">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">站点标题</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" name="webname" value="${syssetting.webname}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">个性签名</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" name="sign" value="${syssetting.sign}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">版权信息</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" name="copyright" value="${syssetting.copyright}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-2">
                                <button class="btn btn-primary" type="submit" id="saveBtn">保存</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<!-- /#wrapper -->
<#include "admin/common/_js.ftl">
<script>
    $(function () {
        selectMenuByID("admin_syssetting");
        $("#saveBtn").click(function () {
            $.ajax({
                url:'/admin/syssetting/add',
                type:'post',
                data:$('form').serialize(),
                dataType:'json',
                success:function (data) {
                    if(data.success) {
                        layer.msg(data.message,{
                            time:2000
                        });
                    } else {
                        layer.open({
                            title:'操作失败',
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
