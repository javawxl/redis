package com.good.word.redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.good.word.redis.entity.RobbingDetailModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IRobbingMapper extends BaseMapper<RobbingDetailModel> {
}
