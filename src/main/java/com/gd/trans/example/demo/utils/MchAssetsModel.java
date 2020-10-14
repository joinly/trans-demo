package com.gd.trans.example.demo.utils;

import lombok.Data;

/**
 * @author joinly
 * @company: goodinvest
 * @date 2020-06-12 10:05
 * @desc: TODO
 */

@Data
public class MchAssetsModel {

    private String username;

    private String coinType;

    /**
     * 商户名
     */
    private String appId;

    /**
     * 签名
     */
    private String sign;
}
