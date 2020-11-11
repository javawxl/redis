package com.good.word.redis.service;

import java.util.concurrent.Future;

public interface ITestService {
    void asyncMethodWithNoReturnType();
    Future<String> asyncMethodWithReturnType();
}
