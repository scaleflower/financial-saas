package com.fs.common.util;

import com.fs.common.constant.CommonConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT工具类
 */
@Slf4j
public class JwtUtil {

    // JWT密钥（实际项目应该从配置文件读取）
    private static final String SECRET = "financial-reimbursement-saas-jwt-secret-key-2024-very-long-key";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * JWT过期时间（24小时）
     */
    private static final long JWT_EXPIRATION = 24 * 60 * 60 * 1000L;

    /**
     * 刷新Token过期时间（7天）
     */
    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000L;

    /**
     * 生成Token
     *
     * @param claims 声明
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(KEY, Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 生成Token（包含用户ID、租户ID、用户名、角色）
     *
     * @param userId    用户ID
     * @param tenantId  租户ID
     * @param username  用户名
     * @param roles     角色列表
     */
    public static String generateToken(Long userId, Long tenantId, String username, Object roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CommonConstants.CLAIM_USER_ID, userId);
        claims.put(CommonConstants.CLAIM_TENANT_ID, tenantId);
        claims.put(CommonConstants.CLAIM_USERNAME, username);
        claims.put(CommonConstants.CLAIM_ROLES, roles);
        return generateToken(claims);
    }

    /**
     * 生成刷新Token
     *
     * @param username 用户名
     */
    public static String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CommonConstants.CLAIM_USERNAME, username);
        claims.put("type", "refresh");
        
        return Jwts.builder()
                .claims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(KEY, Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 从Token中获取用户名
     */
    public static String extractUsername(String token) {
        return extractClaim(token, claims -> claims.get(CommonConstants.CLAIM_USERNAME, String.class));
    }

    /**
     * 从Token中获取用户ID
     */
    public static Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get(CommonConstants.CLAIM_USER_ID, Long.class));
    }

    /**
     * 从Token中获取租户ID
     */
    public static Long extractTenantId(String token) {
        return extractClaim(token, claims -> claims.get(CommonConstants.CLAIM_TENANT_ID, Long.class));
    }

    /**
     * 从Token中获取角色
     */
    public static Object extractRoles(String token) {
        return extractClaim(token, claims -> claims.get(CommonConstants.CLAIM_ROLES));
    }

    /**
     * 从Token中获取过期时间
     */
    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 从Token中获取指定声明
     *
     * @param token          Token字符串
     * @param claimsResolver 声明解析器
     */
    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 从Token中获取所有声明
     */
    private static Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.warn("Token已过期: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            log.warn("Token不支持: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            log.warn("Token格式错误: {}", e.getMessage());
            throw e;
        } catch (JwtException e) {
            log.warn("Token验证失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("解析Token异常", e);
            throw new RuntimeException("Token解析异常");
        }
    }

    /**
     * 检查Token是否过期
     */
    public static Boolean isTokenExpired(String token) {
        try {
            Date expiration = extractExpiration(token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            log.error("检查Token过期异常", e);
            return true;
        }
    }

    /**
     * 验证Token
     *
     * @param token Token字符串
     * @return true: 有效, false: 无效
     */
    public static Boolean validateToken(String token) {
        try {
            // 如果Token为空，则无效
            if (com.fs.common.util.StringUtils.isBlank(token)) {
                return false;
            }

            // 移除Bearer前缀
            if (token.startsWith(CommonConstants.TOKEN_PREFIX)) {
                token = token.substring(CommonConstants.TOKEN_PREFIX.length());
            }

            // 检查Token是否过期
            if (isTokenExpired(token)) {
                return false;
            }

            // 尝试解析Token
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            log.error("Token验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 验证Token并获取用户信息
     *
     * @param token Token字符串
     * @return 用户信息Map，包含userId、tenantId、username、roles
     */
    public static Map<String, Object> validateAndGetUserInfo(String token) {
        if (!validateToken(token)) {
            throw new JwtException("Token无效或已过期");
        }

        // 移除Bearer前缀
        if (token.startsWith(CommonConstants.TOKEN_PREFIX)) {
            token = token.substring(CommonConstants.TOKEN_PREFIX.length());
        }

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", extractUserId(token));
        userInfo.put("tenantId", extractTenantId(token));
        userInfo.put("username", extractUsername(token));
        userInfo.put("roles", extractRoles(token));

        return userInfo;
    }

    /**
     * 刷新Token
     *
     * @param refreshToken 刷新Token
     * @return 新的访问Token
     */
    public static String refreshToken(String refreshToken) {
        try {
            // 移除Bearer前缀
            if (refreshToken.startsWith(CommonConstants.TOKEN_PREFIX)) {
                refreshToken = refreshToken.substring(CommonConstants.TOKEN_PREFIX.length());
            }

            // 验证刷新Token
            if (!validateToken(refreshToken)) {
                throw new JwtException("刷新Token无效或已过期");
            }

            // 从刷新Token中提取用户名
            String username = extractUsername(refreshToken);
            
            // TODO: 根据用户名查询用户信息，生成新的访问Token
            // 这里简化处理，实际应该查询数据库
            
            // 生成新的访问Token
            Map<String, Object> claims = new HashMap<>();
            claims.put(CommonConstants.CLAIM_USERNAME, username);
            claims.put("type", "access");
            
            return generateToken(claims);
        } catch (Exception e) {
            log.error("刷新Token失败", e);
            throw new JwtException("刷新Token失败");
        }
    }

    /**
     * 从请求头中获取Token
     *
     * @param authHeader Authorization头
     */
    public static String extractTokenFromHeader(String authHeader) {
        if (com.fs.common.util.StringUtils.isBlank(authHeader) || !authHeader.startsWith(CommonConstants.TOKEN_PREFIX)) {
            return null;
        }
        return authHeader.substring(CommonConstants.TOKEN_PREFIX.length());
    }

    /**
     * 获取Token剩余有效时间（秒）
     */
    public static long getTokenRemainingTime(String token) {
        try {
            // 移除Bearer前缀
            if (token.startsWith(CommonConstants.TOKEN_PREFIX)) {
                token = token.substring(CommonConstants.TOKEN_PREFIX.length());
            }

            Date expiration = extractExpiration(token);
            long remainingTime = (expiration.getTime() - System.currentTimeMillis()) / 1000;
            return Math.max(0, remainingTime);
        } catch (Exception e) {
            log.error("获取Token剩余时间失败", e);
            return 0;
        }
    }

    /**
     * 检查是否是刷新Token
     */
    public static boolean isRefreshToken(String token) {
        try {
            // 移除Bearer前缀
            if (token.startsWith(CommonConstants.TOKEN_PREFIX)) {
                token = token.substring(CommonConstants.TOKEN_PREFIX.length());
            }

            String type = extractClaim(token, claims -> claims.get("type", String.class));
            return "refresh".equals(type);
        } catch (Exception e) {
            return false;
        }
    }
}
