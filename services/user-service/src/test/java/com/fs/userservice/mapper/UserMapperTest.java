package com.fs.userservice.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fs.userservice.UserServiceApplication;
import com.fs.userservice.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = UserServiceApplication.class)
@ActiveProfiles("test")
@Transactional
@DisplayName("用户 Mapper 集成测试")
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    private User createTestUser(String suffix) {
        return User.builder()
                .tenant_id(1L)
                .username("testuser_" + suffix)
                .password("$2a$10$encodedPassword")
                .real_name("测试用户" + suffix)
                .email("test" + suffix + "@example.com")
                .phone("1380013800" + suffix)
                .org_id(1L)
                .status("ACTIVE")
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .deleted(0)
                .build();
    }

    @Test
    @DisplayName("插入用户 - 成功")
    void insert_Success() {
        User user = createTestUser("001");
        int rows = userMapper.insert(user);
        
        assertThat(rows).isEqualTo(1);
        assertThat(user.getUser_id()).isNotNull();
    }

    @Test
    @DisplayName("根据ID查询用户 - 成功")
    void selectById_Success() {
        User user = createTestUser("002");
        userMapper.insert(user);
        
        User found = userMapper.selectById(user.getUser_id());
        
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).contains("testuser_002");
    }

    @Test
    @DisplayName("根据用户名查询 - 成功")
    void selectByUsername_Success() {
        User user = createTestUser("003");
        userMapper.insert(user);
        
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, "testuser_003");
        
        User found = userMapper.selectOne(wrapper);
        
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("testuser_003");
    }

    @Test
    @DisplayName("查询租户用户列表 - 成功")
    void selectList_Success() {
        userMapper.insert(createTestUser("004"));
        userMapper.insert(createTestUser("005"));
        
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getTenant_id, 1L);
        
        List<User> users = userMapper.selectList(wrapper);
        
        assertThat(users).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("更新用户 - 成功")
    void updateById_Success() {
        User user = createTestUser("006");
        userMapper.insert(user);
        
        user.setReal_name("更新后的用户名");
        user.setPhone("13900139000");
        
        int rows = userMapper.updateById(user);
        
        assertThat(rows).isEqualTo(1);
        
        User updated = userMapper.selectById(user.getUser_id());
        assertThat(updated).isNotNull();
        assertThat(updated.getReal_name()).isEqualTo("更新后的用户名");
    }

    @Test
    @DisplayName("删除用户 - 软删除")
    void deleteById_SoftDelete() {
        User user = createTestUser("007");
        userMapper.insert(user);
        Long userId = user.getUser_id();
        
        int rows = userMapper.deleteById(userId);
        
        assertThat(rows).isEqualTo(1);
        
        User deleted = userMapper.selectById(userId);
        assertThat(deleted).isNull();
    }
}
