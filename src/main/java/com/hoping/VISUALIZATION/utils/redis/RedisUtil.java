package com.hoping.VISUALIZATION.utils.redis;

import com.hoping.VISUALIZATION.common.StaticParams;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * Description:
 *
 * @author YKL on 2018/5/23.
 * @version 1.0
 *          spark:梦想开始的地方
 */
public class RedisUtil {

    public static JedisCluster getJedisCluster() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        jedisClusterNodes.add(new HostAndPort(StaticParams.REDISCLUSTERHOST[0], StaticParams.REDISCLUSTERPORT[0]));
        jedisClusterNodes.add(new HostAndPort(StaticParams.REDISCLUSTERHOST[0], StaticParams.REDISCLUSTERPORT[1]));
        jedisClusterNodes.add(new HostAndPort(StaticParams.REDISCLUSTERHOST[1], StaticParams.REDISCLUSTERPORT[0]));
        jedisClusterNodes.add(new HostAndPort(StaticParams.REDISCLUSTERHOST[1], StaticParams.REDISCLUSTERPORT[1]));
        jedisClusterNodes.add(new HostAndPort(StaticParams.REDISCLUSTERHOST[2], StaticParams.REDISCLUSTERPORT[0]));
        jedisClusterNodes.add(new HostAndPort(StaticParams.REDISCLUSTERHOST[2], StaticParams.REDISCLUSTERPORT[1]));
        return new JedisCluster(jedisClusterNodes);
    }

}
