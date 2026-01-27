package com.fs.userservice.service;

import com.fs.common.security.UserAuthInfo;
import com.fs.common.util.JwtUtil;
import com.fs.userservice.entity.User;
import com.fs.userservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public Map<String, Object> login(String username, String password) {
        User user = userMapper.selectOne(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
        );

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        if (!"ACTIVE".equals(user.getStatus())) {
            throw new RuntimeException("用户已被禁用");
        }

        UserAuthInfo authInfo = UserAuthInfo.builder()
                .userId(user.getUser_id())
                .username(user.getUsername())
                .tenantId(user.getTenant_id())
                .status(user.getStatus())
                .build();

        // 将 UserAuthInfo 转换为 Map
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", authInfo.getUserId());
        claims.put("username", authInfo.getUsername());
        claims.put("tenantId", authInfo.getTenantId());
        claims.put("status", authInfo.getStatus());

        String token = JwtUtil.generateToken(claims);

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", authInfo);

        return result;
    }

    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus("ACTIVE");
        userMapper.insert(user);
    }
}
