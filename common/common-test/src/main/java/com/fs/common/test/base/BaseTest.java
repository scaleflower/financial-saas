package com.fs.common.test.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 测试基类
 * 所有测试类继承此类以获取通用配置和行为
 */
@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseTest {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws Exception {
        // 子类可重写此方法进行额外初始化
        setUpInternal();
    }

    /**
     * 子类可重写的初始化方法
     */
    protected void setUpInternal() throws Exception {
        // 默认空实现
    }

    /**
     * 将对象转换为JSON字符串
     */
    protected String toJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * 将JSON字符串转换为对象
     */
    protected <T> T fromJson(String json, Class<T> clazz) throws Exception {
        return objectMapper.readValue(json, clazz);
    }
}
