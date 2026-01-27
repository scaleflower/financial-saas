-- Financial Reimbursement SaaS Database Schema
-- PostgreSQL 15

-- ============================================================================
-- 1. 租户表 (tenant)
-- ============================================================================
CREATE TABLE IF NOT EXISTS tenant (
    tenant_id BIGSERIAL PRIMARY KEY,
    tenant_code VARCHAR(50) NOT NULL UNIQUE,
    tenant_name VARCHAR(200) NOT NULL,
    subdomain VARCHAR(50) UNIQUE,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    max_users INTEGER DEFAULT 10,
    max_storage BIGINT DEFAULT 1073741824,
    expired_date TIMESTAMP,
    contact_name VARCHAR(60),
    contact_phone VARCHAR(20),
    contact_email VARCHAR(100),
    address VARCHAR(500),
    remarks TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted INTEGER DEFAULT 0
);

-- ============================================================================
-- 2. 组织表 (org)
-- ============================================================================
CREATE TABLE IF NOT EXISTS org (
    org_id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    org VARCHAR(50) NOT NULL,
    org_name VARCHAR(200) NOT NULL,
    org_type VARCHAR(50) NOT NULL,
    org_level INTEGER NOT NULL DEFAULT 1,
    parent_org_id BIGINT,
    parent_path VARCHAR(500),
    sort_order INTEGER DEFAULT 0,
    leader_id BIGINT,
    leader_name VARCHAR(60),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    contact_name VARCHAR(60),
    contact_phone VARCHAR(20),
    contact_email VARCHAR(100),
    address VARCHAR(500),
    remarks TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted INTEGER DEFAULT 0,
    CONSTRAINT fk_org_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id)
);

CREATE INDEX idx_org_tenant ON org(tenant_id);
CREATE INDEX idx_org_parent ON org(parent_org_id);

-- ============================================================================
-- 3. 用户表 (user)
-- ============================================================================
CREATE TABLE IF NOT EXISTS "user" (
    user_id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(255),
    real_name VARCHAR(60) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    org_id BIGINT,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    dingtalk_userid VARCHAR(100),
    last_login_time TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted INTEGER DEFAULT 0,
    CONSTRAINT fk_user_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id),
    CONSTRAINT fk_user_org FOREIGN KEY (org_id) REFERENCES org(org_id),
    UNIQUE(tenant_id, username)
);

CREATE INDEX idx_user_tenant ON "user"(tenant_id);
CREATE INDEX idx_user_org ON "user"(org_id);
CREATE INDEX idx_user_status ON "user"(status);

-- ============================================================================
-- 4. 报销单表 (trans)
-- ============================================================================
CREATE TABLE IF NOT EXISTS trans (
    trans_id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    trans_code VARCHAR(60) NOT NULL,
    trans_type_id BIGINT,
    user_id BIGINT NOT NULL,
    project_id BIGINT,
    trans_date TIMESTAMP,
    trans_charge NUMERIC(12,2),
    currency_id INTEGER,
    exchange_rate NUMERIC(12,6) DEFAULT 1.0,
    lc_charge NUMERIC(12,2),
    trans_sett_charge NUMERIC(12,2),
    lc_sett_charge NUMERIC(12,2),
    state VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    description TEXT,
    submit_time TIMESTAMP,
    processinstance_id VARCHAR(100),
    created_by BIGINT,
    settled_by BIGINT,
    settled_time TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted INTEGER DEFAULT 0,
    CONSTRAINT fk_trans_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id),
    CONSTRAINT fk_trans_user FOREIGN KEY (user_id) REFERENCES "user"(user_id)
);

CREATE INDEX idx_trans_tenant ON trans(tenant_id);
CREATE INDEX idx_trans_user ON trans(user_id);
CREATE INDEX idx_trans_state ON trans(state);
CREATE INDEX idx_trans_code ON trans(trans_code);

-- ============================================================================
-- 5. 报销明细表 (trans_item)
-- ============================================================================
CREATE TABLE IF NOT EXISTS trans_item (
    item_id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    trans_id BIGINT NOT NULL,
    item_code VARCHAR(60),
    expense_item_type_id BIGINT,
    expense_item_type_name VARCHAR(100),
    item_charge NUMERIC(12,2),
    lc_item_charge NUMERIC(12,2),
    item_sett_charge NUMERIC(12,2),
    lc_item_sett_charge NUMERIC(12,2),
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_trans_item_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id),
    CONSTRAINT fk_trans_item_trans FOREIGN KEY (trans_id) REFERENCES trans(trans_id) ON DELETE CASCADE
);

