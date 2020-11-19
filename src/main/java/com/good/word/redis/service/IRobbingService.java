package com.good.word.redis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.good.word.redis.entity.ProductModel;
import com.good.word.redis.entity.RobbingDetailModel;

public interface IRobbingService extends IService<RobbingDetailModel> {
    void rob1(ProductModel product, String phone);
    void rob2(ProductModel product, String phone);
    void rob3(ProductModel product, String phone);
    boolean robWithPessimisticLock(ProductModel product, String phone);
    boolean robWithOptimisticLock(ProductModel product, String phone);
}
