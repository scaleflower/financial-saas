package com.fs.userservice.config;

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
        // 创建 user 表
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS user (
                user_id BIGSERIAL PRIMARY KEY,
                tenant_id BIGINT NOT NULL,
                username VARCHAR(100) NOT NULL,
                password VARCHAR(200),
                real_name VARCHAR(60),
                email VARCHAR(100),
                phone VARCHAR(20),
                org_id BIGINT,
                status VARCHAR(20) NOT NULL,
                dingtalk_userid VARCHAR(100),
                last_login_time TIMESTAMP,
                created_at TIMESTAMP NOT NULL,
                updated_at TIMESTAMP NOT NULL,
                deleted INT DEFAULT 0
            )
        """);
    }
}
