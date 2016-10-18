package org.seckill.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClosedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by pengfei on 2016/10/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml" , "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {


    private Logger logger = LoggerFactory.getLogger(this.getClass()) ;

    @Autowired private SeckillService seckillService ;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckillServices = seckillService.getSeckillList() ;
        logger.info("list{}"  , seckillServices) ;
    }

    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillService.getById(1000L) ;
        logger.info("seckill={}" , seckill) ;
    }

    @Test
    public void testSeckillLogic(){
        Long id = 1000L ;
        Exposer exposer = seckillService.exportSeckillUrl(id) ;
        if(exposer.isExposed()){
            try {
                logger.info("exposer{}" , exposer);
				String md5 = exposer.getMd5();
				long userPhone = 12345678902L;
				SeckillExecution seckillExecution = seckillService.executeSeckill(id, userPhone, md5);
				logger.info("SeckillExecution{}", seckillExecution);
			} catch (RepeatKillException e) {
				logger.error(e.getMessage());
			}catch(SeckillClosedException e){
			    logger.error(e.getMessage());
            }
        }else{
            logger.warn("Exposer{}" , exposer);
        }

    }
}