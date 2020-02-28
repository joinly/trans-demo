package com.gd.trans.example.demo.utils;

import lombok.Data;

@Data
public class ResultJson {
    private int code;
    private String msg;
    private String data;

    public ResultJson(int code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
