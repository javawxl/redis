package com.good.word.redis.rabbitmq;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author wangxianlei
 * @since 1.0
 */
@Component
@RabbitListener(queuesToDeclare = @Queue(value = "Hello队列"))
public class HelloConsumer {

    @RabbitHandler
    public void consumer(String message) {
        System.out.println("Consumer 1:" + message);
    }
}
