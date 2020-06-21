package com.lee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.entity.Article;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lee
 * @since 2020-02-22
 */
@Component
public interface ArticleMapper extends BaseMapper<Article> {
    public void deleteByCateId(Integer id);
    public Article getNextInfo(Map<String,Object> searchParam);
    public Article getPreInfo(Map<String,Object> searchParam);
}
