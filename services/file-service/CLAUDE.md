[根目录](../../CLAUDE.md) > [services](../) > **file-service**

---

# file-service 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`file-service` 负责文件管理：

- **文件上传**：支持多种文件类型
- **文件下载**：安全下载
- **文件预览**：图片、PDF预览
- **MinIO集成**：对象存储

---

## 入口与启动

### 主启动类

```java
// 待补充
```

### 服务配置

```yaml
server:
  port: 8770

spring:
  application:
    name: file-service
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://127.0.0.1:5432/financial_saas
    username: heyake
    password: ""

minio:
  endpoint: http://localhost:9000
  access-key: admin
  secret-key: admin123
  bucket-name: financial-saas
```

---

## 对外接口

### REST API

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /file/upload | 上传文件 |
| GET | /file/{id} | 下载文件 |
| GET | /file/{id}/preview | 预览文件 |
| DELETE | /file/{id} | 删除文件 |

---

## 关键依赖与配置

### Maven 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| common-core | 1.0.0 | 核心模块 |
| common-web | 1.0.0 | Web 配置 |
| minio | 8.5.7 | 对象存储 |
| postgresql | 42.7.1 | 数据库驱动 |

---

## 常见问题 (FAQ)

---

## 相关文件清单

```
services/file-service/
├── pom.xml
└── src/main/resources/
    └── application.yml
```
