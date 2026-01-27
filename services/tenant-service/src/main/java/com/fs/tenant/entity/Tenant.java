package com.fs.tenant.entity;

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
@TableName("tenant")
public class Tenant {

    @TableId(type = IdType.AUTO)
    private Long tenant_id;

    @TableField("tenant_code")
    private String tenant_code;

    @TableField("tenant_name")
    private String tenant_name;

    @TableField("subdomain")
    private String subdomain;

    @TableField("status")
    private String status;

    @TableField("max_users")
    private Integer max_users;

    @TableField("max_storage")
    private Long max_storage;

    @TableField("expired_date")
    private LocalDateTime expired_date;

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
