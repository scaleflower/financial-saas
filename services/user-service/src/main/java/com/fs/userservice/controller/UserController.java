package com.fs.userservice.controller;

import com.fs.common.result.Result;
import com.fs.userservice.entity.User;
import com.fs.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public Result<User> getById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return Result.success(user);
    }

    @GetMapping
    public Result<List<User>> list(@RequestParam Long tenantId) {
        List<User> users = userService.listUsers(tenantId);
        return Result.success(users);
    }

    @PutMapping("/{userId}")
    public Result<Void> update(@PathVariable Long userId, @RequestBody User user) {
        user.setUser_id(userId);
        userService.updateUser(user);
        return Result.success();
    }

    @DeleteMapping("/{userId}")
    public Result<Void> delete(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return Result.success();
    }
}
