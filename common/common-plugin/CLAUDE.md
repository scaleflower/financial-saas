[根目录](../../CLAUDE.md) > [common](../) > **common-plugin**

---

# common-plugin 模块

> 最后更新：2025-01-27

---

## 变更记录 (Changelog)

| 日期 | 操作 | 说明 |
|------|------|------|
| 2025-01-27 | 初始化 | 创建模块文档 |

---

## 模块职责

`common-plugin` 提供插件化架构支持：

- **报销类型插件接口**：`TransTypePlugin`
- **插件管理器**：`TransTypePluginManager`
- **动态表单引擎**：`DynamicFormEngine`
- **表单模型**：`FormSchema`、`FormField`

支持不同类型的报销单通过插件扩展，无需修改核心代码。

---

## 入口与启动

本模块是库模块，无独立启动入口。

```xml
<dependency>
    <groupId>com.fs</groupId>
    <artifactId>common-plugin</artifactId>
</dependency>
```

---

## 对外接口

### TransTypePlugin - 报销类型插件接口

```java
public interface TransTypePlugin {
    // 插件类型代码
    String getType();

    // 插件类型名称
    String getName();

    // 获取表单配置
    FormSchema getFormSchema();

    // 验证表单数据
    void validate(FormData data);

    // 计算报销金额
    BigDecimal calculateAmount(FormData data);
}
```

### TransTypePluginManager - 插件管理器

```java
// 注册插件
pluginManager.registerPlugin(new TravelTransTypePlugin());

// 获取插件
TransTypePlugin plugin = pluginManager.getPlugin("TRAVEL");

// 执行插件逻辑
BigDecimal amount = plugin.calculateAmount(formData);
```

### 内置插件

- `GeneralTransTypePlugin` - 通用报销
- `TravelTransTypePlugin` - 差旅报销

---

## 关键依赖与配置

### Maven 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| common-core | 1.0.0 | 核心模块 |
| fastjson2 | 2.0.42 | JSON 处理 |

---

## 数据模型

### FormSchema - 表单配置

```java
public class FormSchema {
    private String schemaId;      // 配置ID
    private String schemaName;    // 配置名称
    private List<FormField> fields; // 字段列表
    private Map<String, Object> rules; // 验证规则
}
```

### FormField - 表单字段

```java
public class FormField {
    private String fieldId;       // 字段ID
    private String fieldName;     // 字段名称
    private String fieldType;     // 字段类型
    private boolean required;     // 是否必填
    private Object defaultValue;  // 默认值
    private List<Option> options; // 选项（下拉框等）
}
```

### TransType - 报销类型枚举

```java
public enum TransType {
    GENERAL("通用报销"),
    TRAVEL("差旅报销"),
    TRAFFIC("交通报销"),
    HOTEL("住宿报销"),
    MEAL("餐饮报销"),
    OTHER("其他报销")
}
```

---

## 测试与质量

### 测试位置
- 暂无单元测试

---

## 常见问题 (FAQ)

**Q: 如何添加新的报销类型插件？**

A: 1. 创建类实现 `TransTypePlugin` 接口
   2. 实现 `getFormSchema()` 定义表单结构
   3. 实现 `calculateAmount()` 计算逻辑
   4. 在 `TransTypePluginManager` 中注册

**Q: 如何自定义表单验证？**

A: 在 `FormSchema` 中配置 `rules` 字段，支持正则、范围等验证规则。

---

## 相关文件清单

```
common/common-plugin/
├── pom.xml
└── src/main/java/com/fs/common/
    ├── plugin/
    │   ├── TransTypePlugin.java           # 插件接口
    │   ├── TransTypePluginManager.java    # 插件管理器
    │   └── impl/
    │       ├── GeneralTransTypePlugin.java # 通用报销插件
    │       └── TravelTransTypePlugin.java  # 差旅报销插件
    ├── form/
    │   └── DynamicFormEngine.java         # 动态表单引擎
    ├── model/
    │   ├── FormSchema.java                # 表单配置
    │   └── FormField.java                 # 表单字段
    └── enums/
        └── TransType.java                 # 报销类型枚举
```
