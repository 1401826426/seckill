package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by pengfei on 2016/10/17.
 */
@Service
public class SeckillServiceImpl  implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass()) ;

    @Autowired  private SeckillDao seckillDao ;
    @Autowired  private SuccessKilledDao successKilledDao ;

    private final String slat = "sdhfdfdvfhhb ghscadwqienjwveyqwo" ;


    private String getMD5(long seckillId){
        String base = seckillId + "/" +slat ;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes()) ;
        return md5 ;
    }



    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0 , 4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId) ;
        if(seckill == null){
            return new Exposer(false , seckillId) ;
        }
        Date  startTime = seckill.getStartTime()  ;
        Date endTime = seckill.getEndTime() ;
        Date now = new Date() ;
        if(now.getTime()  < startTime.getTime()  ||  now.getTime() > endTime.getTime()){
            return new Exposer(false , now.getTime() , startTime.getTime() , endTime.getTime()) ;
        }
        String md5 = getMD5(seckillId);
        return new Exposer(true   , md5 , seckillId);
    }


    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillClosedException {
        try {
			if (md5 == null || !md5.equals(getMD5(seckillId))) {
				throw new SeckillException("seckill data rewrite");
			}
			Date now = new Date();
			int updateNumber = seckillDao.reduceNumber(seckillId, now);
			if (updateNumber <= 0) {
				throw new SeckillClosedException("seckill is closed");
			} else {
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				if (insertCount == 0) {
					throw new RepeatKillException("seckill repeated");
				} else {
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
				}
			} 
		}catch (RepeatKillException e){
		    throw e ;
        }catch(SeckillClosedException e){
            throw e ;
        } catch (Exception e) {
			logger.error(e.getMessage());
            //所有编译期异常  装换为运行期异常
			throw new SeckillException("seckill inner error" + e.getMessage()) ; 
		}
    }
}


























