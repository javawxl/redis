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
public class TopicConsumer {
    @RabbitListener(bindings = {
      @QueueBinding(
        value = @Queue,
        exchange = @Exchange(value = "Topic交换机", type = "topic"),
        key = {"user.*"}
      )
    })
    public void receive(String message) {
        System.out.println("User Consumer :" + message);
    }


    @RabbitListener(bindings = {
      @QueueBinding(
        value = @Queue,
        exchange = @Exchange(value = "Topic交换机", type = "topic"),
        key = {"role.*"}
      )
    })
    public void receive2(String message) {
        System.out.println("Role Consumer :" + message);
    }
}
