package com.fs.userservice.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.userservice.entity.User;
import com.fs.userservice.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    public User getUserById(Long userId) {
        return baseMapper.selectById(userId);
    }

    public User getUserByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return baseMapper.selectOne(wrapper);
    }

    public List<User> listUsers(Long tenantId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getTenant_id, tenantId);
        return baseMapper.selectList(wrapper);
    }

    public boolean updateUser(User user) {
        int rows = baseMapper.updateById(user);
        return rows > 0;
    }

    public boolean deleteUser(Long userId) {
        int rows = baseMapper.deleteById(userId);
        return rows > 0;
    }
}
