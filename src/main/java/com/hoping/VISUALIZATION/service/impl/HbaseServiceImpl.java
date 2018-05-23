package com.hoping.VISUALIZATION.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoping.VISUALIZATION.common.*;
import com.hoping.VISUALIZATION.entity.HbasePageModel;
import com.hoping.VISUALIZATION.service.HbaseService;
import com.hoping.VISUALIZATION.utils.hbase.HbasePageFilterUtil;
import com.hoping.VISUALIZATION.utils.hbase.HbaseTableDataUtil;
import com.hoping.VISUALIZATION.utils.hbase.HbaseTableManageUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;


/**
 * @author YKL on 2018/5/10.
 * @version 1.0
 *          spark:梦想开始的地方
 */
@Service
public class HbaseServiceImpl implements HbaseService {

    // 声明静态配置
    private static Logger logger = LogManager.getRootLogger();
    //    static Configuration hbaseConf = null;
//    static {
//        hbaseConf = HBaseConfiguration.create();
//        hbaseConf.set("hbase.zookeeper.quorum", "bigdata-slave01,bigdata-slave02,bigdata-slave03");
//        hbaseConf.set("hbase.zookeeper.property.clientPort", "2181");
//        hbaseConf.set("hbase.defaults.for.version.skip", "true");
//    }
    Connection connection = HbaseTableManageUtil.getConnection();

    public HbaseServiceImpl() throws IOException {
    }

    @Override
    public void creatTable(String tableName, String[] family) throws Exception {

        Admin admin = connection.getAdmin();
        HTableDescriptor desc = new HTableDescriptor(TableName.valueOf(tableName));
        for (int i = 0; i < family.length; i++) {
            desc.addFamily(new HColumnDescriptor(family[i]));
        }
        if (admin.tableExists(TableName.valueOf(tableName))) {
            logger.info("table Exists!");
            System.exit(0);
        } else {
            admin.createTable(desc);
            logger.info("create table Success!");
        }

        connection.close();

    }

