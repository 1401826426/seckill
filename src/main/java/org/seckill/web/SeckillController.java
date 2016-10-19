package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by pengfei on 2016/10/17.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired  private SeckillService seckillService ;
    private Logger logger = LoggerFactory.getLogger(this.getClass()) ;

    @RequestMapping(value = "/list"  , method = RequestMethod.GET)
    public String listView(Model model){
        List<Seckill> seckillList = seckillService.getSeckillList() ;
        model.addAttribute("list" , seckillList) ;
        return "list" ;
    }

    @RequestMapping(value = "/{seckillId}/detail" , method =  RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId , Model model){
        if(seckillId == null){
            return "redirect:/seckill/list" ;
        }
        Seckill seckill = seckillService.getById(seckillId) ;
        if(seckill == null){
            return "forward:/seckill/list" ;
        }
        model.addAttribute("seckill" , seckill) ;
        return "detail" ;
    }

    @ResponseBody
    @RequestMapping(value = "/{seckillId}/exposer"  ,
            method = RequestMethod.GET ,
            produces = {"application/json;charset=UTF-8"})
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> result = null ;
        try{
            Exposer exposer = seckillService.exportSeckillUrl(seckillId) ;
            result = new SeckillResult<Exposer>(true , exposer) ;
        }catch (Exception e){
            logger.error(e.getMessage() , e);
            result = new SeckillResult<Exposer>(false , e.getMessage()) ;
        }
        return  result ;
    }


    //todo
    @RequestMapping(value = "/{seckillId}/{md5}/execution" ,
            method = RequestMethod.POST ,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId ,
                                                   @PathVariable("md5") String md5  ,
                                                   @CookieValue(value = "killPhone" ,required=false) Long killPhone){
        if(killPhone == null){
            return new SeckillResult<SeckillExecution>(false , "未注册" ) ;
        }
        SeckillResult<SeckillExecution> result ;
        try{
            SeckillExecution seckillExecution  = seckillService.executeSeckill(seckillId ,  killPhone , md5) ;
            result = new SeckillResult<SeckillExecution>(true , seckillExecution) ;
        }catch (SeckillClosedException e){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId , SeckillStateEnum.END) ;
            result = new SeckillResult<SeckillExecution>(true , seckillExecution) ;
        }catch (RepeatKillException e){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId , SeckillStateEnum.REPEAT_KILL) ;
            result = new SeckillResult<SeckillExecution>(true , seckillExecution) ;
        } catch (Exception e){
            logger.error(e.getMessage() , e);
            SeckillExecution seckillExecution = new SeckillExecution(seckillId , SeckillStateEnum.INNER_ERROR) ;
            result = new SeckillResult<SeckillExecution>(true , seckillExecution) ;
        }
        return result;
    }

    @RequestMapping(value =  "/time/now" , method = RequestMethod.GET ,
            produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public SeckillResult<Long> time(){
        Date date = new Date() ;
        return new SeckillResult<Long>(true , date.getTime()) ;
    }



}
































