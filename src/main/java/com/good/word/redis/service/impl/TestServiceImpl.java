package com.good.word.redis.service.impl;

import com.good.word.redis.service.ITestService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.Future;

@Service
public class TestServiceImpl implements ITestService {
    @Async
    @Override
    public void asyncMethodWithNoReturnType() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("asyncMethodWithNoReturnType...");
    }

    @Async
    @Override
    public Future<String> asyncMethodWithReturnType() {
        try {
            Thread.sleep(3000);
            return new AsyncResult<String>("success");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("asyncMethodWithReturnType...");
        return null;
    }
}
