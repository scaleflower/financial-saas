package com.fs.common.util;

import cn.hutool.core.util.StrUtil;

import java.util.Collection;
import java.util.UUID;

/**
 * 字符串工具类
 */
public class StringUtils extends StrUtil {

    /**
     * 空字符串
     */
    public static final String EMPTY = "";

    /**
     * 生成UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成UUID（无横线）
     */
    public static String simpleUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成业务编码（前缀+日期+6位随机数）
     */
    public static String generateCode(String prefix) {
        return generateCode(prefix, 6);
    }

    /**
     * 生成业务编码（前缀+日期+指定位数随机数）
     */
    public static String generateCode(String prefix, int randomLength) {
        String date = DateUtils.today().replace("-", "");
        String random = String.valueOf(System.currentTimeMillis())
                .substring(String.valueOf(System.currentTimeMillis()).length() - randomLength);
        return prefix + date + random;
    }

    /**
     * 生成流水号
     */
    public static String generateSerialNo(String prefix) {
        return prefix + System.currentTimeMillis();
    }

    /**
     * 检查是否包含敏感词
     */
    public static boolean containsSensitive(String text) {
        if (isBlank(text)) {
            return false;
        }
        
        // 这里可以集成敏感词过滤服务
        // 目前简单检查一些基础敏感词
        String[] sensitiveWords = {"admin", "root", "test", "demo"};
        String lowerText = text.toLowerCase();
        
        for (String word : sensitiveWords) {
            if (lowerText.contains(word)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * 脱敏处理
     */
    public static String mask(String text) {
        if (isBlank(text)) {
            return text;
        }
        
        int length = text.length();
        if (length <= 2) {
            return "**";
        } else if (length <= 6) {
            return text.charAt(0) + "***" + text.charAt(length - 1);
        } else {
            return text.substring(0, 3) + "****" + text.substring(length - 3);
        }
    }

    /**
     * 手机号脱敏
     */
    public static String maskPhone(String phone) {
        if (isBlank(phone) || phone.length() != 11) {
            return phone;
        }
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

    /**
     * 邮箱脱敏
     */
    public static String maskEmail(String email) {
        if (isBlank(email)) {
            return email;
        }
        
        int atIndex = email.indexOf('@');
        if (atIndex <= 0) {
            return email;
        }
        
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex);
        
        if (username.length() <= 2) {
            return "**" + domain;
        } else {
            return username.substring(0, 2) + "***" + domain;
        }
    }

    /**
     * 身份证号脱敏
     */
    public static String maskIdCard(String idCard) {
        if (isBlank(idCard) || idCard.length() < 8) {
            return idCard;
        }
        return idCard.substring(0, 4) + "********" + idCard.substring(idCard.length() - 4);
    }

    /**
     * 银行卡号脱敏
     */
    public static String maskBankCard(String bankCard) {
        if (isBlank(bankCard) || bankCard.length() < 8) {
            return bankCard;
        }
        return bankCard.substring(0, 4) + " **** **** " + bankCard.substring(bankCard.length() - 4);
    }

    /**
     * 检查集合是否为空
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 检查集合是否不为空
     */
    public static boolean isNotNullOrEmpty(Collection<?> collection) {
        return !isNullOrEmpty(collection);
    }

    /**
     * 字符串首字母大写
     */
    public static String capitalize(String str) {
        if (isBlank(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 字符串首字母小写
     */
    public static String uncapitalize(String str) {
        if (isBlank(str)) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 驼峰转下划线
     */
    public static String camelToSnake(String str) {
        if (isBlank(str)) {
            return str;
        }
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    /**
     * 下划线转驼峰
     */
    public static String snakeToCamel(String str) {
        if (isBlank(str)) {
            return str;
        }
        
        StringBuilder result = new StringBuilder();
        boolean upperCase = false;
        
        for (char c : str.toCharArray()) {
            if (c == '_') {
                upperCase = true;
            } else {
                if (upperCase) {
                    result.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }
        
        return result.toString();
    }

    /**
     * 比较两个字符串是否相等（忽略大小写）
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 == null || str2 == null) {
            return false;
        }
        return str1.equalsIgnoreCase(str2);
    }
}
