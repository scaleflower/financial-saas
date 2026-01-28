package com.fs.transservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fs.transservice.TransServiceApplication;
import com.fs.transservice.entity.Trans;
import com.fs.transservice.service.TransService;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TransServiceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("报销控制器 API 测试")
class TransControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransService transService;

    private Trans testTrans;

    @BeforeEach
    void setUp() {
        testTrans = Trans.builder()
                .trans_id(1L)
                .tenant_id(1L)
                .trans_code("TRANS001")
                .trans_type_id(1L)
                .user_id(1L)
                .trans_charge(new BigDecimal("1000.00"))
                .lc_charge(new BigDecimal("1000.00"))
                .state("DRAFT")
                .description("测试报销单")
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .deleted(0)
                .build();
    }

    @Test
    @DisplayName("POST /trans - 创建报销单")
    void create_Success() throws Exception {
        when(transService.createTrans(any(Trans.class))).thenReturn(testTrans);

        mockMvc.perform(post("/trans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTrans)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @DisplayName("GET /trans/{transId} - 获取报销单详情")
    void getById_Success() throws Exception {
        when(transService.getTransById(1L)).thenReturn(testTrans);

        mockMvc.perform(get("/trans/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.trans_id").value(1));
    }

    @Test
    @DisplayName("GET /trans - 获取报销单列表")
    void list_Success() throws Exception {
        when(transService.listTrans(any(), any())).thenReturn(List.of(testTrans));

        mockMvc.perform(get("/trans?tenantId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @DisplayName("POST /trans/{transId}/submit - 提交报销单")
    void submit_Success() throws Exception {
        mockMvc.perform(post("/trans/1/submit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @DisplayName("PUT /trans/{transId} - 更新报销单")
    void update_Success() throws Exception {
        when(transService.updateTrans(any(Trans.class))).thenReturn(true);

        mockMvc.perform(put("/trans/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTrans)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @DisplayName("DELETE /trans/{transId} - 删除报销单")
    void delete_Success() throws Exception {
        when(transService.deleteTrans(1L)).thenReturn(true);

        mockMvc.perform(delete("/trans/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
