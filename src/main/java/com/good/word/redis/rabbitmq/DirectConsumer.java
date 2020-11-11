package com.good.word.redis.rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Optional;

/**
 * @author wangxianlei
 * @since 1.0
 */
@Component
@Slf4j
public class DirectConsumer {
    @RabbitListener(bindings = {
      @QueueBinding(
        value = @Queue("Direct队列1"),
        exchange = @Exchange(value = RabbitConst.EXCHANGE_NAME_DIRECT, type = RabbitConst.EXCHANGE_TYPE_DIRECT),
        key = {"debug", "info", "warning", "error"}
      )
    })
    public void receive1(Message message, Channel channel) throws IOException {
        System.out.println("Consumer 1 : " + new String(message.getBody()));
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // 两个布尔值  第二个设为 false 则丢弃该消息 设为true 则返回给队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

    @RabbitListener(bindings = {
      @QueueBinding(
        value = @Queue(name = "Direct队列2"),
        exchange = @Exchange(value = RabbitConst.EXCHANGE_NAME_DIRECT, type = RabbitConst.EXCHANGE_TYPE_DIRECT),
        key = {"error"}
      )
    })
    public void receive2(Message message, Channel channel) throws IOException {
        System.out.println("Consumer 2 : " + new String(message.getBody()));
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // 两个布尔值  第二个设为 false 则丢弃该消息 设为true 则返回给队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

}
