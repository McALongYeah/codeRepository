package com.yc.controller;


import com.yc.StringUtils.StringUtils;
import com.yc.bean.Resadmin;
import com.yc.biz.ResadminBiz;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/resadmin")
@Slf4j
@Api(tags = "管理员后台")
public class ResadminController {

    @Autowired
    private ResadminBiz resadminBiz;

    @RequestMapping(value = "login",method = {GET,POST})
    public Map<String,Object> login(Resadmin resadmin, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        String code = (String) session.getAttribute("code");

        if (StringUtils.empty(resadmin.getRaname() )||StringUtils.empty (resadmin.getRapwd())){
            map.put("code",-2);
            map.put("msg","用户名或密码为空");
            return map;
        }
        //处理密码: 用md5 加密
        String md5pass = DigestUtils.md5DigestAsHex(resadmin.getRapwd().getBytes());
        //访问业务层 login
        Resadmin ru = resadminBiz.findByName(resadmin.getRapwd(),md5pass);
        if (ru == null){
            //失败 则code = 0
            map.put("code","-3");
            map.put("msg","用户名或密码错误");
            return map;
        }
        //成功，则code = 1
        map.put("code","1");
        //返回一个数据给客户端
        ru.setRapwd("");
        //在session中保存用户信息，以维持登录状态
        session.setAttribute("resadmin",ru);

        map.put("obj",ru);
        return map;
    }
}
