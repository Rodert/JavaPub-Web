package com.lee.controller.admin;

import com.lee.entity.Article;
import com.lee.entity.Member;
import com.lee.service.ArticleService;
import com.lee.service.CommentService;
import com.lee.service.MemberService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.nio.channels.MembershipKey;

@Controller
@RequestMapping("/admin")
public class HomeController {
    @Autowired
    MemberService memberService;
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;

    @RequestMapping("home")
    public String home(Model model){
        int memberCount = memberService.count();
        int articleCount = articleService.count();
        int commentCount = commentService.count();
        model.addAttribute("memberCount",memberCount);
        model.addAttribute("articleCount",articleCount);
        model.addAttribute("commentCount",commentCount);
        return "admin/home";
    }

    /**
     * 退出管理
     * @return
     */
    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("admin");
        if(request.getSession().getAttribute("admin") != null) {
            //ToDO session清空失败，可以跳转到退出失败页面
        }
//        return "redirect:/admin/login";
        return "admin/login";
    }
}
