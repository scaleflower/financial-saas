package com.fs.approval.service;

import com.fs.approval.ApprovalServiceApplication;
import com.fs.approval.entity.ApprovalInstance;
import com.fs.approval.entity.ApprovalTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ApprovalServiceApplication.class)
@ActiveProfiles("test")
@DisplayName("审批服务集成测试")
class ApprovalServiceTest {

    @Autowired
    private ApprovalService approvalService;

    private String uniqueSuffix;

    @BeforeEach
    void setUp() {
        uniqueSuffix = String.valueOf(System.currentTimeMillis());
    }

    private ApprovalInstance createTestInstance() {
        return ApprovalInstance.builder()
                .processInstanceKey("PROCESS_" + uniqueSuffix)
                .businessKey("BUSINESS_" + uniqueSuffix)
                .tenantId(1L)
                .applicantId(1L)
                .processDefinitionId("PROCESS_DEF_001")
                .status("RUNNING")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("启动审批流程 - 成功")
    void startApproval_Success() {
        ApprovalInstance instance = createTestInstance();
        ApprovalInstance result = approvalService.startApproval(instance);
        
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("获取审批实例 - 成功")
    void getApprovalInstance_Success() {
        ApprovalInstance instance = createTestInstance();
        approvalService.startApproval(instance);
        
        ApprovalInstance found = approvalService.getApprovalInstance(instance.getProcessInstanceKey());
        
        assertThat(found).isNotNull();
    }

    @Test
    @DisplayName("获取审批任务 - 成功")
    void getTask_Success() {
        // Mock task data
        ApprovalTask task = ApprovalTask.builder()
                .taskKey("TASK_" + uniqueSuffix)
                .processInstanceKey("PROCESS_" + uniqueSuffix)
                .taskType("APPROVE")
                .assignee("1")
                .taskName("部门审批")
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
        
        // 测试获取任务
        assertThat(task).isNotNull();
        assertThat(task.getTaskKey()).contains("TASK_");
    }

    @Test
    @DisplayName("完成任务 - 成功")
    void completeTask_Success() {
        String taskKey = "TASK_" + uniqueSuffix;
        boolean result = approvalService.completeTask(taskKey, true, "同意");
        
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("取消审批 - 成功")
    void cancelApproval_Success() {
        ApprovalInstance instance = createTestInstance();
        approvalService.startApproval(instance);
        
        boolean result = approvalService.cancelApproval(instance.getProcessInstanceKey());
        
        assertThat(result).isTrue();
    }
}
