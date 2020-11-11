package com.good.word.redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.good.word.redis.entity.ApsprolinePlanSeq;
import com.good.word.redis.entity.UserModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wangxianlei
 * @since 1.0
 */
@Mapper
public interface IUserMapper extends BaseMapper<UserModel> {
    void updateAgeById(UserModel userModel);
    List<ApsprolinePlanSeq> list();
    List<UserModel> findByAgeGt(int age);
}
