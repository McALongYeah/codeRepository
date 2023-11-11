package com.yc.biz;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;
import java.util.List;

public interface ResFoodBiz {
    //查所有菜品
    public List<Resfood> findAll();

    public Resfood findById(Integer fid);

//    mybaties-plus 自带分页组件 Page
    public Page<Resfood> findByPage (int pageno, int pagesize, String sortby, String sort);

//    上架新菜品
    public Integer addResFood(Resfood food);
}
