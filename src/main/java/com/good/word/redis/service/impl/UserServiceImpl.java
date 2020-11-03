package com.good.word.redis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.good.word.redis.entity.UserModel;
import com.good.word.redis.mapper.IUserMapper;
import com.good.word.redis.service.IUserService;
import com.good.word.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author wangxianlei
 * @since 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper, UserModel> implements IUserService {

    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void updateUserAge(UserModel userModel, long timeout) throws InterruptedException {
        userMapper.updateAgeById(userModel);
        TimeUnit.SECONDS.sleep(timeout);
        redisUtil.hset("user:" + userModel.getId(), userModel.getName(), userModel);
    }

}
