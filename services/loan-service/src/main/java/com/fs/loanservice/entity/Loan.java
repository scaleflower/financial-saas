package com.fs.loanservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("loan")
public class Loan {
    @TableId(type = IdType.AUTO)
    private Long loan_id;
    @TableField("tenant_id") private Long tenant_id;
    @TableField("loan_code") private String loan_code;
    @TableField("expense_item_type_id") private Long expense_item_type_id;
    @TableField("user_id") private Long user_id;
    @TableField("project_id") private Long project_id;
    @TableField("loan_amount") private BigDecimal loan_amount;
    @TableField("currency_id") private Integer currency_id;
    @TableField("exchange_rate") private BigDecimal exchange_rate;
    @TableField("lc_loan_amount") private BigDecimal lc_loan_amount;
    @TableField("sett_amount") private BigDecimal sett_amount;
    @TableField("lc_sett_amount") private BigDecimal lc_sett_amount;
    @TableField("state") private String state;
    @TableField("purpose") private String purpose;
    @TableField("created_by") private Long created_by;
    @TableField("settled_by") private Long settled_by;
    @TableField("settled_time") private LocalDateTime settled_time;
    @TableField(value = "created_at", fill = FieldFill.INSERT) private LocalDateTime created_at;
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE) private LocalDateTime updated_at;
    @TableLogic private Integer deleted;
}
