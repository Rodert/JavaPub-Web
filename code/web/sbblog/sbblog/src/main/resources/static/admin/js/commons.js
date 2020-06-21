/**
*	公用js
*
*/

/**
 * 登录
 */
function commonLogin(formId){
	var formSelector = $('#' + formId);
	var isValid = validateForm(formId);	//验证表单
	//验证失败则跳出
	if(!isValid){
		return;
	}
	
	var currentUrl = window.location.href;
	//获取gotoUrl内容
	gotoUrl = currentUrl.replace(/(\S+?)ymxt/, '');
	
	var loginUrl = formSelector.attr("action") + gotoUrl;
	
	$.post(loginUrl, formSelector.serialize(), function(data) {
		if (data.success) {
			window.location.reload();
		} else {
			$("#errorMsg").html(data.message);
			$("#errorMsg").show();
			$("#errorMsg").fadeOut(4000,function(){
				$("#errorMsg").html('');
			}); 
		}
	}, 'JSON');
	
}

/**
 * 通用处理检索区域 多对一情况
 * 固定类型 search_type
 * 固定内容 search_value
 * @param formId
 * @returns 动态内容+其他内容
 */
function searchDataInit(formId){
	var searchObj =  $("#"+ formId).serializeJson();
	var searchType = searchObj.search_type;
	var searchValue = searchObj.search_value;
	searchObj[searchType] = searchValue;
	delete searchObj.search_type;
	delete searchObj.search_value;
	return searchObj;		
}

/**
 * 打开modal
 * @param url
 * @param modalId
 */
function openModal(url, modalId, tableId){
	url = url + ";jsessionid=" + getSessionId();	//IE下不支持下面的清除操作，添加jsessionid避免出现缓存问题，使用随机数、时间戳也可以。
	$('#' + modalId).modal(
		{
			remote : url,
			backdrop: 'static', //屏蔽点击其他地方关闭modal
			keyboard: false		//屏蔽按键（如ESC）
		}
	);
	
	//弹出页面后进行验证的初始化操作
	$('#' + modalId).on("shown.bs.modal", function() {
		var ids = $('form:not(".validated")').attr('id');
		$('form:not(".validated")').each(function(index, ele){
			var formId = $(ele).attr('id');
			if(formId != null){
				initValidateForm(formId);
			}
		});
	});
	
	//在modal隐藏（关闭）时清除里面的内容，防止第二次打开时调用前一次的内容
	$('#' + modalId).on("hidden.bs.modal", function() {
	    $(this).removeData("bs.modal");
	    $(this).find(".modal-content").empty();
	    if(tableId != null ){
	    	try{
	    		$('#' + tableId).bootstrapTable('refresh');
	    	}catch(e){}
	    }
	    
	    $('#' + modalId).off().on('hidden.bs.modal');  //去除上面绑定的事件，避免一个modal绑定超过1个钩子
	});
}

/**
 * 打开modal关闭时触发eventName函数
 * @param url
 * @param modalId
 * @param eventName
 */
function openModalHandler(url, modalId, eventName){
	url = url + ";jsessionid=" + getSessionId();	//IE下不支持下面的清除操作，添加jsessionid避免出现缓存问题，使用随机数、时间戳也可以。
	$('#' + modalId).modal(
		{
			remote : url,
			backdrop: 'static', //屏蔽点击其他地方关闭modal
			keyboard: false		//屏蔽按键（如ESC）
		}
	);
	
	//弹出页面后进行验证的初始化操作
	$('#' + modalId).on("shown.bs.modal", function() {
		var ids = $('form:not(".validated")').attr('id');
		$('form:not(".validated")').each(function(index, ele){
			var formId = $(ele).attr('id');
			if(formId != null){
				initValidateForm(formId);
			}
		});
	});
	
	//在modal隐藏（关闭）时清除里面的内容，防止第二次打开时调用前一次的内容
	$('#' + modalId).on("hidden.bs.modal", function() {
	    $(this).removeData("bs.modal");
	    $(this).find(".modal-content").empty();
	    if(eventName != null ){
	    	try{
	    		eval(eventName + '()');
	    	}catch(e){}
	    }
	    $('#' + modalId).off().on('hidden.bs.modal');  //去除上面绑定的事件，避免一个modal绑定超过1个钩子
	});
}


