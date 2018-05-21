package com.hoping.VISUALIZATION.service;

import org.apache.hadoop.hbase.filter.FilterList;

/**
 * @author YKL on 2018/5/10.
 * @version 1.0
 *spark:梦想开始的地方
 */
public interface HbaseService {

    /**
    * 创建表
    * @tableName 表名
    * @family 列族列表
    */
    public void creatTable(String tableName, String[] family) throws Exception;

    /**
     * 向表中插数据
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @param value
     * @throws Exception
     */
    public void addData(String tableName, String rowKey, String columnFamily, String[] column, String[] value) throws Exception;

    /**
     * 根据rwokey查询
     * @param tableName
     * @param rowKey
     * @throws Exception
     */
    public String getResultByRowkey(String tableName, String rowKey) throws Exception;

    /**
     * 遍历查询
     * @param tableName
     * @return
     * @throws Exception
     */
    public String getResultByScan(String tableName, FilterList filterList) throws Exception;

    /**
     * 遍历查询
     * @param tableName
     * @param start_rowkey
     * @param end_rowkey
     * @return
     * @throws Exception
     */
    public String getResultByScan(String tableName, String start_rowkey, String end_rowkey) throws Exception;

    /**
     * 列出HBase中所有表和列族信息
     * @return
     * @throws Exception
     */
    public String getListTables() throws Exception;

    /**
     *分页查询
     * @param tableName
     * @param startRowKey
     * @param endRowKey
     * @param filterList
     * @param maxVersions
     * @return
     * @throws Exception
     */
    public String pageQuery(String tableName, byte[] startRowKey, byte[] endRowKey, FilterList filterList, int maxVersions,int pageSize) throws Exception;

}
