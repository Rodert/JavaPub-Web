//validate扩展方法，不足的话可以继续扩展

$(function(){
	// 手机号码验证
	$.validator.addMethod("phone", function(value, element) {
		var length = value.length;
		var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
		return this.optional(element) || (length == 11 && mobile.test(value));
	}, "请输入正确的手机号码");
	
	$.validator.addMethod("passwordSet",function(value,element,params){
		var reg = /^[\w.]{6,20}$/;
		return this.optional(element) || reg.test(value);
	},"请输入正确的密码格式");
	
	// 身份证
    $.validator.addMethod("idnumber", function(value, element) {
        var length = value.length;
        //18位身份证号码的正则表达式
        var regIdCard=/^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
        var result = false;
        //如果通过该验证，说明身份证格式正确，但准确性还需计算
        return this.optional(element) || (length == 18 && regIdCard.test(value));
    }, "请输入正确的身份证");
    
    //非中文字符
    $.validator.addMethod("unChinese", function(value, element){
    	var result = false;
    	var reg = /([\u4E00-\u9FA5])/;
    	var result = value.match(reg) != null;
    	 
    	return !result;
    }, "请输入非中文字符");
    
    //登录账号  1-10个以字母开头、可带数字、"_"的字串 
    $.validator.addMethod("loginName", function(value, element){
    	var patrn=/^[a-zA-Z]{1}([a-zA-Z0-9]|[_]){0,9}$/;  
    	if (!patrn.exec(value)){
    		return false
    	}
    	return true 
    	
    }, "请输入正确的登录账号");
    
    //昵称  1-10个以字母开头、可带数字、"_"、汉字的字串 
    $.validator.addMethod("nickname", function(value, element){
    	var patrn=/^([a-zA-Z0-9]|[_]|[\u4E00-\uFA29]|[\uE7C7-\uE7F3]){1,10}$/;  
    	if (!patrn.exec(value)){
    		return false
    	}
    	return true 
    	
    }, "请输入正确的昵称格式");
    
    
    //邮箱验证
    $.validator.addMethod("email", function(value, element){
    	var patrn = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
    	var result = true;
    	if (!patrn.exec(value)){
    		result = false
    	}
    	return this.optional(element) || result; 
    	
    }, "请输入正确的邮箱格式");
});