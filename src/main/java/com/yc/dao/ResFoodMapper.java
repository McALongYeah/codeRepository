package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.bean.Resfood;

public interface ResFoodMapper extends BaseMapper<Resfood> {
    //利用动态代理根据这个接口的方法啊来生成一个代理对象，并将所有的方法实现
}
