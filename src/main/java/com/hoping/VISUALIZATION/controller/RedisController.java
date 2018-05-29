package com.hoping.VISUALIZATION.controller;

import com.alibaba.fastjson.JSONObject;
import com.hoping.VISUALIZATION.common.RequestParams;
import com.hoping.VISUALIZATION.service.RedisService;
import com.hoping.VISUALIZATION.service.impl.RedisServiceImpl;
import org.springframework.web.bind.annotation.*;

/**
 * Author:  yangkunlin
 * Date:    2018/5/29
 * Domain:  pla.hc10
 */
@RestController
public class RedisController {

    public RedisController() {
    }

    @RequestMapping(value = "/Redis/Result", method = RequestMethod.POST)
    public String getResult(@RequestBody String body) throws Exception {

        RedisService redisService = new RedisServiceImpl();

        JSONObject jsonObject = JSONObject.parseObject(body);

        if (jsonObject.containsKey(RequestParams.TYPE) && jsonObject.containsKey(RequestParams.TIME)) {
            //System.out.println(jsonObject.getString(RequestParams.TYPE)+ "   " +jsonObject.getString(RequestParams.DAILY));
            return JSONObject.toJSONString(redisService.getResult(jsonObject.getString(RequestParams.TYPE), jsonObject.getString(RequestParams.TIME)));
        }

        return RequestParams.ERRORSTR;
    }
}
