package com.fs.transservice.config;

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
            CREATE TABLE IF NOT EXISTS trans (
                trans_id BIGSERIAL PRIMARY KEY,
                tenant_id BIGINT NOT NULL,
                trans_code VARCHAR(60) NOT NULL,
                trans_type_id BIGINT NOT NULL,
                user_id BIGINT NOT NULL,
                project_id BIGINT,
                trans_date TIMESTAMP,
                trans_charge NUMERIC(12,2),
                currency_id INTEGER,
                exchange_rate NUMERIC(10,4),
                lc_charge NUMERIC(12,2),
                trans_sett_charge NUMERIC(12,2),
                lc_sett_charge NUMERIC(12,2),
                state VARCHAR(20) NOT NULL,
                description TEXT,
                submit_time TIMESTAMP,
                processinstance_id VARCHAR(60),
                created_by BIGINT,
                settled_by BIGINT,
                settled_time TIMESTAMP,
                created_at TIMESTAMP NOT NULL,
                updated_at TIMESTAMP NOT NULL,
                deleted INT DEFAULT 0
            )
        """);
    }
}
