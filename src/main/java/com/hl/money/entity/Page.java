package com.hl.money.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Page implements Serializable {

    /** 总页数 */
    private int totalPage;
    /** 总条数 */
    private int totalRecord;
    /** 当前页数据 */
    private String dataList;
    
    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalRecord() {
        return totalRecord;
    }
    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }
    public String getDataList() {
        return dataList;
    }
    public void setDataList(String dataList) {
        this.dataList = dataList;
    }
}
