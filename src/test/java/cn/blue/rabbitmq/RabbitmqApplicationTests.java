package cn.blue.rabbitmq;

import cn.blue.rabbitmq.entity.UserInfo;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class RabbitmqApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    /**
     * 正常交换机的名称
     */
    public static final String NORMAL_EXCHANGE_BOOT = "normal_exchange_boot";
    /**
     * 正常队列的名称
     */
    public static final String NORMAL_QUEUE_BOOT = "normal_queue_boot";
    /**
     * 死信队列的名称
     */
    public static final String DLX_QUEUE_BOOT = "dlx_queue_boot";

    @Test
    void putMessageToNormalQueue() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "这是putMessageToNormalQueue发送的消息");
        map.put("data", Arrays.asList((Math.random() * 100) + 1, "helloWorld", false));
        rabbitTemplate.convertAndSend(NORMAL_EXCHANGE_BOOT, NORMAL_QUEUE_BOOT, map);
    }

    @Test
    void getMessageDeadQueue() {
        Map map = (HashMap) rabbitTemplate.receiveAndConvert(DLX_QUEUE_BOOT);
        System.out.println(map);
    }

    @Test
    public void test() {
        for (int i = 0; i < 10000; i++) {
            rabbitTemplate.convertAndSend(NORMAL_EXCHANGE_BOOT, NORMAL_QUEUE_BOOT, i + "");
        }
    }

    /**
     * 单播：点对点发送map格式
     */
    @Test
    void contextLoads() {
        // Message需要自己构造一个；定义消息体内容和消息头
        // rabbitTemplate.send(exchage, routeKey, message);

        // object默认当成消息体，只需传入要发送的对象，自动序列化发送给rabbitmq；
        // rabbitTemplate.converAndSend(exchage,routeKey,object)
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "这是converAndSend发送的消息");
        map.put("data", Arrays.asList(2, "helloWorld", false));
        // 默认使用java的序列化消息：application/x-java-serialized-object
        rabbitTemplate.convertAndSend("exchange.direct", "blue.news", map);
    }

    /**
     * 单播：点对点发送对象格式
     */
    @Test
    void contextLoadsObject() {
        rabbitTemplate.convertAndSend("exchange.direct", "blue.users", new UserInfo("Kity", 20));
    }

    /**
     * 广播
     */
    @Test
    void sendMsg() {
        rabbitTemplate.convertAndSend("exchanges.fanout", "", new UserInfo("fanout", 20));
    }

    @Test
    public void createExchange() {
        // 创建交换器
        amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
        System.out.println("创建完成");
        // 创建队列
        amqpAdmin.declareQueue(new Queue("amqpadmin.queue", true));
        // 创建绑定规则
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE,
                "amqpadmin.exchange", "创建绑定规则", null));
    }

    /**
     * 删除
     */
    @Test
    public void deleteQueue() {
        amqpAdmin.deleteQueue("amqpadmin.queue");
        amqpAdmin.deleteExchange("blue.users");
    }

    /**
     * 接收数据: 接收一个队列里就少一个
     */
    @Test
    public void receive() {
        // 注意有参无参构造器都要
        Object o = rabbitTemplate.receiveAndConvert("blue.users");
        System.err.println(o);
    }
}
