package com.good.word.redis.mq;

import com.alibaba.fastjson.JSON;
import com.good.word.redis.entity.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author wangxianlei
 * @since 1.0
 */
@Component
public class Product {
    private final KafkaTemplate kafkaTemplate;

    public Product(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String name) {
        UserModel userModel = new UserModel();
        userModel.setId("1");
        userModel.setName("wxl");
        kafkaTemplate.send("user-topic", JSON.toJSONString(userModel));
    }
}
