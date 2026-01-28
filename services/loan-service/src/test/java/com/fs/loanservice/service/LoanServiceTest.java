package com.fs.loanservice.service;

import com.fs.loanservice.LoanServiceApplication;
import com.fs.loanservice.entity.Loan;
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

@SpringBootTest(classes = LoanServiceApplication.class)
@ActiveProfiles("test")
@Transactional
@DisplayName("借款服务集成测试")
class LoanServiceTest {

    @Autowired
    private LoanService loanService;

    private String uniqueSuffix;

    @BeforeEach
    void setUp() {
        uniqueSuffix = String.valueOf(System.currentTimeMillis());
    }

    private Loan createTestLoan() {
        return Loan.builder()
                .tenant_id(1L)
                .loan_code("LOAN" + uniqueSuffix)
                .user_id(1L)
                .loan_amount(new BigDecimal("5000.00"))
                .lc_loan_amount(new BigDecimal("5000.00"))
                .state("DRAFT")
                .purpose("备用金借款")
                .created_by(1L)
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .deleted(0)
                .build();
    }

    @Test
    @DisplayName("创建借款单 - 成功")
    void createLoan_Success() {
        Loan loan = createTestLoan();
        Loan result = loanService.createLoan(loan);
        assertThat(result).isNotNull();
        assertThat(result.getLoan_id()).isNotNull();
    }

    @Test
    @DisplayName("根据ID获取借款单 - 成功")
    void getLoanById_Success() {
        Loan loan = createTestLoan();
        loanService.createLoan(loan);
        
        Loan found = loanService.getLoanById(loan.getLoan_id());
        assertThat(found).isNotNull();
    }

    @Test
    @DisplayName("获取借款单列表 - 成功")
    void listLoans_Success() {
        loanService.createLoan(createTestLoan());
        assertThat(loanService.listLoans(1L, null)).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("更新借款单 - 成功")
    void updateLoan_Success() {
        Loan loan = createTestLoan();
        loanService.createLoan(loan);
        
        loan.setPurpose("更新后的借款用途");
        loan.setLoan_amount(new BigDecimal("6000.00"));
        
        assertThat(loanService.updateLoan(loan)).isTrue();
    }

    @Test
    @DisplayName("删除借款单 - 成功")
    void deleteLoan_Success() {
        Loan loan = createTestLoan();
        loanService.createLoan(loan);
        
        assertThat(loanService.deleteLoan(loan.getLoan_id())).isTrue();
    }
}
