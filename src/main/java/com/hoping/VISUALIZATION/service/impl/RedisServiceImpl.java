package com.hoping.VISUALIZATION.service.impl;

import com.hoping.VISUALIZATION.utils.redis.JedisUtil;
import com.hoping.VISUALIZATION.common.RequestParams;
import com.hoping.VISUALIZATION.common.StaticParams;
import com.hoping.VISUALIZATION.entity.RedisResultModel;
import com.hoping.VISUALIZATION.service.RedisService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Author:  yangkunlin
 * Date:    2018/5/29
 * Domain:  pla.hc10
 */
@Service
public class RedisServiceImpl implements RedisService{

    @Override
    public Object getResult(String type, String dateStr) throws Exception {

        String key;

        if (type.equals(StaticParams.REDISONLINEKEY)) {
            key = StaticParams.REDISONLINEKEY;
            RedisResultModel<String, String> redisResultModel = new RedisResultModel<>();
            setRedisResultModelByString(dateStr, key, redisResultModel);
            return redisResultModel;
        }

        if (type.equals(StaticParams.REDISLOGINEDONLINEKEY)) {
            RedisResultModel<String, String> redisResultModel = new RedisResultModel<>();
            setRedisResultModelByString(dateStr, StaticParams.REDISLOGINEDONLINEKEY, redisResultModel);
            return redisResultModel;
        }

        if (type.equals(StaticParams.REDISAGAINONLINEKEY)) {
            RedisResultModel<String, String> redisResultModel = new RedisResultModel<>();
            setRedisResultModelByString(dateStr, StaticParams.REDISAGAINONLINEKEY, redisResultModel);
            return redisResultModel;
        }

        if (type.equals(StaticParams.REDISAGAINLOGINEDONLINEKEY)) {
            RedisResultModel<String, String> redisResultModel = new RedisResultModel<>();
            setRedisResultModelByString(dateStr, StaticParams.REDISAGAINONLINEKEY, redisResultModel);
            return redisResultModel;
        }

        if (type.equals(StaticParams.REDISAREAKEY)) {
            RedisResultModel<String, Map<String, String>> redisResultModel = new RedisResultModel<>();
            key = StaticParams.REDISAREAKEY;
            setRedisResultModelByMap(dateStr, key, redisResultModel);
            return redisResultModel;
        }

        if (type.equals(StaticParams.REDISLOGINEDAREAKEY)) {
            RedisResultModel<String, Map<String, String>> redisResultModel = new RedisResultModel<>();
            key = StaticParams.REDISLOGINEDAREAKEY;
            setRedisResultModelByMap(dateStr, key, redisResultModel);
            return redisResultModel;
        }

        if (type.equals(StaticParams.REDISPATHKEY)) {
            RedisResultModel<String, Map<String, String>> redisResultModel = new RedisResultModel<>();
            key = StaticParams.REDISPATHKEY;
            setRedisResultModelByMap(dateStr, key, redisResultModel);
            return redisResultModel;
        }

        if (type.equals(StaticParams.REDISLOGINEDPATHKEY)) {
            RedisResultModel<String, Map<String, String>> redisResultModel = new RedisResultModel<>();
            key = StaticParams.REDISLOGINEDPATHKEY;
            setRedisResultModelByMap(dateStr, key, redisResultModel);
            return redisResultModel;
        }
        if (type.equals(StaticParams.REDISOSKEY)) {
            RedisResultModel<String, Map<String, String>> redisResultModel = new RedisResultModel<>();
            key = StaticParams.REDISOSKEY;
            setRedisResultModelByMap(dateStr, key, redisResultModel);
            return redisResultModel;
        }
        if (type.equals(StaticParams.REDISLOGINEDOSKEY)) {
            RedisResultModel<String, Map<String, String>> redisResultModel = new RedisResultModel<>();
            key = StaticParams.REDISLOGINEDOSKEY;
            setRedisResultModelByMap(dateStr, key, redisResultModel);
            return redisResultModel;
        }
        if (type.equals(StaticParams.REDISMODELKEY)) {
            RedisResultModel<String, Map<String, String>> redisResultModel = new RedisResultModel<>();
            key = StaticParams.REDISMODELKEY;
            setRedisResultModelByMap(dateStr, key, redisResultModel);
            return redisResultModel;
        }
        if (type.equals(StaticParams.REDISLOGINEDMODELKEY)) {
            RedisResultModel<String, Map<String, String>> redisResultModel = new RedisResultModel<>();
            key = StaticParams.REDISLOGINEDMODELKEY;
            setRedisResultModelByMap(dateStr, key, redisResultModel);
            return redisResultModel;
        }
        if (type.equals(StaticParams.REDISCHANNELKEY)) {
            RedisResultModel<String, Map<String, String>> redisResultModel = new RedisResultModel<>();
            key = StaticParams.REDISCHANNELKEY;
            setRedisResultModelByMap(dateStr, key, redisResultModel);
            return redisResultModel;
        }
        if (type.equals(StaticParams.REDISLOGINEDCHANNELKEY)) {
            RedisResultModel<String, Map<String, String>> redisResultModel = new RedisResultModel<>();
            key = StaticParams.REDISLOGINEDCHANNELKEY;
            setRedisResultModelByMap(dateStr, key, redisResultModel);
            return redisResultModel;
        }
        if (type.equals(StaticParams.REDISSEARCHKEY)) {
            RedisResultModel<String, Map<String, String>> redisResultModel = new RedisResultModel<>();
            key = StaticParams.REDISSEARCHKEY;
            setRedisResultModelByMap(dateStr, key, redisResultModel);
            return redisResultModel;
        }

        return RequestParams.ERRORSTR;
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
