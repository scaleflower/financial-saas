# user-service 功能测试清单

> 用户服务 - 用户管理、认证授权、钉钉集成

**端口**: 8762
**职责**: 用户管理、认证授权、钉钉集成
**最后更新**: 2025-01-28

---

## 测试覆盖情况

| 指标 | 数值 |
|------|------|
| 功能数 | 8 |
| 已测试 | 7 |
| 待测试 | 1 |
| **覆盖率** | **87.5%** |

---

## 功能列表

| 功能 | API端点 | 测试用例 | 状态 |
|------|---------|----------|------|
| 获取用户详情 | GET /users/{userId} | UserControllerTest.getById_Success | ✅ |
| 获取用户列表 | GET /users?tenantId={id} | UserControllerTest.list_Success | ✅ |
| 更新用户 | PUT /users/{userId} | UserControllerTest.update_Success | ✅ |
| 删除用户 | DELETE /users/{userId} | UserControllerTest.delete_Success | ✅ |
| 根据用户名查询 | - | UserServiceTest.getUserByUsername_Success | ✅ |
| 用户数据CRUD | - | UserMapperTest (6个测试) | ✅ |
| 用户注册 | POST /auth/register | - | ⏳ 待实现 |
| 用户登录 | POST /auth/login | - | ⏳ 待实现 |
| 钉钉登录 | POST /auth/dingtalk | - | ⏳ 待实现 |

---

## 测试文件

### Mapper层测试
- **UserMapperTest.java** - 用户Mapper层测试 (6个测试)
  - `insert_Success()` - 插入用户成功
  - `selectById_Success()` - 根据ID查询成功
  - `selectByUsername_Success()` - 根据用户名查询成功
  - `update_Success()` - 更新用户成功
  - `delete_Success()` - 删除用户成功
  - `selectByTenantId_Success()` - 根据租户ID查询

### Service层测试
- **UserServiceTest.java** - 用户Service层测试 (6个测试)
  - `createUser_Success()` - 创建用户成功
  - `getUserById_Success()` - 根据ID查询成功
  - `getUserByUsername_Success()` - 根据用户名查询成功
  - `updateUser_Success()` - 更新用户成功
  - `deleteUser_Success()` - 删除用户成功
  - `listUsers_Success()` - 查询用户列表成功

### Controller层测试
- **UserControllerTest.java** - 用户Controller层测试 (4个测试)
  - `getById_Success()` - 获取用户详情API
  - `list_Success()` - 获取用户列表API
  - `update_Success()` - 更新用户API
  - `delete_Success()` - 删除用户API

---

## 数据模型

### user - 用户表

```sql
CREATE TABLE "user" (
    tenant_id BIGINT NOT NULL,
    user_id BIGSERIAL NOT NULL,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100),
    realname VARCHAR(60) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    dingtalk_id VARCHAR(100),
    org_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    deleted SMALLINT DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    PRIMARY KEY (tenant_id, user_id)
);
```

### 用户状态枚举
- `PENDING` - 待激活
- `ACTIVE` - 正常
- `LOCKED` - 锁定
- `DISABLED` - 停用

---

## 待实现功能

### P0 - 核心功能（高优先级）
1. **用户认证流程**
   - `POST /auth/login` - 用户名密码登录
   - `POST /auth/register` - 用户注册
   - JWT Token生成与验证
   - 密码加密存储（BCrypt）

2. **钉钉集成**
   - `POST /auth/dingtalk` - 钉钉扫码登录
   - 钉钉OAuth2.0集成
   - 用户信息同步

---

## 测试运行

```bash
mvn test -pl services/user-service
```
