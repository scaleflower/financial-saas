[根目录](../../CLAUDE.md) > [common](../) > **common-workflow**

---

# common-workflow 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`common-workflow` 提供 Camunda 工作流引擎集成：

- **工作流服务**：`WorkflowService` 封装流程操作
- **流程常量**：`ProcessConstants` 定义流程变量名
- **任务监听器**：`TaskListener` 处理任务事件
- **Camunda 配置**：`CamundaConfig` 连接配置

---

## 入口与启动

本模块是库模块，无独立启动入口。

```xml
<dependency>
    <groupId>com.fs</groupId>
    <artifactId>common-workflow</artifactId>
</dependency>
```

---

## 对外接口

### WorkflowService - 工作流服务

```java
// 启动流程实例
String processInstanceId = workflowService.startProcess(
    processKey, businessKey, variables
);

// 完成任务
workflowService.completeTask(taskId, variables);

// 查询待办任务
List<Task> tasks = workflowService.getPendingTasks(userId);

// 查询流程状态
ProcessInstanceStatus status = workflowService.getProcessStatus(processInstanceId);
```

### ProcessConstants - 流程常量

```java
public class ProcessConstants {
    public static final String VARIABLE_BUSINESS_KEY = "businessKey";
    public static final String VARIABLE_BUSINESS_TYPE = "businessType";
    public static final String VARIABLE_APPROVER = "approver";
    // ... 更多常量
}
```

---

## 关键依赖与配置

### Maven 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| common-core | 1.0.0 | 核心模块 |
| spring-zeebe-starter | 8.3.0 | Camunda Zeebe |

### 配置项

```yaml
camunda:
  gateway:
    address: localhost:26500
    client:
      id: financial-saas
      secret: ""
```

---

## 测试与质量

### 测试位置
- 暂无单元测试

---

## 常见问题 (FAQ)

**Q: 如何设计审批流程？**

A: 使用 Camunda Modeler（桌面应用）设计 BPMN 2.0 流程图。

**Q: 如何关联业务单据？**

A: 在流程变量中设置 `businessKey` 和 `businessType`：

```java
variables.put("businessKey", transId.toString());
variables.put("businessType", "TRANS");
```

---

## 相关文件清单

```
common/common-workflow/
├── pom.xml
└── src/main/java/com/fs/common/
    ├── workflow/
    │   ├── WorkflowService.java     # 工作流服务
    │   └── ProcessConstants.java    # 流程常量
    ├── listener/
    │   └── TaskListener.java        # 任务监听器
    └── config/
        └── CamundaConfig.java       # Camunda配置
```
