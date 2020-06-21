package com.lee.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lee
 * @since 2020-02-22
 */
public class Article extends Model<Article> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    @NotEmpty(message = "文章标题不能为空")
    private String title;

    /**
     * 概要
     */
    private String artdesc;

    /**
     * 标签
     */
    @NotEmpty(message = "文章标签不能为空")
    private String tags;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否推荐
     */
    private Integer isTop;

    /**
     * 所属导航id
     */
    private Integer cateId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 软删除
     */
    private String deleteTime;

    /**
     * 软删除标记位
     */
    @TableLogic
    private Integer isdel;

    /**
     * 添加用户id
     */
    private Integer memberId;
    /**
     * 作者
     */
    @NotEmpty(message = "文章作者不能为空")
    private String authorname;
    /**
     * 浏览次数
     */
    private Integer viewnum;

    /**
     * 评论次数
     */
    private Integer commentnum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public String getArtdesc() {
        return artdesc;
    }

    public void setArtdesc(String artdesc) {
        this.artdesc = artdesc;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }
    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }
    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }
    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
    public Integer getViewnum() {
        return viewnum;
    }

    public void setViewnum(Integer viewnum) {
        this.viewnum = viewnum;
    }
    public Integer getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(Integer commentnum) {
        this.commentnum = commentnum;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Article{" +
        "id=" + id +
        ", title=" + title +
        ", desc=" + artdesc +
        ", tags=" + tags +
        ", authorname" + authorname +
        ", content=" + content +
        ", isTop=" + isTop +
        ", cateId=" + cateId +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", deleteTime=" + deleteTime +
        ", isdel=" + isdel +
        ", memberId=" + memberId +
        ", viewnum=" + viewnum +
        ", commentnum=" + commentnum +
        "}";
    }
}
