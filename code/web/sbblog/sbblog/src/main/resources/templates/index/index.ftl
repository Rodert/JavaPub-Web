<#include "index/common/_head.ftl">
<div class="main-wrapper">
    <div class="main-content">
        <div class="content-title">
            博客列表
        </div>
        <div class="content-title-little">
            共有文章：<span id="total_num">1</span>篇
        </div>
        <input type="hidden" id="cateid" value="${id}">
        <input type="hidden" id="searchKey" value="${searchKey}">
        <div class="row">
            <div class="col-lg-8 col-md-12">
                <ul class="content-list" id="content-list">
                </ul>
                <div class="content-page" id="content_page"></div>
            </div>
            <div class="col-lg-4 col-sm-12">
                <#include "index/common/_toplist.ftl">
            </div>
        </div>

    </div>
    <#include "index/common/_foot.ftl">

</div>
<#include "index/common/_info.ftl">

<#include "index/common/_js.ftl">

<script>
    var size = 5;
    $(function () {
        initPageList(1);
    });



    function initPageList(current) {
        var cate = $("#cateid");
        var cateid = -1;
        var searchKey = '';
        if($("#searchKey")) {
            searchKey = $("#searchKey").val();
            $("#search_content").val( $("#searchKey").val());
        }
        if(cate) {
            cateid = cate.val();
        }
        $.ajax({
            url:'/findList',
            type:'post',
            data:{page:current,size:size,cateid:cateid,searchKey:searchKey},
            dataType:'json',
            success:function (data) {
                if(data) {
                    var total = data.total;//数据总数
                    $("#total_num").text(total);
                    var rows = data.rows;
                    initListContent(rows);
                    var pageTotal = Math.ceil(total/size);
                    console.info(pageTotal);
                    $("#content_page").pagination({
                        pageCount:pageTotal,
                        current:current,
                        showData: size,
                        mode: 'unfixed',
                        jump: false,
                        coping: false,
                        keepShowPN: true,
                        prevContent: '上页',
                        nextContent: '下页',
                        callback: function (api) {
                            initPageList(api.getCurrent());
                        }
                    });
                }
            }
        });
    }

    function initListContent(rows){
        var listHtml = '';
        if(rows != null && rows.length > 0) {
            for(var i = 0 ; i < rows.length; i ++) {
                var article = rows[i];
                listHtml +=
                    '<li>'+
                    '<div class="title">'+
                    '<a href="/article/'+article.id+'">'+ article.title +'</a>'+
                    '</div>'+
                    '<div class="info">作者：'+article.authorname+' 发布时间：'+article.create_time+' 阅读量：'+article.viewnum+' 评论数：'+article.commentnum +'</div>'+
                    '</li>';
            }
        } else {
            listHtml += '<li class="text-center">该分类下暂无文章</li>';
        }

        $("#content-list").empty();
        $("#content-list").append(listHtml);
    }
</script>
</body>
</html>