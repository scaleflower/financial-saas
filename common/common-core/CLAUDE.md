[根目录](../../CLAUDE.md) > [common](../) > **common-core**

---

# common-core 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`common-core` 是整个系统的核心基础模块，提供：

- **统一返回格式**：`Result<T>` 和 `PageResult<T>`
- **全局异常处理**：`GlobalExceptionHandler`
- **业务异常类**：`BusinessException`
- **响应码枚举**：`ResultCode`
- **工具类**：日期、字符串等常用工具

---

## 入口与启动

本模块是库模块，无独立启动入口。被其他模块作为依赖引入。

```xml
<dependency>
    <groupId>com.fs</groupId>
    <artifactId>common-core</artifactId>
</dependency>
```

---

## 对外接口

### 统一返回结果

```java
// 成功返回
Result.success(data)

// 失败返回
Result.error(ResultCode.ERROR, "错误信息")

// 分页返回
PageResult.of(list, total, page, size)
```

### 异常处理

```java
// 抛出业务异常
throw new BusinessException(ResultCode.ERROR, "错误信息");

// 使用预定义的错误码
throw new BusinessException(ResultCode.USER_NOT_FOUND);
```

---

## 关键依赖与配置

### Maven 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| spring-boot-starter | 3.2.0 | Spring Boot 核心 |
| spring-boot-starter-validation | 3.2.0 | 参数校验 |
| spring-boot-starter-web | 3.2.0 | Web 支持 |
| lombok | 1.18.30 | 简化代码 |
| hutool-all | 5.8.23 | 工具类库 |
| fastjson2 | 2.0.42 | JSON 处理 |

### 无需配置

本模块无需额外配置，开箱即用。

---

## 数据模型

### Result<T> - 统一返回结果

```java
public class Result<T> {
    private Integer code;      // 响应码
    private String message;    // 响应消息
    private T data;           // 响应数据
    private Long timestamp;   // 时间戳
}
```

### PageResult<T> - 分页返回结果

```java
public class PageResult<T> {
    private List<T> records;  // 数据列表
    private Long total;       // 总记录数
    private Long current;     // 当前页
    private Long size;        // 每页大小
    private Long pages;       // 总页数
}
```

### ResultCode - 响应码枚举

```java
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    ERROR(500, "操作失败"),
    PARAM_ERROR(400, "参数错误"),
    // ... 更多预定义错误码
}
```

---

## 测试与质量

### 测试位置
- 暂无单元测试

### 测试运行
```bash
mvn test -pl common/common-core
```

---

## 常见问题 (FAQ)

**Q: 如何添加自定义错误码？**

A: 在 `ResultCode` 枚举中添加新的常量：

```java
CUSTOM_ERROR(1001, "自定义错误")
```

**Q: 如何处理分页查询？**

A: 使用 `PageResult.of()` 方法：

```java
PageResult.of(list, total, current, size)
```

---

## 相关文件清单

```
common/common-core/
├── pom.xml
└── src/main/java/com/fs/common/
    ├── result/
    │   ├── Result.java           # 统一返回结果
    │   ├── ResultCode.java       # 响应码枚举
    │   └── PageResult.java       # 分页返回结果
    ├── exception/
    │   ├── BusinessException.java # 业务异常
    │   └── GlobalExceptionHandler.java # 全局异常处理
    ├── util/
    │   ├── DateUtils.java        # 日期工具
    │   └── StringUtils.java      # 字符串工具
    └── constant/
        └── CommonConstants.java  # 通用常量
```
