package com.fs.orgservice.config;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS org (
                org_id BIGSERIAL PRIMARY KEY,
                tenant_id BIGINT NOT NULL,
                org VARCHAR(50) NOT NULL,
                org_name VARCHAR(200) NOT NULL,
                org_type VARCHAR(50) NOT NULL,
                org_level INTEGER NOT NULL,
                parent_org_id BIGINT,
                parent_path VARCHAR(500),
                sort_order INTEGER DEFAULT 0,
                leader_id BIGINT,
                leader_name VARCHAR(60),
                status VARCHAR(20) NOT NULL,
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
