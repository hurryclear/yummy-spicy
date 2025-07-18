package com.yummy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * 生成jwt
     * Generate jwt
     * 使用Hs256算法, 私匙使用固定秘钥
     * Use Hs256 algorithm, the private key uses a fixed secret key
     *
     * @param secretKey jwt秘钥
     * @param ttlMillis jwt过期时间(毫秒)
     * @param claims    设置的信息
     * @return
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 指定签名的时候使用的签名算法，也就是header那部分
        // Specify the signature algorithm used for signing, which is the header part
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的时间
        // The time when the JWT is generated
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        // 设置jwt的body
        // Set the body of the jwt
        JwtBuilder builder = Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                // If there is a private claim, you must set this self-created private claim first. This is to assign a value to the builder's claim. Once it is written after the standard claim is assigned, it will overwrite those standard claims.
                .setClaims(claims)
                // 设置签名使用的签名算法和签名使用的秘钥
                // Set the signature algorithm used for signing and the secret key used for signing
                .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
                // 设置过期时间
                // Set the expiration time
                .setExpiration(exp);

        return builder.compact();
    }

    /**
     * Token解密
     * Token decryption
     *
     * @param secretKey jwt秘钥 此秘钥一定要保留好在服务端, 不能暴露出去, 否则sign就可以被伪造, 如果对接多个客户端建议改造成多个
     * @param token     加密后的token
     * @return
     */
    public static Claims parseJWT(String secretKey, String token) {
        // 得到DefaultJwtParser
        // Get DefaultJwtParser
        Claims claims = Jwts.parser()
                // 设置签名的秘钥
                // Set the signing secret key
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                // 设置需要解析的jwt
                // Set the jwt to be parsed
                .parseClaimsJws(token).getBody();
        return claims;
    }

}
