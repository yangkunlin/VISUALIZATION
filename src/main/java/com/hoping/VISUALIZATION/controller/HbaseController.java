package com.hoping.VISUALIZATION.controller;

import com.alibaba.fastjson.JSONObject;
import com.hoping.VISUALIZATION.utils.hbase.HbaseFilterUtils;
import com.hoping.VISUALIZATION.common.RequestParams;
import com.hoping.VISUALIZATION.common.StaticParams;
import com.hoping.VISUALIZATION.service.impl.HbaseServiceImpl;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YKL on 2018/5/10.
 * @version 1.0
 * spark:梦想开始的地方
 */
@RestController
public class HbaseController {

    public HbaseController() {
    }

    /**
     * 全表遍历
     * @param body
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/Hbase/Scan", method = RequestMethod.POST)
    public String getResultByScan(@RequestBody String body) throws Exception {

        HbaseServiceImpl hbaseService = new HbaseServiceImpl();

        JSONObject jsonObject = JSONObject.parseObject(body);
        if (jsonObject.containsKey(RequestParams.TABLENAME)) {
            return hbaseService.getResultByScan(jsonObject.getString(RequestParams.TABLENAME), null);
        } else {
            return null;
        }
    }

    /**
     * 列出HBase中所有表和列族信息
     * @param body
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/Hbase/List", method = RequestMethod.POST)
    public String getListTables(@RequestBody String body) throws Exception {
        HbaseServiceImpl hbaseService = new HbaseServiceImpl();
        return hbaseService.getListTables();
    }

    /**
     * 分页查询
     * @param body
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/Hbase/PageQuery", method = RequestMethod.POST)
    public String getPageQuery(@RequestBody String body) throws Exception {

        FilterList filterList = null;
        return queryWithPage(body, filterList);

    }

    /**
     * 指定列和列值查询
     * @param body
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/Hbase/SingleColumnValueQuery", method = RequestMethod.POST)
    public String getSingleColumnValueQuery(@RequestBody String body) throws Exception {

        HbaseServiceImpl hbaseService = new HbaseServiceImpl();

        JSONObject jsonObject = JSONObject.parseObject(body);

        String familyColumn;
        String column;
        String value;

        if (jsonObject.containsKey(RequestParams.FAMILYCOLUMN)) {
            familyColumn = jsonObject.getString(RequestParams.FAMILYCOLUMN);
        } else {
            familyColumn = "";
        }

        if (jsonObject.containsKey(RequestParams.COLUMN)) {
            column = jsonObject.getString(RequestParams.COLUMN);
        } else {
            column = "";
        }

        if (jsonObject.containsKey(RequestParams.VALUE)) {
            value = jsonObject.getString(RequestParams.VALUE);
        } else {
            value = "";
        }

        FilterList filterList = new FilterList();
        filterList.addFilter(HbaseFilterUtils.getSingleColumnValueFilter(familyColumn, column, value));
//        return hbaseService.getResultByScan(jsonObject.getString(RequestParams.TABLENAME), filterList);
        return queryWithPage(body, filterList);
    }

    private String queryWithPage(String body, FilterList filterList) throws Exception {
        HbaseServiceImpl hbaseService = new HbaseServiceImpl();

        JSONObject jsonObject = JSONObject.parseObject(body);

        String tableName = null;
        byte[] startRowKey = null;
        byte[] endRowKey = null;
        int pageSize = StaticParams.PAGESIZE;

        if (jsonObject.containsKey(RequestParams.TABLENAME)) {
            tableName = jsonObject.getString(RequestParams.TABLENAME);
        }

        if (jsonObject.containsKey(RequestParams.STARTROWKEY)) {
            startRowKey = Bytes.toBytes(jsonObject.getString(RequestParams.STARTROWKEY));
        }

        if (jsonObject.containsKey(RequestParams.ENDROWKEY)) {
            endRowKey = Bytes.toBytes(jsonObject.getString(RequestParams.ENDROWKEY));
        }

        if (jsonObject.containsKey(RequestParams.PAGESIZE)) {
            pageSize = jsonObject.getInteger(RequestParams.PAGESIZE);
        }

        if (tableName != null) {
                return hbaseService.pageQuery(tableName, startRowKey, endRowKey, filterList, StaticParams.MAXVERSIONS, pageSize);
        } else {
            return null;
        }
    }

}
