package com.hoping.VISUALIZATION.service.impl;

import com.hoping.VISUALIZATION.common.JedisUtil;
import com.hoping.VISUALIZATION.common.RequestParams;
import com.hoping.VISUALIZATION.common.StaticParams;
import com.hoping.VISUALIZATION.entity.RedisResultModel;
import com.hoping.VISUALIZATION.service.RedisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Author:  yangkunlin
 * Date:    2018/5/29
 * Domain:  pla.hc10
 */
@Service
public class RedisServiceImpl implements RedisService{

    // 声明静态配置
    private static Logger logger = LogManager.getRootLogger();

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
