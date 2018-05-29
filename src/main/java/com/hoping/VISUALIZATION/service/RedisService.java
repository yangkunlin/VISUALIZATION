package com.hoping.VISUALIZATION.service;

/**
 * Author:  yangkunlin
 * Date:    2018/5/26
 * Domain:  pla.hc10
 */
public interface RedisService<T> {

    /**
     * ***************************获取每天的相关数据统计结果***************************************
     * @param type
     * @param dateStr
     * @return
     */
    T getResult(String type, String dateStr) throws Exception;

}
