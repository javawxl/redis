package com.good.word.redis.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {
    private String id;
    private String tenantId;
    private Integer dr;
    private Date ts;
}
