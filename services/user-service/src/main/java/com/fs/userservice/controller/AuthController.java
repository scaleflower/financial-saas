package com.fs.userservice.controller;

import com.fs.common.result.Result;
import com.fs.userservice.entity.User;
import com.fs.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestParam String username, @RequestParam String password) {
        Map<String, Object> result = authService.login(username, password);
        return Result.success(result, "登录成功");
    }

    @PostMapping("/register")
    public Result<Void> register(@RequestBody User user) {
        authService.register(user);
        return Result.success(null, "注册成功");
    }
}
