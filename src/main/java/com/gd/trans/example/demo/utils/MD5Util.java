package com.gd.trans.example.demo.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;

/**
 * @Author Windy
 * @Date 2019-01-07
 * @Description MD5加密解密工具类
 */
public class MD5Util {

    private static final String hexDigIts[] = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    /**
     * Md5加密
     * @param src 源字符串
     * @return 加密后数据
     */
    public static String encrypt(String src){
        return DigestUtils.md5Hex(src);
    }

    /**
     * 可调节加密次数Md5加密
     * @param src 源字符串
     * @param count 加密次数
     * @return 加密后字符串
     */
    public static String encrypt(String src,int count){
        for(int i=0;i<count;i++){
            src = encrypt(src);
        }
        return src;
    }

    /**
     * MD5加密
     * @param origin 字符
     * @param charsetname 编码
     * @return
     */
    public static String MD5Encode(String origin, String charsetname){
        String resultString = null;
        try{
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if(null == charsetname || "".equals(charsetname)){
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            }else{
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        }catch (Exception e){
        }
        return resultString;
    }


    public static String byteArrayToHexString(byte b[]){
        StringBuffer resultSb = new StringBuffer();
        for(int i = 0; i < b.length; i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    public static String byteToHexString(byte b){
        int n = b;
        if(n < 0){
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigIts[d1] + hexDigIts[d2];
    }
}