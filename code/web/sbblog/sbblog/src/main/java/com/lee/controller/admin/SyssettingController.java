package com.lee.controller.admin;


import com.lee.common.DateTimeUtil;
import com.lee.common.Message;
import com.lee.entity.Syssetting;
import com.lee.service.SyssettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Time;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lee
 * @since 2020-02-24
 */
@Controller
@RequestMapping("/admin/syssetting")
public class SyssettingController {
    @Autowired
    SyssettingService syssettingService;

    @GetMapping("add")
    public String add(Model model){
        Syssetting syssetting =  syssettingService.getById(1);
        if(syssetting != null) {
            model.addAttribute("syssetting",syssetting);
        }
        return "admin/syssetting/add";
    }
    @PostMapping("add")
    @ResponseBody
    public Message doAdd(@Validated Syssetting syssetting){
        try{
            Syssetting syssettingInfo = syssettingService.getById(1);
            if (syssettingInfo == null) {
                syssettingInfo = new Syssetting();
                syssettingInfo.setCopyright(syssetting.getCopyright());
                syssettingInfo.setWebname(syssetting.getWebname());
                syssettingInfo .setCreateTime(DateTimeUtil.nowTimeStr());
                syssettingInfo.setSign(syssetting.getSign());
                syssettingService.save(syssettingInfo);
            } else {
                syssettingInfo.setCopyright(syssetting.getCopyright());
                syssettingInfo.setWebname(syssetting.getWebname());
                syssettingInfo .setUpdateTime(DateTimeUtil.nowTimeStr());
                syssettingInfo.setSign(syssetting.getSign());
                syssettingService.updateById(syssettingInfo);
            }
            return Message.success("系统设置保存成功");
        } catch (Exception e) {
            return Message.fail("系统设置保存异常!");
        }
    }
}
