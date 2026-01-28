package com.fs.tenant.config;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 测试数据库初始化器
 */
@Component
public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        // 创建 tenant 表
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS tenant (
                tenant_id BIGSERIAL PRIMARY KEY,
                tenant_code VARCHAR(50) NOT NULL,
                tenant_name VARCHAR(200) NOT NULL,
                subdomain VARCHAR(50),
                status VARCHAR(20) NOT NULL,
                max_users INTEGER DEFAULT 10,
                max_storage BIGINT DEFAULT 1073741824,
                expired_date TIMESTAMP,
                contact_name VARCHAR(60),
                contact_phone VARCHAR(20),
                contact_email VARCHAR(100),
                address VARCHAR(200),
                remarks TEXT,
                created_at TIMESTAMP NOT NULL,
                updated_at TIMESTAMP NOT NULL,
                deleted INT DEFAULT 0
            )
        """);
    }
}
