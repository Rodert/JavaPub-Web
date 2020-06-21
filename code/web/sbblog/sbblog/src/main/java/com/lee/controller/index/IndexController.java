package com.lee.controller.index;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.DataGrid;
import com.lee.common.DateTimeUtil;
import com.lee.common.Message;
import com.lee.entity.Article;
import com.lee.entity.Comment;
import com.lee.entity.Member;
import com.lee.service.CommentService;
import com.lee.service.MemberService;
import com.sun.org.apache.regexp.internal.RE;
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
@RequestMapping("/")
public class IndexController extends BasicController{
@Autowired
CommentService commentService;
@Autowired
MemberService memberService;
    @GetMapping("article/{id}")
    public String article(@PathVariable Integer id,Model model){
        Article article = articleService.getById(id);
        if(article != null) {
            if(article.getTags() != null) {
                String tags = article.getTags();
                String[] tagList = tags.split("\\|");
                model.addAttribute("tags",tagList);
            }
            article.setViewnum(article.getViewnum()+1);
            articleService.updateById(article);
            Map<String,Object> searchParam = new HashMap<String,Object>();
            searchParam.put("id",id);
            searchParam.put("create_time",article.getCreateTime());
            Article nextInfo = articleService.getNextInfo(searchParam);
            Article preInfo = articleService.getPreInfo(searchParam);
            model.addAttribute("nextInfo",nextInfo);
            model.addAttribute("preInfo",preInfo);
            searchParam.clear();
            searchParam.put("article_id",article.getId());
            List<Comment> commentList = commentService.getCommentList(searchParam);//建议用ajax页面异步加载
            model.addAttribute("commentList",commentList);
        } else {
            return "index/index";
        }

        model.addAttribute("article",article);
        return "index/article";
    }


    @GetMapping("")
    public String index(){
        return "index/index";
    }
    @PostMapping("findList")
    @ResponseBody
    public DataGrid findList(@RequestParam(value = "page")Integer page,
                             @RequestParam(value = "cateid",defaultValue = "-1") Integer cateid,
                             @RequestParam(value = "size")Integer size,
                             @RequestParam(value = "searchKey") String searchKey){
        Map<String, Object> searchParams = new HashMap<String,Object>();

        if(cateid >= 0) {
            searchParams.put("cateid",cateid);
        }
        if(!StringUtils.isEmpty(searchKey)){
            searchParams.put("title",searchKey);
        }
        IPage<Map<String, Object>> artPage = articleService.getPageInfoByPageNum(searchParams, page, size);

        DataGrid result = new DataGrid();
        result.setTotal(artPage.getTotal());
        result.setRows(artPage.getRecords());
        return result;
    }
    @GetMapping("/cate/{id}")
    public String cateList(@PathVariable Integer id, Model model){
        model.addAttribute("id",id);
        return "index/index";
    }

    @GetMapping("search/{content}")
    public String search(@PathVariable String content,Model model){
        model.addAttribute("searchKey",content);
        return "index/index";
    }


    @PostMapping("login")
    @ResponseBody
    public Message login(@RequestParam(value = "username")String username, @RequestParam(value = "password")String password, HttpServletRequest request){
        if(StringUtils.isEmpty(username)) {
            return Message.fail("用户名不能为空");
        }
        if(StringUtils.isEmpty(password)) {
            return Message.fail("密码不能为空");
        }
        Member member =  memberService.getOne(new QueryWrapper<Member>().eq("username",username).eq("password",password));
        if(member != null) {
            request.getSession().setAttribute("member",member);
            return Message.success("登录成功！");
        } else {
            return Message.fail("用户名或密码错误");
        }
    }
    @PostMapping("logout")
    @ResponseBody
    public Message logout(HttpServletRequest request){
        request.getSession().removeAttribute("member");
        return Message.success("退出成功");
    }
    @PostMapping("comment")
    @ResponseBody
    public Message comment(@RequestParam(value = "id")Integer id,@RequestParam(value = "content")String content,HttpServletRequest request){
        try {
            if(request.getSession().getAttribute("member") != null) {
                if(id == null) {
                    return Message.fail("文章信息获取失败");
                } else {
                    Article article = articleService.getById(id);

                    if(article == null) {
                        return Message.fail("文章不存在或已被删除，请刷新后重新评论");
                    } else {
                        if(StringUtils.isEmpty(content)) {
                            return Message.fail("评论内容获取失败");
                        }
                        Integer memberid = getCurrentUser(request).getId();
                        if(memberid == null) {
                            return Message.fail("当前登录信息获取失败");
                        }
                        Comment comment = new Comment();
                        comment.setArticleId(id);
                        comment.setMemberId(memberid);
                        comment.setContent(content);
                        comment.setCreateTime(DateTimeUtil.nowTimeStr());
                        commentService.save(comment);
                        article.setCommentnum(article.getCommentnum()+1);
                        articleService.updateById(article);
                        return Message.success("评论成功，感谢您的评论！");
                    }
                }

            } else {
                return Message.fail("请先登录后再评论");
            }
        } catch (Exception e) {
            return Message.fail("评论保存异常");
        }
    }
    @GetMapping("register")
    public String register(){
        return "index/reg";
    }
    @PostMapping("register")
    @ResponseBody
    public Message doRegister(@Validated Member member,@RequestParam(value = "confirmpassword")String confirmpassword){
        try {
            if(StringUtils.isEmpty(confirmpassword)) {
                return Message.fail("确认密码不能为空");
            }
            if(!confirmpassword.equals(member.getPassword())) {
                return Message.fail("确认密码和密码不一致");
            }
            List<Member> members =  memberService.getByUsername(member.getUsername());
            if(members != null && members.size() > 0) {
                return Message.fail("用户名已存在，请换一个");
            }
            member.setCreateTime(DateTimeUtil.nowTimeStr());
            memberService.save(member);
            return Message.success("恭喜你，注册成功！");
        } catch (Exception e) {
            return Message.fail("注册数据保存异常");
        }
    }
}
