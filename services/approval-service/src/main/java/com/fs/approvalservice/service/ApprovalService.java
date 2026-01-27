package com.fs.approvalservice.service;

import com.fs.approvalservice.entity.ApprovalInstance;
import com.fs.approvalservice.entity.ApprovalTask;
import com.fs.common.workflow.WorkflowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApprovalService {

    private final WorkflowService workflowService;

    public long startApproval(String processDefinitionId, Long tenantId, Long applicantId,
                              String businessKey, Map<String, Object> variables) {
        Map<String, Object> vars = new HashMap<>(variables);
        vars.put("tenantId", tenantId);
        vars.put("applicantId", applicantId);
        vars.put("businessKey", businessKey);

        return workflowService.startProcess(processDefinitionId);
    }

    public ApprovalInstance getApprovalInstance(long processInstanceKey) {
        return ApprovalInstance.builder()
                .processInstanceKey(processInstanceKey)
                .status("RUNNING")
                .build();
    }

    public ApprovalTask getTask(Long taskKey) {
        return ApprovalTask.builder()
                .taskKey(taskKey)
                .status("ASSIGNED")
                .build();
    }

    public void completeTask(Long taskKey, Map<String, Object> variables) {
        log.info("Completing task: {} with variables: {}", taskKey, variables);
    }

    public void cancelApproval(Long processInstanceKey) {
        workflowService.cancelProcessInstance(processInstanceKey);
    }
}
