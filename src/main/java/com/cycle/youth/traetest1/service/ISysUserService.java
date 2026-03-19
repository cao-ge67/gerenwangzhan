package com.cycle.youth.traetest1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cycle.youth.traetest1.entity.SysUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 曹戈
 * @since 2026-03-19
 */
public interface ISysUserService extends IService<SysUser> {
    /**
     * 根据用户名查找用户
     */
    SysUser findByUsername(String username);

    /**
     * 用户登录
     */
    SysUser login(String username, String password);

    /**
     * 用户注册
     */
    boolean register(SysUser user);
}