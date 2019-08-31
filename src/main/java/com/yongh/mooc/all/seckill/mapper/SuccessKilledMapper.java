package com.yongh.mooc.all.seckill.mapper;

import com.yongh.mooc.all.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SuccessKilledMapper {

    //根据商品id和用户手机号查询秒杀信息
    SuccessKilled queryByIdAndPhoneWithSeckill(@Param("seckillId") Long seckillId, @Param("userPhone") Long userPhone);
    //新增秒杀信息
    int insertSuccessKilled(@Param ("seckillId") long seckillId, @Param("userPhone") long userPhone);

}