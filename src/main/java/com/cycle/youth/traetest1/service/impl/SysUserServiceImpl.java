package com.cycle.youth.traetest1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cycle.youth.traetest1.entity.SysUser;
import com.cycle.youth.traetest1.mapper.SysUserMapper;
import com.cycle.youth.traetest1.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public SysUser findByUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("deleted", 0); // 未删除的用户
        return sysUserMapper.selectOne(wrapper);
    }

    @Override
    public SysUser login(String username, String password) {
        System.out.println("=== 登录调试信息 ===");
        System.out.println("输入用户名: " + username);
        System.out.println("输入密码: " + password);
        
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("deleted", 0);
        
        SysUser user = sysUserMapper.selectOne(wrapper);
        
        if (user != null) {
            System.out.println("找到用户: " + user.getUsername());
            System.out.println("数据库密码哈希: " + user.getPassword());
            System.out.println("密码匹配测试开始...");
            
            boolean matches = passwordEncoder.matches(password, user.getPassword());
            System.out.println("密码匹配结果: " + matches);
            
            if (matches) {
                System.out.println("登录成功!");
                return user;
            } else {
                System.out.println("密码不匹配!");
            }
        } else {
            System.out.println("用户不存在!");
        }
        
        System.out.println("登录失败!");
        return null;
    }

    @Override
    public boolean register(SysUser user) {
        SysUser existingUser = findByUsername(user.getUsername());
        if (existingUser != null) {
            return false;
        }
        
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        
        int result = sysUserMapper.insert(user);
        return result > 0;
    }
}