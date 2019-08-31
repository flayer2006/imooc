package com.yongh.mooc.all.seckill.mapper;

import com.yongh.mooc.all.seckill.entity.Seckill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface SeckillMapper {

    List<Seckill> selectAllByParams(@Param("offset") Integer offset, @Param ("limit") Integer limit);

    Seckill selectByPrimaryKey(@Param("seckillId") Long seckillId);

    int reduceNumber(@Param("seckillId") Long seckillId, @Param("killTime") Date killTime);

    void seckillViaStoreProcedure(Map<String,Object> paramMap);
}