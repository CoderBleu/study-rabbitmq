package cn.blue.rabbitmq.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Blue
 * @date 2020/8/30
 **/
@Service
public class AsyncService {

    @Async
    public void asyncDemo() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("处理数据中...");
    }
}
