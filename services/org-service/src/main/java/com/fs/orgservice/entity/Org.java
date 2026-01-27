package com.fs.orgservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("org")
public class Org {
    @TableId(type = IdType.AUTO)
    private Long org_id;
    
    @TableField("tenant_id")
    private Long tenant_id;
    
    @TableField("org")
    private String org;
    
    @TableField("org_name")
    private String org_name;
    
    @TableField("org_type")
    private String org_type;
    
    @TableField("org_level")
    private Integer org_level;
    
    @TableField("parent_org_id")
    private Long parent_org_id;
    
    @TableField("parent_path")
    private String parent_path;
    
    @TableField("sort_order")
    private Integer sort_order;
    
    @TableField("leader_id")
    private Long leader_id;
    
    @TableField("leader_name")
    private String leader_name;
    
    @TableField("status")
    private String status;
    
    @TableField("contact_name")
    private String contact_name;
    
    @TableField("contact_phone")
    private String contact_phone;
    
    @TableField("contact_email")
    private String contact_email;
    
    @TableField("address")
    private String address;
    
    @TableField("remarks")
    private String remarks;
    
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime created_at;
    
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated_at;
    
    @TableLogic
    private Integer deleted;
}
