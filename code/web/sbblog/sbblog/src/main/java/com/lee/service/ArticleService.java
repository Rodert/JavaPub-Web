package com.lee.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2020-02-22
 */
public interface ArticleService extends IService<Article> {
    public IPage<Map<String,Object>> getPageInfo(Map<String,Object> queryParam, int offset, int size);
    public IPage<Map<String,Object>> getPageInfoByPageNum(Map<String,Object> queryParam, int pagenum, int size);
    public Article getNextInfo(Map<String,Object> searchParam);
    public Article getPreInfo(Map<String,Object> searchParam);
    public boolean deleteArticleAndCommentById(Integer id);
}
