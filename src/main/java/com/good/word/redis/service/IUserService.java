package com.good.word.redis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.good.word.redis.entity.UserModel;

import java.util.List;
import java.util.Map;

/**
 * @author wangxianlei
 * @since 1.0
 */
public interface IUserService extends IService<UserModel> {
    void updateUserAge(UserModel userModel, long timeout) throws InterruptedException;
    List<UserModel> findByAgeGt(int age);
    List<UserModel> findByCache1(int age);
    List<UserModel> findByCache2(int age);
    Map<String, String> findMapResult(UserModel userModel, int age);
    Map<String, UserModel> mapKey();
}
