<div class="main-left">
    <div class="left-sidebar">
        <!-- 左侧菜单头像区域 -->
        <div class="user-pannel">
            <div class="pull-left image">
                <img class="img-circle user-left-img" src="/static/admin/images/avator.jpg">
            </div>
            <div class="pull-left info">
                <p><#if Session["admin"]?exists>${Session["admin"].nickname}</#if></p>
                <a href="#"><i class="fa fa-circle text-success"></i> 当前在线</a>
            </div>
        </div>
        <!--end 左侧菜单头像区域 -->
        <ul class="nav left-menu" id="left-menu">
            <li class="header">导航 <span class="header-second">NAVIGATION</span>
            </li>
            <li><a href="/admin/home" id="admin_home"><i class="fa fa-dashboard fa-fw"></i> 首页</a></li>
            <li><a href="/admin/cate/list" id="admin_cate"><i class="fa fa-th-list fa-fw"></i> 栏目管理</a></li>
            <li><a href="/admin/article/list" id="admin_article"><i class="fa fa-file-text fa-fw"></i> 文章管理</a></li>
            <li><a href="/admin/comment/list" id="admin_comment"><i class="fa fa-edit fa-fw"></i> 评论管理</a></li>
            <li><a href="/admin/syssetting/add" id="admin_syssetting"><i class="fa fa-cog fa-fw"></i> 系统设置</a></li>
            <li><a href="javascript:void(0);"><i class="fa fa-user fa-fw"></i>用户管理<span class="fa arrow"></span></a>
                <ul class="nav nav-second-level hidden">
                    <li><a href="/admin/member/list" id="admin_member">会员管理</a></li>
                    <#if Session["admin"]?exists>
                        <#if Session["admin"].isSupper == 1>
                            <li><a href="/admin/user/list" id="admin_user">管理员管理</a></li>
                        </#if>
                    </#if>
                </ul>
            </li>
        </ul>
    </div>
</div>