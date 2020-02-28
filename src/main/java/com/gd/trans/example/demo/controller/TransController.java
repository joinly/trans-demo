package com.gd.trans.example.demo.controller;

import com.gd.trans.example.demo.utils.HttpUtil;
import com.gd.trans.example.demo.utils.TransferApply;
import com.gd.trans.example.demo.utils.TransferSignUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
public class TransController {

    private static final String intoUrl = "http://localhost:8082/api/mchTransInApply/into";

    private static final String outUrl = "http://localhost:8082/api/mchTransOutApply/out";

    private static final String notifyUrl = "http://localhost:8083/earth/notify";

    private static final String secrtKey = "s77R/2k4dOiWDYOlCp8XHw==";

    /**
     * 提交转入申请
     */
    public void submitInto(TransferApply apply) {
        SortedMap<String, Object> sortedMap = new TreeMap<>();
        sortedMap.put("username", apply.getUsername());
        sortedMap.put("mchUsername", apply.getMchUsername());
        sortedMap.put("coinType", apply.getCoinType());
        sortedMap.put("transAmount", apply.getTransAmount());
        sortedMap.put("wallet", apply.getWallet());
        sortedMap.put("body", apply.getBody());
        sortedMap.put("requestTime", apply.getRequestTime());
        sortedMap.put("orderNo", apply.getOrderNo());
        sortedMap.put("notifyUrl", notifyUrl);
        String sign = TransferSignUtils.createSign(secrtKey, sortedMap);
        apply.setSign(sign);

        Gson gson = new Gson();
        String gsonString = gson.toJson(apply);
        try {
            ResponseEntity entity = HttpUtil.postJson(intoUrl, gsonString);
            if (entity.getStatusCode().value() == 200) {
                String body = entity.getBody().toString();
                Map<String, Object> map = gson.fromJson(body, Map.class);
                int code = Double.valueOf(map.get("code").toString()).intValue();
                if (code == 200) {
                    log.info("=====转入请求成功=====");
                } else {
                    log.info("=====转入请求失败=====");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("=====转入请求失败=====");
        }
    }

    /**
     * 提交转出申请
     */
    public void submitOut(TransferApply apply) {
        SortedMap<String, Object> sortedMap = new TreeMap<>();
        sortedMap.put("username", apply.getUsername());
        sortedMap.put("mchUsername", apply.getMchUsername());
        sortedMap.put("coinType", apply.getCoinType());
        sortedMap.put("transAmount", apply.getTransAmount());
        sortedMap.put("wallet", apply.getWallet());
        sortedMap.put("body", apply.getBody());
        sortedMap.put("requestTime", apply.getRequestTime());
        sortedMap.put("orderNo", apply.getOrderNo());
        sortedMap.put("notifyUrl", notifyUrl);
        String sign = TransferSignUtils.createSign(secrtKey, sortedMap);
        apply.setSign(sign);

        Gson gson = new Gson();
        String gsonString = gson.toJson(apply);
        try {
            ResponseEntity entity = HttpUtil.postJson(outUrl, gsonString);
            if (entity.getStatusCode().value() == 200) {
                String body = entity.getBody().toString();
                Map<String, Object> map = gson.fromJson(body, Map.class);
                int code = Double.valueOf(map.get("code").toString()).intValue();
                if (code == 200) {
                    log.error("=====转出请求成功=====");
                } else {
                    log.error("=====转出请求失败=====");
                }
            }
        } catch (Exception e) {
            log.error("=====转出请求失败=====");
        }
    }

    public static void main(String[] args) {
        TransferApply apply = new TransferApply();
        apply.setUsername("13560443784");
        apply.setMchUsername("15916264616");
        apply.setCoinType("UIGI");
        apply.setTransAmount(new BigDecimal(888));
        apply.setWallet("EARTH");
        apply.setBody("地球村转入");
        //apply.setBody("地球村转出");
        apply.setRequestTime("2020-02-22 17:44:06");
        apply.setOrderNo("202002271039004853");
        apply.setNotifyUrl(notifyUrl);

        TransController trans = new TransController();
        trans.submitInto(apply);
        //trans.submitOut(apply);
    }
}
