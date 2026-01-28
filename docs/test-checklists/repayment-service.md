# repayment-service 功能测试清单

> 还款服务 - 还款管理、冲销结算

**端口**: 8766
**职责**: 还款管理、冲销结算
**最后更新**: 2025-01-28

---

## 测试覆盖情况

| 指标 | 数值 |
|------|------|
| 功能数 | 4 |
| 已测试 | 0 |
| 待测试 | 4 |
| **覆盖率** | **0%** |

---

## 功能列表

| 功能 | API端点 | 测试用例 | 状态 |
|------|---------|----------|------|
| 创建还款记录 | POST /repayments | - | ⏳ 待实现 |
| 获取还款详情 | GET /repayments/{repaymentId} | - | ⏳ 待实现 |
| 获取还款列表 | GET /repayments?userId={id} | - | ⏳ 待实现 |
| 还款冲销 | POST /repayments/{repaymentId}/offset | - | ⏳ 待实现 |
| 还款结算 | - | - | ⏳ 待实现 |

---

## 测试文件

暂无测试文件

---

## 数据模型

### repayment - 还款表

```sql
CREATE TABLE repayment (
    tenant_id BIGINT NOT NULL,
    repayment_id BIGSERIAL NOT NULL,
    repayment_code VARCHAR(60) NOT NULL,
    loan_id BIGINT NOT NULL,
    repayment_amount NUMERIC(12,2) NOT NULL,
    principal_amount NUMERIC(12,2),
    interest_amount NUMERIC(12,2),
    repayment_date TIMESTAMP NOT NULL,
    repayer_id BIGINT NOT NULL,
    state VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    PRIMARY KEY (tenant_id, repayment_id)
);
```

### 还款状态枚举
- `PENDING` - 待处理
- `CONFIRMED` - 已确认
- `OFFSET` - 已冲销
- `CANCELLED` - 已取消

---

## 待实现功能

### P1 - 重要功能（中优先级）

**注意**: 本服务需从头实现

1. **基础功能实现**
   - [ ] Entity类: Repayment
   - [ ] Mapper接口: RepaymentMapper
   - [ ] Service层: RepaymentService
   - [ ] Controller层: RepaymentController

2. **还款记录管理**
   - `POST /repayments` - 创建还款记录
   - `GET /repayments/{id}` - 获取还款详情
   - `GET /repayments` - 还款列表查询
   - `PUT /repayments/{id}` - 更新还款信息
   - `DELETE /repayments/{id}` - 删除还款记录

3. **还款冲销功能**
   - `POST /repayments/{id}/offset` - 执行冲销
   - 关联借款金额扣减
   - 生成冲销记录
   - 更新借款状态

4. **与借款服务集成**
   - 根据借款ID查询还款记录
   - 计算剩余未还金额
   - 借款还款进度统计

5. **测试用例编写**
   - [ ] RepaymentMapperTest
   - [ ] RepaymentServiceTest
   - [ ] RepaymentControllerTest

---

## 开发检查清单

### 阶段1: 基础设施
- [ ] 创建模块结构
- [ ] 配置pom.xml依赖
- [ ] 创建Entity实体类
- [ ] 创建Mapper接口
- [ ] 配置数据源

### 阶段2: 业务逻辑
- [ ] 实现Service业务层
- [ ] 实现Controller控制层
- [ ] 实现冲销逻辑
- [ ] 与loan-service集成

### 阶段3: 测试验证
- [ ] 编写Mapper测试
- [ ] 编写Service测试
- [ ] 编写Controller测试
- [ ] API集成测试
