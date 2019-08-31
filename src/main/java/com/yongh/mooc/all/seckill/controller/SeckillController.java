package com.yongh.mooc.all.seckill.controller;

import com.yongh.mooc.all.seckill.core.BaseController;
import com.yongh.mooc.all.seckill.dto.Exposer;
import com.yongh.mooc.all.seckill.dto.SeckillExecution;
import com.yongh.mooc.all.seckill.dto.SeckillResult;
import com.yongh.mooc.all.seckill.entity.Seckill;
import com.yongh.mooc.all.seckill.enums.SeckillStateEnum;
import com.yongh.mooc.all.seckill.exception.RepeatKillException;
import com.yongh.mooc.all.seckill.exception.SeckillCloseException;
import com.yongh.mooc.all.seckill.exception.SeckillException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 描述:
 *
 * @author soitis
 * @create 2019-08-30 17:42
 */

@Controller
@RequestMapping(value = "/seckill")
@Slf4j
public class SeckillController extends BaseController {

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> list = seckillService.getList ();
        model.addAttribute ("list",list);
        System.out.println ("秒杀列表已入购买仓库");
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){
        if(seckillId == null){
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById (seckillId);
        if(seckill == null){
            return "redirect:/seckill/list";
        }
        model.addAttribute ("seckill",seckill);
        return "detail";
    }

    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST)
    @ResponseBody
    public SeckillResult exposer(@PathVariable("seckillId") Long seckillId){
        SeckillResult result;

        try {
            Exposer exposer = seckillService.exportSeckillUrl (seckillId);
            result = new SeckillResult<> (true,exposer);
        } catch (Exception e) {
            log.error ("暴露秒杀地址错误",e.fillInStackTrace ());
            result = new SeckillResult<> (false,e.getMessage ());
        }

        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone", required = false) Long userPhone){
        SeckillResult<SeckillExecution> result;
        System.out.println("执行秒杀" + md5);
        if(userPhone == null){
            return new SeckillResult<> (false,"未注册");
        }

        try {
            SeckillExecution execution = seckillService.executeSeckill (seckillId,userPhone,md5);
            return new SeckillResult<> (true,execution);
        }catch (RepeatKillException e){
            SeckillExecution execution = new SeckillExecution (seckillId, SeckillStateEnum.REPEAT_KILL);
            System.out.println ("sys:重复秒杀");
            return new SeckillResult<> (true,execution);
        }catch (SeckillCloseException e){
            SeckillExecution execution = new SeckillExecution (seckillId,SeckillStateEnum.END);
            System.out.println ("sys:秒杀已结束");
            return new SeckillResult<> (true,execution);
        }catch (SeckillException e){
            SeckillExecution execution = new SeckillExecution (seckillId,SeckillStateEnum.INNER_ERROR);
            System.out.println ("sys:内部错误");
            return new SeckillResult<> (true,execution);
        }
    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date ();
        System.out.println (new SeckillResult<> (true,now.getTime ()));
        return new SeckillResult<> (true,now.getTime ());
    }
}