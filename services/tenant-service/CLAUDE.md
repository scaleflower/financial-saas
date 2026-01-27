[根目录](../../CLAUDE.md) > [services](../) > **tenant-service**

---

# tenant-service 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`tenant-service` 是多租户系统的核心基础服务，负责：

- **租户注册**：新租户入驻申请
- **租户信息管理**：租户基本信息维护
- **租户状态管理**：启用/停用/过期
- **租户配额管理**：用户数、存储空间配额
- **租户配置管理**：个性化配置（JSON格式）

---

## 入口与启动

### 主启动类

```java
package com.fs.tenant;

@MapperScan("com.fs.tenant.mapper")
public class TenantApplication {
    public static void main(String[] args) {
        SpringApplication.run(TenantApplication.class, args);
    }
}
```

### 服务配置

```yaml
server:
  port: 8080

spring:
  application:
    name: tenant-service
  datasource:
    url: jdbc:postgresql://localhost:5432/financial_saas
    username: heyake
    driver-class-name: org.postgresql.Driver
  cloud:
    nacos:
      server-addr: 127.0.0.1:8848
      discovery:
        enabled: true
```

### 启动命令

```bash
mvn spring-boot:run -pl services/tenant-service
```

---

## 对外接口

### REST API

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /tenants | 注册新租户 |
| GET | /tenants | 查询租户列表 |
| GET | /tenants/{id} | 获取租户详情 |
| PUT | /tenants/{id} | 更新租户信息 |
| DELETE | /tenants/{id} | 删除租户（软删除） |
| PUT | /tenants/{id}/status | 更新租户状态 |

---

## 关键依赖与配置

### Maven 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| common-core | 1.0.0 | 核心模块 |
| common-web | 1.0.0 | Web 配置 |
| mybatis-plus | 3.5.4.1 | ORM 框架 |
| postgresql | 42.7.1 | 数据库驱动 |

---

## 数据模型

### tenant - 租户表

```sql
CREATE TABLE tenant (
    tenant_id BIGSERIAL PRIMARY KEY,
    tenant_code VARCHAR(50) NOT NULL,
    tenant_name VARCHAR(200) NOT NULL,
    tenant_type VARCHAR(50) NOT NULL,
    contact_name VARCHAR(60),
    contact_phone VARCHAR(20),
    contact_email VARCHAR(100),
    status VARCHAR(20) NOT NULL,
    config JSONB,
    max_users INTEGER DEFAULT 10,
    max_storage_mb INTEGER DEFAULT 1024,
    expire_date DATE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP
);
```

### 租户状态枚举

- `PENDING` - 待审核
- `ACTIVE` - 正常
- `SUSPENDED` - 停用
- `EXPIRED` - 已过期

---

## 测试与质量

### 测试位置
- 暂无单元测试

### 测试运行

```bash
mvn test -pl services/tenant-service
```

---

## 常见问题 (FAQ)

**Q: 如何处理租户配额？**

A: 在租户创建时检查配额，业务操作时验证配额是否充足。

**Q: 租户删除后数据如何处理？**

A: 使用软删除（`deleted_at`），数据保留但不可访问。

---

## 相关文件清单

```
services/tenant-service/
├── pom.xml
└── src/main/
    ├── java/com/fs/tenant/
    │   ├── TenantApplication.java    # 主启动类
    │   ├── controller/
    │   │   └── TenantController.java # 租户控制器
    │   ├── service/
    │   │   └── TenantService.java    # 租户服务
    │   ├── mapper/
    │   │   └── TenantMapper.java     # 租户Mapper
    │   └── entity/
    │       └── Tenant.java           # 租户实体
    └── resources/
        └── application.yml           # 配置文件
```
