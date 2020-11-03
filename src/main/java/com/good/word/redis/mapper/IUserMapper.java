package com.good.word.redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.good.word.redis.entity.UserModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wangxianlei
 * @since 1.0
 */
@Mapper
public interface IUserMapper extends BaseMapper<UserModel> {
    void updateAgeById(UserModel userModel);
}
