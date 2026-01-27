package com.fs.approvalservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalInstance {
    private Long processInstanceKey;
    private String businessKey;
    private Long tenantId;
    private Long applicantId;
    private String processDefinitionId;
    private String status;
    private LocalDateTime createdAt;
}
