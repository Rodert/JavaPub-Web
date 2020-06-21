package com.lee.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.DataGrid;
import com.lee.common.DateTimeUtil;
import com.lee.common.Message;
import com.lee.entity.Admin;
import com.lee.entity.Member;
import com.lee.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lee
 * @since 2020-02-23
 */
@Controller
@RequestMapping("/admin/member")
public class MemberController {
    @Autowired
    MemberService memberService;
    @GetMapping("list")
    public String list(){
        return "admin/member/list";
    }
    /**
     * 加载列表数据
     */
    @PostMapping("findList")
    @ResponseBody
    public DataGrid findList(@RequestBody JSONObject jsonObject) {
        Map<String, Object> searchParams = (HashMap<String, Object>) jsonObject.get("search");
        int offset = "".equals(jsonObject.getString("offset")) ? 0 : jsonObject.getIntValue("offset");
        int size = "".equals(jsonObject.getString("limit")) ? 10 : jsonObject.getIntValue("limit");

        IPage<Map<String, Object>> page = memberService.getPageInfo(searchParams, offset, size);

        DataGrid result = new DataGrid();
        result.setTotal(page.getTotal());
        result.setRows(page.getRecords());
        return result;
    }
    @GetMapping("add")
    public String add(){
        return "admin/member/add";
    }

    @PostMapping("add")
    @ResponseBody
    public Message doAdd(@Validated Member member){
        try{
            if(memberService.getByUsername(member.getUsername()) != null) {
                return Message.fail("会员名已存在");
            }
            member.setCreateTime(DateTimeUtil.nowTimeStr());
            memberService.save(member);
            return Message.success("会员创建成功");
        } catch (Exception e){
            return Message.fail("会员新增异常，保存失败！");
        }
    }

    @GetMapping("update/{id}")
    public String update(@PathVariable Integer id, Model model){
       Member member =  memberService.getById(id);
       if(member != null) {
           model.addAttribute("memberInfo",member);
       }
       return "admin/member/update";
    }
    @PostMapping("update")
    @ResponseBody
    public Message doUpdate(Member member){
        member.getId();
        try {
            Member memberInfo = memberService.getById(member.getId());
            if(memberInfo != null) {
                if(!StringUtils.isEmpty(member.getPassword())){
                   memberInfo.setPassword(member.getPassword());
                }
                if(StringUtils.isEmpty(member.getNickname())) {
                    return Message.fail("会员昵称不能为空！");
                } else {
                    memberInfo.setNickname(member.getNickname());
                }
                if(StringUtils.isEmpty(member.getEmail())) {
                    return Message.fail("会员邮箱不能为空！");
                } else {
                    memberInfo.setEmail(member.getEmail());
                }
                memberInfo.setUpdateTime(DateTimeUtil.nowTimeStr());
                memberService.updateById(memberInfo);
                return Message.success("会员修改成功！");
            } else {
                return Message.fail("会员信息不存在或已被删除");
            }
        } catch (Exception e) {
            return Message.fail("会员修改操作异常，修改失败");
        }
    }


    @PostMapping("check")
    @ResponseBody
    public boolean check(@RequestParam(value = "username") String username){
        List<Member> memberList = memberService.getByUsername(username);
        if(memberList != null ) {
            return false;
        } else {
            return true;
        }
    }

    @PostMapping("delete")
    @ResponseBody
    public Message delete(@RequestParam(value = "ids") List<Integer> ids){
        try {
            for (int id :ids) {
                memberService.removeById(id);
            }
            return Message.success("删除成功");
        } catch (Exception e){
            return Message.fail("删除异常！");
        }
    }

}
