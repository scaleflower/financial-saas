package com.fs.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fs.userservice.UserServiceApplication;
import com.fs.userservice.entity.User;
import com.fs.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = UserServiceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("用户控制器 API 测试")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .user_id(1L)
                .tenant_id(1L)
                .username("testuser")
                .password("$2a$10$encodedPassword")
                .real_name("测试用户")
                .email("test@example.com")
                .phone("13800138000")
                .org_id(1L)
                .status("ACTIVE")
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .deleted(0)
                .build();
    }

    @Test
    @DisplayName("GET /users/{userId} - 获取用户详情")
    void getById_Success() throws Exception {
        when(userService.getUserById(1L)).thenReturn(testUser);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.user_id").value(1))
                .andExpect(jsonPath("$.data.username").value("testuser"));
    }

    @Test
    @DisplayName("GET /users - 获取用户列表")
    void list_Success() throws Exception {
        when(userService.listUsers(1L)).thenReturn(List.of(testUser));

        mockMvc.perform(get("/users?tenantId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    @DisplayName("PUT /users/{userId} - 更新用户")
    void update_Success() throws Exception {
        when(userService.updateUser(any(User.class))).thenReturn(true);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @DisplayName("DELETE /users/{userId} - 删除用户")
    void delete_Success() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(true);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
