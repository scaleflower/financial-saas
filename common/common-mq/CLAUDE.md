[根目录](../../CLAUDE.md) > [common](../) > **common-mq**

---

# common-mq 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`common-mq` 提供消息队列封装：

- **RabbitMQ 配置**：连接、交换机、队列声明
- **消息发送者**：统一的消息发送接口
- **消息消费者**：基础消费框架

---

## 入口与启动

本模块是库模块，无独立启动入口。

```xml
<dependency>
    <groupId>com.fs</groupId>
    <artifactId>common-mq</artifactId>
</dependency>
```

---

## 对外接口

### 消息发送

```java
// 发送消息
rabbitTemplate.convertAndSend(exchange, routingKey, message);
```

### 消息消费

```java
@RabbitListener(queues = "queue-name")
public void handleMessage(Message message) {
    // 处理消息
}
```

---

## 关键依赖与配置

### Maven 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| common-core | 1.0.0 | 核心模块 |
| spring-boot-starter-amqp | 3.2.0 | RabbitMQ |

### 配置项

```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: admin
    password: admin
```

---

## 常见问题 (FAQ)

**Q: 如何创建新的队列？**

A: 使用 `@Bean` 声明队列、交换机和绑定关系。

---

## 相关文件清单

```
common/common-mq/
├── pom.xml
└── (待补充具体实现)
```
