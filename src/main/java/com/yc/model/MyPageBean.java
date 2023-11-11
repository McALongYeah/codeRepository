package com.yc.model;

import com.yc.bean.Resfood;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
//vo对象 与界面相关
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageBean<T> {
    private Integer pageno;
    private Integer pagesize;
    private String sortby;
    private String sort;
    private long total;
    private List<T> dataset;
    private Integer totalpages;
    private Integer next;
    private Integer pre;

}
