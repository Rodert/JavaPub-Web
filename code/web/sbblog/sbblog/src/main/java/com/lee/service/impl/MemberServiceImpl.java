package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.entity.Member;
import com.lee.mapper.MemberMapper;
import com.lee.service.MemberService;
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
 * @since 2020-02-23
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    MemberMapper memberMapper;
    public IPage<Map<String, Object>> getPageInfo(Map<String, Object> queryParam, int offset, int size){
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        if(queryParam.get("username") != null){
            queryWrapper.like("username",queryParam.get("username"));
        }
        if(queryParam.get("nickname") != null){
            queryWrapper.like("nickname",queryParam.get("nickname"));
        }
        if(queryParam.get("email") != null){
            queryWrapper.like("email",queryParam.get("email"));
        }
        queryWrapper.orderByDesc("create_time");
        Page<Member> page = new Page<Member>(offset/size+1,size);
        IPage<Map<String, Object>> mapIpage = memberMapper.selectMapsPage(page,queryWrapper);
        return mapIpage;
    }
    /**
     * 根据栏目名称加载数据列表
     * @param username
     * @return
     */
    public List<Member> getByUsername(String username) {
        List<Member> members =  memberMapper.selectList(new QueryWrapper<Member>().eq("username",username));
        if(members != null && members.size() > 0 ) {
            return members;
        }
        return null;
    }
}
