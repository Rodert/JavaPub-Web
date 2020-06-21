package com.lee.controller.admin;

import com.lee.entity.Admin;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础控制类
 *
 */
public class BasicController {

    public Admin getCurrentUser(HttpServletRequest request){
        Admin admin = (Admin)request.getSession().getAttribute("admin");
        return admin;
    }

}
