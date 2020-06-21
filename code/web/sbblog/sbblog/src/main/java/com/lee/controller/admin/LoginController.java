package com.lee.controller.admin;

import com.lee.common.Message;
import com.lee.entity.Admin;
import com.lee.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class LoginController extends BasicController {
    @Autowired
    AdminService sbAdminService;

    @GetMapping("login")
    public String login(){
        return "admin/login";
    }
    @PostMapping("/login")
    @ResponseBody
    public Message doLogin(@RequestParam("username")String username,
                           @RequestParam("password")String password,
                           HttpServletRequest request){
        if(StringUtils.isEmpty(username)) {
            return Message.fail("用户名不能为空");
        }
        if(StringUtils.isEmpty(password)) {
            return Message.fail("密码不能为空");
        }

        Admin sbadmin = sbAdminService.getInfoByUsernameAndPassword(username,password);
        if(sbadmin == null) {
            return Message.fail("用户不存在或用户名密码错误");
        }
        if(sbadmin.getStatus() != 0) {
            return Message.fail("该账号已被禁用!");
        }
        request.getSession().setAttribute("admin",sbadmin);
        return Message.success("登录成功!",sbadmin,"/admin/home");

    }
}
