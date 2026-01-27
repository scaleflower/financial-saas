package com.fs.loanservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fs.loanservice.entity.Loan;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoanMapper extends BaseMapper<Loan> {
}
