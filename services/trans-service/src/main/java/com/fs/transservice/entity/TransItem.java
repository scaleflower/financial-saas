package com.fs.transservice.entity;

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
@TableName("trans_item")
public class TransItem {
    @TableId(type = IdType.AUTO)
    private Long item_id;
    
    @TableField("tenant_id")
    private Long tenant_id;
    
    @TableField("trans_id")
    private Long trans_id;
    
    @TableField("item_code")
    private String item_code;
    
    @TableField("expense_item_type_id")
    private Long expense_item_type_id;
    
    @TableField("expense_item_type_name")
    private String expense_item_type_name;
    
    @TableField("item_charge")
    private BigDecimal item_charge;
    
    @TableField("lc_item_charge")
    private BigDecimal lc_item_charge;
    
    @TableField("item_sett_charge")
    private BigDecimal item_sett_charge;
    
    @TableField("lc_item_sett_charge")
    private BigDecimal lc_item_sett_charge;
    
    @TableField("description")
    private String description;
    
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime created_at;
    
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated_at;
}
