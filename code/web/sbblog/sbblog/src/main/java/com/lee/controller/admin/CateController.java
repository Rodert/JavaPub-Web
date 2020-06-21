package com.lee.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.DataGrid;
import com.lee.common.DateTimeUtil;
import com.lee.common.Message;
import com.lee.entity.Cate;
import com.lee.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lee
 * @since 2020-02-21
 */
@Controller
@RequestMapping("/admin/cate")
public class CateController extends BasicController {
    @Autowired
    CateService cateService;

    /**
     * 栏目列表
     *
     * @return
     */
    @GetMapping("list")
    public String list() {

        return "admin/cate/list";
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

        IPage<Map<String, Object>> page = cateService.getPageInfo(searchParams, offset, size);

        DataGrid result = new DataGrid();
        result.setTotal(page.getTotal());
        result.setRows(page.getRecords());
        return result;
    }

    /**
     * 添加页面跳转
     */
    @RequestMapping("add")
    public String add() {
        return "admin/cate/add";
    }

    @PostMapping("add")
    @ResponseBody
    public Message doAdd(@Validated Cate cate) {
        try {
            cate.setCreateTime(DateTimeUtil.nowTimeStr());
            List<Cate> exists = cateService.getByCatename(cate.getCatename());
            if (exists != null) {
                return Message.fail("栏目名称已存在，保存失败！");
            }
            cateService.save(cate);
            return Message.success("栏目保存成功");
        } catch (Exception e) {
            return Message.fail("栏目保存异常");
        }
    }

    @PostMapping("check")
    @ResponseBody
    public boolean check(@RequestParam(value = "catename") String catename) {
        if (!StringUtils.isEmpty(catename)) {
            List<Cate> cates = cateService.getByCatename(catename);
            if (cates == null) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("update/{id}")
    public String update(@PathVariable Integer id, Model model) {
        Cate cateInfo = cateService.getById(id);
        if (cateInfo != null) {
            model.addAttribute("cateInfo", cateInfo);
        }
        return "admin/cate/update";
    }

    @PostMapping("update")
    @ResponseBody
    public Message update(@Validated Cate cate) {
        try {
            Cate cateInfo = cateService.getById(cate.getId());
            if (cateInfo != null) {
                cateInfo.setCatename(cate.getCatename());
                cateInfo.setUpdateTime(DateTimeUtil.nowTimeStr());
                List<Cate> existCates = cateService.getByCatename(cateInfo.getCatename());
                if ((existCates == null) || (existCates != null && existCates.size() == 1 && existCates.get(0).getId() == cateInfo.getId())) {
                    cateService.updateById(cateInfo);
                    return Message.success("栏目更新成功！");
                } else {
                    return Message.fail("栏目名称已存在！");
                }
            } else {
                return Message.fail("当前栏目不存在或已被删除，更新失败！");
            }
        } catch (Exception e) {
            return Message.fail("栏目更新操作异常，更新失败！");
        }
    }

    @PostMapping("delete")
    @ResponseBody
    public Message delete(@RequestParam(value = "ids") List<Integer> ids) {
        try {
            for (int id : ids) {
                cateService.deleteCateAndArticleAndCommentById(id);
            }
            return Message.success("删除成功");
        } catch (Exception e) {
            return Message.fail("删除异常！");
        }
    }

    @PostMapping("sort")
    @ResponseBody
    public Message sort(@RequestParam(value = "id") Integer id, @RequestParam(value = "sort") Integer sort) {
        try {
            Cate cateInfo = cateService.getById(id);
            if (cateInfo != null) {
                cateInfo.setSort(sort);
                cateService.updateById(cateInfo);
                return Message.success("排序成功！");
            } else {
                return Message.fail("栏目不存在或已被删除，排序失败！");
            }
        } catch (Exception e) {
            return Message.fail("排序数据操作异常，排序失败！");
        }
    }
}
