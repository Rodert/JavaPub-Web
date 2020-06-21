<div class="info-window ">
    <#if Session['member']??>
        <div class="user-info">
            <a href="#">${Session['member'].nickname}</a>
            <a href="#"  id="logout">退出</a>
        </div>
    <#else >
        <div class="user-info">
            <a href="#" id="login">登录</a>
            <a href="/register">注册</a>
        </div>
    </#if>


</div>