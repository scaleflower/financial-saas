[根目录](../../CLAUDE.md) > [services](../) > **notification-service**

---

# notification-service 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`notification-service` 负责通知管理：

- **通知发送**：系统通知、审批通知
- **通知查询**：已读、未读通知
- **通知模板管理**：通知内容模板
- **多渠道推送**：站内信、钉钉、邮件

---

## 入口与启动

### 主启动类

```java
// 待补充
```

### 服务配置

```yaml
server:
  port: 8768

spring:
  application:
    name: notification-service
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://127.0.0.1:5432/financial_saas
    username: heyake
    password: ""
```

---

## 对外接口

### REST API

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /notification | 查询通知列表 |
| GET | /notification/{id} | 获取通知详情 |
| POST | /notification | 发送通知 |
| PUT | /notification/{id}/read | 标记已读 |
| DELETE | /notification/{id} | 删除通知 |
| GET | /notification/unread/count | 未读通知数量 |

---

## 关键依赖与配置

### Maven 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| common-core | 1.0.0 | 核心模块 |
| common-web | 1.0.0 | Web 配置 |
| common-mq | 1.0.0 | 消息队列 |
| mybatis-plus | 3.5.4.1 | ORM 框架 |
| postgresql | 42.7.1 | 数据库驱动 |

---

## 数据模型

### notification - 通知表

```sql
CREATE TABLE notification (
    tenant_id BIGINT NOT NULL,
    notification_id BIGSERIAL NOT NULL,
    notification_type VARCHAR(20) NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    receiver_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    business_type VARCHAR(50),
    business_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (tenant_id, notification_id)
);
```

### 通知类型枚举

- `SYSTEM` - 系统通知
- `APPROVAL` - 审批通知
- `REMIND` - 提醒通知
- `MESSAGE` - 消息通知

---

## 常见问题 (FAQ)

---

## 相关文件清单

```
services/notification-service/
├── pom.xml
└── src/main/resources/
    └── application.yml
```
