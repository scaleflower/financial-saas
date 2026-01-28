# tenant-service 功能测试清单

> 租户服务 - 多租户管理、租户注册、配额控制

**端口**: 8080
**职责**: 多租户管理、租户注册、配额控制
**最后更新**: 2025-01-28

---

## 测试覆盖情况

| 指标 | 数值 |
|------|------|
| 功能数 | 8 |
| 已测试 | 8 |
| 待测试 | 0 |
| **覆盖率** | **100%** |

---

## 功能列表

| 功能 | API端点 | 测试用例 | 状态 |
|------|---------|----------|------|
| 创建租户 | POST /tenants | TenantControllerTest.createTenant_Success | ✅ |
| 获取租户详情 | GET /tenants/{tenantId} | TenantControllerTest.getTenantById_Success | ✅ |
| 获取租户列表 | GET /tenants | TenantControllerTest.listTenants_Success | ✅ |
| 更新租户 | PUT /tenants/{tenantId} | TenantControllerTest.updateTenant_Success | ✅ |
| 删除租户 | DELETE /tenants/{tenantId} | TenantControllerTest.deleteTenant_Success | ✅ |
| 租户编码唯一性校验 | - | TenantServiceTest.createTenant_CodeExists | ✅ |
| 租户不存在异常 | - | TenantServiceTest.getTenantById_NotFound | ✅ |
| 租户数据CRUD | - | TenantMapperTest (6个测试) | ✅ |

---

## 测试文件

### Mapper层测试
- **TenantMapperTest.java** - 租户Mapper层测试 (6个测试)
  - `insert_Success()` - 插入租户成功
  - `selectById_Success()` - 根据ID查询成功
  - `selectByCode_Success()` - 根据编码查询成功
  - `update_Success()` - 更新租户成功
  - `delete_Success()` - 删除租户成功
  - `selectByTenantId_Success()` - 根据租户ID列表查询

### Service层测试
- **TenantServiceIntegrationTest.java** - 租户Service层测试 (7个测试)
  - `createTenant_Success()` - 创建租户成功
  - `createTenant_CodeExists()` - 租户编码已存在失败
  - `getTenantById_Success()` - 根据ID查询成功
  - `getTenantById_NotFound()` - 租户不存在异常
  - `updateTenant_Success()` - 更新租户成功
  - `deleteTenant_Success()` - 删除租户成功
  - `listTenants_Success()` - 查询租户列表成功

### Controller层测试
- **TenantControllerTest.java** - 租户Controller层测试 (5个测试)
  - `createTenant_Success()` - 创建租户API
  - `getTenantById_Success()` - 获取租户详情API
  - `listTenants_Success()` - 获取租户列表API
  - `updateTenant_Success()` - 更新租户API
  - `deleteTenant_Success()` - 删除租户API

---

## 数据模型

### tenant - 租户表

```sql
CREATE TABLE tenant (
    tenant_id BIGSERIAL NOT NULL,
    tenant_code VARCHAR(50) NOT NULL UNIQUE,
    tenant_name VARCHAR(200) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    PRIMARY KEY (tenant_id)
);
```

### 租户状态枚举
- `ACTIVE` - 正常
- `SUSPENDED` - 暂停
- `EXPIRED` - 过期

---

## 待实现功能

无 - 所有功能已实现并测试覆盖 ✅

---

## 测试运行

```bash
mvn test -pl services/tenant-service
```
