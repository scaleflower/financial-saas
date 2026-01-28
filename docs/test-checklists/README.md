# 微服务功能测试清单

> 本目录包含各微服务的功能列表及测试用例覆盖情况

**最后更新**: 2025-01-28

---

## 📋 测试覆盖总览

| 服务 | 功能数 | 已测试 | 待测试 | 覆盖率 | 清单文件 |
|------|--------|--------|--------|--------|----------|
| tenant-service | 8 | 8 | 0 | 100% | [查看](./tenant-service.md) |
| user-service | 8 | 7 | 1 | 87.5% | [查看](./user-service.md) |
| org-service | 7 | 6 | 1 | 85.7% | [查看](./org-service.md) |
| trans-service | 10 | 7 | 3 | 70% | [查看](./trans-service.md) |
| loan-service | 6 | 5 | 1 | 83.3% | [查看](./loan-service.md) |
| repayment-service | 4 | 0 | 4 | 0% | [查看](./repayment-service.md) |
| approval-service | 6 | 5 | 1 | 83.3% | [查看](./approval-service.md) |
| notification-service | 3 | 0 | 3 | 0% | [查看](./notification-service.md) |
| report-service | 4 | 0 | 4 | 0% | [查看](./report-service.md) |
| file-service | 5 | 0 | 5 | 0% | [查看](./file-service.md) |
| gateway-service | 5 | 0 | 5 | 0% | [查看](./gateway-service.md) |
| **总计** | **66** | **38** | **28** | **57.6%** | - |

---

## 🎯 开发优先级建议

### P0 - 核心功能（高优先级）

1. **user-service**
   - 用户登录/注册认证
   - JWT Token生成与验证

2. **trans-service**
   - 报销审批流程
   - 报销结算流程

3. **approval-service**
   - Camunda工作流完整集成
   - 驳回流程
   - 审批历史记录

4. **gateway-service**
   - 统一鉴权
   - 路由转发
   - 租户识别

### P1 - 重要功能（中优先级）

1. **trans-service**
   - 报销明细和费用明细管理
   - 动态表单Schema

2. **loan-service**
   - 借款审批和发放流程
   - Controller层API测试

3. **file-service**
   - 文件上传下载
   - MinIO集成

4. **repayment-service**
   - 还款和冲销功能
   - 基础服务实现

### P2 - 辅助功能（低优先级）

1. **notification-service**
   - 通知发送
   - 通知渠道集成

2. **report-service**
   - 报表生成
   - 数据导出

3. 各服务的批量操作功能

---

## 📝 测试用例编写规范

### 命名约定
- Mapper测试: `{Entity}MapperTest.java`
- Service测试: `{Service}Test.java` 或 `{Service}IntegrationTest.java`
- Controller测试: `{Controller}Test.java`

### 测试方法命名
```
{操作}_{场景}_{预期结果}()

示例:
- createTenant_Success() - 创建租户成功
- createTenant_CodeExists() - 租户编码已存在失败
- getTenantById_NotFound() - 租户不存在异常
```

### 测试覆盖要求
- **正常流程**: 验证功能正常工作
- **边界条件**: 空值、最大值、最小值
- **异常场景**: 数据不存在、权限不足、业务规则违反

---

## 📂 各服务清单

| 服务 | 端口 | 职责 | 文档 |
|------|------|------|------|
| [tenant-service](./tenant-service.md) | 8080 | 多租户管理、租户注册、配额控制 | [查看](./tenant-service.md) |
| [user-service](./user-service.md) | 8762 | 用户管理、认证授权、钉钉集成 | [查看](./user-service.md) |
| [org-service](./org-service.md) | 8763 | 组织架构管理、组织树维护 | [查看](./org-service.md) |
| [trans-service](./trans-service.md) | 8764 | 报销单管理、报销审批、动态表单 | [查看](./trans-service.md) |
| [loan-service](./loan-service.md) | 8765 | 借款管理、借款审批、还款跟踪 | [查看](./loan-service.md) |
| [repayment-service](./repayment-service.md) | 8766 | 还款管理、冲销结算 | [查看](./repayment-service.md) |
| [approval-service](./approval-service.md) | 8767 | 审批流程管理、Camunda集成 | [查看](./approval-service.md) |
| [notification-service](./notification-service.md) | 8768 | 通知管理、消息推送 | [查看](./notification-service.md) |
| [report-service](./report-service.md) | 8769 | 报表统计、数据导出 | [查看](./report-service.md) |
| [file-service](./file-service.md) | 8770 | 文件上传、MinIO集成 | [查看](./file-service.md) |
| [gateway-service](./gateway-service.md) | 9200 | API网关、路由转发、租户识别 | [查看](./gateway-service.md) |
