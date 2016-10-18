package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * Created by pengfei on 2016/10/16.
 */
@SuppressWarnings("unused")
public interface SuccessKilledDao  {


    int insertSuccessKilled(@Param("seckillId") long seckillId  , @Param("userPhone")long userPhone) ;


    SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId , @Param("userPhone") long userPhone) ;

}
























































