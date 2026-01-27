[根目录](../../CLAUDE.md) > [common](../) > **common-security**

---

# common-security 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`common-security` 提供认证和授权相关功能：

- **JWT 工具类**：Token 生成、解析、验证
- **JWT 拦截器**：请求拦截，验证用户身份
- **用户认证信息**：`UserAuthInfo` 用户信息封装
- **安全配置**：`SecurityConfig`

---

## 入口与启动

本模块是库模块，无独立启动入口。

```xml
<dependency>
    <groupId>com.fs</groupId>
    <artifactId>common-security</artifactId>
</dependency>
```

---

## 对外接口

### JwtUtil - JWT工具类

```java
// 生成Token
String token = JwtUtil.generateToken(userId, tenantId);

// 解析Token
UserAuthInfo userInfo = JwtUtil.parseToken(token);

// 验证Token
boolean valid = JwtUtil.validateToken(token);
```

### UserAuthInfo - 用户认证信息

```java
public class UserAuthInfo {
    private Long userId;
    private Long tenantId;
    private String username;
    // ... 其他用户信息
}
```

---

## 关键依赖与配置

### Maven 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| common-core | 1.0.0 | 核心模块 |
| jjwt-api | 0.11.x | JWT 支持 |

### 配置项

```yaml
jwt:
  secret: your-secret-key
  expiration: 86400  # 24小时（秒）
```

---

## 测试与质量

### 测试位置
- 暂无单元测试

---

## 常见问题 (FAQ)

**Q: 如何在请求中传递Token？**

A: 在请求头中添加 `Authorization`：

```http
GET /api/users
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## 相关文件清单

```
common/common-security/
├── pom.xml
└── src/main/java/com/fs/common/
    ├── config/
    │   ├── SecurityConfig.java      # 安全配置
    │   └── JwtAuthInterceptor.java  # JWT拦截器
    ├── util/
    │   ├── JwtUtil.java             # JWT工具类
    │   └── JwtException.java        # JWT异常
    └── security/
        └── UserAuthInfo.java        # 用户认证信息
```
