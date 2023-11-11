package com.yc.controller;

import com.yc.bean.Resfood;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ResFoodControllerTest {


    @Autowired
    private MockMvc mockMvc;

    //预期对象
    Resfood resfood = new Resfood(1,"素炒莴笋丝",22.00,20.00,"营养丰富","500008.jpg",1);

    @Test
    public void findById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/resfood/findById")
                        .param("fid", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                //期望返回的 HTTP 状态码为 200。
                .andExpect(MockMvcResultMatchers.status().isOk())
                //期望返回的结果中，属性 data.fname 的值与预期的 resfood.getFname() 相等，忽略大小写。
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.fname", Matchers.equalToIgnoringCase(resfood.getFname())))
                //使用 andExpect 方法验证响应的内容类型
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                //在控制台打印请求和响应的详细信息。
                .andDo(print())
                //返回执行结果。
                .andReturn();
    }
}