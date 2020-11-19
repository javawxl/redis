package com.good.word.redis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.good.word.redis.entity.ProductModel;
import com.good.word.redis.mapper.IProductMapper;
import com.good.word.redis.service.IProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<IProductMapper, ProductModel> implements IProductService {
}
