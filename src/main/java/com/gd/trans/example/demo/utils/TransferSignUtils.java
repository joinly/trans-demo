package com.gd.trans.example.demo.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

@Slf4j
public class TransferSignUtils {

    /**
     * 签名算法sign
     */
    @SuppressWarnings("rawtypes")
    public static String createSign(String secrtKey, SortedMap<String, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        //所有参与传参的参数按照accsii排序（升序）
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry) it.next();
            String k = entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("secretKey=" + secrtKey);
        return MD5Util.MD5Encode(sb.toString(), "utf-8").toUpperCase();
    }
}