    @Override
    public void addData(String tableName, String rowKey, String columnFamily, String[] column, String[] value) throws Exception {

        Put put = new Put(Bytes.toBytes(rowKey));// 设置rowkey
        Table table = connection.getTable(TableName.valueOf(tableName));// Htabel负责跟记录相关的操作如增删改查等

        int columnSize = column.length;

        for (int i = 0; i < columnSize; i++) {
            put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column[i]), Bytes.toBytes(value[i]));
        }

        table.put(put);
        logger.info("add data Success!");

        connection.close();
    }

    @Override
    public String getResultByRowkey(String tableName, String rowKey) throws Exception {
        Get get = new Get(Bytes.toBytes(rowKey));
        Table table = connection.getTable(TableName.valueOf(tableName));// 获取表
        Result result = table.get(get);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Map<String, Object>> resList = new ArrayList<>();
            Map<String, Object> tempmap = results2Map(result);
            resList.add(tempmap);
            return objectMapper.writeValueAsString(resList);
        } catch (Exception ex) {
            logger.error(ex.toString());
            return ex.toString();
        } finally {
            table.close();
        }
    }

    @Override
    public String getResultByScan(String tableName, FilterList filterList) throws Exception {
        Scan scan = new Scan();
        ResultScanner results = null;
        Table table = connection.getTable(TableName.valueOf(tableName));
        ObjectMapper objectMapper = new ObjectMapper();

        scan.setFilter(filterList);

        try {
            results = table.getScanner(scan);
            List<Map<String, Object>> resList = new ArrayList<>();
            for (Result result : results) {
                Map<String, Object> tempmap = results2Map(result);
                resList.add(tempmap);
            }
            return objectMapper.writeValueAsString(resList);
        } catch (Exception ex) {
            logger.error(ex.toString());
            return ex.toString();
        } finally {
            results.close();
            table.close();
            connection.close();

        }
    }

    @Override
    public String getResultByScan(String tableName, String start_rowkey, String end_rowkey) throws Exception {
//        Scan scan = new Scan();
//        scan.withStartRow(Bytes.toBytes(start_rowkey));
//        scan.withStopRow(Bytes.toBytes(end_rowkey));
//        ResultScanner results = null;
//        Table table = connection.getTable(TableName.valueOf(tableName));
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            results = table.getScanner(scan);
//            List<Map<String, Object>> resList = new ArrayList<>();
//            for (Result result : results) {
//                Map<String, Object> tempmap = results2Map(result);
//                resList.add(tempmap);
//            }
//            return objectMapper.writeValueAsString(resList);
//        } catch (Exception ex) {
//            logger.error(ex.toString());
//            return ex.toString();
//        } finally {
//            results.close();
//            table.close();
//        }

        return null;

    }

    @Override
    public String getListTables() throws Exception {

        Admin admin = connection.getAdmin();
        HTableDescriptor[] tableDescriptors = admin.listTables();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> list = new ArrayList<>();

        for (HTableDescriptor tableDescriptor : tableDescriptors) {
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> subMap = new HashMap<>();
            HColumnDescriptor[] hColumnDescriptors = tableDescriptor.getColumnFamilies();
            for (HColumnDescriptor hColumnDescriptor : hColumnDescriptors) {
                subMap.put(RequestParams.FAMILYCOLUMN, hColumnDescriptor.getNameAsString());
                //获取该列族所有属性值
                Set<ImmutableBytesWritable> keys = hColumnDescriptor.getValues().keySet();
                for (ImmutableBytesWritable key : keys) {
                    String value = Bytes.toString(hColumnDescriptor.getValues().getOrDefault(key, null).copyBytes());
                    subMap.put(Bytes.toString(key.copyBytes()), value);
                }
            }

            map.put(StaticParams.COLUMNDESCRIPTOR, subMap);
            map.put(RequestParams.TABLENAME, String.valueOf(tableDescriptor.getTableName()));
            //map.put("familyColumns", tableDescriptor.getFamilies().toArray());
            list.add(map);
        }
        connection.close();

        return objectMapper.writeValueAsString(list);
    }

    @Override
    public String pageQuery(String tableName, byte[] startRowKey, byte[] endRowKey, FilterList filterList, int maxVersions, int pageSize) throws Exception {

        HbasePageModel hbasePageModel = new HbasePageModel(pageSize);

        if (startRowKey == null) {
            Result firstResult = HbaseTableDataUtil.selectFirstResultRow(tableName, filterList);
            startRowKey = Bytes.toBytes(results2Map(firstResult).get("rowname").toString());
        }

        List<Result> resultList = HbasePageFilterUtil.getResultByPageFilter(tableName, startRowKey, endRowKey, filterList,maxVersions,hbasePageModel).getResultList();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Map<String, Object>> resList = new ArrayList<>();
            for (Result result : resultList) {
                Map<String, Object> tempmap = results2Map(result);
                resList.add(tempmap);
            }
            return objectMapper.writeValueAsString(resList);
        } catch (Exception ex) {
            logger.error(ex.toString());
            return ex.toString();
        } finally {
        }

    }

    //把result转换成map，方便返回json数据
    public static Map<String, Object> results2Map(Result result) {
        Map<String, Object> resMap = new HashMap<>();
        List<Cell> listCell = result.listCells();
        Map<String, Object> tempMap = new HashMap<>();
        String rowname = "";
        List<String> familynamelist = new ArrayList<>();
        for (Cell cell : listCell) {
            byte[] rowArray = cell.getRowArray();
            byte[] familyArray = cell.getFamilyArray();
            byte[] qualifierArray = cell.getQualifierArray();
            byte[] valueArray = cell.getValueArray();
            int rowoffset = cell.getRowOffset();
            int familyoffset = cell.getFamilyOffset();
            int qualifieroffset = cell.getQualifierOffset();
            int valueoffset = cell.getValueOffset();
            int rowlength = cell.getRowLength();
            int familylength = cell.getFamilyLength();
            int qualifierlength = cell.getQualifierLength();
            int valuelength = cell.getValueLength();

            byte[] temprowarray = new byte[rowlength];
            System.arraycopy(rowArray, rowoffset, temprowarray, 0, rowlength);
            String temprow = Bytes.toString(temprowarray);
//            System.out.println(Bytes.toString(temprowarray));

            byte[] tempqulifierarray = new byte[qualifierlength];
            System.arraycopy(qualifierArray, qualifieroffset, tempqulifierarray, 0, qualifierlength);
            String tempqulifier = Bytes.toString(tempqulifierarray);
//            System.out.println(Bytes.toString(tempqulifierarray));

            byte[] tempfamilyarray = new byte[familylength];
            System.arraycopy(familyArray, familyoffset, tempfamilyarray, 0, familylength);
            String tempfamily = Bytes.toString(tempfamilyarray);
//            System.out.println(Bytes.toString(tempfamilyarray));

            byte[] tempvaluearray = new byte[valuelength];
            System.arraycopy(valueArray, valueoffset, tempvaluearray, 0, valuelength);
            String tempvalue = Bytes.toString(tempvaluearray);
//            System.out.println(Bytes.toString(tempvaluearray));


            tempMap.put(tempfamily + ":" + tempqulifier, tempvalue);
//            long t= cell.getTimestamp();
//            tempMap.put("timestamp",t);
            rowname = temprow;
            String familyname = tempfamily;
            if (familynamelist.indexOf(familyname) < 0) {
                familynamelist.add(familyname);
            }
        }
        resMap.put("rowname", rowname);
        for (String familyname : familynamelist) {
            HashMap<String, Object> tempFilterMap = new HashMap<>();
            for (String key : tempMap.keySet()) {
                String[] keyArray = key.split(":");
                if (keyArray[0].equals(familyname)) {
                    tempFilterMap.put(keyArray[1], tempMap.get(key));
                }
            }
            resMap.put(familyname, tempFilterMap);
        }

        return resMap;
    }
}
