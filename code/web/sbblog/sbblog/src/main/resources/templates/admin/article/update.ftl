<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">×</span>
    </button>
    <div class="modal-title">文章修改</div>
</div>
<div class="modal-body">
    <div>
        <form id="article_update_form" role="form" class="form-horizontal" action="/admin/article/update" method="post">
            <input type="hidden" name="id" value="${articleInfo.id}">
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>文章标题</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control validate"   name="title" type="text"  placeholder="请输入文章标题" value="${articleInfo.title}" data-options="required:true,messages:{required:'请输入文章标题'}">
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>所属栏目</label>
                </div>
                <div class="col-md-9 controls">
                   <select name="cateId" class="form-control validate" data-options="required:true,messages:{required:'请选择一个栏目分类'}">
                        <option value="">栏目分类</option>
                       <#list cateInfos as cateInfo>
                           <option value="${cateInfo.id}" <#if articleInfo.cateId == cateInfo.id > selected </#if>>${cateInfo.catename}</option>
                       </#list>
                   </select>
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>作者</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control validate" value="${articleInfo.authorname}"  name="authorname" type="text"  placeholder="请输入作者" data-options="required:true,messages:{required:'请输入作者'}">
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>标签</label>
                </div>
                <div class="col-md-9 controls">
                    <input class="form-control validate"  value="${articleInfo.tags}"  name="tags" type="text"  placeholder="多个标签请用 | 分开"  data-options="required:true,messages:{required:'请输入文章标签'}">
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>是否推荐</label>
                </div>
                <div class="col-md-9 controls">
                    <div class="checkbox">
                        <label class="control-label"> <input type="checkbox" name="isTop" <#if articleInfo.isTop == 1> checked="checked" </#if>  value="1">推荐</label>
                    </div>
                </div>
            </div>
            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>文章概要</label>
                </div>
                <div class="col-md-9 controls">
                    <textarea  class="form-control" rows="5" name="artdesc" >${articleInfo.artdesc}</textarea>
                </div>
            </div>

            <div class="row form-group">
                <div  class="col-md-2 control-label">
                    <label>文章内容</label>
                </div>
                <div class="col-md-9 controls">
                    <div id="contentDiv">${articleInfo.content}</div>
                    <textarea  name="content" id="content" class="hidden" >${articleInfo.content}</textarea>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="modal-footer">
    <div class="pull-right">
        <button class="btn btn-primary " onclick="validateAndSubmitForm('article_update_form', this);">保存</button>
        <button class="btn btn-default" type="button" data-dismiss="modal">取消</button>
    </div>
</div>
<script src="/static/lib/wangeditor/wangEditor.js"></script>
<script type="text/javascript">
    $(function () {
        var e = window.wangEditor;
        var editor = new e('#contentDiv');
        editor.customConfig.onchange = function (html) {
            $("#content").val(html)
        }
        editor.create();
        $("#content").val(editor.txt.html());
    });

</script>
