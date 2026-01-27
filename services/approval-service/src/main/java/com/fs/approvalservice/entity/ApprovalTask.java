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
public class ApprovalTask {
    private Long taskKey;
    private Long processInstanceKey;
    private String taskType;
    private String assignee;
    private String taskName;
    private String status;
    private LocalDateTime createdAt;
}
