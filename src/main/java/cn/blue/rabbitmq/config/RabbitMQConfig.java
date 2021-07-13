package cn.blue.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Blue
 * @date 2020/8/30
 *
 **/
@Configuration
public class RabbitMQConfig {

    /**
     * 更改rabbitmq默认序列化规则
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 正常交换机的名称
     */
    public static final String NORMAL_EXCHANGE_BOOT = "normal_exchange_boot";
    /**
     * 死信交换机的名称
     */
    public static final String DLX_EXCHANGE_BOOT = "dlx_exchange_boot";
    /**
     * 正常队列的名称
     */
    public static final String NORMAL_QUEUE_BOOT = "normal_queue_boot";
    /**
     * 死信队列的名称
     */
    public static final String DLX_QUEUE_BOOT = "dlx_queue_boot";


    /**
     * 声明队列
     *
     * @return
     */
    @Bean("normalQueue")
    public Queue queueDeclare() {
        Map<String, Object> args = new HashMap<>(2);
        // x-dead-letter-exchange
        args.put("x-dead-letter-exchange", DLX_EXCHANGE_BOOT);
        // x-dead-letter-routing-key
        args.put("x-dead-letter-routing-key", DLX_QUEUE_BOOT);
        //x-message-ttl
        args.put("x-message-ttl", 9000);
        //x-max-length
        args.put("x-max-length", 99);
        return QueueBuilder.durable(NORMAL_QUEUE_BOOT).withArguments(args).build();
    }

    /**
     * 声明正常的交换机
     *
     * @return
     */
    @Bean("exchangeNormal")
    public Exchange exchangeNormal() {
        return ExchangeBuilder.directExchange(NORMAL_EXCHANGE_BOOT).durable(true).build();
    }

    /**
     * 队列绑定交换机
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding queueBindExchange(@Qualifier("normalQueue") Queue queue, @Qualifier("exchangeNormal") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(NORMAL_QUEUE_BOOT).noargs();
    }

    /**
     * 声明死信队列
     *
     * @return
     */
    @Bean("queueDlx")
    public Queue queueDlx() {
        return QueueBuilder.durable(DLX_QUEUE_BOOT).build();
    }

    /**
     * 声明死信交换机
     *
     * @return
     */
    @Bean("exchangeDlx")
    public Exchange exchangeDlx() {
        return ExchangeBuilder.directExchange(DLX_EXCHANGE_BOOT).durable(true).build();
    }

    /**
     * 死信队列绑定死信交换机
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding dlxQueueBindDlxExchange(@Qualifier("queueDlx") Queue queue, @Qualifier("exchangeDlx") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DLX_QUEUE_BOOT).noargs();
    }


}
