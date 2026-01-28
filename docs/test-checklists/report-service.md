# report-service 功能测试清单

> 报表服务 - 报表统计、数据导出

**端口**: 8769
**职责**: 报表统计、数据导出
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
| 生成报销报表 | POST /report/trans | - | ⏳ 待实现 |
| 生成借款报表 | POST /report/loan | - | ⏳ 待实现 |
| 生成还款报表 | POST /report/repayment | - | ⏳ 待实现 |
| 导出报表 | GET /report/{reportId}/export | - | ⏳ 待实现 |

---

## 测试文件

暂无测试文件

---

## 数据模型

### report - 报表表

```sql
CREATE TABLE report (
    tenant_id BIGINT NOT NULL,
    report_id BIGSERIAL NOT NULL,
    report_code VARCHAR(60) NOT NULL,
    report_name VARCHAR(200) NOT NULL,
    report_type VARCHAR(50) NOT NULL,
    query_params JSONB,
    status VARCHAR(20) NOT NULL,
    file_url VARCHAR(500),
    generated_by BIGINT NOT NULL,
    generated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (tenant_id, report_id)
);
```

### 报表类型枚举
- `TRANS_SUMMARY` - 报销汇总报表
- `TRANS_DETAIL` - 报销明细报表
- `LOAN_SUMMARY` - 借款汇总报表
- `LOAN_DETAIL` - 借款明细报表
- `REPAYMENT_SUMMARY` - 还款汇总报表
- `USER_EXPENSE` - 个人费用报表

### 报表状态枚举
- `PENDING` - 待生成
- `GENERATING` - 生成中
- `COMPLETED` - 已完成
- `FAILED` - 生成失败

---

## 待实现功能

### P2 - 辅助功能（低优先级）

**注意**: 本服务需从头实现

1. **基础功能实现**
   - [ ] Entity类: Report
   - [ ] Mapper接口: ReportMapper
   - [ ] Service层: ReportService
   - [ ] Controller层: ReportController

2. **报表生成**
   - `POST /report/trans` - 生成报销报表
   - `POST /report/loan` - 生成借款报表
   - `POST /report/repayment` - 生成还款报表
   - `POST /report/user-expense` - 生成个人费用报表
   - 支持异步生成
   - 生成进度跟踪

3. **报表导出**
   - `GET /report/{id}/export` - 导出报表
   - 支持Excel格式 (xlsx)
   - 支持PDF格式
   - 支持CSV格式

4. **报表查询**
   - `GET /reports` - 报表列表
   - `GET /reports/{id}` - 报表详情
   - `DELETE /reports/{id}` - 删除报表

5. **测试用例编写**
   - [ ] ReportMapperTest
   - [ ] ReportServiceTest
   - [ ] ReportControllerTest

---

## 开发检查清单

### 阶段1: 基础设施
- [ ] 创建模块结构
- [ ] 配置pom.xml依赖
- [ ] 创建Entity实体类
- [ ] 创建Mapper接口
- [ ] 配置数据源

### 阶段2: 业务逻辑
- [ ] 实现ReportService
- [ ] 实现Controller控制层
- [ ] 实现异步生成机制

### 阶段3: 导出功能
- [ ] 集成Apache POI (Excel)
- [ ] 集成iText/OpenPDF (PDF)
- [ ] 实现CSV导出

### 阶段4: 测试验证
- [ ] 编写Mapper测试
- [ ] 编写Service测试
- [ ] 编写Controller测试
- [ ] 导出功能测试
