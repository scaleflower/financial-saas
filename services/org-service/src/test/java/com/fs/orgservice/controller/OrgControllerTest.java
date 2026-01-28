package com.fs.orgservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fs.orgservice.OrgServiceApplication;
import com.fs.orgservice.entity.Org;
import com.fs.orgservice.service.OrgService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = OrgServiceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("组织控制器 API 测试")
class OrgControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrgService orgService;

    private Org testOrg;

    @BeforeEach
    void setUp() {
        testOrg = Org.builder()
                .org_id(1L)
                .tenant_id(1L)
                .org("ORG001")
                .org_name("测试部门")
                .org_type("DEPARTMENT")
                .org_level(1)
                .parent_org_id(null)
                .parent_path("/")
                .status("ACTIVE")
                .sort_order(0)
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .deleted(0)
                .build();
    }

    @Test
    @DisplayName("GET /orgs/{orgId} - 获取组织详情")
    void getById_Success() throws Exception {
        when(orgService.getOrgById(1L)).thenReturn(testOrg);

        mockMvc.perform(get("/orgs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.org_id").value(1))
                .andExpect(jsonPath("$.data.org_name").value("测试部门"));
    }

    @Test
    @DisplayName("GET /orgs/tree - 获取组织树")
    void getTree_Success() throws Exception {
        when(orgService.listOrgs(1L)).thenReturn(List.of(testOrg));

        mockMvc.perform(get("/orgs/tree?tenantId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @DisplayName("GET /orgs/children - 获取子组织")
    void getChildren_Success() throws Exception {
        when(orgService.getChildOrgs(1L, 1L)).thenReturn(List.of(testOrg));

        mockMvc.perform(get("/orgs/children?parentId=1&tenantId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @DisplayName("POST /orgs - 创建组织")
    void create_Success() throws Exception {
        when(orgService.createOrg(any(Org.class))).thenReturn(testOrg);

        mockMvc.perform(post("/orgs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrg)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @DisplayName("PUT /orgs/{orgId} - 更新组织")
    void update_Success() throws Exception {
        when(orgService.updateOrg(any(Org.class))).thenReturn(true);

        mockMvc.perform(put("/orgs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrg)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @DisplayName("DELETE /orgs/{orgId} - 删除组织")
    void delete_Success() throws Exception {
        when(orgService.deleteOrg(1L)).thenReturn(true);

        mockMvc.perform(delete("/orgs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