/**
 * 打开modal，tree定制版，关闭modal后treeid存在则刷新树，刷新节点
 * @param url 
 * @param modalId 页面modalid
 * @param treeId	待刷新的树id
 * @param freshNode 待刷新的节点（缺失默认刷新整棵树），否则刷新该节点下的子节点
 */
function openModalForTree(url, modalId, treeId, freshNode){
	url = url + ";jsessionid=" + getSessionId();
	var freshNode = freshNode == null?null:freshNode;
	$('#' + modalId).modal(
		{
			remote : url,
			backdrop: 'static', //屏蔽点击其他地方关闭modal
			keyboard: false		//屏蔽按键（如ESC）
		}
	);
	
	//弹出页面后进行验证的初始化操作
	$('#' + modalId).on("shown.bs.modal", function() {
		var ids = $('form:not(".validated")').attr('id');
		$('form:not(".validated")').each(function(index, ele){
			var formId = $(ele).attr('id');
			if(formId != null){
				initValidateForm(formId);
			}
		});
	});
	
	//在modal隐藏（关闭）时清除里面的内容，防止第二次打开时调用前一次的内容
	$('#' + modalId).on("hidden.bs.modal", function() {
	    $(this).removeData("bs.modal");
	    $(this).find(".modal-content").empty();
	    //刷新树
	    if(treeId != null ){
	    	try{
	    		var currentTree = $.fn.zTree.getZTreeObj(treeId);
	    		//刷新节点，
	    		currentTree.reAsyncChildNodes(freshNode, 'refresh');
	    	}catch(e){}
	    }
	    $('#' + modalId).off().on('hidden.bs.modal');  //去除上面绑定的on事件，钩子事件过多会在触发其他操作时重复
	});
	
    
}


