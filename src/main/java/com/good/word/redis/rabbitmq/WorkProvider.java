package com.good.word.redis.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author wangxianlei
 * @since 1.0
 */
@Component
public class WorkProvider {

    private final RabbitTemplate rabbitTemplate;
    public WorkProvider(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String message) {
        this.rabbitTemplate.convertAndSend("work", message);
    }
}
