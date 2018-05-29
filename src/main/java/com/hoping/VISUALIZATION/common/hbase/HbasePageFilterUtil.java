package com.hoping.VISUALIZATION.common.hbase;

import com.hoping.VISUALIZATION.entity.HbasePageModel;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:分页检索表数据
 * @author YKL on 2018/5/11.
 * @version 1.0
 * spark:梦想开始的地方
 */
public class HbasePageFilterUtil {

    public static HbasePageModel getResultByPageFilter(String tableName, byte[] startRowKey, byte[] endRowKey, FilterList filterList, int maxVersions, HbasePageModel hbasePageModel) throws IOException {

        if (hbasePageModel == null) {
            hbasePageModel = new HbasePageModel(10);
        }
        if (maxVersions <= 0) {
            //默认只检索数据的最新版本
            maxVersions = Integer.MIN_VALUE;
        }
        hbasePageModel.initStartTime();
        hbasePageModel.initEndTime();
        if (StringUtils.isBlank(tableName)) {
            return hbasePageModel;
        }

        Table table = null;
        try {
            table = HbaseTableManageUtil.getHbaseTable(tableName);
            int tempPageSize = hbasePageModel.getPageSize();
            boolean isEmptyStartRowKey = false;
            if (startRowKey == null) {
                //读取表的第一行记录
                Result firstResult = HbaseTableDataUtil.selectFirstResultRow(tableName, filterList);
                if (firstResult.isEmpty()) {
                    return hbasePageModel;
                }
                startRowKey = firstResult.getRow();
            }
            if (hbasePageModel.getPageStartRowKey() == null) {
                isEmptyStartRowKey = true;
                hbasePageModel.setPageStartRowKey(startRowKey);
            } else {
                if (hbasePageModel.getPageEndRowKey() != null) {
                    hbasePageModel.setPageStartRowKey(hbasePageModel.getPageEndRowKey());
                }
                //从第二页开始，每次都多取一条记录，因为第一条记录是要删除的。
                tempPageSize += 1;
            }

            Scan scan = new Scan();
            scan.withStartRow(hbasePageModel.getPageStartRowKey());
            if (endRowKey != null) {
                scan.withStopRow(endRowKey);
            }
            PageFilter pageFilter = new PageFilter(hbasePageModel.getPageSize() + 1);
            if (filterList != null) {
                filterList.addFilter(pageFilter);
                scan.setFilter(filterList);
            } else {
                scan.setFilter(pageFilter);
            }
            if (maxVersions == Integer.MAX_VALUE) {
                scan.setMaxVersions();
            } else if (maxVersions == Integer.MIN_VALUE) {

            } else {
                scan.setMaxVersions(maxVersions);
            }
            ResultScanner scanner = table.getScanner(scan);
            List<Result> resultList = new ArrayList<Result>();
            int index = 0;
            for (Result result : scanner.next(tempPageSize)) {
                if (isEmptyStartRowKey == false && index == 0) {
                    index += 1;
                    continue;
                }
                if (!result.isEmpty()) {
                    resultList.add(result);
                }
                index += 1;
            }
            scanner.close();
            hbasePageModel.setResultList(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                table.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int pageIndex = hbasePageModel.getPageIndex() + 1;
        hbasePageModel.setPageIndex(pageIndex);
        if (hbasePageModel.getResultList().size() > 0) {
            //获取本次分页数据首行和末行的行键信息
            byte[] pageStartRowKey = hbasePageModel.getResultList().get(0).getRow();
            byte[] pageEndRowKey = hbasePageModel.getResultList().get(hbasePageModel.getResultList().size() - 1).getRow();
            hbasePageModel.setPageStartRowKey(pageStartRowKey);
            hbasePageModel.setPageEndRowKey(pageEndRowKey);
        }
        int queryTotalCount = hbasePageModel.getQueryTotalCount() + hbasePageModel.getResultList().size();
        table.close();
        hbasePageModel.setQueryTotalCount(queryTotalCount);
        hbasePageModel.initEndTime();
        hbasePageModel.printTimeInfo();
        return hbasePageModel;
    }

}
