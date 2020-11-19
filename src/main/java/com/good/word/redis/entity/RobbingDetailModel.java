package com.good.word.redis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("robbing_detail")
public class RobbingDetailModel {
    @TableId
    private String id;
    private String productId;
    private Date robbingTime;
    private String phone;
}
