package com.blueeyescloud.ext.devicemaster.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 分页消息实体类
 * @param <T>
 */
public class PageResEntity<T> implements Serializable {
    @SerializedName("page_no")
    private int pageNo;

    @SerializedName("page_size")
    private int pageSize;

    @SerializedName("total_count")
    private int totalCount;

    private List<T> result;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
