[根目录](../../CLAUDE.md) > [services](../) > **loan-service**

---

# loan-service 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`loan-service` 负责借款管理：

- **借款申请创建**：员工借款申请
- **借款审批**：集成工作流引擎
- **借款发放**：审批通过后发放
- **借款查询**：列表查询、详情查询
- **还款跟踪**：跟踪借款还款状态

---

## 入口与启动

### 主启动类

```java
// 待补充
```

### 服务配置

```yaml
server:
  port: 8765

spring:
  application:
    name: loan-service
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://127.0.0.1:5432/financial_saas
    username: heyake
    password: ""
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
```

---

## 对外接口

### REST API

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /loan | 查询借款单列表 |
| GET | /loan/{id} | 获取借款单详情 |
| POST | /loan | 创建借款单 |
| PUT | /loan/{id} | 更新借款单 |
| POST | /loan/{id}/submit | 提交借款单 |
| POST | /loan/{id}/approve | 审批借款单 |
| POST | /loan/{id}/release | 发放借款 |
| GET | /loan/{id}/repayment | 查询还款状态 |

---

## 关键依赖与配置

### Maven 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| common-core | 1.0.0 | 核心模块 |
| common-web | 1.0.0 | Web 配置 |
| common-workflow | 1.0.0 | 工作流支持 |
| mybatis-plus | 3.5.4.1 | ORM 框架 |
| postgresql | 42.7.1 | 数据库驱动 |

---

## 数据模型

### loan - 借款表

```sql
CREATE TABLE loan (
    tenant_id BIGINT NOT NULL,
    loan_id BIGSERIAL NOT NULL,
    loan_code VARCHAR(60) NOT NULL,
    loan_type VARCHAR(50),
    user_id BIGINT NOT NULL,
    user_name VARCHAR(60) NOT NULL,
    project_id BIGINT NOT NULL,
    loan_amount NUMERIC(12,2) NOT NULL,
    loan_lc_amount NUMERIC(12,2),
    amount_repaid NUMERIC(12,2) DEFAULT 0,
    amount_outstanding NUMERIC(12,2),
    loan_date DATE NOT NULL,
    due_date DATE,
    state VARCHAR(20) NOT NULL,
    processinstance_id VARCHAR(60),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    PRIMARY KEY (tenant_id, loan_id)
);
```

### 借款状态枚举

- `DRAFT` - 草稿
- `SUBMITTED` - 已提交
- `IN_APPROVAL` - 审批中
- `APPROVED` - 已通过
- `REJECTED` - 已驳回
- `RELEASED` - 已发放
- `COMPLETED` - 已完成
- `CANCELLED` - 已取消

---

## 常见问题 (FAQ)

---

## 相关文件清单

```
services/loan-service/
├── pom.xml
└── src/main/
    ├── java/com/fs/loanservice/
    │   ├── controller/
    │   │   └── LoanController.java        # 借款控制器
    │   ├── service/
    │   │   └── LoanService.java           # 借款服务
    │   └── entity/
    │       └── Loan.java                  # 借款实体
    └── resources/
        └── application.yml
```
