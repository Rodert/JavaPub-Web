<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${sysInfo.webname}</title>
    <link href="/static/index/plugins/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="/static/index/plugins/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="/static/index/plugins/pagination/pagination.css" rel="stylesheet">
    <link href="/static/index/css/lee-blog.css" rel="stylesheet">
</head>
<body>
<div class="header">
    <div class="left-blog-name">
        <a href="#">${sysInfo.webname}</a>
    </div>
    <div class="nav-bar">
        <div class="nav-bar-header">
            <div class="header-img">
                <img src="/static/index/img/profile-avatar.jpg">
            </div>
            <div class="header-title">${sysInfo.sign}</div>
            <ul class="header-link">
                <li>
                    <a href="#"><i class="fa fa-github fa-fw"></i></a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-qq fa-fw"></i></a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-google fa-fw"></i></a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-weibo fa-fw"></i></a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-wechat fa-fw"></i></a>
                </li>
            </ul>
        </div>
        <hr>
        <div class="nav-bar-search">
            <form>
                <div class="nav-bar-search-content">
                    <input type="text" id="search_content"  placeholder="搜搜文章">
                    <button type="button" id="search_button"><i class="fa fa-search fa-fw"></i></button>
                </div>
            </form>
        </div>
        <ul class="nav-bar-menu">
            <li>
                <a href="/">
                    <i class="fa fa-caret-right fa-fw"></i>博客首页
                </a>
            </li>
            <#list cateList as cate>
                <li>
                    <a href="/cate/${cate.id}">
                        <i class="fa  fa-caret-right fa-fw"></i>${cate.catename}
                    </a>
                </li>
            </#list>
        </ul>
    </div>
</div>