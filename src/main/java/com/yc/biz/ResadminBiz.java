package com.yc.biz;

import com.yc.bean.Resadmin;

public interface ResadminBiz {
    public Resadmin findByName(String raname, String rapwd);
}
