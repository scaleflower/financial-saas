# notification-service 功能测试清单

> 通知服务 - 通知管理、消息推送

**端口**: 8768
**职责**: 通知管理、消息推送
**最后更新**: 2025-01-28

---

## 测试覆盖情况

| 指标 | 数值 |
|------|------|
| 功能数 | 3 |
| 已测试 | 0 |
| 待测试 | 3 |
| **覆盖率** | **0%** |

---

## 功能列表

| 功能 | API端点 | 测试用例 | 状态 |
|------|---------|----------|------|
| 发送通知 | POST /notifications/send | - | ⏳ 待实现 |
| 获取通知列表 | GET /notifications?userId={id} | - | ⏳ 待实现 |
| 标记已读 | PUT /notifications/{id}/read | - | ⏳ 待实现 |
| 通知模板管理 | - | - | ⏳ 待实现 |
| 邮件发送 | - | - | ⏳ 待实现 |
| 短信发送 | - | - | ⏳ 待实现 |

---

## 测试文件

暂无测试文件

---

## 数据模型

### notification - 通知表

```sql
CREATE TABLE notification (
    tenant_id BIGINT NOT NULL,
    notification_id BIGSERIAL NOT NULL,
    receiver_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    type VARCHAR(50) NOT NULL,
    channel VARCHAR(50) NOT NULL,
    is_read SMALLINT DEFAULT 0,
    read_at TIMESTAMP,
    sent_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (tenant_id, notification_id)
);
```

### notification_template - 通知模板表

```sql
CREATE TABLE notification_template (
    tenant_id BIGINT NOT NULL,
    template_id BIGSERIAL NOT NULL,
    template_code VARCHAR(50) NOT NULL,
    template_name VARCHAR(100) NOT NULL,
    title_template VARCHAR(200),
    content_template TEXT NOT NULL,
    type VARCHAR(50) NOT NULL,
    channel VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    PRIMARY KEY (tenant_id, template_id)
);
```

### 通知类型枚举
- `SYSTEM` - 系统通知
- `APPROVAL` - 审批通知
- `REMINDER` - 提醒通知
- `ANNOUNCEMENT` - 公告通知

### 通知渠道枚举
- `IN_APP` - 站内信
- `EMAIL` - 邮件
- `SMS` - 短信
- `DINGTALK` - 钉钉

---

## 待实现功能

### P2 - 辅助功能（低优先级）

**注意**: 本服务需从头实现

1. **基础功能实现**
   - [ ] Entity类: Notification, NotificationTemplate
   - [ ] Mapper接口: NotificationMapper, NotificationTemplateMapper
   - [ ] Service层: NotificationService, NotificationTemplateService
   - [ ] Controller层: NotificationController

2. **通知管理**
   - `POST /notifications/send` - 发送通知
   - `GET /notifications` - 获取通知列表
   - `GET /notifications/{id}` - 获取通知详情
   - `PUT /notifications/{id}/read` - 标记已读
   - `PUT /notifications/read-all` - 全部标记已读
   - `DELETE /notifications/{id}` - 删除通知

3. **通知模板管理**
   - `POST /notification-templates` - 创建模板
   - `GET /notification-templates` - 模板列表
   - `PUT /notification-templates/{id}` - 更新模板
   - `DELETE /notification-templates/{id}` - 删除模板

4. **通知渠道集成**
   - 邮件发送 (JavaMail/SMTP)
   - 短信发送 (阿里云SMS/腾讯云SMS)
   - 钉钉消息推送
   - 模板变量渲染

5. **测试用例编写**
   - [ ] NotificationMapperTest
   - [ ] NotificationServiceTest
   - [ ] NotificationControllerTest

---

## 开发检查清单

### 阶段1: 基础设施
- [ ] 创建模块结构
- [ ] 配置pom.xml依赖
- [ ] 创建Entity实体类
- [ ] 创建Mapper接口
- [ ] 配置数据源

### 阶段2: 业务逻辑
- [ ] 实现通知Service
- [ ] 实现模板Service
- [ ] 实现Controller控制层
- [ ] 模板引擎集成 (FreeMarker/Thymeleaf)

### 阶段3: 渠道集成
- [ ] 邮件发送集成
- [ ] 短信发送集成
- [ ] 钉钉消息集成

### 阶段4: 测试验证
- [ ] 编写Mapper测试
- [ ] 编写Service测试
- [ ] 编写Controller测试
- [ ] 渠道发送测试
