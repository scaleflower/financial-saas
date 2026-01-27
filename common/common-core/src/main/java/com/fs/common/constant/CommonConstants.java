package com.fs.common.constant;

/**
 * 通用常量
 */
public class CommonConstants {

    // 状态常量
    public static final String STATUS_ENABLE = "ENABLE";
    public static final String STATUS_DISABLE = "DISABLE";
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_APPROVED = "APPROVED";
    public static final String STATUS_REJECTED = "REJECTED";
    public static final String STATUS_DRAFT = "DRAFT";
    public static final String STATUS_SUBMITTED = "SUBMITTED";
    public static final String STATUS_COMPLETED = "COMPLETED";
    public static final String STATUS_CANCELLED = "CANCELLED";

    // 布尔值
    public static final Integer TRUE = 1;
    public static final Integer FALSE = 0;
    public static final Integer YES = 1;
    public static final Integer NO = 0;

    // 时间常量
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    // 编码常量
    public static final String UTF8 = "UTF-8";
    public static final String GBK = "GBK";

    // 默认值
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    public static final Integer DEFAULT_PAGE_NUM = 1;
    public static final Integer DEFAULT_MAX_PAGE_SIZE = 1000;
    public static final String DEFAULT_SORT_FIELD = "created_at";
    public static final String DEFAULT_SORT_ORDER = "DESC";

    // 日期时间单位
    public static final long SECOND_MILLIS = 1000L;
    public static final long MINUTE_MILLIS = 60 * SECOND_MILLIS;
    public static final long HOUR_MILLIS = 60 * MINUTE_MILLIS;
    public static final long DAY_MILLIS = 24 * HOUR_MILLIS;

    // 正则表达式
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String PHONE_REGEX = "^1[3-9]\\d{9}$";
    public static final String ID_CARD_REGEX = "^[1-9]\\d{5}(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$";
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{3,20}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{8,}$";

    // 文件相关常量
    public static final long MAX_FILE_SIZE = 10 * 1024 * 1024L; // 10MB
    public static final String[] ALLOWED_IMAGE_TYPES = {"jpg", "jpeg", "png", "gif", "webp"};
    public static final String[] ALLOWED_DOCUMENT_TYPES = {"pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx"};
    public static final String[] ALLOWED_ARCHIVE_TYPES = {"zip", "rar", "7z", "tar", "gz"};

    // 缓存相关常量
    public static final long DEFAULT_CACHE_TTL = 3600L; // 1小时
    public static final long SESSION_TTL = 86400L; // 24小时
    public static final long VERIFICATION_CODE_TTL = 300L; // 5分钟

    // 限流相关常量
    public static final int DEFAULT_RATE_LIMIT = 100; // 100次/分钟
    public static final int LOGIN_RATE_LIMIT = 5; // 5次/分钟
    public static final int SMS_RATE_LIMIT = 10; // 10次/分钟

    // 请求头
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_TENANT_ID = "X-Tenant-ID";
    public static final String HEADER_USER_ID = "X-User-ID";
    public static final String HEADER_TRACE_ID = "X-Trace-ID";
    public static final String HEADER_CLIENT_IP = "X-Client-IP";

    // JWT相关
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String CLAIM_USER_ID = "userId";
    public static final String CLAIM_TENANT_ID = "tenantId";
    public static final String CLAIM_USERNAME = "username";
    public static final String CLAIM_ROLES = "roles";
    public static final String CLAIM_EXPIRED = "exp";

    // 业务相关
    public static final String BUSINESS_TYPE_TRANS = "TRANS";
    public static final String BUSINESS_TYPE_LOAN = "LOAN";
    public static final String BUSINESS_TYPE_REPAYMENT = "REPAYMENT";
    public static final String BUSINESS_TYPE_USER = "USER";
    public static final String BUSINESS_TYPE_ORG = "ORG";

    // 操作类型
    public static final String OPERATION_CREATE = "CREATE";
    public static final String OPERATION_UPDATE = "UPDATE";
    public static final String OPERATION_DELETE = "DELETE";
    public static final String OPERATION_QUERY = "QUERY";
    public static final String OPERATION_EXPORT = "EXPORT";
    public static final String OPERATION_IMPORT = "IMPORT";

    // 日志级别
    public static final String LOG_LEVEL_DEBUG = "DEBUG";
    public static final String LOG_LEVEL_INFO = "INFO";
    public static final String LOG_LEVEL_WARN = "WARN";
    public static final String LOG_LEVEL_ERROR = "ERROR";

    // 消息队列
    public static final String EXCHANGE_DIRECT = "direct";
    public static final String EXCHANGE_TOPIC = "topic";
    public static final String EXCHANGE_FANOUT = "fanout";

    // 环境变量
    public static final String ENV_DEV = "dev";
    public static final String ENV_TEST = "test";
    public static final String ENV_PROD = "prod";

    // 排序
    public static final String SORT_ASC = "ASC";
    public static final String SORT_DESC = "DESC";

    // HTTP方法
    public static final String HTTP_GET = "GET";
    public static final String HTTP_POST = "POST";
    public static final String HTTP_PUT = "PUT";
    public static final String HTTP_DELETE = "DELETE";
    public static final String HTTP_PATCH = "PATCH";

    // MIME类型
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String TEXT_HTML = "text/html";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    // 角色类型
    public static final String ROLE_SUPER_ADMIN = "SUPER_ADMIN";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";
    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_FINANCE = "FINANCE";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";

    // 数据权限范围
    public static final String DATA_SCOPE_ALL = "ALL";
    public static final String DATA_SCOPE_DEPT = "DEPT";
    public static final String DATA_SCOPE_DEPT_AND_SUB = "DEPT_AND_SUB";
    public static final String DATA_SCOPE_SELF = "SELF";

    // 审批相关
    public static final String APPROVAL_ACTION_SUBMIT = "SUBMIT";
    public static final String APPROVAL_ACTION_APPROVE = "APPROVE";
    public static final String APPROVAL_ACTION_REJECT = "REJECT";
    public static final String APPROVAL_ACTION_DELEGATE = "DELEGATE";
    public static final String APPROVAL_ACTION_WITHDRAW = "WITHDRAW";
}
