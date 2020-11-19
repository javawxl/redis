package com.good.word.redis.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author wangxianlei
 * @since 1.0
 */
@Component
public class TopicConsumer {
    @RabbitListener(bindings = {
      @QueueBinding(
        value = @Queue("Topic队列1"),
        exchange = @Exchange(value = RabbitConst.EXCHANGE_NAME_TOPIC, type = RabbitConst.EXCHANGE_TYPE_TOPIC),
        key = {"user.*"}
      )
    })
    public void receive(Message message, Channel channel) throws IOException {
        try {
            System.out.println("Topic队列消费者(User):" + new String( message.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }


    @RabbitListener(bindings = {
      @QueueBinding(
        value = @Queue("Topic队列2"),
        exchange = @Exchange(value = RabbitConst.EXCHANGE_NAME_TOPIC, type = RabbitConst.EXCHANGE_TYPE_TOPIC),
        key = {"role.*"}
      )
    })
    public void receive2(Message message, Channel channel) throws IOException {
        try {
            System.out.println("Topic队列消费者(Role):" + new String( message.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
