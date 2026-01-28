# approval-service 功能测试清单

> 审批服务 - 审批流程管理、Camunda集成

**端口**: 8767
**职责**: 审批流程管理、Camunda集成
**最后更新**: 2025-01-28

---

## 测试覆盖情况

| 指标 | 数值 |
|------|------|
| 功能数 | 6 |
| 已测试 | 5 |
| 待测试 | 1 |
| **覆盖率** | **83.3%** |

---

## 功能列表

| 功能 | API端点 | 测试用例 | 状态 |
|------|---------|----------|------|
| 启动审批流程 | POST /approvals/start | ApprovalServiceTest.startApproval_Success | ✅ |
| 获取审批实例 | GET /approvals/instance/{key} | ApprovalServiceTest.getApprovalInstance_Success | ✅ |
| 获取审批任务 | GET /approvals/task/{taskKey} | ApprovalServiceTest.getTask_Success | ✅ |
| 完成审批任务 | POST /approvals/task/{taskKey}/complete | ApprovalServiceTest.completeTask_Success | ✅ |
| 取消审批 | POST /approvals/instance/{key}/cancel | ApprovalServiceTest.cancelApproval_Success | ✅ |
| 驳回审批 | POST /approvals/task/{taskKey}/reject | - | ⏳ 待实现 |
| 审批历史查询 | - | - | ⏳ 待实现 |

---

## 测试文件

### Service层测试
- **ApprovalServiceTest.java** - 审批Service层测试 (5个测试)
  - `startApproval_Success()` - 启动审批流程成功
  - `getApprovalInstance_Success()` - 获取审批实例成功
  - `getTask_Success()` - 获取审批任务成功
  - `completeTask_Success()` - 完成审批任务成功
  - `cancelApproval_Success()` - 取消审批成功

---

## 数据模型

### approval_instance - 审批实例表

```sql
CREATE TABLE approval_instance (
    tenant_id BIGINT NOT NULL,
    instance_id BIGSERIAL NOT NULL,
    business_key VARCHAR(100) NOT NULL,
    business_type VARCHAR(50) NOT NULL,
    process_key VARCHAR(100) NOT NULL,
    instance_key VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL,
    started_by BIGINT NOT NULL,
    started_at TIMESTAMP NOT NULL,
    completed_at TIMESTAMP,
    PRIMARY KEY (tenant_id, instance_id)
);
```

### approval_task - 审批任务表

```sql
CREATE TABLE approval_task (
    tenant_id BIGINT NOT NULL,
    task_id BIGSERIAL NOT NULL,
    instance_id BIGINT NOT NULL,
    task_key VARCHAR(100) NOT NULL,
    task_name VARCHAR(200) NOT NULL,
    assignee_id BIGINT,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    completed_at TIMESTAMP,
    PRIMARY KEY (tenant_id, task_id)
);
```

### 审批状态枚举
- `RUNNING` - 运行中
- `COMPLETED` - 已完成
- `CANCELLED` - 已取消
- `SUSPENDED` - 已挂起

---

## 待实现功能

### P0 - 核心功能（高优先级）
1. **驳回流程**
   - `POST /approvals/task/{taskKey}/reject` - 驳回审批
   - 驳回回退到指定节点
   - 驳回原因记录

2. **审批历史记录**
   - `GET /approvals/history/{instanceKey}` - 查询审批历史
   - 审批操作日志
   - 审批意见记录

3. **Camunda完整集成**
   - BPMN流程定义部署
   - 流程变量管理
   - 任务委托功能
   - 并行网关支持

### P1 - 重要功能（中优先级）
4. **Controller层API测试**
   - ApprovalController测试用例
   - API接口集成测试

5. **审批配置**
   - 审批规则配置
   - 审批人动态指定
   - 审批超时处理

---

## 测试运行

```bash
mvn test -pl services/approval-service
```
