[根目录](../../CLAUDE.md) > [common](../) > **common-feign**

---

# common-feign 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`common-feign` 提供服务间调用封装：

- **Feign 客户端配置**：统一请求头、超时设置
- **服务发现**：基于 Nacos 的服务调用
- **负载均衡**：集成 Spring Cloud LoadBalancer

---

## 入口与启动

本模块是库模块，无独立启动入口。

```xml
<dependency>
    <groupId>com.fs</groupId>
    <artifactId>common-feign</artifactId>
</dependency>
```

---

## 对外接口

### Feign 客户端示例

```java
@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/users/{id}")
    Result<User> getUser(@PathVariable Long id);
}
```

---

## 关键依赖与配置

### Maven 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| common-core | 1.0.0 | 核心模块 |
| spring-cloud-starter-openfeign | 4.0.0 | Feign |
| spring-cloud-starter-loadbalancer | 4.0.0 | 负载均衡 |

### 配置项

```yaml
spring:
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
```

---

## 常见问题 (FAQ)

**Q: 如何在 Feign 调用中传递租户ID？**

A: 使用拦截器自动添加请求头：

```java
public class FeignTenantInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        Long tenantId = TenantContextHolder.getTenantId();
        if (tenantId != null) {
            template.header("X-Tenant-Id", tenantId.toString());
        }
    }
}
```

---

## 相关文件清单

```
common/common-feign/
├── pom.xml
└── (待补充具体实现)
```
