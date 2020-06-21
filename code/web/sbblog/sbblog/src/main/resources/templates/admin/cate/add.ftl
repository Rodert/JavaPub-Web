<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">×</span>
    </button>
    <div class="modal-title">栏目添加</div>
</div>
<div class="modal-body">
    <div>
        <form id="cate_add_form" role="form" class="form-horizontal" action="/admin/cate/add" method="post">

            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>栏目名</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control" id="add_catename"  name="catename" type="text"  placeholder="请输入栏目名称">
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>排序</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control"  name="sort" type="text"  placeholder="请输入排序，序号越大越靠前" >
                </div>
            </div>
        </form>
    </div>
</div>
<div class="modal-footer">
    <div class="pull-right">
        <button class="btn btn-primary " onclick="validateAndSubmitForm('cate_add_form', this);">保存</button>
        <button class="btn btn-default" type="button" data-dismiss="modal">取消</button>
    </div>
</div>
<script type="text/javascript">
    //validation后台验证，由于代码较长，不便于在HTML中使用，暂时使用js执行。
    $(function(){
        var validator = $("#cate_add_form").validate({
            rules: {
                catename : {
                    required : true,
                    remote: {
                        type:'POST',
                        dataType: 'json',
                        url:'/admin/cate/check',
                        data:{
                            catename:function() {
                                return $("#add_catename").val();
                            }
                        }
                    }
                },
                sort:{
                    required:true,
                    digits:true
                }
            },
            messages: {
                catename: {
                    required: '请输入栏目名称',
                    remote:'栏目名称已存在!'
                },
                sort:{
                    required:'请输入排序',
                    digits:'排序请输入正整数数字'
                }
            }
        });

    });

</script>
