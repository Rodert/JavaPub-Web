package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lee.entity.Admin;
import com.lee.entity.Article;
import com.lee.entity.Cate;
import com.lee.mapper.ArticleMapper;
import com.lee.mapper.CateMapper;
import com.lee.mapper.CommentMapper;
import com.lee.service.CateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2020-02-21
 */
@Service
public class CateServiceImpl extends ServiceImpl<CateMapper, Cate> implements CateService {
    @Autowired
    CateMapper cateMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    CommentMapper commentMapper;

    public boolean deleteCateAndArticleAndCommentById(Integer id) {
        List<Article> articles = articleMapper.selectList(new QueryWrapper<Article>().eq("cate_id",id));
        if(articles != null && articles.size() > 0) {
            for(Article article:articles){
                commentMapper.deleteByArticleId(article.getId());
            }
            articleMapper.deleteByCateId(id);
        }
        return SqlHelper.delBool(cateMapper.deleteById(id));
    }

    public IPage<Map<String, Object>> getPageInfo(Map<String, Object> queryParam, int offset, int size){
        QueryWrapper<Cate> queryWrapper = new QueryWrapper<>();
        if(queryParam.get("catename") != null){
            queryWrapper.like("catename",queryParam.get("catename"));
        }
        queryWrapper.orderByDesc("sort","create_time");
        Page<Cate> page = new Page<Cate>(offset/size+1,size);
        IPage<Map<String, Object>> mapIpage = cateMapper.selectMapsPage(page,queryWrapper);
        return mapIpage;
    }
    /**
     * 根据栏目名称加载数据列表
     * @param catename
     * @return
     */
    public List<Cate> getByCatename(String catename) {
        List<Cate> cates =  cateMapper.selectList(new QueryWrapper<Cate>().eq("catename",catename));
        if(cates != null && cates.size() > 0 ) {
            return cates;
        }
        return null;
    }

    public List<Cate> getList(){
        QueryWrapper<Cate> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sort","create_time");
        List<Cate> result = cateMapper.selectList(queryWrapper);
        if(result != null) {
            return result;
        } else {
            return new ArrayList<Cate>();
        }
    }
}
