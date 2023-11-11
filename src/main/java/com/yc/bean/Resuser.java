package com.yc.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resuser implements Serializable {
    private Integer userid;   //PO:与数据库绑定
    private String username;
    private String pwd;
    private String email;

    //为了与页面传过来的yzm对应 ，在这里增加一个String yzm
    @TableField(exist = false) //排除
    private String yzm;  //同时也是个VO类
}
