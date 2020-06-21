package com.lee.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.lee.common.DataGrid;
import com.lee.common.Message;
import com.lee.entity.Comment;
import com.lee.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/admin/comment")
public class CommentController extends BasicController {
    @Autowired
    CommentService commentService;
    @GetMapping("list")
    public String list(){
        return "admin/comment/list";
    }
    @PostMapping("findList")
    @ResponseBody
    public DataGrid findList(@RequestBody JSONObject jsonObject) {
        Map<String, Object> searchParams = (HashMap<String, Object>) jsonObject.get("search");
        int offset = "".equals(jsonObject.getString("offset")) ? 0 : jsonObject.getIntValue("offset");
        int size = "".equals(jsonObject.getString("limit")) ? 10 : jsonObject.getIntValue("limit");
        searchParams.put("offsetIndex", offset);
        searchParams.put("limit", size);

        List<Comment> list = commentService.getCommentList(searchParams);
        Long pageCount = commentService.getCommentPageCount(searchParams);
        DataGrid result = new DataGrid();
        result.setTotal(pageCount);
        result.setRows(list);
        return result;
    }
    @PostMapping("delete")
    @ResponseBody
    public Message delete(@RequestParam(value = "ids") List<Integer> ids) {
        try {
            for (int id : ids) {
                commentService.removeById(id);
            }
            return Message.success("删除成功");
        } catch (Exception e) {
            return Message.fail("删除异常！");
        }
    }

}
