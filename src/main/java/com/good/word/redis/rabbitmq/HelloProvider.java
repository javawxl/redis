package com.good.word.redis.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangxianlei
 * @since 1.0
 */
@Component
public class HelloProvider {

    private final RabbitTemplate rabbitTemplate;

    public HelloProvider(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(Object objectMessage) {
        rabbitTemplate.convertAndSend("hello", objectMessage);
    }
}
