package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.entity.Admin;
import com.lee.mapper.AdminMapper;
import com.lee.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2020-02-19
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 根据用户名和密码获取管理员信息
     * @param username
     * @param password
     * @return
     */
    public Admin getInfoByUsernameAndPassword(String username, String password){
       Admin admin =  adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("password",password));
        return admin;
    }

    /**
     * 根据查询条件获取管理员列表信息
     * @param queryParam
     * @param offset
     * @param size
     * @return
     */
    public  IPage<Map<String, Object>> getPageInfo(Map<String, Object> queryParam,int offset,int size){
        QueryWrapper<Admin> queryWrapper = new QueryWrapper();
        if(queryParam.get("username") != null) {
            queryWrapper.like("username",queryParam.get("username"));
        }
        if(queryParam.get("nickname") != null) {
            queryWrapper.like("nickname",queryParam.get("nickname"));
        }

        if(queryParam.get("email") != null) {
            queryWrapper.like("email",queryParam.get("email"));
        }
        queryWrapper.orderByDesc("create_time");
        Page<Admin> page = new Page<Admin>(offset/size +1,size);
        IPage<Map<String, Object>> mapIPage = adminMapper.selectMapsPage(page, queryWrapper);
        return mapIPage;
    }

    /**
     * 根据用户名查询信息，只取一条
     * @param username
     * @return
     */
    public Admin getByUsername(String username){
       return  adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username));
    }

    /**
     * 验证用户名规则
     * @param username
     * @return
     */

}
