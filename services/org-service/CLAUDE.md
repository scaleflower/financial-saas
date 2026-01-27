[根目录](../../CLAUDE.md) > [services](../) > **org-service**

---

# org-service 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`org-service` 负责组织架构管理：

- **组织树管理**：支持多层级组织结构
- **组织信息维护**：组织基本信息、联系方式
- **组织成员管理**：组织成员添加、移除
- **组织负责人设置**：设置组织负责人
- **组织权限管理**：组织级权限控制

---

## 入口与启动

### 主启动类

```java
package com.fs.orgservice;

@SpringBootApplication
@MapperScan("com.fs.orgservice.mapper")
public class OrgServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrgServiceApplication.class, args);
    }
}
```

### 服务配置

```yaml
spring:
  application:
    name: org-service
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

---

## 对外接口

### REST API

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /orgs | 查询组织列表 |
| GET | /orgs/tree | 获取组织树 |
| GET | /orgs/{id} | 获取组织详情 |
| POST | /orgs | 创建组织 |
| PUT | /orgs/{id} | 更新组织信息 |
| DELETE | /orgs/{id} | 删除组织 |
| GET | /orgs/{id}/members | 获取组织成员 |
| PUT | /orgs/{id}/leader | 设置组织负责人 |

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

### org - 组织表

```sql
CREATE TABLE org (
    tenant_id BIGINT NOT NULL,
    org_id BIGSERIAL NOT NULL,
    org VARCHAR(50) NOT NULL,
    org_name VARCHAR(200) NOT NULL,
    org_type VARCHAR(50) NOT NULL,
    org_level INTEGER NOT NULL,
    parent_org_id BIGINT,
    parent_path VARCHAR(500),
    leader_id BIGINT,
    leader_name VARCHAR(60),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    PRIMARY KEY (tenant_id, org_id)
);
```

### 组织类型

- `COMPANY` - 公司
- `DEPARTMENT` - 部门
- `TEAM` - 团队

---

## 常见问题 (FAQ)

**Q: 如何防止组织树循环引用？**

A: 在保存时检查 `parent_org_id` 是否在当前组织的子孙节点中。

---

## 相关文件清单

```
services/org-service/
├── pom.xml
└── src/main/
    ├── java/com/fs/orgservice/
    │   ├── OrgServiceApplication.java    # 主启动类
    │   ├── controller/
    │   │   └── OrgController.java        # 组织控制器
    │   ├── service/
    │   │   └── OrgService.java           # 组织服务
    │   ├── mapper/
    │   │   └── OrgMapper.java            # 组织Mapper
    │   └── entity/
    │       └── Org.java                  # 组织实体
    └── resources/
        └── application.yml
```
