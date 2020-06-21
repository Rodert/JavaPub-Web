<#include "index/common/_head.ftl">
<div class="main-wrapper">
    <div class="main-content">
        <div class="row">
            <div class="col-lg-8 col-md-12">
                <div class="content-title">
                    ${article.title}
                </div>
                <div class="content-title-little">
                    ${article.authorname}  ${article.createTime} 浏览: ${article.viewnum} 评论： ${article.commentnum}
                    <#list tags as tag>
                        <span class="title-tags">${tag}</span>
                    </#list>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-8 col-md-12">
                <div class="content-detail">
                    ${article.content}
                </div>
                <div class="row other-article">
                    <div class="col-xs-6">
                        <div class="pre-title text-left">
                            <#if preInfo??>
                                <a href="/article/${preInfo.id}">上一篇：${preInfo.title}</a>
                            <#else >
                                <a href="#">上一篇：没有了哎！</a>
                            </#if>
                        </div>
                    </div>
                    <div class="col-xs-6">
                        <div class="next-title text-right">
                            <#if nextInfo??>
                                <a href="/article/${nextInfo.id}">下一篇：${nextInfo.title}</a>
                                <#else >
                                    <a href="#">下一篇：没有了哎！</a>
                            </#if>

                        </div>
                    </div>
                </div>
                <div class="comment-area">
                    <div class="comment-title">相关评论</div>
                    <hr>
                    <div class="comment-text">
                        <textarea rows="3" placeholder="看都看了，留下点评论吧" id="comment_content"></textarea>
                    </div>
                    <div class="comment-btn">
                        <button id="comment_btn" dataid="${article.id}">留言</button>
                    </div>
                    <div class="comment-list">
                        <ul>
                            <#list commentList as comment>
                                <li>
                                    <div class="comment-msg-box">
                                        <div class="comment-info">${comment.membername}<span>${comment.createTime}</span></div>
                                        <div class="comment-msg-content">
                                            ${comment.content}
                                        </div>
                                    </div>
                                </li>
                            </#list>

                        </ul>
                    </div>
                </div>
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
    $(function () {
        $("#comment_btn").click(function () {
            var member = '${Session['member'].id}';
            if(member) {
                var comment = $("#comment_content").val();
                if(comment) {
                    $.ajax({
                        url:'/comment',
                        type:'post',
                        data:{id:$(this).attr("dataid"),content:comment},
                        dataType:'json',
                        success:function (data) {
                            if(data.success) {
                                layer.msg(data.message,{time:500},function () {
                                    window.location.reload();
                                });
                            } else {
                                layer.msg(data.message,{time:2000});
                            }
                        }
                    });
                } else {
                    layer.msg('请先写点内容再评论哦',{time:2000});
                }
            } else {
                layer.msg('请先登录后再评论',{time:2000});
            }
            return false;
        });
    });

</script>
</body>
</html>