package com.yummy.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * properties class 配置类
 */
@Component
@ConfigurationProperties(prefix = "yummy.jwt")
@Data
public class JwtProperties {

    /**
     * Configuration related to generating JWT tokens for admin employees
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * Configuration related to generating JWT tokens for WeChat users
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}
