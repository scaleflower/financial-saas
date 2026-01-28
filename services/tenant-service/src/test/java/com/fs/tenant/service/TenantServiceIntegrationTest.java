package com.fs.tenant.service;

import com.fs.common.exception.BusinessException;
import com.fs.tenant.TenantApplication;
import com.fs.tenant.entity.Tenant;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fs.tenant.mapper.TenantMapper;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * TenantService 集成测试
 */
@SpringBootTest(classes = TenantApplication.class)
@ActiveProfiles("test")
@Transactional
@DisplayName("租户服务集成测试")
class TenantServiceIntegrationTest {

    @Autowired
    private TenantService tenantService;
    
    @Autowired
    private TenantMapper tenantMapper;

    private String uniqueCode;

    @BeforeEach
    void setUp() {
        uniqueCode = "TEST_" + System.currentTimeMillis();
    }

    private Tenant createTestTenant(String suffix) {
        return Tenant.builder()
                .tenant_code(uniqueCode + "_" + suffix)
                .tenant_name("测试企业" + suffix)
                .subdomain("test-" + suffix)
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
    @DisplayName("创建租户 - 成功")
    void createTenant_Success() {
        Tenant result = tenantService.createTenant(createTestTenant("001"));
        assertThat(result).isNotNull();
        assertThat(result.getTenant_id()).isNotNull();
    }

    @Test
    @DisplayName("创建租户 - 租户编码已存在")
    void createTenant_CodeExists() {
        tenantService.createTenant(createTestTenant("002"));
        Tenant duplicate = createTestTenant("002");
        assertThatThrownBy(() -> tenantService.createTenant(duplicate))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("根据ID获取租户 - 成功")
    void getTenantById_Success() {
        Tenant created = tenantService.createTenant(createTestTenant("003"));
        Tenant result = tenantService.getTenantById(created.getTenant_id());
        assertThat(result).isNotNull();
        assertThat(result.getTenant_id()).isEqualTo(created.getTenant_id());
    }

    @Test
    @DisplayName("根据ID获取租户 - 租户不存在")
    void getTenantById_NotFound() {
        assertThatThrownBy(() -> tenantService.getTenantById(99999L))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("获取租户列表 - 成功")
    void listTenants_Success() {
        tenantService.createTenant(createTestTenant("004"));
        tenantService.createTenant(createTestTenant("005"));
        List<Tenant> result = tenantService.listTenants();
        assertThat(result).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("更新租户 - 成功")
    void updateTenant_Success() {
        Tenant created = tenantService.createTenant(createTestTenant("006"));
        Long tenantId = created.getTenant_id();
        
        created.setTenant_name("更新后的企业名称");
        created.setContact_phone("13900139000");

        boolean result = tenantService.updateTenant(created);
        assertThat(result).isTrue();
        
        // 使用 Mapper 直接查询避免缓存问题
        Tenant updated = tenantMapper.selectById(tenantId);
        assertThat(updated).isNotNull();
        assertThat(updated.getTenant_name()).isEqualTo("更新后的企业名称");
    }

    @Test
    @DisplayName("删除租户 - 成功")
    void deleteTenant_Success() {
        Tenant created = tenantService.createTenant(createTestTenant("007"));
        boolean result = tenantService.deleteTenant(created.getTenant_id());
        assertThat(result).isTrue();
        
        assertThatThrownBy(() -> tenantService.getTenantById(created.getTenant_id()))
                .isInstanceOf(BusinessException.class);
    }
}