CREATE INDEX idx_trans_item_tenant ON trans_item(tenant_id);
CREATE INDEX idx_trans_item_trans ON trans_item(trans_id);

-- ============================================================================
-- 6. 费用明细表 (charge_item)
-- ============================================================================
CREATE TABLE IF NOT EXISTS charge_item (
    charge_id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    trans_item_id BIGINT NOT NULL,
    expense_item_type_id BIGINT,
    expense_item_type_name VARCHAR(100),
    charge_amount NUMERIC(12,2),
    lc_charge NUMERIC(12,2),
    charge_desc TEXT,
    charge_date DATE,
    exchange_rate NUMERIC(12,6) DEFAULT 1.0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_charge_item_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id),
    CONSTRAINT fk_charge_item_trans_item FOREIGN KEY (trans_item_id) REFERENCES trans_item(item_id) ON DELETE CASCADE
);

CREATE INDEX idx_charge_item_tenant ON charge_item(tenant_id);
CREATE INDEX idx_charge_item_trans_item ON charge_item(trans_item_id);

-- ============================================================================
-- 7. 借款表 (loan)
-- ============================================================================
CREATE TABLE IF NOT EXISTS loan (
    loan_id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    loan_code VARCHAR(60) NOT NULL,
    expense_item_type_id BIGINT,
    user_id BIGINT NOT NULL,
    project_id BIGINT,
    loan_amount NUMERIC(12,2),
    currency_id INTEGER,
    exchange_rate NUMERIC(12,6) DEFAULT 1.0,
    lc_loan_amount NUMERIC(12,2),
    sett_amount NUMERIC(12,2) DEFAULT 0,
    lc_sett_amount NUMERIC(12,2) DEFAULT 0,
    state VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    purpose TEXT,
    created_by BIGINT,
    settled_by BIGINT,
    settled_time TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted INTEGER DEFAULT 0,
    CONSTRAINT fk_loan_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id),
    CONSTRAINT fk_loan_user FOREIGN KEY (user_id) REFERENCES "user"(user_id)
);

CREATE INDEX idx_loan_tenant ON loan(tenant_id);
CREATE INDEX idx_loan_user ON loan(user_id);
CREATE INDEX idx_loan_state ON loan(state);
CREATE INDEX idx_loan_code ON loan(loan_code);

-- ============================================================================
-- 8. 还款表 (repayment)
-- ============================================================================
CREATE TABLE IF NOT EXISTS repayment (
    repayment_id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    repayment_code VARCHAR(60) NOT NULL,
    repayment_amount NUMERIC(12,2),
    state VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted INTEGER DEFAULT 0,
    CONSTRAINT fk_repayment_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id),
    CONSTRAINT fk_repayment_user FOREIGN KEY (user_id) REFERENCES "user"(user_id)
);

CREATE INDEX idx_repayment_tenant ON repayment(tenant_id);
CREATE INDEX idx_repayment_user ON repayment(user_id);
CREATE INDEX idx_repayment_code ON repayment(repayment_code);

-- ============================================================================
-- 初始化数据
-- ============================================================================

-- 插入默认租户
INSERT INTO tenant (tenant_code, tenant_name, subdomain, status, max_users, expired_date, contact_name)
VALUES ('DEFAULT', '默认租户', 'default', 'ACTIVE', 1000, '2030-12-31', '系统管理员')
ON CONFLICT (tenant_code) DO NOTHING;

-- 插入默认组织
INSERT INTO org (tenant_id, org, org_name, org_type, org_level, status)
SELECT 1, 'ROOT', '根组织', 'COMPANY', 1, 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM org WHERE tenant_id = 1 AND org = 'ROOT');

-- 插入默认管理员用户 (密码: admin123, BCrypt加密后的值)
INSERT INTO "user" (tenant_id, username, password, real_name, org_id, status)
SELECT 1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '系统管理员', 1, 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM "user" WHERE username = 'admin');

-- ============================================================================
-- 授权
-- ============================================================================
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO heyake;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO heyake;
