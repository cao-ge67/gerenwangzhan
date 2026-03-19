package com.cycle.youth.traetest1.controller;

import com.cycle.youth.traetest1.common.Result;
import com.cycle.youth.traetest1.entity.SysUser;
import com.cycle.youth.traetest1.service.ISysUserService;
import com.cycle.youth.traetest1.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 曹戈
 * @since 2026-03-19
 */
@RestController
@RequestMapping("/api/user")
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody SysUser user) {
        SysUser loginUser = sysUserService.login(user.getUsername(), user.getPassword());
        if (loginUser != null) {
            // 生成JWT token
            String token = jwtUtil.generateToken(loginUser.getUsername());
            
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("user", loginUser);
            
            return Result.success("登录成功", data);
        } else {
            return Result.error("用户名或密码错误");
        }
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody SysUser user) {
        boolean success = sysUserService.register(user);
        if (success) {
            return Result.success("注册成功");
        } else {
            return Result.error("用户名已存在");
        }
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public Result<SysUser> getUserInfo(@RequestParam String username) {
        SysUser user = sysUserService.findByUsername(username);
        if (user != null) {
            // 不返回密码信息
            user.setPassword("");
            return Result.success(user);
        } else {
            return Result.error("用户不存在");
        }
    }
}