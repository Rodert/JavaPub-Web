package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lee.entity.Article;
import com.lee.entity.Comment;
import com.lee.mapper.ArticleMapper;
import com.lee.mapper.CommentMapper;
import com.lee.service.ArticleService;
import com.lee.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2020-02-22
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    CommentMapper commentMapper;

    public IPage<Map<String,Object>> getPageInfo(Map<String,Object> queryParam,int offset,int size){
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if(queryParam.get("title") != null) {
            queryWrapper.like("title",queryParam.get("title"));
        }
        queryWrapper.orderByDesc("create_time");
        Page<Article> page = new Page<Article>(offset/size+1,size);
        IPage<Map<String,Object>> mapIPage = articleMapper.selectMapsPage(page,queryWrapper);
        return mapIPage;
    }
    public IPage<Map<String,Object>> getPageInfoByPageNum(Map<String,Object> queryParam,int pagenum,int size){
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if(queryParam.get("title") != null) {
            queryWrapper.like("title",queryParam.get("title"));
        }
        if(queryParam.get("cateid") != null) {
            queryWrapper.eq("cate_id",queryParam.get("cateid"));
        }
        queryWrapper.orderByDesc("create_time");
        Page<Article> page = new Page<Article>(pagenum,size);
        IPage<Map<String,Object>> mapIPage = articleMapper.selectMapsPage(page,queryWrapper);
        return mapIPage;
    }

    public boolean deleteArticleAndCommentById(Integer id){
        commentMapper.deleteByArticleId(id);
        return SqlHelper.delBool(articleMapper.deleteById(id));
    }

    public Article getNextInfo(Map<String,Object> searchParam){
        return this.articleMapper.getNextInfo(searchParam);
    }
    public Article getPreInfo(Map<String,Object> searchParam){
        return this.articleMapper.getPreInfo(searchParam);
    }
}
