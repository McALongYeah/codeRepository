package com.yc.biz;

import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.model.CartItem;

import java.util.Set;

public interface ResOrderBiz {

    /**
     * resorder 订单信息
     * cartItems 购物项信息  这是一个set
     * resuser 当前下单的用户
     */

    public int order(Resorder resOrder, Set<CartItem> cartItems, Resuser resUser);
}
