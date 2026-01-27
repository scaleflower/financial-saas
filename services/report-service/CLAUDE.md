[根目录](../../CLAUDE.md) > [services](../) > **report-service**

---

# report-service 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`report-service` 负责报表统计：

- **报销统计**：按时间、项目、人员统计
- **借款统计**：借款金额、还款情况
- **数据导出**：Excel 导出
- **图表数据**：前端图表数据接口

---

## 入口与启动

### 主启动类

```java
// 待补充
```

### 服务配置

```yaml
server:
  port: 8769

spring:
  application:
    name: report-service
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://127.0.0.1:5432/financial_saas
    username: heyake
    password: ""
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
```

---

## 对外接口

### REST API

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /report/trans/summary | 报销汇总 |
| GET | /report/trans/by-project | 按项目统计 |
| GET | /report/trans/by-user | 按人员统计 |
| GET | /report/loan/summary | 借款汇总 |
| POST | /report/export | 导出报表 |

---

## 关键依赖与配置

### Maven 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| common-core | 1.0.0 | 核心模块 |
| common-web | 1.0.0 | Web 配置 |
| postgresql | 42.7.1 | 数据库驱动 |
| poi | 5.2.x | Excel 导出 |

---

## 常见问题 (FAQ)

---

## 相关文件清单

```
services/report-service/
├── pom.xml
└── src/main/resources/
    └── application.yml
```
