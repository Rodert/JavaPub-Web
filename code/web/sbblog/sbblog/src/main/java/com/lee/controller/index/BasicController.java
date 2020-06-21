package com.lee.controller.index;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.entity.Article;
import com.lee.entity.Cate;
import com.lee.entity.Member;
import com.lee.entity.Syssetting;
import com.lee.service.ArticleService;
import com.lee.service.CateService;
import com.lee.service.SyssettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BasicController {
    @Autowired
    CateService cateService;
    @Autowired
    SyssettingService syssettingService;
    @Autowired
    ArticleService articleService;
    @ModelAttribute
    public void initCommonData(Model model){
        Syssetting setting =  syssettingService.getById(1);
        if(setting == null) {
            setting = new Syssetting();
            setting.setWebname("博客");
            setting.setCopyright("Copyright:2020");
        }
        model.addAttribute("sysInfo",setting);
        List<Cate> cateList =  cateService.getList();
        model.addAttribute("cateList",cateList);
        List<Article> topList =  articleService.list(new QueryWrapper<Article>().eq("is_top",1));
        model.addAttribute("topList",topList);

    }

    public Member getCurrentUser(HttpServletRequest request) {
        return (Member)request.getSession().getAttribute("member");
    }
}
