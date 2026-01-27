package com.fs.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 统一错误码
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // 通用错误码 (10000-19999)
    SUCCESS(200, "操作成功"),
    ERROR(500, "操作失败"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "无权限"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    CONFLICT(409, "资源冲突"),

    // 业务错误码 (20000-29999)
    PARAM_ERROR(20001, "参数错误"),
    PARAM_MISSING(20002, "缺少必要参数"),
    PARAM_FORMAT_ERROR(20003, "参数格式错误"),
    PARAM_INVALID(20004, "参数值无效"),

    // 租户相关错误码 (21000-21999)
    TENANT_NOT_FOUND(21001, "租户不存在"),
    TENANT_DISABLED(21002, "租户已禁用"),
    TENANT_EXPIRED(21003, "租户已过期"),
    TENANT_CODE_EXIST(21004, "租户代码已存在"),
    TENANT_SUBDOMAIN_EXIST(21005, "子域名已存在"),

    // 用户相关错误码 (22000-22999)
    USER_NOT_FOUND(22001, "用户不存在"),
    USER_DISABLED(22002, "用户已禁用"),
    USER_LOCKED(22003, "用户已锁定"),
    USER_PASSWORD_ERROR(22004, "密码错误"),
    USER_PASSWORD_EXPIRED(22005, "密码已过期"),
    USER_EXISTS(22006, "用户已存在"),
    USER_USERNAME_EXISTS(22007, "用户名已存在"),
    USER_EMAIL_EXISTS(22008, "邮箱已存在"),
    USER_PHONE_EXISTS(22009, "手机号已存在"),
    USER_NOT_ACTIVE(22010, "用户未激活"),

    // 组织相关错误码 (23000-23999)
    ORG_NOT_FOUND(23001, "组织不存在"),
    ORG_DISABLED(23002, "组织已禁用"),
    ORG_CODE_EXIST(23003, "组织代码已存在"),
    ORG_HAS_CHILDREN(23004, "组织下有子组织"),
    ORG_HAS_USERS(23005, "组织下有用户"),
    ORG_CIRCULAR_REFERENCE(23006, "组织循环引用"),

    // 权限相关错误码 (24000-24999)
    PERMISSION_DENIED(24001, "无权限"),
    ROLE_NOT_FOUND(24002, "角色不存在"),
    ROLE_DISABLED(24003, "角色已禁用"),
    ROLE_EXIST(24004, "角色已存在"),
    ROLE_CODE_EXIST(24005, "角色代码已存在"),

    // 报销相关错误码 (25000-25999)
    TRANS_NOT_FOUND(25001, "报销单不存在"),
    TRANS_DISABLED(25002, "报销单已禁用"),
    TRANS_CODE_EXIST(25003, "报销单号已存在"),
    TRANS_STATE_INVALID(25004, "报销单状态无效"),
    TRANS_AMOUNT_INVALID(25005, "报销金额无效"),
    TRANS_ATTACHMENT_INVALID(25006, "附件无效"),
    TRANS_DRAFT_SUBMITTED(25007, "草稿已提交"),

    // 借款相关错误码 (26000-26999)
    LOAN_NOT_FOUND(26001, "借款单不存在"),
    LOAN_DISABLED(26002, "借款单已禁用"),
    LOAN_CODE_EXIST(26003, "借款单号已存在"),
    LOAN_STATE_INVALID(26004, "借款单状态无效"),
    LOAN_AMOUNT_INVALID(26005, "借款金额无效"),
    LOAN_AMOUNT_EXCEED_LIMIT(26006, "借款金额超限"),
    LOAN_REPAY_AMOUNT_EXCEED(26007, "还款金额超限"),

    // 还款相关错误码 (27000-27999)
    REPAYMENT_NOT_FOUND(27001, "还款单不存在"),
    REPAYMENT_DISABLED(27002, "还款单已禁用"),
    REPAYMENT_CODE_EXIST(27003, "还款单号已存在"),
    REPAYMENT_STATE_INVALID(27004, "还款单状态无效"),
    REPAYMENT_AMOUNT_INVALID(27005, "还款金额无效"),
    REPAYMENT_SETT_AMOUNT_INVALID(27006, "冲销金额无效"),

    // 审批相关错误码 (28000-28999)
    APPROVAL_PROCESS_NOT_FOUND(28001, "审批流程不存在"),
    APPROVAL_TASK_NOT_FOUND(28002, "审批任务不存在"),
    APPROVAL_TASK_COMPLETED(28003, "审批任务已完成"),
    APPROVAL_TASK_NOT_ASSIGNED(28004, "审批任务未分配"),
    APPROVAL_PROCESS_COMPLETED(28005, "审批流程已完成"),
    APPROVAL_COMMENT_REQUIRED(28006, "审批意见必填"),

    // 插件相关错误码 (29000-29999)
    PLUGIN_NOT_FOUND(29001, "插件不存在"),
    PLUGIN_DISABLED(29002, "插件已禁用"),
    PLUGIN_VERSION_INCOMPATIBLE(29003, "插件版本不兼容"),
    PLUGIN_LOAD_ERROR(29004, "插件加载失败"),
    PLUGIN_CONFIG_ERROR(29005, "插件配置错误"),

    // 文件相关错误码 (30000-30999)
    FILE_NOT_FOUND(30001, "文件不存在"),
    FILE_UPLOAD_FAILED(30002, "文件上传失败"),
    FILE_DOWNLOAD_FAILED(30003, "文件下载失败"),
    FILE_SIZE_EXCEED_LIMIT(30004, "文件大小超限"),
    FILE_TYPE_NOT_SUPPORTED(30005, "文件类型不支持"),
    FILE_FORMAT_INVALID(30006, "文件格式无效"),

    // 系统相关错误码 (50000-59999)
    SYSTEM_ERROR(50000, "系统错误"),
    DATABASE_ERROR(50001, "数据库错误"),
    NETWORK_ERROR(50002, "网络错误"),
    TIMEOUT_ERROR(50003, "请求超时"),
    RATE_LIMIT_ERROR(50004, "请求频率过高"),
    SERVICE_UNAVAILABLE(50005, "服务不可用"),
    DEPENDENCY_ERROR(50006, "依赖服务错误"),
    CONFIGURATION_ERROR(50007, "配置错误");

    private final Integer code;
    private final String message;
}