//批量删除
function deleteBatch(url, tableId){
	var rows = $('#' + tableId).bootstrapTable('getSelections');	//获取所选行信息
	var ids = [];
	if (rows.length > 0) {
		for (var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		var idParam = decodeURIComponent($.param({
			ids : ids
		}, true));

		layer.confirm('确定要删除选中的'+rows.length+'条数据么？',{
			title:'删除操作',
			icon:3
		},function (index) {
			layer.close(index);
			$.post(url,idParam, function(data) {
				if (data) {
					if(data.success){	//删除成功，刷新table，关闭dialog
						$('#' + tableId).bootstrapTable('refresh');
					}
					showMsg(data.message);
				}
			}, 'JSON');
		});
	} else {
		showMsg('请选择要删除的记录！');
	}
}
//普通alert样式，type样式一下所示
function showAlertMsg(mes,type){  
	var typestr=BootstrapDialog.TYPE_DEFAULT;//没有传入type，使用默认样式
	if(type){
		if(type=='TYPE_DEFAULT'){
			typestr=BootstrapDialog.TYPE_DEFAULT;
		}else if(type=='TYPE_INFO'){
			typestr=BootstrapDialog.TYPE_INFO;
		}else if(type=='TYPE_PRIMARY'){
			typestr=BootstrapDialog.TYPE_PRIMARY;
		}else if(type=='TYPE_SUCCESS'){
			typestr=BootstrapDialog.TYPE_SUCCESS;
		}else if(type=='TYPE_WARNING'){
			typestr=BootstrapDialog.TYPE_WARNING;
		}else if(type=='TYPE_DANGER'){
			typestr=BootstrapDialog.TYPE_DANGER;
		}else{
			typestr=BootstrapDialog.TYPE_DEFAULT;
		} 
	}  
	BootstrapDialog.show({
		title: '提示',
		type:typestr,
        message: mes
    }); 
}

//封禁单条用户信息
function blockedUser(url, id, delName, tableId){
	BootstrapDialog.show({
		title: '警告',
		type:BootstrapDialog.TYPE_DANGER,
        message: '确定要封禁' + delName + '吗？',
        buttons: [{
            label: '确认',
            cssClass: 'btn-danger',
            action: function(dialogItself){
            	$.post(url, {
            		ids : id
    			}, function(data) {
    				//data = $.parseJSON(data);	//下面已经将返回结果转换为json了，再转换将出异常
        			if (data) {
        				if(data.success){	//删除成功，刷新table，关闭dialog
        					$('#' + tableId).bootstrapTable('refresh');
                            dialogItself.close();
                            showMsg(data.message);
        				}
        				
        			} else {
        				//后台都未获取数据
        			}
        		}, 'JSON');
            }
        },{
            label: '取消',
            action: function(dialogItself){
                dialogItself.close();
            }
        }]
    });
}
//解除封禁单条用户信息
function unblockedUser(url, id, delName, tableId) {
	BootstrapDialog.show({
		title : '警告',
		type : BootstrapDialog.TYPE_DANGER,
		message : '真的要解封' + delName + '吗？',
		buttons : [
				{
					label : '确认',
					cssClass : 'btn-danger',
					action : function(dialogItself) {
						$.post(url, {
							ids : id
						}, function(data) {
							if (data) {
								if (data.success) { //删除成功，刷新table，关闭dialog
									$('#' + tableId).bootstrapTable('refresh');
									dialogItself.close();
									showMsg(data.message);
								}

							} else {
								//后台都未获取数据
							}
						}, 'JSON');
					}
				}, {
					label : '取消',
					action : function(dialogItself) {
						dialogItself.close();
					}
				} ]
	});
}

/**
 * 删除单条信息
 * 
 */
function deleteConfirm(url, id, tableId){
	layer.confirm('确定要执行删除操作吗？',{
		title:'删除操作',
		icon:3
	},function (index) {
		layer.close(index);
		$.post(url, {
			ids : id
		}, function(data) {
			if (data) {
				if(data.success){	//删除成功，刷新table，关闭dialog
					$('#' + tableId).bootstrapTable('refresh');
				}

				showMsg(data.message);
			} else {
				//后台都未获取数据
			}
		}, 'JSON');
	});
}

/**
 * 初始化密码为123456
 * 
 */
function refreshPasswordConfirm(url, id, tableId){
	BootstrapDialog.show({
		title: '警告',
		type:BootstrapDialog.TYPE_DANGER,
        message: '真的要重置密码为“123456”吗？',
        buttons: [{
            label: '确认',
            cssClass: 'btn-danger',
            action: function(dialogItself){
            	$.post(url, {
            		id : id
    			}, function(data) {
        			if (data) {
        				if(data.success){	//删除成功，刷新table，关闭dialog
        					$('#' + tableId).bootstrapTable('refresh');
                            dialogItself.close();
                            showMsg(data.message);
        				}
        				
        			} else {
        				//后台都未获取数据
        			}
        		}, 'JSON');
            }
        },{
            label: '取消',
            action: function(dialogItself){
                dialogItself.close();
            }
        }]
    });
}

/*
 * 可传信息，非敏感操作，单条信息确认
 * 
 */
function onedataConfirm(url, id, tableId,msg){
	BootstrapDialog.show({
		title: '提示',
		type:BootstrapDialog.TYPE_WARNING,
        message: msg,
        buttons: [{
            label: '确认',
            cssClass: 'btn-primary',
            action: function(dialogItself){
            	$.post(url, {
            		id : id
    			}, function(data) {
    				//data = $.parseJSON(data);	//下面已经将返回结果转换为json了，再转换将出异常
        			if (data) {
        				if(data.success){	//删除成功，刷新table，关闭dialog
        					$('#' + tableId).bootstrapTable('refresh');
                            dialogItself.close();
        				}
        				showMsg(data.message);
        				
        			} else {
        				//后台都未获取数据
        			}
        		}, 'JSON');
            }
        },{
            label: '取消',
            action: function(dialogItself){
                dialogItself.close();
            }
        }]
    });
}
/**
 * serializeJson方法，将form或其他对象序列化成json对象，一般form提交时使用
 * @param $
 */
(function($){  
    $.fn.serializeJson=function(){  
        var serializeObj = {};
        $(this.serializeArray()).each(function() {
          if (this['value'] != undefined && this['value'].length > 0) {// 如果表单项的值非空，才进行序列化操作
            if (serializeObj[this['name']]) {
              serializeObj[this['name']] = serializeObj[this['name']] + "," + this['value'];//相同name的数据用逗号分隔
            } else {
              serializeObj[this['name']] = this['value'];
            }
          }
        });
        return serializeObj;
    };  
    
    var ids = $(this).find('form:not(".validated")').attr('id');
    if(ids != null){
    	for(var i = 0;i<ids.length;i++){
	    	initValidateForm(ids[i]);
	    }
    }
})(jQuery);
/**
 * 替换@nickname为到个人主页的链接
 * @param ctx
 */
function convertAtNickname(ctx){
	$(".at-nickname").each(function(){
		var self = $(this);
    	var str = $(this).text();
    	var arr = str.match(/@\S+/g);
    	var url = ctx+"/front/convertAtNickname";
    	if(arr && arr.length>0){
    		var params = decodeURIComponent($.param({
				"nicknames" : arr
			}, true));
    		$.post({
    			url : url,
    			data : params,
    			success : function(data){
    				if(data){
    					var result = $.parseJSON(data);
    					$.each(result,function(index,val){
    						var link = "javascript:void(0);";
    						var replaceHtml = "<a href='"+link+"'  style='color:#46c37b;'>"+index+"</a>";
    						str = str.replace(index,replaceHtml);
    					});
    					self.html(str);
    				}
    			}
    		});
    	}
    });
}

/**
 * 右下角显示信息，淡蓝色方块
 * @param msg
 */
function showMsg(msg, type, offset){
	layer.msg(msg,{time:1000});
}


/**
 * 验证并提交表单
 * @param formId 待验证并提交的表单id
 * @param modalId 待关闭的modal id
 * @param url
 * @returns
 */
function validateAndSubmitForm(formId, btnThis, eventName){
	var formSelector = $("#" + formId);
	var isValid = validateForm(formId);	//验证表单 
	if(isValid){
		$.ajax({
			cache: true,
			type: 'POST',  
            url: formSelector.attr("action") ,  	//默认使用form的action
            data : formSelector.serialize(),	//序列化表单
            async: true,
			dataType: "json",
            success: function(data){  
            	if(data.success){
            		//根据按钮id获取modal的id
            		var modalId = $(btnThis).parents().filter(".modal:first").attr("id");
            		$("#" + modalId).modal("hide");	//隐藏modal
                   	//$('#' + tableName).bootstrapTable('refresh');	//modal的form提交只提供formid以及modalId即可，刷新table放到modal关闭事件中
            	}
               	showMsg(data.message);
               	// validateAndSubmitFormCallBack存在且是function
               	if( typeof validateAndSubmitFormCallBack === 'function' ){
                    validateAndSubmitFormCallBack(data);
               	}
               	
               	//触发的回调函数
               	if(eventName != null && eventName != ''){
               		eval(eventName + '(data)');
               	}
            },  
            error: function(XmlHttpRequest, textStatus, errorThrown){  
                alert("提交失败");  
            }
		});
	}
}
/**
 * 验证并提交表单
 * @param formId 待验证并提交的表单id
 * @param modalId 待关闭的modal id
 * @param url
 * @returns
 */
function submitForm(formId, btnThis, eventName){
	var formSelector = $("#" + formId);
		$.ajax({
			cache: true,
			type: 'POST',  
			url: formSelector.attr("action") ,  	//默认使用form的action
			data : formSelector.serialize(),	//序列化表单
			async: true,
			success: function(data){  
				data = $.parseJSON(data);            	
				if(data.success){
					//根据按钮id获取modal的id
					var modalId = $(btnThis).parents().filter(".modal:first").attr("id");
					$("#" + modalId).modal("hide");	//隐藏modal
					//$('#' + tableName).bootstrapTable('refresh');	//modal的form提交只提供formid以及modalId即可，刷新table放到modal关闭事件中
				}
				showMsg(data.message);
				// validateAndSubmitFormCallBack存在且是function
				if( typeof validateAndSubmitFormCallBack === 'function' ){
					validateAndSubmitFormCallBack(data);
				}
				
				//触发的回调函数
				if(eventName != null && eventName != ''){
					eval(eventName + '(data)');
				}
			},  
			error: function(XmlHttpRequest, textStatus, errorThrown){  
				alert("提交失败");  
			}
		});
}

/**
 * 验证并提交表单
 * @param formId 待验证并提交的表单id
 * @returns
 */
function validateAndSubmit(formId){
	var formSelector = $("#" + formId);
	var isValid = validateForm(formId);	//验证表单 
	if(isValid){
		$.ajax({
			type: 'POST',  
            url: formSelector.attr("action") ,  	//默认使用form的action
            data : formSelector.serialize(),	//序列化表单
            dataType: 'JSON',
            success: function(data){  
               	showMsg(data.message);
            },  
            error: function(XmlHttpRequest, textStatus, errorThrown){  
                alert("提交失败");  
            }
		});
	}
}


function initValidateForm(formId){
	
	if($("#" + formId).hasClass("validated")){
		return ;
	}
	//初始化
	$("#"+formId).validate(
		/*{onclick: true,
        onfocusout: true,  
        onkeyup: true  }*/
		{
			onfocusout: function(element){
		        $(element).valid();
		    },
		    onkeyup: function(element){
		        $(element).valid();
		    },
		    onclick: function(element){
		        $(element).valid();
		    }
		}
     );
	//查找form中class中含有validate的元素，获取其data-options内容，将其赋值给class
	$("#" + formId).find(".validate").each(function(index, e){
		var options = $(this).data('options');
		//没有data-options的不添加校验配置
		if(options != null){
			var classes = "{" + options + "}";
			$(this).addClass(classes);
		}
		
	});
	
	$("#" + formId).addClass("validated");
}
/**
 * 验证表单
 * @param formId
 * @returns
 */
function validateForm(formId){
	//避免没有初始化校验配置
	initValidateForm(formId);
	return $("#" + formId).valid();
}
/**
 * Table中操作区有下拉内容的情况，初始化下拉监听事件来控制滚动条
 * @param bsTable
 */
function initTableForDropMenu(bsTable){
	
	var table_body = bsTable.parents(".fixed-table-body");
	table_body.on('show.bs.dropdown', function () {
		if(table_body.scrollTop()>0 || table_body.scrollLeft() > 0){
		} else {
			table_body.css( "overflow", "inherit" );
		}
	});

	table_body.on('hide.bs.dropdown', function () {
		table_body.css( "overflow", "auto" );
	});
}

/**
 * 格式化操作
 * @param str
 * @returns
 */
formatString = function(str) {
	for ( var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

/**
 * 计算传入时间到当前时间的时间差
 * @param timeStr
 * @returns
 */
function calculateTimeDiff(timeStr){
	if(!timeStr){
		return ' ';
	}
	var nowTime = new Date().getTime();
	var preTime = new Date(timeStr.replace(/-/g,"/")).getTime();
	var timediff = nowTime-preTime;
	//相差年数
	var yeardiff = timediff/(365*24*60*60*1000);
	if(yeardiff >= 1){
		return Math.round(yeardiff)+"年前";
	}
	//相差月数
	var monthdiff = timediff/(30*24*60*60*1000);
	if(monthdiff >= 1){
		return Math.round(monthdiff)+"个月前";
	}
	//相差天数
	var daydiff = timediff/(24*60*60*1000);
	if(daydiff >= 1){
		return Math.round(daydiff)+"天前";
	}
	//相差小时数
	var hourdiff = timediff/(60*60*1000);
	if(hourdiff >= 1){
		return Math.round(hourdiff)+"小时前";
	}
	//相差分钟数
	var minutediff = timediff/(60*1000);
	if(minutediff >= 1){
		return Math.round(minutediff)+"分钟前";
	}
	//相差秒数
	var seconddiff = timediff/1000;
	if(seconddiff>=1){
		return Math.round(seconddiff)+"秒前";
	}
	return "刚刚";
}

/**
 * 获取sessionId，用于一些需要唯一标识作为参数的情况，如URL请求，添加sessionId避免缓存
 * //TODO  xy **20161029**360下可以通过代码获取sessionId，但部分IE下获取为undefined，暂时先使用时间戳代替
 * @returns
 */
function getSessionId(){
	var timestamp=new Date().getTime();
	return timestamp;
}
//去除null，undefined等数据
function replaceNull(str){
	str = str+"";
	if(str){
		if(str==undefined || str=='undefined' || str == "null" || str == null || str == ""){
			str = " ";
		}
	}else{
		str = " ";
	}
	return str;
}

//添加下拉按钮
var addBtns = function(){
	var btns = null;
	this.addBtn = function(title, funEvent){
		if(btns == null){
			btns = new Array()
		}

		var o = new Object();
		o.title = title;
		o.funEvent = funEvent;
		btns.push(o);
	};
	this.formatBtns = function(){
		
		var code = '<div class="btn-group"> ';
		code += '		<a ' + btns[0].funEvent + ' class="btn btn-default btn-sm">' + btns[0].title + '</a>';

		code += '  <a href="#" type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">';
		code += '    <span class="caret"></span> ';
		code += '  </a>';
		if(btns.length>1){
			code += '<ul class="dropdown-menu">';
    		for(var i = 1; i<btns.length;i++){
    			code += '<li><a ' + btns[i].funEvent + ' >' + btns[i].title + '</a></li>';
    		}

    		code += '</ul>';
		}

		code += '</div>';

		return code;
	}

}

//处理下拉按钮信息
function formatBtns(str, params){
	for( var i = 0; i < params.length - 1; i++) {
		str = str.replace("{" + i + "}", params[i + 2]);
	}
	return str;
}

//限制展示的字符长度，num可不传，默认50
function shortContent(content,num){
	if(!num){
		num = 50;
	}
	if(content.length > parseInt(num)){
		return content.substr(0,parseInt(num))+"...";
	} else {
		return content;
	}
}

//判断是否是合法的URL书写类型
function checkURL(urlStr){
	var str = urlStr;
	//下面的代码中应用了转义字符"\"输出一个字符"/"
	var expression=/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
	var objExp=new RegExp(expression);
	if(objExp.test(str) == true){
		return true;
	}else{
		return false;
	}
}

//验证手机号是否合法
function checkPhone(phone){
	var mobileRegex = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
	var result = mobileRegex.test(phone);
	return result;
}
/**
 * 显示信息，淡蓝色方块
 * @param msg
 */
function showMessageByAlign(msg, type, offset,align){
	var type = type==null?"info":type;
	var offset = offset==null?{from: 'bottom', amount: 20}:offset;
	$.bootstrapGrowl(msg, 
		{
		  ele: 'body', // which element to append to
		  type: type, // (null, 'info', 'danger', 'success')，三种显示样式，INFO（天蓝色），danger（淡红色），success（淡绿色）
		  offset: offset, // 'top', or 'bottom'
		  align: align, // ('left', 'right', or 'center')
		  width: 'auto', // (integer, or 'auto')
		  delay: 2000,	//延迟4s，即4s后消失
		  allow_dismiss: true,
		  stackup_spacing: 10 // spacing between consecutively stacked growls.
		}
	);
}
//去除ckeditor中的一些特殊内容，为了计算文本长度
function getContentForLength(ckeditorContent){
	var len = '';
	//处理pre
	var pres = ckeditorContent.match(/<pre*.?>(.*?)<\/pre>/g);
		ckeditorContent = ckeditorContent.replace(/<pre*.?>(.*?)<\/pre>/g,"");
	if(pres)
	    len += pres.join("").length;
		len += ckeditorContent
	    .replace(/\s+/g,"")               //将多个空字符换成空
	    .replace(/<br\s*?\/?>/g,".")       //将所有换行符替换成一个字符(不用\n是因为可能会被后面换掉)
	    .replace(/(<\/p>)/g,"$1")         //为所有段落添加一个字符(或两个字符，自己定)将点放在前面避免影响后面的替换
	    .replace(/<\/.+?>\s*<[^\/]>/g,"") //去掉所有尾-首相连的HTML标签(包括中间的空字符)
	    .replace(/<.+?>/g,"")             //去掉剩下的HTML标签
	    .replace(/&.+?;/g,"")            //转换所有实体为一个字符
	    .replace(" ", "");
	return len;
	
}
function isDigits(value){
	if(value) {
		if (!(/(^[1-9]\d*$)/.test(value))) {
			return false;
		}else {
			return true;
		}
	}
	return false;
}

$.fn.modal.Constructor.prototype.enforceFocus = function () {     
	modal_this = this;          
	$(document).on('focusin.modal', function (e) {  
	if (modal_this.$element[0] !== e.target 
	&& !modal_this.$element.has(e.target).length                  
	&& !$(e.target.parentNode).hasClass('cke_dialog_ui_input_select') 
	&& !$(e.target.parentNode).hasClass('cke_dialog_ui_input_text')) { 
	modal_this.$element.focus();                
	} 
	});        
	};