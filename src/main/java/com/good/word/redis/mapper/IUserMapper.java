package com.good.word.redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.good.word.redis.entity.ApsprolinePlanSeq;
import com.good.word.redis.entity.UserModel;
import com.good.word.redis.interceptor.MappedColumn;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wangxianlei
 * @since 1.0
 */
@Mapper
public interface IUserMapper extends BaseMapper<UserModel> {
    void updateAgeById(UserModel userModel);
    List<ApsprolinePlanSeq> list();
    List<UserModel> findByAgeGt(int age);
    @MappedColumn(key = "id", value = "name")
    Map<String, String> findMapResult(@Param("userModel") UserModel userModel, @Param("age") int age);
    @MapKey("id")
    Map<String, UserModel> mapKey();
}
