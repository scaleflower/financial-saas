package com.fs.repaymentservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("repayment")
public class Repayment {
    @TableId(type = IdType.AUTO) private Long repayment_id;
    @TableField("tenant_id") private Long tenant_id;
    @TableField("user_id") private Long user_id;
    @TableField("repayment_code") private String repayment_code;
    @TableField("repayment_amount") private BigDecimal repayment_amount;
    @TableField("state") private String state;
    @TableField("created_at") private LocalDateTime created_at;
    @TableLogic private Integer deleted;
}
