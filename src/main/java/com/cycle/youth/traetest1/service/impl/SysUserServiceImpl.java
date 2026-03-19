package com.cycle.youth.traetest1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cycle.youth.traetest1.entity.SysUser;
import com.cycle.youth.traetest1.mapper.SysUserMapper;
import com.cycle.youth.traetest1.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 曹戈
 * @since 2026-03-19
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findByUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("deleted", 0); // 未删除的用户
        return sysUserMapper.selectOne(wrapper);
    }

    @Override
    public SysUser login(String username, String password) {
        // 对密码进行MD5加密
        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", encryptedPassword);
        wrapper.eq("deleted", 0);
        
        return sysUserMapper.selectOne(wrapper);
    }

    @Override
    public boolean register(SysUser user) {
        // 检查用户名是否已存在
        SysUser existingUser = findByUsername(user.getUsername());
        if (existingUser != null) {
            return false;
        }
        
        // 对密码进行MD5加密
        String encryptedPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(encryptedPassword);
        
        // 设置默认角色
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        
        int result = sysUserMapper.insert(user);
        return result > 0;
    }
}