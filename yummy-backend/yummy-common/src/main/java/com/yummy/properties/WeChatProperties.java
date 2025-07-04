package com.yummy.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "yummy.wechat")
@Data
public class WeChatProperties {

    private String appid; //小程序的appid
    // AppID of the Mini Program
    private String secret; //小程序的秘钥
    // Secret of the Mini Program
    private String mchid; //商户号
    // Merchant ID
    private String mchSerialNo; //商户API证书的证书序列号
    // Certificate serial number of the merchant API certificate
    private String privateKeyFilePath; //商户私钥文件
    // Merchant private key file
    private String apiV3Key; //证书解密的密钥
    // Key for certificate decryption
    private String weChatPayCertFilePath; //平台证书
    // Platform certificate
    private String notifyUrl; //支付成功的回调地址
    // Callback address for successful payment
    private String refundNotifyUrl; //退款成功的回调地址
    // Callback address for successful refund

}
