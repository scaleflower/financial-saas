package com.fs.common.test.builder;

import com.fs.tenant.entity.Tenant;
import com.fs.userservice.entity.User;
import com.fs.transservice.entity.Trans;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 测试数据构建器
 * 提供流式 API 构建测试数据
 */
public class TenantTestDataBuilder {

    /**
     * 构建租户测试数据
     */
    public static Tenant tenant() {
        return tenantBuilder().build();
    }

    /**
     * 租户构建器
     */
    public static TenantBuilder tenantBuilder() {
        return new TenantBuilder();
    }

    /**
     * 用户构建器
     */
    public static UserBuilder userBuilder() {
        return new UserBuilder();
    }

    /**
     * 报销单构建器
     */
    public static TransBuilder transBuilder() {
        return new TransBuilder();
    }

    /**
     * 租户构建器实现
     */
    public static class TenantBuilder {
        private Long tenant_id = 1L;
        private String tenant_code = "TEST001";
        private String tenant_name = "测试企业";
        private String subdomain = "test";
        private String status = "ACTIVE";
        private Integer max_users = 100;
        private Long max_storage = 10737418240L; // 10GB
        private LocalDateTime expired_date = LocalDateTime.now().plusYears(1);
        private String contact_name = "测试联系人";
        private String contact_phone = "13800138000";
        private String contact_email = "test@example.com";
        private String address = "北京市朝阳区";
        private String remarks = "测试租户";

        public TenantBuilder tenantId(Long id) {
            this.tenant_id = id;
            return this;
        }

        public TenantBuilder tenantCode(String code) {
            this.tenant_code = code;
            return this;
        }

        public TenantBuilder tenantName(String name) {
            this.tenant_name = name;
            return this;
        }

        public TenantBuilder status(String status) {
            this.status = status;
            return this;
        }

        public Tenant build() {
            return Tenant.builder()
                    .tenant_id(tenant_id)
                    .tenant_code(tenant_code)
                    .tenant_name(tenant_name)
                    .subdomain(subdomain)
                    .status(status)
                    .max_users(max_users)
                    .max_storage(max_storage)
                    .expired_date(expired_date)
                    .contact_name(contact_name)
                    .contact_phone(contact_phone)
                    .contact_email(contact_email)
                    .address(address)
                    .remarks(remarks)
                    .created_at(LocalDateTime.now())
                    .updated_at(LocalDateTime.now())
                    .deleted(0)
                    .build();
        }
    }

    /**
     * 用户构建器实现
     */
    public static class UserBuilder {
        private Long user_id = 1L;
        private Long tenant_id = 1L;
        private String username = "testuser";
        private String password = "$2a$10$encodedPassword";
        private String real_name = "测试用户";
        private String email = "testuser@example.com";
        private String phone = "13900139000";
        private Long org_id = 1L;
        private String status = "ACTIVE";
        private String dingtalk_userid = "";

        public UserBuilder userId(Long id) {
            this.user_id = id;
            return this;
        }

        public UserBuilder tenantId(Long id) {
            this.tenant_id = id;
            return this;
        }

        public UserBuilder username(String name) {
            this.username = name;
            return this;
        }

        public UserBuilder realName(String name) {
            this.real_name = name;
            return this;
        }

        public UserBuilder status(String status) {
            this.status = status;
            return this;
        }

        public User build() {
            return User.builder()
                    .user_id(user_id)
                    .tenant_id(tenant_id)
                    .username(username)
                    .password(password)
                    .real_name(real_name)
                    .email(email)
                    .phone(phone)
                    .org_id(org_id)
                    .status(status)
                    .dingtalk_userid(dingtalk_userid)
                    .last_login_time(LocalDateTime.now())
                    .created_at(LocalDateTime.now())
                    .updated_at(LocalDateTime.now())
                    .deleted(0)
                    .build();
        }
    }

    /**
     * 报销单构建器实现
     */
    public static class TransBuilder {
        private Long trans_id = 1L;
        private Long tenant_id = 1L;
        private String trans_code = "TRANS20250127001";
        private Long trans_type_id = 1L;
        private Long user_id = 1L;
        private Long project_id = null;
        private LocalDateTime trans_date = LocalDateTime.now();
        private BigDecimal trans_charge = new BigDecimal("1000.00");
        private Integer currency_id = 1;
        private BigDecimal exchange_rate = new BigDecimal("1.0");
        private BigDecimal lc_charge = new BigDecimal("1000.00");
        private String state = "DRAFT";
        private String description = "测试报销单";
        private Long created_by = 1L;

        public TransBuilder transId(Long id) {
            this.trans_id = id;
            return this;
        }

        public TransBuilder tenantId(Long id) {
            this.tenant_id = id;
            return this;
        }

        public TransBuilder state(String state) {
            this.state = state;
            return this;
        }

        public TransBuilder transCharge(BigDecimal charge) {
            this.trans_charge = charge;
            return this;
        }

        public Trans build() {
            return Trans.builder()
                    .trans_id(trans_id)
                    .tenant_id(tenant_id)
                    .trans_code(trans_code)
                    .trans_type_id(trans_type_id)
                    .user_id(user_id)
                    .project_id(project_id)
                    .trans_date(trans_date)
                    .trans_charge(trans_charge)
                    .currency_id(currency_id)
                    .exchange_rate(exchange_rate)
                    .lc_charge(lc_charge)
                    .state(state)
                    .description(description)
                    .created_by(created_by)
                    .created_at(LocalDateTime.now())
                    .updated_at(LocalDateTime.now())
                    .deleted(0)
                    .build();
        }
    }
}
