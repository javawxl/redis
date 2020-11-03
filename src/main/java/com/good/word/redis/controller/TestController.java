package com.good.word.redis.controller;

import com.good.word.redis.service.IUserService;
import com.good.word.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
public class TestController {

    private final RedisUtil redisUtil;
    private final RedisTemplate redisTemplate;
    private final IUserService iUserService;

    public TestController(RedisUtil redisUtil, RedisTemplate redisTemplate, IUserService iUserService) {
        this.redisUtil = redisUtil;
        this.redisTemplate = redisTemplate;
        this.iUserService = iUserService;
    }


    @ResponseBody
    public Object set() {

        List<Object> strings = new ArrayList<>();
        strings.add("哈哈哈");
        strings.add("嘤嘤嘤");
        strings.add("嘎嘎嘎");

        redisUtil.lSetList("666", strings);

        get();

        return "set success~";
    }

    @GetMapping("/get")
    @ResponseBody
    public Object get() {

        System.out.println("list size: " + redisUtil.lGetListSize("666"));
        System.out.println(redisUtil.lGet("666", 0, -1));

        return "get success";
    }

    @GetMapping("/del")
    @ResponseBody
    public Object del() {

        redisUtil.lRemove("666", 0, "哈哈哈");
        get();

        return "delete success";
    }


    @GetMapping("/order/prepare")
    public void prepareOrder() {
        String key = "orderId";
        for (int i = 0; i < 20; i++) {
            String value = "00000000" + (i+1);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, new Random().nextInt(5) + 1);
            long score = calendar.getTimeInMillis() / 1000;
            redisTemplate.opsForZSet().add(key, value, score);
        }
    }


    @GetMapping("/order/consumer")
    public void consumerOrder() {
        while (true) {
            Set<ZSetOperations.TypedTuple<String>> orderSet = redisTemplate.opsForZSet().rangeWithScores("orderId", 0, 0);
            if (orderSet == null || orderSet.isEmpty()) {
                TimeUnit.MICROSECONDS.toSeconds(500);
                continue;
            }
            ZSetOperations.TypedTuple<String> typedTuple = (ZSetOperations.TypedTuple<String>) orderSet.toArray()[0];
            double score = typedTuple.getScore();
            Calendar calendar = Calendar.getInstance();
            if ((calendar.getTimeInMillis() / 1000) >= score) {
                String element = typedTuple.getValue();
                Long orderId = redisTemplate.opsForZSet().remove("orderId", element);
                if (orderId != null && orderId > 0) {
                    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                      + ":redis消费了一个任务：消费的订单OrderId为" + element);
                }
            }
        }
    }

}
