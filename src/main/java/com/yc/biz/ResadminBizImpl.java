package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.Resadmin;
import com.yc.dao.ResadminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)//这个业务类所有方法都是find 所以事物配置为readonly
public class ResadminBizImpl implements ResadminBiz{
    @Autowired
    private ResadminMapper resadminMapper;

    @Override
    public Resadmin findByName(String raname, String rapwd) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("raname",raname);
        wrapper.eq("rapwd",rapwd);
        Resadmin resadmin = resadminMapper.selectOne(wrapper);
        return resadmin;
    }

}
