<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">×</span>
    </button>
    <div class="modal-title">栏目修改</div>
</div>
<div class="modal-body">
    <div>
        <form id="cate_update_form" role="form" class="form-horizontal" action="/admin/cate/update" method="post">
            <input type="hidden" name="id" value="${cateInfo.id}">
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>栏目名</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control validate" name="catename" type="text" value="${cateInfo.catename}"  placeholder="请输入栏目名称"
                           data-options="required:true,messages:{required:'栏目名称不能为空'}">
                </div>
            </div>
        </form>
    </div>
</div>
<div class="modal-footer">
    <div class="pull-right">
        <button class="btn btn-primary " onclick="validateAndSubmitForm('cate_update_form', this);">保存</button>
        <button class="btn btn-default" type="button" data-dismiss="modal">取消</button>
    </div>
</div>
