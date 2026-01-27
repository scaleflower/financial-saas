package com.fs.approvalservice.controller;

import com.fs.common.result.Result;
import com.fs.approvalservice.entity.ApprovalInstance;
import com.fs.approvalservice.entity.ApprovalTask;
import com.fs.approvalservice.service.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/approvals")
@RequiredArgsConstructor
public class ApprovalController {

    private final ApprovalService approvalService;

    @PostMapping("/start")
    public Result<Long> startApproval(@RequestParam String processDefinitionId,
                                      @RequestParam Long tenantId,
                                      @RequestParam Long applicantId,
                                      @RequestParam String businessKey,
                                      @RequestBody Map<String, Object> variables) {
        long instanceKey = approvalService.startApproval(
                processDefinitionId, tenantId, applicantId, businessKey, variables);
        return Result.success(instanceKey);
    }

    @GetMapping("/instance/{processInstanceKey}")
    public Result<ApprovalInstance> getInstance(@PathVariable Long processInstanceKey) {
        ApprovalInstance instance = approvalService.getApprovalInstance(processInstanceKey);
        return Result.success(instance);
    }

    @GetMapping("/task/{taskKey}")
    public Result<ApprovalTask> getTask(@PathVariable Long taskKey) {
        ApprovalTask task = approvalService.getTask(taskKey);
        return Result.success(task);
    }

    @PostMapping("/task/{taskKey}/complete")
    public Result<Void> completeTask(@PathVariable Long taskKey, @RequestBody Map<String, Object> variables) {
        approvalService.completeTask(taskKey, variables);
        return Result.success();
    }

    @PostMapping("/instance/{processInstanceKey}/cancel")
    public Result<Void> cancelApproval(@PathVariable Long processInstanceKey) {
        approvalService.cancelApproval(processInstanceKey);
        return Result.success();
    }
}
