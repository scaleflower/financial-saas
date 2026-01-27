[根目录](../../CLAUDE.md) > [common](../) > **common-tenant**

---

# common-tenant 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`common-tenant` 提供多租户基础设施：

- **租户上下文**：`TenantContextHolder` 存储当前请求的租户ID
- **租户拦截器**：MyBatis-Plus 租户行级拦截器
- **租户隔离**：自动在SQL中添加 `tenant_id` 条件

---

## 入口与启动

本模块是库模块，无独立启动入口。

```xml
<dependency>
    <groupId>com.fs</groupId>
    <artifactId>common-tenant</artifactId>
</dependency>
```

---

## 对外接口

### TenantContextHolder - 租户上下文

```java
// 设置租户ID
TenantContextHolder.setTenantId(123L);

// 获取租户ID
Long tenantId = TenantContextHolder.getTenantId();

// 清除租户ID（请求结束时）
TenantContextHolder.clear();
```

### TenantLineInnerInterceptor - 租户行拦截器

MyBatis-Plus 拦截器，自动在SQL中添加租户条件：

```sql
SELECT * FROM user WHERE username = ? AND tenant_id = 123
```

---

## 关键依赖与配置

### Maven 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| common-core | 1.0.0 | 核心模块 |
| mybatis-plus | 3.5.4.1 | ORM 框架 |

### 配置项

```yaml
mybatis-plus:
  tenant-line-interceptor:
    enabled: true
    tenant-id-column: tenant_id
```

---

## 数据模型

### 租户字段规范

所有业务表必须包含 `tenant_id` 字段：

```sql
CREATE TABLE example (
    tenant_id BIGINT NOT NULL,
    -- 其他字段
    PRIMARY KEY (tenant_id, id)
);
```

---

## 测试与质量

### 测试位置
- 暂无单元测试

---

## 常见问题 (FAQ)

**Q: 如何确保租户隔离？**

A: 1. 所有表添加 `tenant_id` 字段
   2. 使用 `TenantLineInnerInterceptor`
   3. 复合主键包含 `tenant_id`

**Q: 如何处理跨租户数据？**

A: 跨租户操作需要特殊权限，暂时不支持。

---

## 相关文件清单

```
common/common-tenant/
├── pom.xml
└── src/main/java/com/fs/common/
    ├── context/
    │   └── TenantContextHolder.java    # 租户上下文
    └── plugin/
        └── TenantLineInnerInterceptor.java # 租户行拦截器
```
