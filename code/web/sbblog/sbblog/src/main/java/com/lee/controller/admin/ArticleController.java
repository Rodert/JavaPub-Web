package com.lee.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.DataGrid;
import com.lee.common.DateTimeUtil;
import com.lee.common.Message;
import com.lee.entity.Admin;
import com.lee.entity.Article;
import com.lee.service.ArticleService;
import com.lee.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lee
 * @since 2020-02-22
 */
@Controller
@RequestMapping("/admin/article")
public class ArticleController extends BasicController {
    @Autowired
    ArticleService articleService;
    @Autowired
    CateService cateService;
    @GetMapping("list")
    public String list(){
        return "admin/article/list";
    }
    @PostMapping("findList")
    @ResponseBody
    public DataGrid findList(@RequestBody JSONObject jsonObject){
        Map<String, Object> searchParams = (HashMap<String, Object>) jsonObject.get("search");
        int offset = "".equals(jsonObject.getString("offset")) ? 0 : jsonObject.getIntValue("offset");
        int size = "".equals(jsonObject.getString("limit")) ? 10 : jsonObject.getIntValue("limit");

        IPage<Map<String, Object>> page = articleService.getPageInfo(searchParams, offset, size);

        DataGrid result = new DataGrid();
        result.setTotal(page.getTotal());
        result.setRows(page.getRecords());
        return result;
    }
    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("cateInfos",cateService.getList());
        return "admin/article/add";
    }

    @PostMapping("add")
    @ResponseBody
    public Message doAdd(@Validated Article article, HttpServletRequest request){
        try {
            Admin admin = getCurrentUser(request);
            if(admin != null) {
                if(article.getIsTop() == null) {
                    article.setIsTop(0);
                }
                article.setCreateTime(DateTimeUtil.nowTimeStr());
                article.setMemberId(admin.getId());
                articleService.save(article);
                return Message.success("文章添加成功！");
            } else {
                return Message.fail("登录超时，请重新登录！",null,"/admin/login");
            }
        } catch (Exception e) {
            return Message.fail("文章数据保存异常，保存失败！");
        }

    }
    @PostMapping("top")
    @ResponseBody
    public Message top(@RequestParam(value = "id")Integer id,@RequestParam(value = "istop")Integer istop){
        try{
           Article article =  articleService.getById(id);
           if(article != null) {
               if(istop == 1) {
                   article.setIsTop(0);
               } else if(istop == 0) {
                   article.setIsTop(1);
               }
               articleService.updateById(article);
               return Message.success("操作成功！");
           } else {
               return Message.fail("文章不存在或已被删除，操作失败！");
           }
        } catch (Exception e) {
            return Message.fail("文章推荐操作处理异常！");
        }
    }
    @GetMapping("/update/{id}")
    public String update(@PathVariable Integer id,Model model){
       Article article =  articleService.getById(id);
        model.addAttribute("cateInfos",cateService.getList());
        model.addAttribute("articleInfo",article);
       return "admin/article/update";
    }
    @PostMapping("/update")
    @ResponseBody
    public Message doUpdate(@Validated Article article){
        try{
            Article articleInfo = articleService.getById(article.getId());
            if(articleInfo != null) {
                if(article.getIsTop() == null) {
                    articleInfo.setIsTop(0);
                } else {
                    articleInfo.setIsTop(article.getIsTop());
                }

                articleInfo.setTitle(article.getTitle());
                articleInfo.setAuthorname(article.getAuthorname());
                articleInfo.setTags(article.getTags());
                articleInfo.setArtdesc(article.getArtdesc());
                articleInfo.setContent(article.getContent());
                articleInfo.setCateId(article.getCateId());
                articleInfo.setUpdateTime(DateTimeUtil.nowTimeStr());
                articleService.updateById(articleInfo);
                return Message.success("文章修改成功!");
            } else {
                return Message.fail("文章不存在或已被删除！");
            }
        } catch (Exception e) {
            return Message.fail("文章修改保存异常，操作失败！");
        }
    }
    @PostMapping("delete")
    @ResponseBody
    public Message delete(@RequestParam(value = "ids") List<Integer> ids) {
        try {
            for (int id : ids) {
                articleService.deleteArticleAndCommentById(id);
            }
            return Message.success("文章删除成功");
        } catch (Exception e) {
            return Message.fail("文章删除异常！");
        }
    }

}
