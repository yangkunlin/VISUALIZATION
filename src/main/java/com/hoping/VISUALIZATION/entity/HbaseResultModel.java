package com.hoping.VISUALIZATION.entity;

/**
 * @author YKL on 2018/5/10.
 * @version 1.0
 *          spark:梦想开始的地方
 */
public class HbaseResultModel {

    private String row;
    private String family;
    private String qualifier;
    private String value;
    private Long timestamp;

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
