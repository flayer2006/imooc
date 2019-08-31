package com.yongh.mooc.all.seckill.controller;

import com.yongh.mooc.all.seckill.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述: only a redis test,unused at this project!
 * 测试: http://localhost:8095/redis/getRedis
 * 返回: 这是一条测试数据 可视化工具：RedisDesktopManager
 * @author soitis
 * @create 2019-08-30 16:09
 */
@Controller
@RequestMapping("/redis")
@Slf4j
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "getRedis",method = RequestMethod.GET)
    @ResponseBody
    public String getRedis(){
        redisUtil.set ("20182019","这是一条测试数据");
        //为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
        Long resExpire = redisUtil.expire ("20182019",60,1);
        log.info ("resExpire= "+resExpire);
        //开启redis服务之后测试 windows-cmd: redis-server.exe redis.windows.conf
        //redis需要设置密码，否则连接池问题
        //redis-cli.exe/auth password/config set requirepass password
        String res = redisUtil.get ("20182019");
        return res;
    }
}