<#include "admin/common/_head.ftl">
<#include "admin/common/_left.ftl">
<div class="main-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="main-content-header">
                <div class="pull-left header-title">
                    <h4>首页</h4>
                    <div class="small-title">欢迎使用Lee-Blog管理后台</div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-3">
            <div class="widget  bg-blue white-font">
                <div class="row">
                    <div class="col-xs-4">
                        <i class="fa fa-user fa-5x"></i>
                    </div>
                    <div class="col-xs-8 text-right">
                        <span> 会员数量 </span>
                        <h2 class="font-bold">${memberCount}</h2>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-3">
            <div class="widget bg-green white-font">
                <div class="row">
                    <div class="col-xs-4">
                        <i class="fa fa- fa-file-text fa-5x"></i>
                    </div>
                    <div class="col-xs-8 text-right">
                        <span> 文章数量 </span>
                        <h2 class="font-bold">${articleCount}</h2>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-3">
            <div class="widget  bg-red white-font">
                <div class="row">
                    <div class="col-xs-4">
                        <i class="fa  fa-envelope fa-5x"></i>
                    </div>
                    <div class="col-xs-8 text-right">
                        <span>评论总量</span>
                        <h2 class="font-bold">${commentCount}</h2>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-3">
            <div class="widget  bg-purple white-font">
                <div class="row">
                    <div class="col-xs-4">
                        <i class="fa fa-money fa-5x"></i>
                    </div>
                    <div class="col-xs-8 text-right">
                        <span> 平台盈利额 </span>
                        <h2 class="font-bold">30000</h2>
                    </div>
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
        selectMenuByID("admin_home");
    });
</script>
</body>
</html>
