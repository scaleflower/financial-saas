[根目录](../../CLAUDE.md) > [common](../) > **common-web**

---

# common-web 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`common-web` 为所有微服务提供 Web 层公共配置：

- **Web MVC 配置**：跨域处理、拦截器注册
- **租户拦截器**：从请求头提取租户ID并设置到上下文
- **统一异常处理**：结合 common-core 的异常处理

---

## 入口与启动

本模块是库模块，无独立启动入口。

```xml
<dependency>
    <groupId>com.fs</groupId>
    <artifactId>common-web</artifactId>
</dependency>
```

---

## 对外接口

### WebConfig - Web配置类

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    // 跨域配置
    // 拦截器注册
}
```

### TenantInterceptor - 租户拦截器

```java
public class TenantInterceptor implements HandlerInterceptor {
    // 从请求头提取 tenant_id
    // 设置到 TenantContextHolder
}
```

---

## 关键依赖与配置

### Maven 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| common-core | 1.0.0 | 核心模块 |
| spring-boot-starter-web | 3.2.0 | Web 支持 |

### 配置项

```yaml
# 无需额外配置
```

---

## 测试与质量

### 测试位置
- 暂无单元测试

---

## 常见问题 (FAQ)

**Q: 如何在请求中传递租户ID？**

A: 在请求头中添加 `X-Tenant-Id`：

```http
GET /api/users
X-Tenant-Id: 123
```

---

## 相关文件清单

```
common/common-web/
├── pom.xml
└── src/main/java/com/fs/common/
    ├── config/
    │   └── WebConfig.java          # Web配置
    └── interceptor/
        └── TenantInterceptor.java  # 租户拦截器
```
