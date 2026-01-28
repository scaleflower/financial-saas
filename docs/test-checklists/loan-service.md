# loan-service 功能测试清单

> 借款服务 - 借款管理、借款审批、还款跟踪

**端口**: 8765
**职责**: 借款管理、借款审批、还款跟踪
**最后更新**: 2025-01-28

---

## 测试覆盖情况

| 指标 | 数值 |
|------|------|
| 功能数 | 6 |
| 已测试 | 5 |
| 待测试 | 1 |
| **覆盖率** | **83.3%** |

---

## 功能列表

| 功能 | API端点 | 测试用例 | 状态 |
|------|---------|----------|------|
| 创建借款单 | POST /loans | - | ⏳ 待实现 |
| 获取借款单详情 | GET /loans/{loanId} | LoanServiceTest.getLoanById_Success | ✅ |
| 获取借款单列表 | GET /loans?tenantId={id} | LoanServiceTest.listLoans_Success | ✅ |
| 更新借款单 | PUT /loans/{loanId} | LoanServiceTest.updateLoan_Success | ✅ |
| 删除借款单 | DELETE /loans/{loanId} | LoanServiceTest.deleteLoan_Success | ✅ |
| 借款审批 | - | - | ⏳ 待实现 |
| 借款发放 | - | - | ⏳ 待实现 |
| 借款还款关联 | - | - | ⏳ 待实现 |

---

## 测试文件

### Service层测试
- **LoanServiceTest.java** - 借款Service层测试 (5个测试)
  - `createLoan_Success()` - 创建借款成功
  - `getLoanById_Success()` - 根据ID查询成功
  - `updateLoan_Success()` - 更新借款成功
  - `deleteLoan_Success()` - 删除借款成功
  - `listLoans_Success()` - 查询借款列表成功

---

## 数据模型

### loan - 借款表

```sql
CREATE TABLE loan (
    tenant_id BIGINT NOT NULL,
    loan_id BIGSERIAL NOT NULL,
    loan_code VARCHAR(60) NOT NULL,
    loan_type_id BIGINT NOT NULL,
    loan_type_name VARCHAR(100) NOT NULL,
    loan_amount NUMERIC(12,2) NOT NULL,
    applicant_id BIGINT NOT NULL,
    apply_date TIMESTAMP NOT NULL,
    state VARCHAR(20) NOT NULL,
    approval_id VARCHAR(60),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    PRIMARY KEY (tenant_id, loan_id)
);
```

### 借款状态枚举
- `DRAFT` - 草稿
- `SUBMITTED` - 已提交
- `APPROVED` - 已审批
- `REJECTED` - 已驳回
- `DISBURSED` - 已发放
- `COMPLETED` - 已完成
- `CANCELLED` - 已取消

---

## 待实现功能

### P1 - 重要功能（中优先级）
1. **Controller层API测试**
   - `POST /loans` - 创建借款单API
   - `GET /loans/{id}` - 获取借款详情API
   - `PUT /loans/{id}` - 更新借款API
   - `DELETE /loans/{id}` - 删除借款API
   - `GET /loans` - 借款列表查询API

2. **借款审批流程**
   - 与approval-service集成
   - 审批通过后状态变更
   - 审批驳回处理

3. **借款发放流程**
   - 审批通过后发放
   - 生成发放记录
   - 状态变更为DISBURSED

4. **与还款服务集成**
   - 借款与还款关联
   - 剩余本金计算
   - 还款状态同步

---

## 测试运行

```bash
mvn test -pl services/loan-service
```
