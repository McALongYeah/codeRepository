package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.Resuser;
import com.yc.dao.ResUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)//这个业务类所有方法都是find 所以事物配置为readonly
public class ResUserBizImpl implements ResUserBiz{
    @Autowired
    private ResUserMapper resUserMapper;

    @Override
    public Resuser findByName(String name) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",name);
        Resuser resUser = resUserMapper.selectOne(wrapper);
        return resUser;
    }

    @Override
    public Resuser findByName(String name, String password) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",name);
        wrapper.eq("pwd",password);
        Resuser resUser = resUserMapper.selectOne(wrapper);
        return resUser;
    }

    @Override
    public Resuser findById(Integer userid) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("userid",userid);
        Resuser resUser = resUserMapper.selectOne(wrapper);
        return resUser;
    }
}
