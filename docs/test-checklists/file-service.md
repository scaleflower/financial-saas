# file-service 功能测试清单

> 文件服务 - 文件上传、MinIO集成

**端口**: 8770
**职责**: 文件上传、MinIO集成
**最后更新**: 2025-01-28

---

## 测试覆盖情况

| 指标 | 数值 |
|------|------|
| 功能数 | 5 |
| 已测试 | 0 |
| 待测试 | 5 |
| **覆盖率** | **0%** |

---

## 功能列表

| 功能 | API端点 | 测试用例 | 状态 |
|------|---------|----------|------|
| 上传文件 | POST /file/upload | - | ⏳ 待实现 |
| 下载文件 | GET /file/download/{fileId} | - | ⏳ 待实现 |
| 删除文件 | DELETE /file/{fileId} | - | ⏳ 待实现 |
| 获取文件信息 | GET /file/{fileId} | - | ⏳ 待实现 |
| 文件预览 | GET /file/preview/{fileId} | - | ⏳ 待实现 |

---

## 测试文件

暂无测试文件

---

## 数据模型

### file_info - 文件信息表

```sql
CREATE TABLE file_info (
    tenant_id BIGINT NOT NULL,
    file_id BIGSERIAL NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    original_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT NOT NULL,
    file_type VARCHAR(100) NOT NULL,
    mime_type VARCHAR(100),
    bucket_name VARCHAR(100),
    storage_type VARCHAR(20) NOT NULL,
    uploaded_by BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (tenant_id, file_id)
);
```

### 存储类型枚举
- `MINIO` - MinIO对象存储
- `OSS` - 阿里云OSS
- `COS` - 腾讯云COS
- `LOCAL` - 本地存储

### 文件状态枚举
- `ACTIVE` - 正常
- `DELETED` - 已删除
- `QUARANTINE` - 隔离

---

## 待实现功能

### P1 - 重要功能（中优先级）

**注意**: 本服务需从头实现

1. **基础功能实现**
   - [ ] Entity类: FileInfo
   - [ ] Mapper接口: FileInfoMapper
   - [ ] Service层: FileService
   - [ ] Controller层: FileController

2. **文件上传**
   - `POST /file/upload` - 单文件上传
   - `POST /file/upload/batch` - 批量上传
   - 支持分片上传
   - 文件类型校验
   - 文件大小限制
   - 病毒扫描（可选）

3. **文件下载**
   - `GET /file/download/{id}` - 下载文件
   - 支持断点续传
   - 下载权限校验
   - 下载日志记录

4. **文件管理**
   - `GET /file/{id}` - 获取文件信息
   - `DELETE /file/{id}` - 删除文件
   - `GET /file/list` - 文件列表查询

5. **文件预览**
   - `GET /file/preview/{id}` - 文件预览
   - 支持图片预览
   - 支持PDF预览
   - 支持Office文档预览

6. **MinIO集成**
   - MinIO Client配置
   - Bucket管理
   - 文件存储/读取
   - 访问URL生成

7. **测试用例编写**
   - [ ] FileInfoMapperTest
   - [ ] FileServiceTest
   - [ ] FileControllerTest

---

## 开发检查清单

### 阶段1: 基础设施
- [ ] 创建模块结构
- [ ] 配置pom.xml依赖
- [ ] 创建Entity实体类
- [ ] 创建Mapper接口
- [ ] 配置数据源

### 阶段2: MinIO集成
- [ ] 添加MinIO依赖
- [ ] 配置MinIO Client
- [ ] 创建Bucket配置
- [ ] 实现文件存储Service

### 阶段3: 业务逻辑
- [ ] 实现文件上传功能
- [ ] 实现文件下载功能
- [ ] 实现文件管理功能
- [ ] 实现文件预览功能

### 阶段4: 测试验证
- [ ] 编写Mapper测试
- [ ] 编写Service测试
- [ ] 编写Controller测试
- [ ] MinIO集成测试
