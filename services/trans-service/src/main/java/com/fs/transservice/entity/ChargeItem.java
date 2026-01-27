package com.fs.transservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("charge_item")
public class ChargeItem {
    @TableId(type = IdType.AUTO)
    private Long charge_id;
    
    @TableField("tenant_id")
    private Long tenant_id;
    
    @TableField("trans_item_id")
    private Long trans_item_id;
    
    @TableField("expense_item_type_id")
    private Long expense_item_type_id;
    
    @TableField("expense_item_type_name")
    private String expense_item_type_name;
    
    @TableField("charge_amount")
    private BigDecimal charge_amount;
    
    @TableField("lc_charge")
    private BigDecimal lc_charge;
    
    @TableField("charge_desc")
    private String charge_desc;
    
    @TableField("charge_date")
    private LocalDate charge_date;
    
    @TableField("exchange_rate")
    private BigDecimal exchange_rate;
    
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime created_at;
    
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated_at;
}
