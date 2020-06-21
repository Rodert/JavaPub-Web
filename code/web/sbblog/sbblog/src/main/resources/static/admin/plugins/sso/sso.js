/*
 * 文件名: sso.js
 * 描述: 提供对 CAS 单点登录的封装
 *
 * 功能说明：
 * 实现多个应用之间的单点登录（SSO）功能，应用可以部署在不同的域名。
 *
 * 版本: 1.0.0.1
 * 作者: jhzhang@iflytek.com
 * 日期：2013/10/20
 */


/**
* @param serviceUrl - 应用部署地址，PHP 网站请使用 ssoservice.php 所在 Url，
*   JAVA 网站对应 ssoservice.jsp 所在 Url，否则需要自己处理单点登出事件。
* @param casUrl - CAS 认证中心地址
**/
function EduSSO(serviceUrl, casUrl){
  
  var _this = this;
  this._casUrl = casUrl;
  this._serviceUrl = serviceUrl;
  
  this._callback = function(callback, data) {
    if (typeof(callback) == "function"){
        callback(data);
    }
  }
  
  this._realCasLogin = function(callback, params, retry) {
    var loginURL = _this._casUrl + "/login?service=" + encodeURIComponent(_this._serviceUrl);
    $.ajax({
        url: loginURL,
        type: "GET",
        dataType: 'jsonp',
        jsonp: "callback",
        data: params,
        crossDomain: true,
        cache: false,
        success: function (html) {
            html = $.trim(html).replace(/\t/g, '');
            var resultobj = eval('(' + html + ')');
            if (resultobj.result && resultobj.result == "success") {
                if (parseInt(resultobj.code) == 1000 && (!retry)) {
                    //try again
                    _this._realCasLogin(callback, params, true);
                    return;
                }
                //Ajax to service
                if (resultobj.data && resultobj.data.st) {
                    _this._serviceLogin(callback, params.username, params.password, resultobj.data.st);
                    return;
                }
            }
            
            _this._callback(callback, resultobj);
        },
        error: function (data) {
            var resultobj = {result: "fail", code: "-1", data: "登录CAS失败，出现异常！"};
            _this._callback(callback, resultobj);
        }
    });
  }
  
  this._serviceLogin = function(callback, username, password, serviceTicket) {
    var pl = {};
    pl.action = "login";
    pl.nextpage = "/front/eduBindInfoForm";
    pl.loginType = "edu";
    pl.username = username;
    pl.password = password;
    pl.ticket = serviceTicket; 
      
    $.ajax({
        url: _this._serviceUrl,
        type: "POST",
        data: pl,
        cache: false,
        success: function (html) {
            html = $.trim(html).replace(/\t/g, '');
            var resultobj = {};
            resultobj.result = "success";
            resultobj.code = 3001;
            resultobj.data = html;
            
            _this._callback(callback, resultobj);
        },
        error: function (data) {
            var resultobj = {result: "fail", code: "-1", data: "登录Service失败，出现异常！"};
            _this._callback(callback, resultobj);
        }
    });
  }
  
  this._serviceLogout = function(callback) {
    var pl = {};
    pl.action = "logout";
    
    $.ajax({
        url: _this._serviceUrl,
        type: "POST",
        data: pl,
        cache: false,
        success: function (html) {
            html = $.trim(html).replace(/\t/g, '');
            var resultobj = {};
            resultobj.result = "success";
            resultobj.code = 4001;
            resultobj.data = html;
            
            _this._callback(callback, resultobj);
        },
        error: function (data) {
            var resultobj = {result: "fail", code: "-1", data: "登出Service失败，出现异常！"};
            _this._callback(callback, resultobj);
        }
    });
  }
    
  /**
  * 用户登录
  * @param username - 用户名
  * @param password - 密码
  * @param key - `login_name`, `email`, `mobile`
  **/  
  this.login = function(appname, sourceappname, methodkey, username, password, callback){
    var loginURL = _this._casUrl + "/login?service=" + encodeURIComponent(_this._serviceUrl);
    $.ajax({
        url: loginURL,
        type: "GET",
        dataType: 'jsonp',
        jsonp: "callback",
        crossDomain: true,
        cache: false,
        success: function (html) {
            html = $.trim(html).replace(/\t/g, '');
            //var resultobj = html.parseJSON();
            var resultobj = eval('(' + html + ')');
            if (resultobj.result && resultobj.result == "success") {
                //已经登录了
                if (parseInt(resultobj.code) == 1001 && resultobj.data && resultobj.data.st) {
                    _this.logout(function() {
                        setTimeout(function() { _this.login(appname, sourceappname, methodkey, username, password, callback); }, 100);
                        return;
                    });
                    
                    return;
                }
                
                var pl = {};
                pl.username = username;
                pl.password = password;
                pl.sourceappname = appname + "," + sourceappname;
                pl.key = methodkey;
                pl._eventId = "submit";
                pl.lt = resultobj.data.lt;
                pl.execution = resultobj.data.execution;
                
                _this._realCasLogin(callback, pl, false);
            } else {
            	_this._callback(callback, resultobj);
        	}
        },
        error: function (data) {
            var resultobj = {result: "fail", code: "-1", data: "登录失败，出现异常！"};
            _this._callback(callback, resultobj);
        }
    });
  }

  /**
  * 用户登出一般总是返回成功
  **/
  this.logout = function(callback) {
    var logoutURL = _this._casUrl + "/logout";
    $.ajax({
        url: logoutURL,
        type: "GET",
        dataType: 'jsonp',
        jsonp: "callback",
        crossDomain: true,
        cache: false,
        success: function (html) {
            html = $.trim(html).replace(/\t/g, '');
            var resultobj =  eval('(' + html + ')');
           
            if (resultobj.result && resultobj.result == "success") {
                _this._serviceLogout(callback);
                return;
            }
            
        	//_this._callback(callback, resultobj);
        },
        error: function (data) {
            var resultobj = {result: "fail", code: "-1", data: "登出失败，出现异常！"};
            //_this._callback(callback, resultobj);
        }
    });
  }
}
