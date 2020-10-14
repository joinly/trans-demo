package com.gd.trans.example.demo.controller;

import com.gd.trans.example.demo.utils.HttpUtil;
import com.gd.trans.example.demo.utils.MchAssetsModel;
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

    //private static final String intoUrl = "http://test.sce.ethancz.com/api/open/api/mchTransInApply/into";
    private static final String intoUrl = "http://localhost:8082/api/open/api/mchTransInApply/into";

    private static final String outUrl = "http://localhost:8082/api/open/api/mchTransOutApply/out";

    private static final String notifyUrl = "http://localhost:8083/earth/notify";

    private static final String secretKey = "8a224a6a88a4f30647172a3041ada080";

    private static final String getAssetsUrl = "http://test.sce.ethancz.com/api/open/api/mchAssets/get";
    //private static final String getAssetsUrl = "http://localhost:8082/api/open/api/mchAssets/get";

    /**
     * 提交转入申请
     */
    public void submitInto(TransferApply apply) {
        SortedMap<String, Object> sortedMap = new TreeMap<>();
        sortedMap.put("username", apply.getUsername());
        sortedMap.put("appId", apply.getAppId());
        sortedMap.put("coinType", apply.getCoinType());
        sortedMap.put("transAmount", apply.getTransAmount());
        sortedMap.put("body", apply.getBody());
        sortedMap.put("requestTime", apply.getRequestTime());
        sortedMap.put("orderNo", apply.getOrderNo());
        sortedMap.put("notifyUrl", notifyUrl);
        String sign = TransferSignUtils.createSign(secretKey, sortedMap);
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
                    log.info("=====转入请求失败=====" + map.get("msg").toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("=====转入请求失败=====");
        }
    }

    /**
     * 提交转出申请 优惠
     */
    public void submitOut(TransferApply apply) {
        SortedMap<String, Object> sortedMap = new TreeMap<>();
        sortedMap.put("username", apply.getUsername());
        sortedMap.put("appId", apply.getAppId());
        sortedMap.put("coinType", apply.getCoinType());
        sortedMap.put("transAmount", apply.getTransAmount());
        sortedMap.put("body", apply.getBody());
        sortedMap.put("requestTime", apply.getRequestTime());
        sortedMap.put("orderNo", apply.getOrderNo());
        sortedMap.put("notifyUrl", notifyUrl);
        String sign = TransferSignUtils.createSign(secretKey, sortedMap);
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
                    log.error("=====转出请求失败=====" + map.get("msg"));
                }
            }
        } catch (Exception e) {
            log.error("=====转出请求失败=====");
        }
    }

    private void getAssets(MchAssetsModel model) {
        SortedMap<String, Object> sortedMap = new TreeMap<>();
        sortedMap.put("username", model.getUsername());
        sortedMap.put("appId", model.getAppId());
        sortedMap.put("coinType", model.getCoinType());
        String sign = TransferSignUtils.createSign(secretKey, sortedMap);
        model.setSign(sign);

        Gson gson = new Gson();
        String gsonString = gson.toJson(model);
        try {
            ResponseEntity entity = HttpUtil.postJson(getAssetsUrl, gsonString);
            if (entity.getStatusCode().value() == 200) {
                String body = entity.getBody().toString();
                Map<String, Object> map = gson.fromJson(body, Map.class);
                int code = Double.valueOf(map.get("code").toString()).intValue();
                if (code == 200) {
                    log.error("=====请求成功=====" + map.get("data"));
                } else {
                    log.error("=====请求失败1=====" + map.get("msg"));
                }
            }
        } catch (Exception e) {
            log.error("=====请求失败2=====", e);
        }
    }

    public static void main(String[] args) {
        TransController trans = new TransController();
        TransferApply apply = new TransferApply();
        apply.setAppId("7042741617");
        apply.setCoinType("GlobalCoin");
        apply.setUsername("200423100000273");
        apply.setTransAmount("1.0");

        apply.setRequestTime("2020-02-22 17:44:06");
        apply.setOrderNo("202009121039002222");
        apply.setNotifyUrl(notifyUrl);


        //apply.setBody("地球村用户转出, 调用goodwallet的转入接口");
        //trans.submitInto(apply);
        apply.setBody("地球村用户转入, 调用goodwallet的转出接口");
        trans.submitOut(apply);
        /*MchAssetsModel model = new MchAssetsModel();
        model.setAppId("4546197386");
        model.setCoinType("UIGI");
        model.setUsername("18688180876");
        trans.getAssets(model);*/
    }
}
