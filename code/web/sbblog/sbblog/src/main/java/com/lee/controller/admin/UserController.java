package com.lee.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.DataGrid;
import com.lee.common.DateTimeUtil;
import com.lee.common.Message;
import com.lee.entity.Admin;
import com.lee.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/user")
public class UserController extends BasicController {
    @Autowired
    AdminService adminService;
    /**
     * 管理员管理列表页面跳转
     * @return
     */
    @RequestMapping("list")
    public String list(HttpServletRequest request){
        //TODO 超级管理员权限控制
        if(getCurrentUser(request).getIsSupper() == 1) {
            return "admin/user/list";
        } else {
            return "admin/home";
        }

    }

    /**
     * 加载列表数据
     * @param jsonObject
     * @return
     */
    @RequestMapping("findList")
    @ResponseBody
    public  DataGrid findList(@RequestBody JSONObject jsonObject){
        //获取查询参数
        Map<String, Object> searchParams = (HashMap<String, Object>)jsonObject.get("search");
        int offset = "".equals(jsonObject.getString("offset"))?0:jsonObject.getIntValue("offset");
        int size = "".equals(jsonObject.getString("limit"))?10:jsonObject.getIntValue("limit");

        IPage<Map<String, Object>> page = adminService.getPageInfo(searchParams,offset,size);
        DataGrid result = new DataGrid();
        result.setRows(page.getRecords());
        result.setTotal(page.getTotal());
        return result;
    }

    /**
     * 管理员添加页面跳转
     * @return
     */
    @GetMapping("add")
    public String add(){

        return "admin/user/add";
    }
    @PostMapping("add")
    @ResponseBody
    public Message doAdd(@Validated Admin admin,@RequestParam(value = "confirmpassword") String confirmpassword){
        try{
            //TOTO 可以在service里添加条件验证规则
            if(adminService.getByUsername(admin.getUsername()) != null) {
                return Message.fail("该用户已存在！");
            }
            if(!confirmpassword.equals(admin.getPassword())) {
                return Message.fail("密码和确认密码不一致！");
            }
            admin.setCreateTime(DateTimeUtil.nowTimeStr());
            adminService.save(admin);
            return Message.success("管理员创建成功！");
        } catch (Exception e) {
            return Message.fail("创建保存异常！");
        }
    }


    /**
     * 用户名重复验证
     * @param username
     * @return
     */
    @PostMapping("check")
    @ResponseBody
    public boolean check(@RequestParam String username){
        if(StringUtils.isEmpty(username)){
            return false;
        }
        Admin admin = adminService.getByUsername(username);
        if(admin != null) {
            return false;
        }
        return true;
    }

    @PostMapping("status")
    @ResponseBody
    public Message status(@RequestParam Integer id){
        try {
            Admin admin = adminService.getById(id);
            if (admin != null) {
                String message = admin.getStatus() == 0 ? "管理员禁用成功！" : "管理员启用成功！";
                admin.setStatus(admin.getStatus() == 0 ? 1 : 0);
                adminService.updateById(admin);
                return Message.success(message);
            } else {
                return Message.fail("管理员信息不存在或已被删除！");
            }
        }catch ( Exception e) {
            return Message.fail("数据操作异常");
        }
    }

    /**
     * 修改页面跳转
     */
    @GetMapping("update/{id}")
    public String update(@PathVariable Integer id, Model model){
        Admin admin = adminService.getById(id);
        if(admin != null) {
            model.addAttribute("adminInfo",admin);
        } else {
            //TODO 对象已删除，跳转到错误页面
        }
        return "admin/user/update";
    }

    /**
     * 管理员修改数据提交
     * @param admin
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public Message doUpdate(@Validated Admin admin, HttpServletRequest request){
        try {
            String oldpassword = request.getParameter("oldpassword");
            String newpassword = request.getParameter("newpassword");
           Admin adminInfo = adminService.getById(admin.getId());
           if(StringUtils.isEmpty(newpassword)) {//TODO 增加密码规则验证
               return Message.fail("新密码不能为空");
           }
           if(adminInfo != null) {
               if(oldpassword.equals(adminInfo.getPassword())) {
                   adminInfo.setPassword(newpassword);
                   adminInfo.setNickname(admin.getNickname());
                   adminInfo.setEmail(admin.getEmail());
                   adminInfo.setIsSupper(admin.getIsSupper());
                   adminInfo.setUpdateTime(DateTimeUtil.nowTimeStr());
                   adminService.updateById(adminInfo);
                   return Message.success("管理员信息修改成功");
               } else {
                   return Message.fail("原密码输入错误");
               }
           } else {
               return Message.fail("管理员不存在或已被删除");
           }
        } catch (Exception e) {
            return Message.fail("修改失败");
        }

    }

    @GetMapping("view/{id}")
    public String view(@PathVariable Integer id,Model model){
        Admin adminInfo = adminService.getById(id);
        if(adminInfo != null) {
            model.addAttribute("adminInfo",adminInfo);
        } else{
            //TODO 跳转错误页面
        }
        return "admin/user/view";
    }
    @PostMapping("delete")
    @ResponseBody
    public Message delete(@RequestParam(value = "ids") List<Integer> ids){
        try {
            for (int id :ids) {
                adminService.removeById(id);
            }
            return Message.success("删除成功");
        } catch (Exception e){
           return Message.fail("删除异常！");
        }
    }
}
