package com.good.word.redis.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author wangxianlei
 * @since 1.0
 */
@Component
public class HelloConsumer {

    @RabbitListener(queuesToDeclare = @Queue(value = "Hello队列"))
    @RabbitHandler
    public void consumer(Message message, Channel channel) throws IOException {
        System.out.println("Hello队列消费者:" + message);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            e.printStackTrace();
            channel.basicReject(deliveryTag, false);
        }
    }
}
