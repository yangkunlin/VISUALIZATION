package com.hoping.VISUALIZATION.common;

import java.util.ArrayList;

/**
 * Description:
 *
 * @author YKL on 2018/5/14.
 * @version 1.0
 *          spark:梦想开始的地方
 */
public class StaticParams {

    public static int MAXVERSIONS = -1;

    public static int PAGESIZE = 20;

    public static String REDISCLUSTERHOST[] = {"bigdata-slave01", "bigdata-slave02", "bigdata-slave03"};

    //  val REDISCLUSTERHOST: (String, String, String) = ("10.141.43.10", "10.141.38.244", "10.141.50.68")
//
    public static int REDISCLUSTERPORT[] = {7000, 7001};

    public static String COLUMNDESCRIPTOR = "columnDescritor";

    public static String REDISONLINEKEY = "online";

    public static String REDISLOGINEDONLINEKEY = "logined_online";

    public static String REDISAREAKEY = "area";

    public static String REDISLOGINEDAREAKEY = "logined_area";

    public static String REDISPATHKEY = "path";

    public static String REDISLOGINEDPATHKEY = "logined_path";

    public static String REDISAGAINONLINEKEY = "again_online";

    public static String REDISAGAINLOGINEDONLINEKEY = "again_logined_online";

}
