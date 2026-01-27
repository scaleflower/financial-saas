[根目录](../../CLAUDE.md) > [services](../) > **approval-service**

---

# approval-service 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`approval-service` 负责审批流程管理：

- **流程实例管理**：启动、查询、终止流程
- **任务管理**：待办任务查询、任务领取、任务完成
- **审批历史查询**：查询审批记录
- **流程监控**：监控流程执行状态

---

## 入口与启动

### 主启动类

```java
package com.fs.approvalservice;

@SpringBootApplication
public class ApprovalServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApprovalServiceApplication.class, args);
    }
}
```

### 服务配置

```yaml
server:
  port: 8767

spring:
  application:
    name: approval-service
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://127.0.0.1:5432/financial_saas
    username: heyake
    password: ""
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
      config:
        server-addr: localhost:8848

camunda:
  gateway:
    address: localhost:26500
```

---

## 对外接口

### REST API

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /approval/instances | 查询流程实例 |
| POST | /approval/instances | 启动流程实例 |
| GET | /approval/instances/{id} | 获取流程实例详情 |
| DELETE | /approval/instances/{id} | 终止流程实例 |
| GET | /approval/tasks | 查询待办任务 |
| POST | /approval/tasks/{id}/claim | 领取任务 |
| POST | /approval/tasks/{id}/complete | 完成任务 |
| GET | /approval/history | 查询审批历史 |

---

## 关键依赖与配置

### Maven 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| common-core | 1.0.0 | 核心模块 |
| common-web | 1.0.0 | Web 配置 |
| common-workflow | 1.0.0 | 工作流支持 |
| camunda | 8.3.0 | 流程引擎 |

---

## 数据模型

### approval_instance - 审批实例

```java
public class ApprovalInstance {
    private String instanceId;        // 流程实例ID
    private String processKey;         // 流程定义key
    private String businessType;      // 业务类型
    private Long businessId;          // 业务ID
    private String state;             // 流程状态
    private LocalDateTime startTime;  // 开始时间
}
```

### approval_task - 审批任务

```java
public class ApprovalTask {
    private String taskId;            // 任务ID
    private String taskName;          // 任务名称
    private String instanceId;        // 流程实例ID
    private Long assigneeId;          // 分配人ID
    private String state;             // 任务状态
}
```

---

## 常见问题 (FAQ)

---

## 相关文件清单

```
services/approval-service/
├── pom.xml
└── src/main/
    ├── java/com/fs/approvalservice/
    │   ├── ApprovalServiceApplication.java  # 主启动类
    │   ├── controller/
    │   │   └── ApprovalController.java      # 审批控制器
    │   ├── service/
    │   │   └── ApprovalService.java         # 审批服务
    │   └── entity/
    │       ├── ApprovalInstance.java        # 审批实例
    │       └── ApprovalTask.java            # 审批任务
    └── resources/
        └── application.yml
```
