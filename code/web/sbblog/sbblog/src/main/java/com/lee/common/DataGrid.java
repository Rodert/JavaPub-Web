package com.lee.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 表格数据存储对象
 * 默认作为bootstrap表格对应的数据结构存储对象
 * author:lee
 */
public class DataGrid {
    private Long total = Long.valueOf(0);
    private List rows = new ArrayList();

    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }
    public List getRows() {
        return rows;
    }
    public void setRows(List rows) {
        this.rows = rows;
    }
}
