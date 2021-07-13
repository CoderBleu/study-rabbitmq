package cn.blue.rabbitmq.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Blue
 * @date 2020/8/30
 **/

@Component
@RabbitListener(queues = "dlx_queue_boot")
public class DeadQueueListen {

    @RabbitHandler
    public void getMessage(String msg, Channel channel, Message message) throws IOException {
        System.out.println("接受到的消息:"+msg);
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
    }

}