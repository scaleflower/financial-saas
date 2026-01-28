package com.fs.common.test.base;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * 使用 Testcontainers 的集成测试基类
 * 提供真实的 PostgreSQL 数据库环境
 */
@SpringBootTest
@ActiveProfiles("test")
public abstract class ContainerTest {

    protected static PostgreSQLContainer<?> postgresContainer;

    @BeforeAll
    static void startContainer() {
        postgresContainer = new PostgreSQLContainer<>("postgres:15-alpine")
                .withDatabaseName("test_db")
                .withUsername("test")
                .withPassword("test");
        
        postgresContainer.start();
        
        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());
    }

    @AfterAll
    static void stopContainer() {
        if (postgresContainer != null) {
            postgresContainer.stop();
        }
    }
}
