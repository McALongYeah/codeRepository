package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.Resorder;
import com.yc.bean.Resorderitem;
import com.yc.bean.Resuser;
import com.yc.dao.ResOrderItemMapper;
import com.yc.dao.ResOrderMapper;
import com.yc.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.DEFAULT,//隔离级别,与数据库保持一致
        readOnly = false,
        rollbackFor = RuntimeException.class,
        timeout = 2000)
public class ResOrderBizImpl implements ResOrderBiz{

    @Autowired
    private ResOrderItemMapper resOrderItemMapper;

    @Autowired
    private ResOrderMapper resOrderMapper;

    @Override
    public int order(Resorder resOrder, Set<CartItem> cartItems, Resuser resUser) {
        QueryWrapper wrapper = new QueryWrapper<>(resOrder);
//        spring 事务 -》 new RuntimeException () 把异常包装成这个异Rt常
        resOrder.setUserid(resUser.getUserid());
        this.resOrderMapper.insert(resOrder);
        for (CartItem cartItem : cartItems) {
            Resorderitem resorderitem = new Resorderitem();
            resorderitem.setRoid(resOrder.getRoid());
            resorderitem.setFid(cartItem.getFood().getFid());
            resorderitem.setNum(cartItem.getNum());
            resorderitem.setDealprice(cartItem.getFood().getRealprice());
            this.resOrderItemMapper.insert(resorderitem);
        }
        return resOrder.getRoid();
    }
}
