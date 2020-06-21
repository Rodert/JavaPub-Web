<#include "admin/common/_head.ftl">
<#include "admin/common/_left.ftl">
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="main-content-header">
                <div class="pull-left header-title">
                    <h4>管理员管理</h4>
                    <div class="small-title">管理员列表</div>
                </div>
            </div>
        </div>
    </div>
    <div class="row list-search-row">
        <div class="col-sm-12">
            <div class="pull-left">
                <button class="btn list-btn btn-primary " type="button" onclick="openModal(user_insert_modal_url, 'user_create_modal', user_list_table_id )">
                    <i class="fa fa-fw fa-plus"></i>新增管理员
                </button>
                <button class="btn list-btn btn-danger " type="button" onclick="deleteBatch(user_list_delete_url,  user_list_table_id )">
                    <i class="fa fa-fw fa-times"></i>删除管理员
                </button>
            </div>
            <div class="pull-right">
                <form id="user_list_searchForm" name="user_list_searchForm" class="form-inline list-search" onsubmit="return false">
                    <div class="form-group">
                        <select class="form-control" name="search_type">
                            <option value="username" selected>用户名</option>
                            <option value="nickname">昵称</option>
                            <option value="email">邮箱</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="search_value" placeholder="搜索相关数据" />
                    </div>
                    <div class="form-group">
                        <a class="btn list-btn btn-info " onClick="user_list_search();"><i class="fa fa-search fa-fw"></i>查询</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <table class="table table-striped table-hover table-no-bordered " id="user_list_table"></table>
        </div>
    </div>
    <!-- 注册modal -->
    <div class="modal modal-top fade" id="user_create_modal" tabindex="-1">
        <div class="modal-dialog modal-primary">
            <div class="modal-content"></div>
        </div>
    </div>

    <!-- 修改查看modal -->
    <div id="user_listUpdateModal" class="modal modal-top fade" tabindex="1">
        <div class="modal-dialog modal-primary">
            <div class="modal-content"></div>
        </div>
    </div>

</div>
</div>
<!-- /#wrapper -->
<#include "admin/common/_js.ftl">
<script>
    var user_insert_modal_url = '/admin/user/add'; //新增注册url
    var user_list_view_url = '/admin/user/view/'; //查看信息URL
    var user_list_update_url = '/admin/user/update/'; //更新信息URL
    var user_list_delete_url = '/admin/user/delete'; //封禁URL

    var user_list_table; //用户表对象
    var user_list_table_id = 'user_list_table'; //用户表id

    $(function () {
        selectMenuByID("admin_user");
        user_list_loadData();
    });
    //显示列
    var user_list_table_columns = [
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
            field : 'username',
            width : 150,//宽度
            title : '用户名'
        },
        {
            field : 'nickname',
            width : 150,//宽度
            title : '昵称',
            formatter : function(value, row, index) {
                return '<a href="javascript:void(0);" onclick=\"openModal(\''
                    + user_list_view_url
                    + row.id
                    + '\', \'user_listUpdateModal\')\"  title="信息查看">'
                    + value + '</a>';
            }
        },
        {
            field : 'email',
            width : 150,//宽度
            title : '邮箱'
        },
        {
            field : 'is_supper',
            title : '管理员类型',
            width : 150,//宽度
            align : 'center',
            formatter : function(value, row, index) {
                if (value == '1') {
                    return '超级管理员';
                } else {
                    return '普通管理员';
                }
            }
        },
        {
            field : 'create_time',
            title : '注册时间'
        },
        {
            field : 'status',
            title : '用户状态',
            formatter:function (value,row,index) {
                if(value == 0) {
                    return '已启用'
                } else {
                    return '已禁用'
                }
            }
        },
        {
            field : 'action',
            title : '操作',
            formatter : function(value, row, index) {
                var result = '';

                if(row.status == 0) {
                    result += '<button class="btn btn-xs btn-warning user-status" dataid="'+row.id+'" status="'+row.status+'">禁用</button>&nbsp; ';
                } else {
                    result += '<button class="btn btn-xs btn-success user-status" dataid="'+row.id+'" status="'+row.status+'">启用</button>&nbsp; ';
                }

                result += '<button class="btn btn-xs btn-primary" onclick="openModal(\''
                    + user_list_update_url
                    + row.id
                    + '\', \'user_listUpdateModal\', \''
                    + user_list_table_id
                    + '\');">修改</button>&nbsp;';
                result += '<button class="btn btn-xs btn-danger" onclick="deleteConfirm(\''
                    + user_list_delete_url
                    + '\',\''+ row.id +'\',\''+ user_list_table_id +'\');">删除</button>';
                return result;
            }
            //格式化
        } ];
    function user_list_loadData() {
        user_list_table = $('#' + user_list_table_id).bootstrapTable({
            method : 'post',
            columns : user_list_table_columns,
            url : "/admin/user/findList",
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
                $(".user-status").click(function () {
                    var status = $(this).attr("status");
                    var id = $(this).attr("dataid");
                    var msg = '';
                    if(status == 0) {
                        msg = '确定禁用该管理员账号吗？';
                    } else {
                        msg = '确定启用该管理员账号吗？';
                    }
                    layer.confirm(msg,{
                        title:'管理员状态操作',
                        icon:3
                    },function (index) {
                        layer.close(index);
                        $.ajax({
                            url:'/admin/user/status',
                            type:'post',
                            data:{id:id},
                            dataType:'json',
                            success:function (data) {
                                layer.msg(data.message,{time:2000});
                                user_list_table.bootstrapTable('refresh');
                            }
                        });
                    })
                });
            }
        });
    }
    //刷新查询
    function user_list_search() {

        user_list_table.bootstrapTable('refreshOptions', {
            pageNumber : 1
        });
        return false;
    }

    //查询参数
    function queryParams(params) {
        params.search = searchDataInit("user_list_searchForm");
        return params;
    }
</script>
</body>
</html>
