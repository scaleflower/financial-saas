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
@TableName("trans")
public class Trans {
    @TableId(type = IdType.AUTO)
    private Long trans_id;
    
    @TableField("tenant_id")
    private Long tenant_id;
    
    @TableField("trans_code")
    private String trans_code;
    
    @TableField("trans_type_id")
    private Long trans_type_id;
    
    @TableField("user_id")
    private Long user_id;
    
    @TableField("project_id")
    private Long project_id;
    
    @TableField("trans_date")
    private LocalDateTime trans_date;
    
    @TableField("trans_charge")
    private BigDecimal trans_charge;
    
    @TableField("currency_id")
    private Integer currency_id;
    
    @TableField("exchange_rate")
    private BigDecimal exchange_rate;
    
    @TableField("lc_charge")
    private BigDecimal lc_charge;
    
    @TableField("trans_sett_charge")
    private BigDecimal trans_sett_charge;
    
    @TableField("lc_sett_charge")
    private BigDecimal lc_sett_charge;
    
    @TableField("state")
    private String state;
    
    @TableField("description")
    private String description;
    
    @TableField("submit_time")
    private LocalDateTime submit_time;
    
    @TableField("processinstance_id")
    private String processinstance_id;
    
    @TableField("created_by")
    private Long created_by;
    
    @TableField("settled_by")
    private Long settled_by;
    
    @TableField("settled_time")
    private LocalDateTime settled_time;
    
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime created_at;
    
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated_at;
    
    @TableLogic
    private Integer deleted;
}
