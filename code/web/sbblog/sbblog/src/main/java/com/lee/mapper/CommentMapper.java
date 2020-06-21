package com.lee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lee
 * @since 2020-02-23
 */
@Component
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> selectCommentVoList(Map<String,Object> filter);
    Long selectCommentPageCount(Map<String,Object> filter);
    void deleteByArticleId(Integer articleid);
}
