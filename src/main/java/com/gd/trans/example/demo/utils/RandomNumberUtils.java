package com.gd.trans.example.demo.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.utils.DateUtils;

import java.util.Date;
import java.util.UUID;

/**
 * @创建人: joinly
 * @c创建时间: 2020-02-06 20:36
 * @描述: 生成订号
 **/
public class RandomNumberUtils {


    /**
     * orderNo共18位
     * 1. 年月日时分秒 14位
     * 2. 随机数 4位
     */
    public static String getBizNo() {
        StringBuilder sbd = new StringBuilder();
        /** yyyyMMddhhmmss */
        String date = DateUtils.formatDate(new Date(), "yyyyMMddhhmmss");
        /* 4位随机数 */
        String random = getRandomNumericString(6);
        return sbd.append(date).append(random).toString();
    }

    /**
     * 获取一个长度为count的随机数字字符串
     *
     * @param count
     * @return
     */
    public static String getRandomNumericString(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

    /**
     * 生成32位的UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
