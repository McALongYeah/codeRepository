package com.yc.biz;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j

public class ResUserBizImplTest {

    @Autowired
    private ResUserBiz resUserBiz;

    @Test
    public void findByName() {
        Assert.assertNotNull(resUserBiz.findByName("a"));
    }

    @Test
    public void testFindByName() {
        Assert.assertNotNull(resUserBiz.findByName("a","0cc175b9c0f1b6a831c399e269772661"));
    }

    @Test
    public void findById() {
        Assert.assertNotNull(resUserBiz.findById(1));
    }

    @Test
    public void testMd5(){
        String md5pass = DigestUtils.md5DigestAsHex("a".getBytes());
        System.out.println("md5pass = " + md5pass);
    }
}