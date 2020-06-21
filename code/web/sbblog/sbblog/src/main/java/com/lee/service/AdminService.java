package com.lee.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2020-02-19
 */
public interface AdminService extends IService<Admin> {
   Admin getInfoByUsernameAndPassword(String username, String password);
   IPage<Map<String, Object>> getPageInfo(Map<String, Object> queryParam,int offset,int size);
   Admin getByUsername(String username);

}
