package com.good.word.redis.controller;

import com.good.word.redis.common.entity.R;
import com.good.word.redis.entity.ProductModel;
import com.good.word.redis.service.IRobbingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/robbing")
@Slf4j
public class RobbingController {

    private final IRobbingService robbingService;
    private final Object lock = new Object();

    public RobbingController(IRobbingService robbingService) {
        this.robbingService = robbingService;
    }

    @GetMapping
    public R robbing() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 1000; i++) {
            ProductModel pm = new ProductModel();
            pm.setProductId("10001");
            int ii = i;
            new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String phone = "phone" + ii;
                robbingService.rob1(pm, phone);
//                robbingService.rob2(pm, phone);
//                robbingService.rob3(pm, phone);
//                robbingService.robWithPessimisticLock(pm, phone);
//                robbingService.robWithOptimisticLock(pm, phone);
            }, "Robbing-Thread-" + i).start();
        }
        countDownLatch.countDown();
        return R.ok();
    }

    @GetMapping("/a")
    public R robbing2() {
        ProductModel pm = new ProductModel();
        pm.setProductId("10001");
        String phone = "phone" + new Random().nextInt(1000) + 1;
        robbingService.rob1(pm, phone);
        return R.ok();
    }
}
