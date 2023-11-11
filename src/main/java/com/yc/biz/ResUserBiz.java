package com.yc.biz;

import com.yc.bean.Resuser;

public interface ResUserBiz {
    public Resuser findByName(String name);

    public Resuser findByName(String name, String password);

    public Resuser findById(Integer userid);
}
