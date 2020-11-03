package com.good.word.redis;

import cn.hutool.Hutool;
import cn.hutool.core.date.DateUtil;
import com.good.word.redis.controller.TestController;
import com.good.word.redis.entity.UserModel;
import com.good.word.redis.mq.Product;
import com.good.word.redis.rabbitmq.HelloProvider;
import com.good.word.redis.rabbitmq.WorkProvider;
import com.good.word.redis.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {

    @Autowired
    TestController testController;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
//        testController.set();
//        redisUtil.set("capture", "随机码", 60);
//        redisUtil.expire("capture", 10);

        UserModel userModel = new UserModel("1", "王显磊", 30, DateUtil.parseDate("1989-04-08"));
//


        redisUtil.hset("user:1", "王显磊", userModel);

        userModel = (UserModel) redisUtil.hget("user:1", "王显磊");
        System.out.println(DateUtil.format(userModel.getBirth(), "yyyy-MM-dd"));


    }



    @Autowired
    private Product product;
    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            product.send("afs" + i);
        }
    }

    @Autowired
    private HelloProvider helloProvider;
    @Autowired
    private WorkProvider workProvider;

    @Test
    public void hello() {
        for (int i = 0; i < 10; i++) {
            helloProvider.send("Hello rabbitmq！" + i);
        }
    }

    @Test
    public void work() {
        for (int i = 0; i < 10; i++) {
            workProvider.send("work queue rabbitmq!" + i);
        }
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void fanoutProduct() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("广播交换机", "", "Fanout Message" + i);
        }
    }

    private final String[] logLevel = {"debug", "info", "warning", "error"};
    @Test
    public void routingKeyDirect() {
        for (int i = 0; i < 20; i++) {
            String routingKey = logLevel[i % logLevel.length];
            rabbitTemplate.convertAndSend("Direct交换机", routingKey, "Direct Message " + i + " : " + routingKey );
        }
    }


    @Test
    public void topic() {
        for (int i = 0; i < 10; i++) {
            String routingKey = i % 2 == 0 ? "user.save" : "role.save";
            rabbitTemplate.convertAndSend("Topic交换机", routingKey, "Topic Message : " + routingKey);
        }
    }


}
