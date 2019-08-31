package com.yongh.mooc.all.seckill.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.yongh.mooc.all.seckill.core.BaseServiceImpl;
import com.yongh.mooc.all.seckill.dto.Exposer;
import com.yongh.mooc.all.seckill.dto.SeckillExecution;
import com.yongh.mooc.all.seckill.entity.Seckill;
import com.yongh.mooc.all.seckill.entity.SuccessKilled;
import com.yongh.mooc.all.seckill.enums.SeckillStateEnum;
import com.yongh.mooc.all.seckill.exception.RepeatKillException;
import com.yongh.mooc.all.seckill.exception.SeckillCloseException;
import com.yongh.mooc.all.seckill.exception.SeckillException;
import com.yongh.mooc.all.seckill.service.SeckillService;
import com.yongh.mooc.all.seckill.utils.RedisUtil;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @author soitis
 * @create 2019-08-29 9:08
 */
@Service
public class SeckillServiceImp extends BaseServiceImpl implements SeckillService{

    private static final Logger log = LoggerFactory.getLogger (SeckillServiceImp.class);

    @Value ("${SLAT}")
    private String slat;

    @Autowired
    private  RedisUtil redisUtil;

    public List<Seckill> getList() {
        return seckillMapper.selectAllByParams (0,4);
    }

    public Seckill getById(Long seckillId) {
        return seckillMapper.selectByPrimaryKey (seckillId);
    }

    public Exposer exportSeckillUrlUseRedis(Long seckillId) {

        Seckill seckill = redisUtil.getSeckill (seckillId);
        if(seckill == null){
            seckill = seckillMapper.selectByPrimaryKey (seckillId);
            if(seckill != null){
                redisUtil.putSeckill (seckill);
            }else {
                return new Exposer (false,seckillId);
            }
        }

        Date startTime = seckill.getStartTime ();
        Date endTime = seckill.getEndTime ();

        String md5 = getMd5 (seckillId);
        //系统当前时间
        Date nowTime = new Date ();
        if(nowTime.getTime ()<startTime.getTime ()|| nowTime.getTime ()>endTime.getTime ()){
            return new Exposer (false,seckillId,nowTime.getTime (),startTime.getTime (),endTime.getTime ());
        }

        return new Exposer (true,md5,seckillId);
    }


    public Exposer exportSeckillUrl(Long seckillId) {

        Seckill seckill = seckillMapper.selectByPrimaryKey (seckillId);
        if(seckill == null){
            return new Exposer (false,seckillId);
        }

        long start = seckill.getStartTime ().getTime ();
        long end = seckill.getEndTime ().getTime ();
        // 系统当前时间
//        Date nowTime = new Date ();
        // 判断当前系统时间是否在秒杀期间
        long now = new Date ().getTime ();
        if(now < start || now > end){
            return new Exposer (false,seckillId,start,end,now);
        }

        // 转换特定字符串的过程，不可逆
        String md5 = getMd5 (seckillId);
        return new Exposer (true,md5,seckillId);
    }

    @Transactional
    public SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5)
            throws RepeatKillException,SeckillCloseException,SeckillException {
        if(StrUtil.isBlank (md5) || !md5.equals (getMd5 (seckillId))){
            return new SeckillExecution (seckillId, SeckillStateEnum.DATA_REWRITE);
        }

        //执行秒杀逻辑：减库存 + 记录购买行为
        Date nowTime = new Date ();
        try {
            int updateCount = seckillMapper.reduceNumber (seckillId,nowTime);
            if(updateCount <= 0){
                //没有更新到记录，秒杀结束
//                return new SeckillExecution (seckillId,SeckillStateEnum.END);
                throw new SeckillCloseException ("seckill is closed");
            }else {
                // 记录用户购买行为，唯一：seckillId，userPhone
                int insertCount = successKilledMapper.insertSuccessKilled (seckillId,userPhone);
                //重复秒杀
                if(insertCount <= 0 ){
//                    return new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
                    throw new RepeatKillException ("seckill repeated");
                }

                SuccessKilled successKilled = successKilledMapper.queryByIdAndPhoneWithSeckill (seckillId,userPhone);
                return new SeckillExecution (seckillId,SeckillStateEnum.SUCCESS,successKilled);
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            log.error (e.getMessage(), e);
            //所有编译器异常转化为运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }

    @Override
    public SeckillExecution executeSeckillProcedur(Long seckillId, Long userPhone, String md5) {
        if(StrUtil.isBlank (md5) || !md5.equals (getMd5 (seckillId))){
            return new SeckillExecution (seckillId, SeckillStateEnum.DATA_REWRITE);
        }

        Date killTime = new Date ();
        Map<String,Object> map = new HashMap<> ();
        map.put ("seckillId",seckillId);
        map.put ("phone",userPhone);
        map.put ("killTime",killTime);
        map.put ("result",null);

        //执行秒杀逻辑：减库存 + 记录购买行为
        try {
            seckillMapper.seckillViaStoreProcedure (map);
            int result = MapUtils.getInteger (map,"result",-2);
            System.out.println (result);

            if(result == 1){
                SuccessKilled successKilled = successKilledMapper.queryByIdAndPhoneWithSeckill (seckillId, userPhone);
                return new SeckillExecution (seckillId,SeckillStateEnum.SUCCESS,successKilled);
            }else {
                return new SeckillExecution (seckillId,SeckillStateEnum.stateOf(result));
            }
        } catch (Exception e) {
            log.error (e.getMessage(), e);
            return new SeckillExecution (seckillId,SeckillStateEnum.INNER_ERROR);
        }
    }

    private String getMd5(Long seckillId){

        String base = seckillId + "/" + slat;
        String md5 = SecureUtil.md5(base);
        String md5_2 = DigestUtils.md5DigestAsHex (base.getBytes ());
        System.out.println ("md5:{},MD5:{}"+md5+md5_2);
        return md5;
    }

}