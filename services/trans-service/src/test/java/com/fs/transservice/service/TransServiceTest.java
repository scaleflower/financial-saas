package com.fs.transservice.service;

import com.fs.transservice.TransServiceApplication;
import com.fs.transservice.entity.Trans;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TransServiceApplication.class)
@ActiveProfiles("test")
@Transactional
@DisplayName("报销服务集成测试")
class TransServiceTest {

    @Autowired
    private TransService transService;

    private String uniqueSuffix;

    @BeforeEach
    void setUp() {
        uniqueSuffix = String.valueOf(System.currentTimeMillis());
    }

    private Trans createTestTrans() {
        return Trans.builder()
                .tenant_id(1L)
                .trans_code("TRANS" + uniqueSuffix)
                .trans_type_id(1L)
                .user_id(1L)
                .project_id(1L)
                .trans_date(LocalDateTime.now())
                .trans_charge(new BigDecimal("1000.00"))
                .currency_id(1)
                .exchange_rate(new BigDecimal("1.0"))
                .lc_charge(new BigDecimal("1000.00"))
                .state("DRAFT")
                .description("测试报销单")
                .created_by(1L)
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .deleted(0)
                .build();
    }

    @Test
    @DisplayName("创建报销单 - 成功")
    void createTrans_Success() {
        Trans trans = createTestTrans();
        
        Trans result = transService.createTrans(trans);
        
        assertThat(result).isNotNull();
        assertThat(result.getTrans_id()).isNotNull();
    }

    @Test
    @DisplayName("提交报销单 - 成功")
    void submitTrans_Success() {
        Trans trans = createTestTrans();
        transService.createTrans(trans);
        
        transService.submitTrans(trans.getTrans_id());
        
        Trans submitted = transService.getTransById(trans.getTrans_id());
        assertThat(submitted.getState()).isEqualTo("SUBMITTED");
    }

    @Test
    @DisplayName("根据ID获取报销单 - 成功")
    void getTransById_Success() {
        Trans trans = createTestTrans();
        transService.createTrans(trans);
        
        Trans found = transService.getTransById(trans.getTrans_id());
        
        assertThat(found).isNotNull();
        assertThat(found.getTrans_code()).contains("TRANS");
    }

    @Test
    @DisplayName("获取报销单列表 - 成功")
    void listTrans_Success() {
        transService.createTrans(createTestTrans());
        
        assertThat(transService.listTrans(1L, null)).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("更新报销单 - 成功")
    void updateTrans_Success() {
        Trans trans = createTestTrans();
        transService.createTrans(trans);
        
        trans.setDescription("更新后的报销单");
        trans.setTrans_charge(new BigDecimal("2000.00"));
        
        boolean result = transService.updateTrans(trans);
        
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("删除报销单 - 成功")
    void deleteTrans_Success() {
        Trans trans = createTestTrans();
        transService.createTrans(trans);
        
        boolean result = transService.deleteTrans(trans.getTrans_id());
        
        assertThat(result).isTrue();
    }
}
