package com.fs.repaymentservice.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.repaymentservice.entity.Repayment;
import com.fs.repaymentservice.mapper.RepaymentMapper;
import com.fs.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RepaymentService extends ServiceImpl<RepaymentMapper, Repayment> {

    public Repayment createRepayment(Repayment repayment) {
        repayment.setRepayment_code(StringUtils.generateCode("RP"));
        repayment.setState("DRAFT");
        repayment.setCreated_at(LocalDateTime.now());
        baseMapper.insert(repayment);
        return repayment;
    }
}
