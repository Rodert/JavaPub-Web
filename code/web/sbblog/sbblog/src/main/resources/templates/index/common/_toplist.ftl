<div class="top-article">
    <div class="top-article-title">
        推荐文章
    </div>
    <div class="top-article-list">
        <ul>
            <#list  topList as top>
                <li><a href="/article/${top.id}">${top.title}</a></li>
            </#list>
        </ul>
    </div>
</div>