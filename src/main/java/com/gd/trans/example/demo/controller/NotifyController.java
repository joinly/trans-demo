package com.gd.trans.example.demo.controller;

import com.gd.trans.example.demo.utils.NotifyModel;
import com.gd.trans.example.demo.utils.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/earth")
@RestController
public class NotifyController {

    @PostMapping("/notify")
    public ResultJson notifyTest(@RequestBody NotifyModel model) {
        ResultJson result = new ResultJson(200, "成功", "success");
        try {
            log.info("====商户拿到数据解析处理逻辑=====");
            log.info(model.toString());
            return result;
        } catch (Exception e) {
            log.error("接到处理异常", e);
        }
        result.setCode(500);
        result.setMsg("失败");
        result.setData("fail");
        return result;
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
