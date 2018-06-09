package com.hoping.VISUALIZATION.service.impl;

import com.hoping.VISUALIZATION.common.RequestParams;
import com.hoping.VISUALIZATION.common.StaticParams;
import com.hoping.VISUALIZATION.entity.RedisResultModel;
import com.hoping.VISUALIZATION.service.RedisService;
import com.hoping.VISUALIZATION.utils.redis.JedisUtil;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Author:  yangkunlin
 * Date:    2018/5/29
 * Domain:  pla.hc10
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Override
    public Object getResult(String type, String dateStr) throws Exception {

        RedisResultModel<String, String> redisResultModelValueIsString = new RedisResultModel<>();
        RedisResultModel<String, Map<String, String>> redisResultModelValueIsMap = new RedisResultModel<>();

        Set<String> stringSet = new HashSet<>();
        Set<String> mapSet = new HashSet<>();

        stringSet.add(StaticParams.REDISONLINEKEY);
        stringSet.add(StaticParams.REDISLOGINEDONLINEKEY);
        stringSet.add(StaticParams.REDISAGAINONLINEKEY);
        stringSet.add(StaticParams.REDISAGAINLOGINEDONLINEKEY);
        stringSet.add(StaticParams.REDISNEWACTIVATIONKEY);
        stringSet.add(StaticParams.REDISNEWUSERKEY);
        stringSet.add(StaticParams.REDISNEWIOSKEY);
        stringSet.add(StaticParams.REDISCLICKTOTALKEY);
        stringSet.add(StaticParams.REDISCLICKUIDKEY);
        stringSet.add(StaticParams.REDISUIDKEY);
        stringSet.add(StaticParams.REDISCOUNTDEVKEY);
        stringSet.add(StaticParams.REDISLEFTNEXTKEY);

        mapSet.add(StaticParams.REDISAREAKEY);
        mapSet.add(StaticParams.REDISLOGINEDAREAKEY);
        mapSet.add(StaticParams.REDISPATHKEY);
        mapSet.add(StaticParams.REDISLOGINEDPATHKEY);
        mapSet.add(StaticParams.REDISOSKEY);
        mapSet.add(StaticParams.REDISLOGINEDOSKEY);
        mapSet.add(StaticParams.REDISMODELKEY);
        mapSet.add(StaticParams.REDISLOGINEDMODELKEY);
        mapSet.add(StaticParams.REDISCHANNELKEY);
        mapSet.add(StaticParams.REDISLOGINEDCHANNELKEY);
        mapSet.add(StaticParams.REDISSEARCHKEY);
        mapSet.add(StaticParams.REDISACTKEY);
        mapSet.add(StaticParams.REDISCLICKHOURKEY);
        mapSet.add(StaticParams.REDISSTAYKEY);


        if (stringSet.contains(type)) {
            setRedisResultModelByString(dateStr, type, redisResultModelValueIsString);
            return redisResultModelValueIsString;
        } else if (mapSet.contains(type)) {
            setRedisResultModelByMap(dateStr, type, redisResultModelValueIsMap);
            return redisResultModelValueIsMap;
        } else {
            return RequestParams.ERRORSTR;
        }
    }

    private void setRedisResultModelByMap(String dateStr, String key, RedisResultModel<String, Map<String, String>> redisResultModel) {
        redisResultModel.setKey(key + "_" + dateStr);
        if (JedisUtil.getJedis().exists(key + "_" + dateStr)) {
            redisResultModel.setValue(JedisUtil.getJedis().hgetAll(key + "_" + dateStr));
        } else {
            redisResultModel.setValue(null);
        }
    }

    private void setRedisResultModelByString(String dateStr, String key, RedisResultModel<String, String> redisResultModel) {
        redisResultModel.setKey(key + "_" + dateStr);
        if (JedisUtil.getJedis().exists(key + "_" + dateStr)) {
            redisResultModel.setValue(JedisUtil.getJedis().get(key + "_" + dateStr));
        } else {
            redisResultModel.setValue(null);
        }
    }
}
