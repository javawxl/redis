package com.good.word.redis.rabbitmq;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author wangxianlei
 * @since 1.0
 */
@Component
public class WorkConsumer {

    @RabbitListener(queuesToDeclare = @Queue("Work队列"))
    public void receive(String message) {
        System.out.println("Consumer 1:" + message);
    }

    @RabbitListener(queuesToDeclare = @Queue("Work队列"))
    public void receive2(String message) {
        System.out.println("Consumer 2:" + message);
    }
}
