package com.yc.controller;

import com.mysql.cj.protocol.a.LocalDateTimeValueEncoder;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.yc.model.CartItem;
import com.yc.bean.Resfood;
import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.biz.ResFoodBiz;
import com.yc.biz.ResOrderBiz;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("resorder")
@Slf4j
//@Api 注解用于标注一个Controller（Class）。
//tags="说明该类的作用，可以在前台界面上看到的注解"
//value="该参数无意义，在UI界面上看不到，不需要配置"
@Api(tags = "购物车管理")
public class ResorderController {

    @Autowired
    private ResFoodBiz resFoodBiz;

    @Autowired
    private ResOrderBiz resOrderBiz;


    @RequestMapping(value = "confirmOrder" ,method = {RequestMethod.GET,RequestMethod.POST} )
    @ApiOperation("提交订单")
    //ApiParam 用在请求参数前面,用于对参数进行描述或说明是否为必添项等说明
    public Map<String,Object> confirmOrder(Resorder order, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (session.getAttribute("cart")==null || ((Map<Integer, CartItem>) session.getAttribute("cart")).size() <= 0){
            map.put("code",-1);
            map.put("msg","暂无任何商品...");
            return map;
        }
        Map<Integer,CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (session.getAttribute("resuser")==null){
            map.put("code",-2);
            map.put("msg","非登录用户不能下单");
            return map;
        }
        Resuser resuser = (Resuser) session.getAttribute("resuser");
        order.setUserid(resuser.getUserid());
        //orderTime 下单时间
        LocalDateTime now  = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        order.setOrdertime(formatter.format(now));
        if (order.getDeliverytime() == null || "".equals(order.getDeliverytime())){
            LocalDateTime deliveryTime = now.plusHours(1);
            order.setDeliverytime(formatter.format(deliveryTime));
        }
        order.setStatus(0);
        try{
            resOrderBiz.order(order,new HashSet(cart.values()),resuser);
        }catch (Exception ex){
            map.put("code",-3);
            map.put("msg",ex.getMessage());
            ex.printStackTrace();
            return map;
        }
        map.put("code",1);
        return map;
    }


    @RequestMapping(value = "getCartInfo" ,method = {RequestMethod.GET,RequestMethod.POST} )
    @ApiOperation("获取购物车列表")
    //ApiParam 用在请求参数前面,用于对参数进行描述或说明是否为必添项等说明
    public Map<String,Object> getCartInfo(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (session.getAttribute("cart")==null || ((Map<Integer, CartItem>) session.getAttribute("cart")).size() <= 0){
            map.put("code",0);
            return map;
        }
        Map<Integer,CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        map.put("code",1);
        map.put("obj",cart.values()); //cart.values 返回的是map 的值的set
        return map;
    }

    //@ApiOperation 注解在用于对一个操作或HTTP方法进行描述
    @RequestMapping(value = "clearAll" ,method = {RequestMethod.GET,RequestMethod.POST} )
    @ApiOperation("清空购物车")
    //ApiParam 用在请求参数前面,用于对参数进行描述或说明是否为必添项等说明
    public Map<String,Object> clearAll(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        session.removeAttribute("cart");
        session.removeAttribute("code");
        map.put("code",1);
        return map;
    }


    //@ApiOperation 注解在用于对一个操作或HTTP方法进行描述
    @RequestMapping(value = "addCart" ,method = {RequestMethod.GET,RequestMethod.POST} )
    @ApiOperation("添加购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "菜品的id号", required = true),
            @ApiImplicitParam(name = "num", value = "数量", required = true)}
    )
    //ApiParam 用在请求参数前面,用于对参数进行描述或说明是否为必添项等说明
    public Map<String,Object> addCart(@RequestParam Integer fid, @RequestParam Integer num, HttpSession session){
        Map<String,Object> map = new HashMap<>();
//         * 1.根据fid取出商品信息
        Resfood food = resFoodBiz.findById(fid);
        if (food == null){
            map.put("code",-1);
            map.put("msg","查无此商品");
            return map;
        }
//         * 2.从session 中取出Cart (map)
        Map<Integer, CartItem> cart = new HashMap<Integer, CartItem>();
        if (session.getAttribute("cart") != null){
            cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        }else {
            session.setAttribute("cart",cart);
        }

//         * 3.判断这个商品在Cart(map)中是否存在
        CartItem ci;
        //判断这个商品在cart（map）是否有
        if (cart.containsKey(fid)){
            ci = cart.get(fid);
            ci.setNum(ci.getNum() + num);
            cart.put(fid,ci);
        }else {
            //         * 4.没有 则创建一个CartItem 存到map 中
            ci = new CartItem();
            ci.setNum(num);
            ci.setFood(food);
            cart.put(fid,ci);
        }

//         * 5.有增加数量]

        //处理数量
        if (ci.getNum() <= 0){
            cart.remove(fid);
        }
        session.setAttribute("cart",cart);
        map.put("code",1);
        map.put("obj",cart.values());
        return map;


    }

}
