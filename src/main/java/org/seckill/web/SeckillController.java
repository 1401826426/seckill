package org.seckill.web;

import org.seckill.entity.Seckill;
import org.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by pengfei on 2016/10/17.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired  private SeckillService seckillService ;

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
    @RequestMapping(value = "/{seckillId}/exposer"  , method = RequestMethod.GET)
    public void  exposer(Long seckillId){

    }



}
































