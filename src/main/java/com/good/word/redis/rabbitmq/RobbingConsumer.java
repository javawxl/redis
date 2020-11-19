package com.good.word.redis.rabbitmq;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.good.word.redis.entity.ProductModel;
import com.good.word.redis.entity.RobbingDetailModel;
import com.good.word.redis.mapper.IProductMapper;
import com.good.word.redis.mapper.IRobbingMapper;
import com.good.word.redis.mq.Product;
import com.good.word.redis.service.IRobbingService;
import com.good.word.redis.service.impl.ConcurrentServiceImpl;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

@Component
public class RobbingConsumer {


    @Autowired
    private IProductMapper productMapper;
    @Autowired
    private IRobbingMapper robbingMapper;
    @Autowired
    private IRobbingService robbingService;

    @RabbitListener(queuesToDeclare = @Queue("Robbing队列"))// FIXME: 2020/11/12 why here
    public void consume(Message message, Channel channel) throws IOException {
        byte[] body = message.getBody();
        ByteArrayInputStream bi = new ByteArrayInputStream(body);
        ObjectInputStream oi = null;
        try {
            oi = new ObjectInputStream(bi);
            Object object = oi.readObject();
            Map<String, Object> map = (Map<String, Object>) object;
            boolean result = robbingService.robWithOptimisticLock((ProductModel) map.get("product"), (String)map.get("phone"));
            if (result) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

}
