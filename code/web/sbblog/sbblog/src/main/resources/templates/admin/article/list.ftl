<#include "admin/common/_head.ftl">
<#include "admin/common/_left.ftl">
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="main-content-header">
                <div class="pull-left header-title">
                    <h4>文章管理</h4>
                    <div class="small-title">文章列表</div>
                </div>
            </div>
        </div>
    </div>
    <div class="row list-search-row">
        <div class="col-sm-12">
            <div class="pull-left">
                <button class="btn list-btn btn-primary " type="button" onclick="openModal(article_insert_modal_url, 'article_create_modal', article_list_table_id )">
                    <i class="fa fa-fw fa-plus"></i>新增文章
                </button>
                <button class="btn list-btn btn-danger " type="button" onclick="deleteBatch(article_list_delete_url,  article_list_table_id )">
                    <i class="fa fa-fw fa-times"></i>删除文章
                </button>
            </div>
            <div class="pull-right">
                <form id="article_list_searchForm" name="article_list_searchForm" class="form-inline list-search" onsubmit="return false">
                    <div class="form-group">
                        <select class="form-control" name="search_type">
                            <option value="title" selected>文章标题</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="search_value" placeholder="搜索相关数据" />
                    </div>
                    <div class="form-group">
                        <a class="btn list-btn btn-info " onClick="article_list_search();"><i class="fa fa-search fa-fw"></i>查询</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <table class="table table-striped table-hover table-no-bordered " id="article_list_table"></table>
        </div>
    </div>
    <!-- 注册modal -->
    <div class="modal modal-top fade" id="article_create_modal" tabindex="-1">
        <div class="modal-dialog modal-primary" style="width: 70%">
            <div class="modal-content"></div>
        </div>
    </div>

    <!-- 修改查看modal -->
    <div id="article_listUpdateModal" class="modal modal-top fade" tabindex="1">
        <div class="modal-dialog modal-primary" style="width: 70%">
            <div class="modal-content"></div>
        </div>
    </div>

</div>
</div>
<!-- /#wrapper -->
<#include "admin/common/_js.ftl">
<script>
    var article_insert_modal_url = '/admin/article/add'; //新增注册url
    var article_list_view_url = '/admin/article/view/'; //查看信息URL
    var article_list_update_url = '/admin/article/update/'; //更新信息URL
    var article_list_delete_url = '/admin/article/delete'; //删除URL

    var article_list_table; //用户表对象
    var article_list_table_id = 'article_list_table'; //用户表id

    $(function () {
        selectMenuByID("admin_article");
        article_list_loadData();
    });
    //显示列
    var article_list_table_columns = [
        {
            field : 'checkbox',
            title : 'check',
            checkbox : true,
            hidden : true,
            align : "center",//水平
            valign : "middle"//垂直
        },
        {
            field : 'index',
            title : '序号',
            width : 50,//宽度
            align : "center",//水平
            valign : "middle",//垂直
            formatter : function(value, row, index) {
                return index + 1;
            }
        },
        {
            field : 'title',
            title : '文章标题'
        },
        {
            field : 'authorname',
            title : '作者'
        },
        {
            field : 'viewnum',
            width : 150,//宽度
            title : '浏览次数'
        },
        {
            field : 'commentnum',
            width : 150,//宽度
            title : '评论数'
        },
        {
            field : 'is_top',
            title : '是否推荐',
            formatter:function (value,row,index) {
                if(value == 1) {
                    return '已推荐';
                } else {
                    return '未推荐';
                }
            }
        },
        {
            field : 'create_time',
            title : '创建时间'
        },
        {
            field : 'action',
            title : '操作',
            formatter : function(value, row, index) {
                var result = '';

                if(row.is_top) {
                    result += '<button class="btn btn-xs btn-warning article-top" istop="1"  dataid="'+row.id+'" >取消推荐</button>&nbsp;';
                } else {
                    result += '<button class="btn btn-xs btn-info article-top" istop="0"  dataid="'+row.id+'">推荐</button>&nbsp;';
                }

                result += '<button class="btn btn-xs btn-primary" onclick="openModal(\''
                    + article_list_update_url
                    + row.id
                    + '\', \'article_listUpdateModal\', \''
                    + article_list_table_id
                    + '\');">修改</button>&nbsp;';
                result += '<button class="btn btn-xs btn-danger" onclick="deleteConfirm(\''
                    + article_list_delete_url
                    + '\',\''+ row.id +'\',\''+ article_list_table_id +'\');">删除</button>';
                return result;
            }
            //格式化
        } ];
    function article_list_loadData() {
        article_list_table = $('#' + article_list_table_id).bootstrapTable({
            method : 'post',
            columns : article_list_table_columns,
            url : "/admin/article/findList",
            pagination : true,
            search : false,
            showRefresh : false,
            showToggle : false,
            showColumns : false,
            paginationLoop : false,
            paginationPreText : '上一页',
            paginationNextText : '下一页',
            queryParams : queryParams,
            sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
            pageNumber : 1, //初始化加载第一页，默认第一页
            pageSize : 10, //每页的记录行数（*）
            pageList : [ 10, 20, 50 ], //可供选择的每页的行数（*）
            uniqueId : "id", //每一行的唯一标识，一般为主键列
            onLoadSuccess : function(data) { //加载成功时执行
                $(".article-top").click(function () {
                    var msg =  '';
                    var id = $(this).attr("dataid");
                    var istop = $(this).attr("istop");
                    if(istop == 1) {
                        msg = '确定取消推荐本条文章吗?';
                    } else {
                        msg = '确定推荐本条文章吗?';
                    }
                    layer.confirm(msg,{
                        title:'推荐操作',
                        icon:3
                    },function (index) {
                        layer.close(index);
                        $.ajax({
                            url:'/admin/article/top',
                            type:'post',
                            data:{id:id,istop:istop},
                            dataType:'json',
                            success:function (data) {
                                article_list_table.bootstrapTable('refresh');
                                layer.msg(data.message,{time:2000});
                            }
                        });
                    })
                    return false;
                });
            }
        });
    }
    //刷新查询
    function article_list_search() {

        article_list_table.bootstrapTable('refreshOptions', {
            pageNumber : 1
        });
        return false;
    }

    //查询参数
    function queryParams(params) {
        params.search = searchDataInit("article_list_searchForm");
        return params;
    }
</script>
</body>
</html>
