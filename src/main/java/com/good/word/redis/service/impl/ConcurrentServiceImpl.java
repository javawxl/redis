package com.good.word.redis.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.good.word.redis.entity.ProductModel;
import com.good.word.redis.entity.RobbingDetailModel;
import com.good.word.redis.mapper.IProductMapper;
import com.good.word.redis.mapper.IRobbingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConcurrentServiceImpl extends ServiceImpl<IRobbingMapper, RobbingDetailModel> {

    @Autowired
    IProductMapper productMapper;

    @Transactional
    public boolean doRob(ProductModel product, String phone) {
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
                return true;
            }
            return false;
        }
        return false;
    }
}
