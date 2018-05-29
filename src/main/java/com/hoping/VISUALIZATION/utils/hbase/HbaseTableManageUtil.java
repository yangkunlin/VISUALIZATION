package com.hoping.VISUALIZATION.utils.hbase;

import com.hoping.VISUALIZATION.common.StaticParams;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

/**
 * Description:表信息管理类
 * @author YKL on 2018/5/11.
 * @version 1.0
 * spark:梦想开始的地方
 */
public class HbaseTableManageUtil {

    static Configuration conf = null;
    static Connection connection = null;

    public static Connection getConnection() throws IOException {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", StaticParams.HBASEHOST);
        conf.set("hbase.zookeeper.property.clientPort", StaticParams.HBASEPORT);
        conf.set("hbase.defaults.for.version.skip", "true");
        connection = ConnectionFactory.createConnection(conf);
        return connection;
    }

    public static Table getHbaseTable(String tableName) {
        Table table = null;
        try {
            table = getConnection().getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }

}
