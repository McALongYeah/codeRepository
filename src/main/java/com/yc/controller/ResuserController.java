package com.yc.controller;

import com.yc.StringUtils.StringUtils;
import com.yc.bean.Resuser;
import com.yc.biz.ResUserBiz;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//TODO 静态导入: 导入静态常量和静态属性
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("resuser")
@Slf4j
//@Api 注解用于标注一个Controller（Class）。
//tags="说明该类的作用，可以在前台界面上看到的注解"
//value="该参数无意义，在UI界面上看不到，不需要配置"
@Api(tags = "用户操作")
public class ResuserController {

    @Autowired
    private ResUserBiz resUserBiz;

    @RequestMapping(value = "logout",method = {GET,POST})
    public Map<String,Object> logout(Resuser user, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        session.removeAttribute("resuser");
        session.invalidate();
        map.put("code",-1);
        return map;
    }

    @RequestMapping(value = "isLogin",method = {GET,POST})
    public Map<String,Object> isLogin(Resuser user, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        if (session.getAttribute("resuser")==null){
            map.put("code",0);
        }else {
            map.put("code",1);
            Resuser resuser = (Resuser) session.getAttribute("resuser");
            map.put("obj",resuser);
        }
        return map;
    }

    @RequestMapping(value = "login",method = {GET,POST})
    public Map<String,Object> login(Resuser user, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        String code = (String) session.getAttribute("code");
        if (!user.getYzm().equals(code)){
            map.put("code",-1);
            map.put("msg","验证码错误");
            return map;
        }

        if (StringUtils.empty(user.getUsername() )||StringUtils.empty (user.getPwd())){
            map.put("code",-2);
            map.put("msg","用户名或密码为空");
            return map;
        }
        //处理密码: 用md5 加密
        String md5pass = DigestUtils.md5DigestAsHex(user.getPwd().getBytes());
        //访问业务层 login
        Resuser ru = resUserBiz.findByName(user.getUsername(),md5pass);
        if (ru == null){
            //失败 则code = 0
            map.put("code","-3");
            map.put("msg","用户名或密码错误");
            return map;
        }
        //成功，则code = 1
        map.put("code","1");
        //返回一个数据给客户端
        ru.setPwd("");
        //在session中保存用户信息，以维持登录状态
        session.setAttribute("resuser",ru);

        map.put("obj",ru);
        return map;
    }

}
