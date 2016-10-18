package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * Created by pengfei on 2016/10/16.
 */
@SuppressWarnings("unused")
public interface SeckillDao {

    int reduceNumber(@Param("seckillId") long seckillId ,  @Param("killTime")Date killTime) ;

    Seckill   queryById(long  seckilled) ;

    List<Seckill> queryAll(@Param("offset") int offset , @Param("limit") int limit) ;
}
