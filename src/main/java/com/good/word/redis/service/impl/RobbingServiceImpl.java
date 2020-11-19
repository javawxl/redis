package com.good.word.redis.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.good.word.redis.entity.ProductModel;
import com.good.word.redis.entity.RobbingDetailModel;
import com.good.word.redis.mapper.IProductMapper;
import com.good.word.redis.mapper.IRobbingMapper;
import com.good.word.redis.service.IRobbingService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class RobbingServiceImpl extends ServiceImpl<IRobbingMapper, RobbingDetailModel> implements IRobbingService {

    @Autowired
    IProductMapper productMapper;
    @Autowired
    RedisTemplate redisTemplate;
    private final Object lock = new Object();
    private final static String PRODUCT_LOCK_PREFIX = "product-";
    private final static String LOCKED = "1";
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    ConcurrentServiceImpl concurrentService;

    @Override
    public void rob1(ProductModel product, String phone) {
        Map<String, Object> map = new HashMap<>();
        map.put("product", product);
        map.put("phone", phone);
        rabbitTemplate.convertSendAndReceive("Robbing队列", map);
    }

    /*
      Transactional+synchronized锁方法或者是代码块都不能保证数据都一致性
      多线程环境读到的是还没提交事务的数据因为Spring Commit是在方法执行完毕后进行的
      所以synchronized应该锁定整个事务
     */
    @Override
    @Transactional
    public void rob2(ProductModel product, String phone) {
        synchronized(lock) {
            ProductModel productModel = productMapper.selectById(product.getProductId());
            if (productModel != null && productModel.getStoreQuantity() > 0) {
                //更新库存
                productModel.setStoreQuantity(productModel.getStoreQuantity() - 1);
                productModel.setSellQuantity(productModel.getSellQuantity() + 1);
                int i = productMapper.updateById(productModel);
                if (i > 0) {
                    //记录购买明细
                    RobbingDetailModel rdm = new RobbingDetailModel();
                    rdm.setId(IdUtil.simpleUUID());
                    rdm.setProductId(product.getProductId());
                    rdm.setRobbingTime(DateUtil.date());
                    rdm.setPhone(phone);
                    this.baseMapper.insert(rdm);
                }
            }
        }
    }

    @Override
    public synchronized void rob3(ProductModel product, String phone) {
        //这种情况方法doRob3的事务不会起作用,同一个类的A方法(无事务)调用B方法，B方法的事务不会生效
//        doRob3(product, phone);
        concurrentService.doRob(product, phone);
    }

    @Override
    @Transactional
    public boolean robWithPessimisticLock(ProductModel product, String phone) {
        ProductModel productModel = productMapper.selectByIdWithPessimisticLock(product.getProductId());
        if (productModel != null && productModel.getStoreQuantity() > 0) {
            //更新库存
            productModel.setStoreQuantity(productModel.getStoreQuantity() - 1);
            productModel.setSellQuantity(productModel.getSellQuantity() + 1);
            int i = productMapper.updateById(productModel);
            if (i > 0) {
                //记录购买明细
                RobbingDetailModel rdm = new RobbingDetailModel();
                rdm.setId(IdUtil.simpleUUID());
                rdm.setProductId(product.getProductId());
                rdm.setRobbingTime(DateUtil.date());
                rdm.setPhone(phone);
                this.baseMapper.insert(rdm);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean robWithOptimisticLock(ProductModel product, String phone) {
        ProductModel productModel = productMapper.selectById(product.getProductId());
        if (productModel != null && productModel.getStoreQuantity() > 0) {
            //更新库存
            int i = productMapper.updateByIdWithOptimisticLock(productModel);
            if (i > 0) {
                //记录购买明细
                RobbingDetailModel rdm = new RobbingDetailModel();
                rdm.setId(IdUtil.simpleUUID());
                rdm.setProductId(product.getProductId());
                rdm.setRobbingTime(DateUtil.date());
                rdm.setPhone(phone);
                this.baseMapper.insert(rdm);
                return true;
            }
            return false;
        }
        return true;
    }

    @Transactional
    public void doRob3(ProductModel product, String phone) {
        ProductModel productModel = productMapper.selectById(product.getProductId());
        if (productModel != null && productModel.getStoreQuantity() > 0) {
            //更新库存
            productModel.setStoreQuantity(productModel.getStoreQuantity() - 1);
            productModel.setSellQuantity(productModel.getSellQuantity() + 1);
            int i = productMapper.updateById(productModel);
            if (i > 0) {
                if (productModel.getStoreQuantity() == 30) {
                    int x =  1 / 0;
                }
                //记录购买明细
                RobbingDetailModel rdm = new RobbingDetailModel();
                rdm.setId(IdUtil.simpleUUID());
                rdm.setProductId(product.getProductId());
                rdm.setRobbingTime(DateUtil.date());
                rdm.setPhone(phone);
                this.baseMapper.insert(rdm);
            }
        }
    }
}
