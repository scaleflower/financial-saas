# trans-service 功能测试清单

> 报销服务 - 报销单管理、报销审批、动态表单

**端口**: 8764
**职责**: 报销单管理、报销审批、动态表单
**最后更新**: 2025-01-28

---

## 测试覆盖情况

| 指标 | 数值 |
|------|------|
| 功能数 | 10 |
| 已测试 | 7 |
| 待测试 | 3 |
| **覆盖率** | **70%** |

---

## 功能列表

| 功能 | API端点 | 测试用例 | 状态 |
|------|---------|----------|------|
| 创建报销单 | POST /trans | TransControllerTest.create_Success | ✅ |
| 获取报销单详情 | GET /trans/{transId} | TransControllerTest.getById_Success | ✅ |
| 获取报销单列表 | GET /trans?tenantId={id} | TransControllerTest.list_Success | ✅ |
| 提交报销单 | POST /trans/{transId}/submit | TransControllerTest.submit_Success | ✅ |
| 更新报销单 | PUT /trans/{transId} | TransControllerTest.update_Success | ✅ |
| 删除报销单 | DELETE /trans/{transId} | TransControllerTest.delete_Success | ✅ |
| 报销单状态流转 | - | TransServiceTest | ✅ |
| 获取表单Schema | GET /trans/schema/{transTypeCode} | - | ⏳ 待实现 |
| 报销明细管理 | - | - | ⏳ 待实现 |
| 费用明细管理 | - | - | ⏳ 待实现 |
| 报销审批 | POST /trans/{transId}/approve | - | ⏳ 待实现 |
| 报销结算 | POST /trans/{transId}/settle | - | ⏳ 待实现 |

---

## 测试文件

### Service层测试
- **TransServiceTest.java** - 报销Service层测试 (7个测试)
  - `createTrans_Success()` - 创建报销单成功
  - `getTransById_Success()` - 根据ID查询成功
  - `updateTrans_Success()` - 更新报销单成功
  - `deleteTrans_Success()` - 删除报销单成功
  - `submitTrans_Success()` - 提交报销单成功
  - `listTrans_Success()` - 查询报销单列表成功
  - `updateTransState_Success()` - 状态流转成功

### Controller层测试
- **TransControllerTest.java** - 报销Controller层测试 (6个测试)
  - `create_Success()` - 创建报销单API
  - `getById_Success()` - 获取报销单详情API
  - `list_Success()` - 获取报销单列表API
  - `submit_Success()` - 提交报销单API
  - `update_Success()` - 更新报销单API
  - `delete_Success()` - 删除报销单API

---

## 数据模型

### trans - 报销单表

```sql
CREATE TABLE trans (
    tenant_id BIGINT NOT NULL,
    trans_id BIGSERIAL NOT NULL,
    trans_code VARCHAR(60) NOT NULL,
    trans_type_id BIGINT NOT NULL,
    trans_type_name VARCHAR(100) NOT NULL,
    project_id BIGINT NOT NULL,
    trans_charge NUMERIC(12,2),
    lc_charge NUMERIC(12,2),
    state VARCHAR(20) NOT NULL,
    processinstance_id VARCHAR(60),
    created_user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    PRIMARY KEY (tenant_id, trans_id)
);
```

### trans_item - 报销明细表

```sql
CREATE TABLE trans_item (
    tenant_id BIGINT NOT NULL,
    trans_item_id BIGSERIAL NOT NULL,
    trans_id BIGINT NOT NULL,
    trans_item_name VARCHAR(200) NOT NULL,
    state VARCHAR(20),
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (tenant_id, trans_item_id)
);
```

### charge_item - 费用明细表

```sql
CREATE TABLE charge_item (
    tenant_id BIGINT NOT NULL,
    charge_id BIGSERIAL NOT NULL,
    trans_item_id BIGINT NOT NULL,
    expense_item_type_id BIGINT NOT NULL,
    charge_amount NUMERIC(12,2) NOT NULL,
    lc_charge NUMERIC(12,2),
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (tenant_id, charge_id)
);
```

### 报销状态枚举
- `DRAFT` - 草稿
- `SUBMITTED` - 已提交
- `IN_APPROVAL` - 审批中
- `APPROVED` - 已通过
- `REJECTED` - 已驳回
- `COMPLETED` - 已完成
- `CANCELLED` - 已取消

---

## 待实现功能

### P0 - 核心功能（高优先级）
1. **报销审批流程**
   - `POST /trans/{transId}/approve` - 审批报销单
   - 与approval-service集成
   - 审批结果回调处理

2. **报销结算流程**
   - `POST /trans/{transId}/settle` - 结算报销单
   - 生成付款记录
   - 状态变更为COMPLETED

### P1 - 重要功能（中优先级）
3. **动态表单Schema**
   - `GET /trans/schema/{transTypeCode}` - 获取表单结构
   - 支持不同报销类型的表单配置

4. **报销明细管理**
   - TransItem CRUD操作
   - 费用明细汇总计算

5. **费用明细管理**
   - ChargeItem CRUD操作
   - 费用类型关联

---

## 测试运行

```bash
mvn test -pl services/trans-service
```
