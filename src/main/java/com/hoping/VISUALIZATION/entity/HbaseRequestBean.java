package com.hoping.VISUALIZATION.entity;

/**
 * @author YKL on 2018/5/10.
 * @version 1.0
 *          spark:梦想开始的地方
 */
public class HbaseRequestBean {

    private String tableName;
    private String rowKey;
    private String columnFamily;
    private String[] column;
    private String[] value;
    private String start_rowkey;
    private String end_rowkey;

}
