package com.yc.dao;

import com.yc.ResApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import java.sql.SQLException;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResApp.class)
@AutoConfigureMockMvc
@Slf4j
public class ResAppTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ResFoodMapper mapper;

    @Test
    public void testDb() throws SQLException {
        log.info( dataSource.getConnection().toString() );
        System.out.println(dataSource.getConnection().toString());
    }

    @Test
    public void testAll(){
        log.info(mapper.selectList(null).toString());
    }



}