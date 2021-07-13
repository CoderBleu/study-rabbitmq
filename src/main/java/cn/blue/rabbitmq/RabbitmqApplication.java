package cn.blue.rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 自动配置
 * 1、RabbitAutoConfiguration
 * 2、@EnableRabbit + @RabbitListener监听消息队列内容
 * @author Blue
 */
@EnableRabbit
@EnableAsync
@SpringBootApplication
public class RabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
    }

}
