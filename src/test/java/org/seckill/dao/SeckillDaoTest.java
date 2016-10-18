package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by pengfei on 2016/10/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SeckillDaoTest {

    @Resource  private SeckillDao seckillDao ;



    @Test
    public void queryById() throws Exception {
        long id = 1000 ;
        Seckill seckill = seckillDao.queryById(id) ;
        System.out.println(seckill) ;
    }

    @Test
    public void reduceNumber() throws Exception {
        seckillDao.reduceNumber(1000L , new Date()) ;
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> seckillList =  seckillDao.queryAll(0 , 100) ;
        for(Seckill seckill:seckillList){
            System.out.println(seckill);
        }
    }



}

















