package com.gd.trans.example.demo.utils;

import lombok.Data;

@Data
public class NotifyModel {
    private String username;
    private String mchUsername;
    private String coinType;
    private String transAmount;
    private String transTime;
    private String orderNo;
    private String transNo;
    private String resultCode;
    private String resultMsg;
    private String sign;

    @Override
    public String toString() {
        return "NotifyModel{" +
                "username='" + username + '\'' +
                ", mchUsername='" + mchUsername + '\'' +
                ", coinType='" + coinType + '\'' +
                ", transferAmount='" + transAmount + '\'' +
                ", transferTime='" + transTime + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", transNo='" + transNo + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
