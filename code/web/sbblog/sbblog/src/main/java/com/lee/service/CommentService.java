package com.lee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2020-02-23
 */
public interface CommentService extends IService<Comment> {
    public List<Comment> getCommentList(Map<String,Object> filter);
    Long getCommentPageCount(Map<String,Object> filter);
}
