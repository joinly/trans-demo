package com.gd.trans.example.demo.utils;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferApply {

    /**
     * 用户名
     */
    private String username;
    /**
     * 商户名
     */
    private String appId;
    /**
     * 币种
     */
    private String coinType;
    /**
     * 转账金额
     */
    private BigDecimal transAmount;
    /**
     * 说明
     */
    private String body;
    /**
     * 第三方订单号
     */
    private String orderNo;
    /**
     * 申请时间
     */
    private String requestTime;

    /**
     * 商家接受通知url
     */
    private String notifyUrl;

    /**
     * 签名
     */
    private String sign;
}
