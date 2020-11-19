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
public class FanoutConsumer {

    @RabbitListener(bindings = {
      @QueueBinding(
        value = @Queue(name = "Fanout队列1"),//没有设置name属性对的值，rabbitMQ会设置临时队列
        exchange = @Exchange(value = RabbitConst.EXCHANGE_NAME_FANOUT, type = RabbitConst.EXCHANGE_TYPE_FANOUT)
      )
    })
    public void receive1(Message message, Channel channel) throws IOException {
        try {
            System.out.println("Fanout队列消费者 1:" + new String( message.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }

    @RabbitListener(bindings = {
      @QueueBinding(
        value = @Queue(name = "Fanout队列2"),//没有设置name属性对的值，rabbitMQ会设置临时队列
        exchange = @Exchange(value = RabbitConst.EXCHANGE_NAME_FANOUT, type = RabbitConst.EXCHANGE_TYPE_FANOUT)
      )
    })
    public void receive2(Message message, Channel channel) throws IOException {
        try {
            System.out.println("Fanout队列消费者 2:" + new String( message.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
