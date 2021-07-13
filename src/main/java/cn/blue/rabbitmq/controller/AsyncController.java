package cn.blue.rabbitmq.controller;

import cn.blue.rabbitmq.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Blue
 * @date 2020/8/30
 **/
@RestController
@RequestMapping("/")
public class AsyncController {
    @Autowired
    AsyncService asyncService;

    @GetMapping
    public void asyncDemo() {
        asyncService.asyncDemo();
    }
}
