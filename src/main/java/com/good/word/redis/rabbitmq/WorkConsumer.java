package com.good.word.redis.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author wangxianlei
 * @since 1.0
 */
@Component
public class WorkConsumer {

    @RabbitListener(queuesToDeclare = @Queue("Work队列"))
    public void receive(Message message, Channel channel) throws IOException {
        System.out.println("Work队列消费者 1:" + new String(message.getBody(), StandardCharsets.UTF_8));
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

    @RabbitListener(queuesToDeclare = @Queue("Work队列"))
    public void receive2(Message message, Channel channel) throws IOException {
        System.out.println("Work队列消费者 2:" + new String(message.getBody(), StandardCharsets.UTF_8));
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
