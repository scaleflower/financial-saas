[根目录](../../CLAUDE.md) > [services](../) > **repayment-service**

---

# repayment-service 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`repayment-service` 负责还款管理：

- **还款申请创建**：员工还款申请
- **还款审批**：集成工作流引擎
- **还款结算**：冲销借款
- **还款查询**：列表查询、详情查询
- **冲销管理**：还款与借款的冲销关系

---

## 入口与启动

### 主启动类

```java
// 待补充
```

### 服务配置

```yaml
server:
  port: 8766

spring:
  application:
    name: repayment-service
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
| GET | /repayment | 查询还款单列表 |
| GET | /repayment/{id} | 获取还款单详情 |
| POST | /repayment | 创建还款单 |
| PUT | /repayment/{id} | 更新还款单 |
| POST | /repayment/{id}/submit | 提交还款单 |
| POST | /repayment/{id}/settle | 结算还款单 |
| GET | /repayment/{id}/settlements | 查询冲销记录 |

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

### repayment - 还款表

```sql
CREATE TABLE repayment (
    tenant_id BIGINT NOT NULL,
    repayment_id BIGSERIAL NOT NULL,
    repayment_code VARCHAR(60) NOT NULL,
    user_id BIGINT NOT NULL,
    user_name VARCHAR(60) NOT NULL,
    project_id BIGINT NOT NULL,
    repayment_amount NUMERIC(12,2) NOT NULL,
    lc_repayment_amount NUMERIC(12,2),
    repayment_date DATE NOT NULL,
    state VARCHAR(20) NOT NULL,
    processinstance_id VARCHAR(60),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    PRIMARY KEY (tenant_id, repayment_id)
);
```

### repayment_sett - 还款冲销表

```sql
CREATE TABLE repayment_sett (
    tenant_id BIGINT NOT NULL,
    sett_id BIGSERIAL NOT NULL,
    repayment_id BIGINT NOT NULL,
    loan_id BIGINT NOT NULL,
    sett_amount NUMERIC(12,2) NOT NULL,
    sett_date DATE NOT NULL,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (tenant_id, sett_id)
);
```

### 还款状态枚举

- `DRAFT` - 草稿
- `SUBMITTED` - 已提交
- `IN_APPROVAL` - 审批中
- `APPROVED` - 已通过
- `REJECTED` - 已驳回
- `COMPLETED` - 已完成
- `CANCELLED` - 已取消

---

## 常见问题 (FAQ)

---

## 相关文件清单

```
services/repayment-service/
├── pom.xml
└── src/main/
    └── java/com/fs/repaymentservice/
        └── entity/
            └── Repayment.java            # 还款实体
```
