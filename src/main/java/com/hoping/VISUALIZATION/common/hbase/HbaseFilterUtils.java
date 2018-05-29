package com.hoping.VISUALIZATION.common.hbase;

import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author YKL on 2018/5/10.
 * @version 1.0
 *          spark:梦想开始的地方
 */
public class HbaseFilterUtils {

    /**
     * 列值过滤器
     * @param familyColumn
     * @param column
     * @param value
     * @return SingleColumnValueFilter
     */
    public static SingleColumnValueFilter getSingleColumnValueFilter(String familyColumn, String column, String value) {
        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(Bytes.toBytes(familyColumn),
                Bytes.toBytes(column), CompareFilter.CompareOp.EQUAL, Bytes.toBytes(value));
        singleColumnValueFilter.setFilterIfMissing(true);
        return singleColumnValueFilter;
    }

    /**
     * 列族过滤器
     * @param familyColumn
     * @return
     */
    public static FamilyFilter getFamilyFilter(String familyColumn) {
        FamilyFilter familyFilter = new FamilyFilter(CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes(familyColumn)));
//       new BinaryPrefixComparator(value) //匹配字节数组前缀
//       new RegexStringComparator(expr) // 正则表达式匹配
//       new SubstringComparator(substr)// 子字符串匹配
        return familyFilter;
    }

    /**
     * Qualifier（列）过滤器
     * @param column
     * @return
     */
    public static QualifierFilter getQualifierFilter(String column) {
        QualifierFilter qualifierFilter = new QualifierFilter(CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes(column)));
        return qualifierFilter;
    }

    /**
     * 列名(即Qualifier)前缀过滤器
     * @param column
     * @return
     */
    public static ColumnPrefixFilter getColumnPrefixFilter(String column) {
        ColumnPrefixFilter columnPrefixFilter = new ColumnPrefixFilter(Bytes.toBytes(column));
        return columnPrefixFilter;
    }

    /**
     * 多个列名(即Qualifier)前缀过滤器
     * @param columns
     * @return
     */
    public static MultipleColumnPrefixFilter getMultipleColumnPrefixFilter(String... columns) {
        byte[][] prefixes = new byte[columns.length][];
        int i = 0;
        for (String column : columns) {
            prefixes[i] = Bytes.toBytes(column);
            i++;
        }
        MultipleColumnPrefixFilter multipleColumnPrefixFilter = new MultipleColumnPrefixFilter(prefixes);
        return multipleColumnPrefixFilter;
    }

    /**
     * 列范围过滤器
     * @param startColumn
     * @param endColumn
     * @return
     */
    public static ColumnRangeFilter getColumnRangeFilter(String startColumn, String endColumn) {
        ColumnRangeFilter columnRangeFilter = new ColumnRangeFilter(Bytes.toBytes(startColumn), true, Bytes.toBytes(endColumn), true);
        return columnRangeFilter;
    }
}
