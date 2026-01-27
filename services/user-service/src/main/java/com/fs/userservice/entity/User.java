package com.fs.userservice.entity;

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
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long user_id;
    
    @TableField("tenant_id")
    private Long tenant_id;
    
    @TableField("username")
    private String username;
    
    @TableField("password")
    private String password;
    
    @TableField("real_name")
    private String real_name;
    
    @TableField("email")
    private String email;
    
    @TableField("phone")
    private String phone;
    
    @TableField("org_id")
    private Long org_id;
    
    @TableField("status")
    private String status;
    
    @TableField("dingtalk_userid")
    private String dingtalk_userid;
    
    @TableField("last_login_time")
    private LocalDateTime last_login_time;
    
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime created_at;
    
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated_at;
    
    @TableLogic
    private Integer deleted;
}
