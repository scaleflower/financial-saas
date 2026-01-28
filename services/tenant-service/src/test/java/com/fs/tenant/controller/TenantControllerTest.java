package com.fs.tenant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fs.tenant.TenantApplication;
import com.fs.tenant.entity.Tenant;
import com.fs.tenant.service.TenantService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * TenantController API 测试
 */
@SpringBootTest(classes = TenantApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("租户控制器 API 测试")
class TenantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TenantService tenantService;

    private Tenant testTenant;

    @BeforeEach
    void setUp() {
        testTenant = Tenant.builder()
                .tenant_id(1L)
                .tenant_code("TEST001")
                .tenant_name("测试企业")
                .subdomain("test")
                .status("ACTIVE")
                .max_users(100)
                .max_storage(10737418240L)
                .expired_date(LocalDateTime.now().plusYears(1))
                .contact_name("测试联系人")
                .contact_phone("13800138000")
                .contact_email("test@example.com")
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .deleted(0)
                .build();
    }

    @Test
    @DisplayName("POST /tenants - 创建租户")
    void createTenant_Success() throws Exception {
        when(tenantService.createTenant(any(Tenant.class))).thenReturn(testTenant);

        mockMvc.perform(post("/tenants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTenant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.tenant_code").value("TEST001"));
    }

    @Test
    @DisplayName("GET /tenants/{tenantId} - 获取租户详情")
    void getTenantById_Success() throws Exception {
        when(tenantService.getTenantById(1L)).thenReturn(testTenant);

        mockMvc.perform(get("/tenants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.tenant_id").value(1));
    }

    @Test
    @DisplayName("GET /tenants - 获取租户列表")
    void listTenants_Success() throws Exception {
        when(tenantService.listTenants()).thenReturn(java.util.List.of(testTenant));

        mockMvc.perform(get("/tenants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @DisplayName("PUT /tenants/{tenantId} - 更新租户")
    void updateTenant_Success() throws Exception {
        when(tenantService.updateTenant(any(Tenant.class))).thenReturn(true);

        mockMvc.perform(put("/tenants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTenant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @DisplayName("DELETE /tenants/{tenantId} - 删除租户")
    void deleteTenant_Success() throws Exception {
        when(tenantService.deleteTenant(1L)).thenReturn(true);

        mockMvc.perform(delete("/tenants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
