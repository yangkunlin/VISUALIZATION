package com.hoping.VISUALIZATION.common;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.FilterList;

import java.io.IOException;
import java.util.Iterator;

/**
 * Description:检索指定表的第一行记录
 * @author YKL on 2018/5/11.
 * @version 1.0
 *spark:梦想开始的地方
 */
public class HbaseTableDataUtil {
    public static Result selectFirstResultRow(String tableName, FilterList filterList) {
        if(StringUtils.isBlank(tableName)) return null;
        Table table = null;
        try {
            table = HbaseTableManageUtil.getHbaseTable(tableName);
            Scan scan = new Scan();
            if(filterList != null) {
                scan.setFilter(filterList);
            }
            ResultScanner scanner = table.getScanner(scan);
            Iterator<Result> iterator = scanner.iterator();
            int index = 0;
            while(iterator.hasNext()) {
                Result rs = iterator.next();
                if(index == 0) {
                    scanner.close();
                    return rs;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                table.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
