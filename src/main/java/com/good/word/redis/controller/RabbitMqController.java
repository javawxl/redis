package com.good.word.redis.controller;

import com.good.word.redis.rabbitmq.RabbitConst;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
public class RabbitMqController {
    final RabbitTemplate rabbitTemplate;
    private final String[] logLevel = {"debug", "info", "warning", "error"};

    public RabbitMqController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    /**
     * 简单模式
     */
    @GetMapping("/hello")
    public void hello() {
        //此模式下，routingKey要和Queue的name名称一致
        rabbitTemplate.convertAndSend( "Hello队列","Hello RabbitMQ!");
    }

    /**
     * 工作模式(资源的竞争)
     */
    @GetMapping("/work")
    public void work() {
        for (int i = 0; i < 10; i++) {
            //此模式下，routingKey要和Queue的name名称一致
            rabbitTemplate.convertAndSend("Work队列","work queue rabbitmq!" + i);
        }
    }

    /**
     * 发布订阅(共享资源)
     */
    @GetMapping("/fanout")
    public void fanout() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(RabbitConst.EXCHANGE_NAME_FANOUT, "", "Fanout Message" + i);
        }
    }

    /**
     * routing路由模式
     */
    @GetMapping("/direct")
    public void direct() {
        rabbitTemplate.setConfirmCallback((correlationData, bool, error) -> {
            if (bool) {
                System.out.println("confirm 消息发送确认成功...消息ID为：" + correlationData.getId());
            } else {
                System.out.println("confirm 消息发送确认失败...消息ID为：" + correlationData.getId() + " 失败原因: " + error);
            }
        });
        for (int i = 0; i < 10000 * 10; i++) {
            String routingKey = logLevel[i % logLevel.length];
            //convertSendAndReceive顺序发送
            rabbitTemplate.convertAndSend(RabbitConst.EXCHANGE_NAME_DIRECT, routingKey,
                    String.format("This is a direct[routingKey=%s] message %d ", routingKey, i),
                    new CorrelationData("id=" + i ));
        }
    }

    /**
     * topic 主题模式(路由模式的一种)
     */
    @GetMapping("/topic")
    public void topic() {
        for (int i = 0; i < 10; i++) {
            String routingKey = i % 2 == 0 ? "user.save" : "role.save";
            rabbitTemplate.convertAndSend(RabbitConst.EXCHANGE_NAME_TOPIC, routingKey, "Topic Message : " + routingKey);
        }
    }
}
