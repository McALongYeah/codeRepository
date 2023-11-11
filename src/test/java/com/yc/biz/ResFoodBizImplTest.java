package com.yc.biz;

import com.yc.bean.Resfood;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ResFoodBizImplTest {

    @Autowired
    private ResFoodBiz resFoodBiz;

    @Test
    public void findAll() {
        Assert.assertNotNull(resFoodBiz.findAll());
    }

    @Test
    public void findById() {
        resFoodBiz.findById(1);
    }

    @Test
    public void findByPage() {
       resFoodBiz.findByPage(1,3,"fid","desc");

    }

//    @Test
//    public void addResFood() {
//        Resfood resfood = new Resfood(13,"瘦肉粥",12.00,10.00,"营养丰富","E:/image/5000047.jpg");
//        resFoodBiz.addResFood(resfood);
//    }
}