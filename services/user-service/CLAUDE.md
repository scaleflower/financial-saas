[根目录](../../CLAUDE.md) > [services](../) > **user-service**

---

# user-service 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`user-service` 负责用户和认证相关功能：

- **用户管理**：用户CRUD、用户状态管理
- **认证授权**：用户名密码登录、JWT Token生成
- **钉钉集成**：钉钉扫码登录、用户信息同步
- **用户组织关联**：用户与组织的关系管理
- **用户角色关联**：用户与角色的关系管理

---

## 入口与启动

### 主启动类

```java
package com.fs.userservice;

@SpringBootApplication
@MapperScan("com.fs.userservice.mapper")
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
```

### 服务配置

```yaml
server:
  port: 8762

spring:
  application:
    name: user-service
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

### 启动命令

```bash
mvn spring-boot:run -pl services/user-service
```

---

## 对外接口

### REST API - 认证

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /auth/login | 用户登录 |
| POST | /auth/logout | 用户登出 |
| POST | /auth/refresh | 刷新Token |
| POST | /auth/dingtalk | 钉钉登录 |

### REST API - 用户

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /users | 查询用户列表 |
| GET | /users/{id} | 获取用户详情 |
| POST | /users | 创建用户 |
| PUT | /users/{id} | 更新用户信息 |
| DELETE | /users/{id} | 删除用户 |
| PUT | /users/{id}/password | 修改密码 |
| PUT | /users/{id}/status | 更新用户状态 |

---

## 关键依赖与配置

### Maven 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| common-core | 1.0.0 | 核心模块 |
| common-web | 1.0.0 | Web 配置 |
| common-security | 1.0.0 | 安全认证 |
| common-tenant | 1.0.0 | 租户支持 |
| mybatis-plus | 3.5.4.1 | ORM 框架 |
| postgresql | 42.7.1 | 数据库驱动 |
| spring-zeebe-starter | 8.3.0 | 工作流 |

---

## 数据模型

### user - 用户表

```sql
CREATE TABLE "user" (
    tenant_id BIGINT NOT NULL,
    user_id BIGSERIAL NOT NULL,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100),
    realname VARCHAR(60) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    dingtalk_id VARCHAR(100),
    org_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    deleted SMALLINT DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    PRIMARY KEY (tenant_id, user_id)
);
```

### 用户状态枚举

- `PENDING` - 待激活
- `ACTIVE` - 正常
- `LOCKED` - 锁定
- `DISABLED` - 停用

---

## 测试与质量

### 测试位置
- 暂无单元测试

### 测试运行

```bash
mvn test -pl services/user-service
```

---

## 常见问题 (FAQ)

**Q: 如何实现密码加密？**

A: 使用 BCrypt 算法加密存储密码：

```java
String encodedPassword = passwordEncoder.encode(rawPassword);
```

**Q: 如何处理钉钉登录？**

A: 通过钉钉OAuth2.0获取用户信息，匹配本地用户。

---

## 相关文件清单

```
services/user-service/
├── pom.xml
└── src/main/
    ├── java/com/fs/userservice/
    │   ├── UserServiceApplication.java    # 主启动类
    │   ├── controller/
    │   │   ├── AuthController.java        # 认证控制器
    │   │   └── UserController.java        # 用户控制器
    │   ├── service/
    │   │   ├── AuthService.java           # 认证服务
    │   │   └── UserService.java           # 用户服务
    │   ├── mapper/
    │   │   └── UserMapper.java            # 用户Mapper
    │   └── entity/
    │       └── User.java                  # 用户实体
    └── resources/
        └── application.yml                # 配置文件
```
