package com.good.word.redis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.good.word.redis.entity.UserModel;

/**
 * @author wangxianlei
 * @since 1.0
 */
public interface IUserService extends IService<UserModel> {
    void updateUserAge(UserModel userModel, long timeout) throws InterruptedException;
}
