[根目录](../../CLAUDE.md) > [services](../) > **trans-service**

---

# trans-service 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`trans-service` 是报销单管理的核心服务：

- **报销单创建**：支持草稿、提交
- **报销单审批**：集成工作流引擎
- **报销单查询**：列表查询、详情查询
- **报销单结算**：审批通过后结算
- **动态表单**：支持不同报销类型的表单
- **插件扩展**：通过插件支持自定义报销类型

---

## 入口与启动

### 主启动类

```java
package com.fs.transservice;

@SpringBootApplication
@MapperScan("com.fs.transservice.mapper")
public class TransServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransServiceApplication.class, args);
    }
}
```

### 服务配置

```yaml
server:
  port: 8764

spring:
  application:
    name: trans-service
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

mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true
  global-config:
    db-config:
      id-type: auto
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0
```

---

## 对外接口

### REST API

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /trans | 查询报销单列表 |
| GET | /trans/{id} | 获取报销单详情 |
| POST | /trans | 创建报销单 |
| PUT | /trans/{id} | 更新报销单 |
| DELETE | /trans/{id} | 删除报销单 |
| POST | /trans/{id}/submit | 提交报销单 |
| POST | /trans/{id}/approve | 审批报销单 |
| POST | /trans/{id}/settle | 结算报销单 |
| GET | /trans/types | 获取报销类型列表 |

---

## 关键依赖与配置

### Maven 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| common-core | 1.0.0 | 核心模块 |
| common-web | 1.0.0 | Web 配置 |
| common-tenant | 1.0.0 | 租户支持 |
| common-plugin | 1.0.0 | 插件支持 |
| mybatis-plus | 3.5.4.1 | ORM 框架 |
| postgresql | 42.7.1 | 数据库驱动 |

---

## 数据模型

### trans - 报销单表

```sql
CREATE TABLE trans (
    tenant_id BIGINT NOT NULL,
    trans_id BIGSERIAL NOT NULL,
    trans_code VARCHAR(60) NOT NULL,
    trans_type_id BIGINT NOT NULL,
    trans_type_name VARCHAR(100) NOT NULL,
    project_id BIGINT NOT NULL,
    trans_charge NUMERIC(12,2),
    lc_charge NUMERIC(12,2),
    state VARCHAR(20) NOT NULL,
    processinstance_id VARCHAR(60),
    created_user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    PRIMARY KEY (tenant_id, trans_id)
);
```

### trans_item - 报销明细表

```sql
CREATE TABLE trans_item (
    tenant_id BIGINT NOT NULL,
    trans_item_id BIGSERIAL NOT NULL,
    trans_id BIGINT NOT NULL,
    trans_item_name VARCHAR(200) NOT NULL,
    state VARCHAR(20),
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (tenant_id, trans_item_id)
);
```

### charge_item - 费用明细表

```sql
CREATE TABLE charge_item (
    tenant_id BIGINT NOT NULL,
    charge_id BIGSERIAL NOT NULL,
    trans_item_id BIGINT NOT NULL,
    expense_item_type_id BIGINT NOT NULL,
    charge_amount NUMERIC(12,2) NOT NULL,
    lc_charge NUMERIC(12,2),
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (tenant_id, charge_id)
);
```

### 报销状态枚举

- `DRAFT` - 草稿
- `SUBMITTED` - 已提交
- `IN_APPROVAL` - 审批中
- `APPROVED` - 已通过
- `REJECTED` - 已驳回
- `COMPLETED` - 已完成
- `CANCELLED` - 已取消

---

## 报销类型

通过插件扩展，内置类型：

- `GENERAL` - 通用报销
- `TRAVEL` - 差旅报销
- `TRAFFIC` - 交通报销
- `HOTEL` - 住宿报销
- `MEAL` - 餐饮报销
- `OTHER` - 其他报销

---

## 测试与质量

### 测试位置
- 暂无单元测试

---

## 常见问题 (FAQ)

**Q: 如何添加新的报销类型？**

A: 参考 `common-plugin` 模块文档，实现 `TransTypePlugin` 接口。

**Q: 报销金额如何计算？**

A: 费用明细汇总到报销明细，报销明细汇总到报销单。

---

## 相关文件清单

```
services/trans-service/
├── pom.xml
└── src/main/
    ├── java/com/fs/transservice/
    │   ├── TransServiceApplication.java    # 主启动类
    │   ├── controller/
    │   │   └── TransController.java        # 报销控制器
    │   ├── service/
    │   │   └── TransService.java           # 报销服务
    │   ├── mapper/
    │   │   └── TransMapper.java            # 报销Mapper
    │   └── entity/
    │       ├── Trans.java                  # 报销单实体
    │       ├── TransItem.java              # 报销明细实体
    │       └── ChargeItem.java             # 费用明细实体
    └── resources/
        └── application.yml
```
