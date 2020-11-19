package com.good.word.redis.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("product")
public class ProductModel implements Serializable {
    @TableId
    private String productId;
    private String productName;
    private int storeQuantity;
    private int sellQuantity;
}
