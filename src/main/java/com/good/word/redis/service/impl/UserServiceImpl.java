package com.good.word.redis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.good.word.redis.entity.UserModel;
import com.good.word.redis.mapper.IUserMapper;
import com.good.word.redis.service.IUserService;
import com.good.word.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
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

    @Override
    public List<UserModel> findByAgeGt(int age) {
        return userMapper.findByAgeGt(age);
    }

    /**
     * 为什么开启事务?
     * 由于使用了数据库连接池，默认每次查询完之后自动commit，这就导致两次查询使用的不是同一个sqlSession，根据一级缓存的原理，它将永远不会生效。
     * 当我们开启了事务，两次查询都在同一个sqlSession中，从而让第二次查询命中了一级缓存。读者可以自行关闭事务验证此结论。
     */
    @Transactional
    @Override
    public List<UserModel> findByCache1(int age) {
        //缓存命中顺序：二级缓存---> 一级缓存---> 数据库
        List<UserModel> list = userMapper.findByAgeGt(30);
        List<UserModel> list1 = userMapper.findByAgeGt(30);
        list.addAll(list1);
        return list;
    }

    @Override
    public List<UserModel> findByCache2(int age) {
        List<UserModel> list = userMapper.findByAgeGt(30);
//        List<UserModel> list1 = userMapper.findByAgeGt(30);
//        list.addAll(list1);
        userMapper.findByAgeGt(30);
        return list;
    }

}
