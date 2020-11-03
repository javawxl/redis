package com.good.word.redis.rabbitmq;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author wangxianlei
 * @since 1.0
 */
@Component
public class DirectConsumer {
    @RabbitListener(bindings = {
      @QueueBinding(
        value = @Queue,
        exchange = @Exchange(value = "Direct交换机", type = "direct"),
        key = {"debug", "info", "warning", "error"}
      )
    })
    public void receive1(String message) {
        System.out.println("Consumer 1 : " + message);
    }

    @RabbitListener(bindings = {
      @QueueBinding(
        value = @Queue,
        exchange = @Exchange(value = "Direct交换机", type = "direct"),
        key = {"error"}
      )
    })
    public void receive2(String message) {
        System.out.println("Consumer 2 : " + message);
    }
}
