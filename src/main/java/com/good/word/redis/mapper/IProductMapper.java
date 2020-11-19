package com.good.word.redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.good.word.redis.entity.ProductModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IProductMapper extends BaseMapper<ProductModel> {
    ProductModel selectByIdWithPessimisticLock(String id);
    int updateByIdWithOptimisticLock(ProductModel productModel);
}
