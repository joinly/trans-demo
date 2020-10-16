package com.gd.trans.example.demo.controller;

import com.gd.trans.example.demo.config.TTSocket;
import com.gd.trans.example.demo.config.ThriftClientConnectPoolFactory;
import com.gd.trans.example.demo.thrift.FreezeModel;
import com.gd.trans.example.demo.thrift.ResponseResult;
import com.gd.trans.example.demo.thrift.TransferModel;
import com.gd.trans.example.demo.thrift.UnFreezeModel;
import com.gd.trans.example.demo.utils.RandomNumberUtils;
import com.gd.trans.example.demo.utils.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author joinly
 * @company: goodinvest
 * @date 2020-10-13 08:59
 * @desc: TODO
 */
@Slf4j
@RequestMapping("/transfer")
@RestController
public class TransferRpcController {

    @Autowired
    private ThriftClientConnectPoolFactory thriftClientPool;

    @GetMapping("/test")
    public ResultJson transfer() {
        ResultJson result = new ResultJson(200, "成功", "success");
        try {
            //获取一个连接对象
            TTSocket ttSocket = thriftClientPool.getConnect();
            TransferModel model = new TransferModel();
            model.setBizNo(RandomNumberUtils.getBizNo());
            model.setCoinType("GoodCoin");
            model.setFromUsername("200515100118");
            model.setToUsername("200515100122");
            model.setTransAmount("50000000");
            model.setTransType("TRANSOUT");
            model.setRemarks("rpc转账测试");
            ResponseResult responseResult = ttSocket.getService().transfer(model);
            //使用完后,放到池中
            thriftClientPool.returnConnection(ttSocket);
            log.info("转账 responseResult: {} ==== {}", responseResult.getCode(), responseResult.getMsg());
            result.setMsg(responseResult.getMsg());
            return result;
        } catch (TTransportException e) {
            log.error("打开socket链接失败, 原因：{}", e);
        } catch (Exception e) {
            log.error("转账异常", e);
        }
        result.setCode(500);
        result.setMsg("转账失败");
        result.setData("fail");
        return result;
    }

    @GetMapping("/freeze")
    public ResultJson freeze() {
        ResultJson result = new ResultJson(200, "成功", "success");
        try {
            //获取一个连接对象
            TTSocket ttSocket = thriftClientPool.getConnect();
            FreezeModel model = new FreezeModel();
            model.setBizNo(RandomNumberUtils.getBizNo());
            model.setUsername("200515100118");
            model.setCoinType("GoodCoin");
            model.setBalance("200000000");
            model.setRemarks("测试冻结");
            ResponseResult responseResult = ttSocket.getService().freeze(model);
            //使用完后,放到池中
            thriftClientPool.returnConnection(ttSocket);
            log.info("冻结 responseResult: {} ==== {}", responseResult.getCode(), responseResult.getMsg());
            result.setMsg(responseResult.getMsg());
            return result;
        } catch (Exception e) {
            log.error("冻结异常", e);
        }
        result.setCode(500);
        result.setMsg("冻结失败");
        result.setData("fail");
        return result;
    }

    @GetMapping("/unFreeze")
    public ResultJson unFreeze() {
        ResultJson result = new ResultJson(200, "成功", "success");
        try {
            //获取一个连接对象
            TTSocket ttSocket = thriftClientPool.getConnect();
            UnFreezeModel model = new UnFreezeModel();
            model.setBizNo("20201016122505722898");
            model.setBalance("200000000");
            ResponseResult responseResult = ttSocket.getService().unFreeze(model);
            //使用完后,放到池中
            thriftClientPool.returnConnection(ttSocket);
            log.info("解冻 responseResult: {} ==== {}", responseResult.getCode(), responseResult.getMsg());
            result.setMsg(responseResult.getMsg());
            return result;
        } catch (Exception e) {
            log.error("解冻异常", e);
        }
        result.setCode(500);
        result.setMsg("解冻失败");
        result.setData("fail");
        return result;
    }

}
