package com.fs.orgservice.service;

import com.fs.orgservice.OrgServiceApplication;
import com.fs.orgservice.entity.Org;
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

@SpringBootTest(classes = OrgServiceApplication.class)
@ActiveProfiles("test")
@Transactional
@DisplayName("组织服务集成测试")
class OrgServiceTest {

    @Autowired
    private OrgService orgService;

    private String uniqueSuffix;

    @BeforeEach
    void setUp() {
        uniqueSuffix = String.valueOf(System.currentTimeMillis());
    }

    private Org createTestOrg(String suffix, Long parentId) {
        return Org.builder()
                .tenant_id(1L)
                .org("ORG_" + suffix)
                .org_name("测试组织" + suffix)
                .org_type("DEPARTMENT")
                .parent_org_id(parentId)
                .status("ACTIVE")
                .sort_order(0)
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .deleted(0)
                .build();
    }

    @Test
    @DisplayName("创建组织 - 根组织")
    void createOrg_Root() {
        Org org = createTestOrg("ROOT", null);
        
        Org result = orgService.createOrg(org);
        
        assertThat(result).isNotNull();
        assertThat(result.getOrg_id()).isNotNull();
        assertThat(result.getOrg_level()).isEqualTo(1);
    }

    @Test
    @DisplayName("创建组织 - 子组织")
    void createOrg_Child() {
        // 先创建父组织
        Org parent = createTestOrg("PARENT", null);
        orgService.createOrg(parent);
        
        // 创建子组织
        Org child = createTestOrg("CHILD", parent.getOrg_id());
        Org result = orgService.createOrg(child);
        
        assertThat(result).isNotNull();
        assertThat(result.getOrg_level()).isEqualTo(2);
    }

    @Test
    @DisplayName("根据ID获取组织 - 成功")
    void getOrgById_Success() {
        Org org = createTestOrg("001", null);
        orgService.createOrg(org);
        
        Org found = orgService.getOrgById(org.getOrg_id());
        
        assertThat(found).isNotNull();
        assertThat(found.getOrg_name()).isEqualTo("测试组织001");
    }

    @Test
    @DisplayName("获取组织树 - 成功")
    void listOrgs_Success() {
        orgService.createOrg(createTestOrg("002", null));
        orgService.createOrg(createTestOrg("003", null));
        
        List<Org> tree = orgService.listOrgs(1L);
        
        assertThat(tree).isNotNull();
        assertThat(tree).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("获取子组织 - 成功")
    void getChildOrgs_Success() {
        Org parent = createTestOrg("PARENT2", null);
        orgService.createOrg(parent);
        
        orgService.createOrg(createTestOrg("CHILD1", parent.getOrg_id()));
        orgService.createOrg(createTestOrg("CHILD2", parent.getOrg_id()));
        
        List<Org> children = orgService.getChildOrgs(parent.getOrg_id(), 1L);
        
        assertThat(children).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("更新组织 - 成功")
    void updateOrg_Success() {
        Org org = createTestOrg("004", null);
        orgService.createOrg(org);
        
        org.setOrg_name("更新后的组织名称");
        org.setContact_phone("13900139000");
        
        boolean result = orgService.updateOrg(org);
        
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("删除组织 - 有子组织时失败")
    void deleteOrg_HasChildren_Fail() {
        Org parent = createTestOrg("PARENT3", null);
        orgService.createOrg(parent);
        
        Org child = createTestOrg("CHILD3", parent.getOrg_id());
        orgService.createOrg(child);
        
        assertThatThrownBy(() -> orgService.deleteOrg(parent.getOrg_id()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("子组织");
    }

    @Test
    @DisplayName("删除组织 - 成功")
    void deleteOrg_Success() {
        Org org = createTestOrg("005", null);
        orgService.createOrg(org);
        
        boolean result = orgService.deleteOrg(org.getOrg_id());
        
        assertThat(result).isTrue();
    }
}
