package com.fs.userservice.service;

import com.fs.userservice.UserServiceApplication;
import com.fs.userservice.entity.User;
import com.fs.userservice.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
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
@DisplayName("用户服务集成测试")
class UserServiceTest {

    @Autowired
    private UserService userService;

    private String uniqueSuffix;

    @BeforeEach
    void setUp() {
        uniqueSuffix = String.valueOf(System.currentTimeMillis());
    }

    private User createTestUser(String suffix) {
        return User.builder()
                .tenant_id(1L)
                .username("testuser_" + uniqueSuffix + "_" + suffix)
                .password("$2a$10$encodedPassword")
                .real_name("测试用户" + suffix)
                .email("test" + suffix + "@example.com")
                .phone("13800138000")
                .org_id(1L)
                .status("ACTIVE")
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .deleted(0)
                .build();
    }

    @Test
    @DisplayName("根据ID获取用户 - 成功")
    void getUserById_Success() {
        User user = createTestUser("001");
        userService.save(user);
        
        User found = userService.getUserById(user.getUser_id());
        
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).contains("testuser_");
    }

    @Test
    @DisplayName("根据用户名获取用户 - 成功")
    void getUserByUsername_Success() {
        User user = createTestUser("002");
        userService.save(user);
        
        User found = userService.getUserByUsername(user.getUsername());
        
        assertThat(found).isNotNull();
        assertThat(found.getReal_name()).isEqualTo("测试用户002");
    }

    @Test
    @DisplayName("获取租户用户列表 - 成功")
    void listUsers_Success() {
        userService.save(createTestUser("003"));
        userService.save(createTestUser("004"));
        
        List<User> users = userService.listUsers(1L);
        
        assertThat(users).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("更新用户 - 成功")
    void updateUser_Success() {
        User user = createTestUser("005");
        userService.save(user);
        
        user.setReal_name("更新后的用户名");
        user.setPhone("13900139000");
        
        boolean result = userService.updateUser(user);
        
        assertThat(result).isTrue();
        
        User updated = userService.getUserById(user.getUser_id());
        assertThat(updated.getReal_name()).isEqualTo("更新后的用户名");
    }

    @Test
    @DisplayName("删除用户 - 成功")
    void deleteUser_Success() {
        User user = createTestUser("006");
        userService.save(user);
        Long userId = user.getUser_id();
        
        boolean result = userService.deleteUser(userId);
        
        assertThat(result).isTrue();
        
        User deleted = userService.getUserById(userId);
        assertThat(deleted).isNull();
    }
}
