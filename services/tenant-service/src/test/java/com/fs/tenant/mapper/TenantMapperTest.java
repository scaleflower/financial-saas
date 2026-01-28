package com.fs.tenant.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fs.tenant.TenantApplication;
import com.fs.tenant.entity.Tenant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TenantMapper 集成测试
 */
@SpringBootTest(classes = TenantApplication.class)
@ActiveProfiles("test")
@Transactional
@DisplayName("租户 Mapper 集成测试")
class TenantMapperTest {

    @Autowired
    private TenantMapper tenantMapper;

    private Tenant createTestTenant(String suffix) {
        return Tenant.builder()
                .tenant_code("TEST_" + suffix + "_" + System.currentTimeMillis())
                .tenant_name("测试企业" + suffix)
                .subdomain("test-" + suffix)
                .status("ACTIVE")
                .max_users(50)
                .max_storage(5368709120L)
                .expired_date(LocalDateTime.now().plusYears(1))
                .contact_name("测试联系人")
                .contact_phone("13900139000")
                .contact_email("test@example.com")
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .deleted(0)
                .build();
    }

    @Test
    @DisplayName("插入租户 - 成功")
    void insert_Success() {
        Tenant tenant = createTestTenant("001");
        int rows = tenantMapper.insert(tenant);
        
        assertThat(rows).isEqualTo(1);
        assertThat(tenant.getTenant_id()).isNotNull();
    }

    @Test
    @DisplayName("根据ID查询租户 - 成功")
    void selectById_Success() {
        Tenant tenant = createTestTenant("002");
        tenantMapper.insert(tenant);
        
        // 直接使用 insert 后返回的对象中的 ID
        Tenant found = tenantMapper.selectById(tenant.getTenant_id());
        
        assertThat(found).isNotNull();
        assertThat(found.getTenant_code()).contains("TEST_002");
    }

    @Test
    @DisplayName("查询所有租户 - 成功")
    void selectList_Success() {
        tenantMapper.insert(createTestTenant("003"));
        tenantMapper.insert(createTestTenant("004"));
        
        List<Tenant> tenants = tenantMapper.selectList(null);
        
        assertThat(tenants).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("更新租户 - 成功")
    void updateById_Success() {
        Tenant tenant = createTestTenant("005");
        tenantMapper.insert(tenant);
        
        tenant.setTenant_name("更新后的企业名称");
        tenant.setContact_phone("13800138000");
        
        int rows = tenantMapper.updateById(tenant);
        assertThat(rows).isEqualTo(1);
        
        Tenant updated = tenantMapper.selectById(tenant.getTenant_id());
        assertThat(updated).isNotNull();
        assertThat(updated.getTenant_name()).isEqualTo("更新后的企业名称");
    }

    @Test
    @DisplayName("删除租户 - 软删除")
    void deleteById_SoftDelete() {
        Tenant tenant = createTestTenant("006");
        tenantMapper.insert(tenant);
        Long tenantId = tenant.getTenant_id();
        
        int rows = tenantMapper.deleteById(tenantId);
        assertThat(rows).isEqualTo(1);
        
        Tenant deleted = tenantMapper.selectById(tenantId);
        assertThat(deleted).isNull();
    }

    @Test
    @DisplayName("根据租户编码查询 - 成功")
    void selectByTenantCode_Success() {
        Tenant tenant = createTestTenant("007");
        tenantMapper.insert(tenant);
        
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(Tenant::getTenant_code, "TEST_007");
        
        Tenant found = tenantMapper.selectOne(wrapper);
        
        assertThat(found).isNotNull();
        assertThat(found.getTenant_code()).contains("TEST_007");
    }
}
