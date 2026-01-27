package com.fs.loanservice.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.loanservice.entity.Loan;
import com.fs.loanservice.mapper.LoanMapper;
import com.fs.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class LoanService extends ServiceImpl<LoanMapper, Loan> {

    public Loan createLoan(Loan loan) {
        String loanCode = StringUtils.generateCode("LN");
        loan.setLoan_code(loanCode);
        loan.setState("DRAFT");
        loan.setCreated_at(LocalDateTime.now());
        baseMapper.insert(loan);
        return loan;
    }

    public Loan getLoanById(Long loanId) {
        return baseMapper.selectById(loanId);
    }

    public List<Loan> listLoans(Long tenantId) {
        LambdaQueryWrapper<Loan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Loan::getTenant_id, tenantId).orderByDesc(Loan::getCreated_at);
        return baseMapper.selectList(wrapper);
    }
}
