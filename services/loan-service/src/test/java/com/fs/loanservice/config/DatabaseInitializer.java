package com.fs.loanservice.config;

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
            CREATE TABLE IF NOT EXISTS loan (
                loan_id BIGSERIAL PRIMARY KEY,
                tenant_id BIGINT NOT NULL,
                loan_code VARCHAR(60) NOT NULL,
                user_id BIGINT NOT NULL,
                loan_amount NUMERIC(12,2),
                lc_loan_amount NUMERIC(12,2),
                state VARCHAR(20) NOT NULL,
                purpose TEXT,
                created_by BIGINT,
                created_at TIMESTAMP NOT NULL,
                updated_at TIMESTAMP NOT NULL,
                deleted INT DEFAULT 0
            )
        """);
    }
}
