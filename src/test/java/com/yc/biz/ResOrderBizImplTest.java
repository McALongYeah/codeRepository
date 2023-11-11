package com.yc.biz;

import com.yc.bean.Resfood;
import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.model.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ResOrderBizImplTest {

    @Autowired
    private ResOrderBiz resOrderBiz;

    @Autowired
    private ResUserBiz resUserBiz;

    @Autowired
    private ResFoodBiz resFoodBiz;
    @Test
    public void order() {
        Resorder resorder = new Resorder();
        resorder.setUserid(1);
        resorder.setAddress("顺枫B5");
        resorder.setOrdertime("2023-11-2 13:56:12");
        resorder.setTel("18374893823");
        resorder.setStatus(0);

        Resfood resfood = new Resfood();
        resfood = resFoodBiz.findById(1);

        Resfood resfood2 = new Resfood();
        resfood2 = resFoodBiz.findById(3);


        Set<CartItem> item = new HashSet<>();
        item.add(new CartItem(resfood,1,24.00));
        item.add(new CartItem(resfood,2,48.00));

        Resuser resuser = new Resuser();
        resuser = resUserBiz.findById(1);

        resOrderBiz.order(resorder,item,resuser);
    }
}