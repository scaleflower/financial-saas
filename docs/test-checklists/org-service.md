# org-service 功能测试清单

> 组织服务 - 组织架构管理、组织树维护

**端口**: 8763
**职责**: 组织架构管理、组织树维护
**最后更新**: 2025-01-28

---

## 测试覆盖情况

| 指标 | 数值 |
|------|------|
| 功能数 | 7 |
| 已测试 | 6 |
| 待测试 | 1 |
| **覆盖率** | **85.7%** |

---

## 功能列表

| 功能 | API端点 | 测试用例 | 状态 |
|------|---------|----------|------|
| 获取组织详情 | GET /orgs/{orgId} | OrgControllerTest.getById_Success | ✅ |
| 获取组织树 | GET /orgs/tree?tenantId={id} | OrgControllerTest.getTree_Success | ✅ |
| 获取子组织 | GET /orgs/children?parentId={id} | OrgControllerTest.getChildren_Success | ✅ |
| 创建组织 | POST /orgs | OrgControllerTest.create_Success | ✅ |
| 更新组织 | PUT /orgs/{orgId} | OrgControllerTest.update_Success | ✅ |
| 删除组织 | DELETE /orgs/{orgId} | OrgControllerTest.delete_Success | ✅ |
| 组织层级计算 | - | OrgServiceTest.createOrg_Child | ✅ |
| 删除有子组织的组织 | - | OrgServiceTest.deleteOrg_HasChildren_Fail | ✅ |
| 组织成员管理 | - | - | ⏳ 待实现 |

---

## 测试文件

### Service层测试
- **OrgServiceTest.java** - 组织Service层测试 (8个测试)
  - `createOrg_Success()` - 创建组织成功
  - `createOrg_Child()` - 创建子组织（验证层级计算）
  - `createOrg_CircularReference()` - 循环引用检测
  - `getOrgById_Success()` - 根据ID查询成功
  - `getOrgTree_Success()` - 获取组织树成功
  - `updateOrg_Success()` - 更新组织成功
  - `deleteOrg_Success()` - 删除组织成功
  - `deleteOrg_HasChildren_Fail()` - 删除有子组织的组织失败

### Controller层测试
- **OrgControllerTest.java** - 组织Controller层测试 (6个测试)
  - `getById_Success()` - 获取组织详情API
  - `getTree_Success()` - 获取组织树API
  - `getChildren_Success()` - 获取子组织API
  - `create_Success()` - 创建组织API
  - `update_Success()` - 更新组织API
  - `delete_Success()` - 删除组织API

---

## 数据模型

### org - 组织表

```sql
CREATE TABLE org (
    tenant_id BIGINT NOT NULL,
    org_id BIGSERIAL NOT NULL,
    org VARCHAR(50) NOT NULL,
    org_name VARCHAR(200) NOT NULL,
    org_type VARCHAR(50) NOT NULL,
    org_level INTEGER NOT NULL,
    parent_org_id BIGINT,
    parent_path VARCHAR(500),
    leader_id BIGINT,
    leader_name VARCHAR(60),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    PRIMARY KEY (tenant_id, org_id)
);
```

### 组织类型枚举
- `COMPANY` - 公司
- `DEPARTMENT` - 部门
- `TEAM` - 团队

---

## 待实现功能

### P1 - 重要功能（中优先级）
1. **组织成员管理**
   - `GET /orgs/{id}/members` - 获取组织成员
   - `POST /orgs/{id}/members` - 添加组织成员
   - `DELETE /orgs/{id}/members/{userId}` - 移除组织成员

2. **组织负责人设置**
   - `PUT /orgs/{id}/leader` - 设置组织负责人

3. **组织权限管理**
   - 组织级权限控制
   - 组织数据权限范围

---

## 测试运行

```bash
mvn test -pl services/org-service
```
