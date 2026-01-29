[根目录](../../CLAUDE.md) > [gateway](../) > **gateway-service**

---

# gateway-service 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`gateway-service` 是系统的统一入口：

- **路由转发**：将请求转发到对应的微服务
- **租户识别**：从请求头提取租户ID
- **统一认证**：JWT Token验证
- **负载均衡**：基于Nacos的服务发现
- **跨域处理**：统一CORS配置

---

## 入口与启动

### 主启动类

```java
package com.fs.gateway;

@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
```

### 服务配置

```yaml
server:
  port: 9200

spring:
  application:
    name: gateway-service
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
      config:
        server-addr: localhost:8848
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      routes:
        - id: tenant-service
          uri: lb://tenant-service
          predicates:
            - Path=/tenants/**
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/auth/**,/users/**
        - id: org-service
          uri: lb://org-service
          predicates:
            - Path=/orgs/**
```

---

## 对外接口

### 路由规则

| 路径 | 目标服务 | 说明 |
|------|---------|------|
| /tenants/** | tenant-service | 租户管理 |
| /auth/** | user-service | 认证相关 |
| /users/** | user-service | 用户管理 |
| /orgs/** | org-service | 组织管理 |
| /trans/** | trans-service | 报销管理 |
| /loan/** | loan-service | 借款管理 |
| /repayment/** | repayment-service | 还款管理 |
| /approval/** | approval-service | 审批管理 |
| /notification/** | notification-service | 通知管理 |
| /report/** | report-service | 报表管理 |
| /file/** | file-service | 文件管理 |

---

## 关键依赖与配置

### Maven 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| spring-cloud-gateway | 4.0.0 | API网关 |
| spring-cloud-starter-loadbalancer | 4.0.0 | 负载均衡 |
| nacos-discovery | 2.2.3 | 服务发现 |

---

## 请求处理流程

```mermaid
graph LR
    A[客户端] --> B[Gateway]
    B --> C[租户识别]
    C --> D[JWT验证]
    D --> E[路由转发]
    E --> F[微服务]
```

---

## 测试与质量

### 测试位置
- 暂无单元测试

---

## 常见问题 (FAQ)

**Q: 如何在Gateway中添加新的路由？**

A: 在 `application.yml` 的 `spring.cloud.gateway.routes` 中添加：

```yaml
- id: new-service
  uri: lb://new-service
  predicates:
    - Path=/new/**
```

**Q: 如何处理跨域？**

A: 在 `GatewayConfig` 中配置 CORS：

```java
@Bean
public CorsWebFilter corsFilter() {
    // CORS配置
}
```

---

## 相关文件清单

```
gateway/gateway-service/
├── pom.xml
└── src/main/
    ├── java/com/fs/gateway/
    │   ├── GatewayApplication.java       # 主启动类
    │   ├── config/
    │   │   └── GatewayConfig.java        # 网关配置
    │   └── filter/
    │       └── TenantFilter.java         # 租户过滤器
    └── resources/
        └── application.yml               # 配置文件
```
