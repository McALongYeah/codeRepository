package com.yc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.config.RedisKeys;
import com.yc.model.MyPageBean;
import com.yc.bean.Resfood;
import com.yc.biz.ResFoodBiz;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resfood")
@Slf4j
//@Api 注解用于标注一个Controller（Class）。
//tags="说明该类的作用，可以在前台界面上看到的注解"
//value="该参数无意义，在UI界面上看不到，不需要配置"
@Api(value = "ResFoodController", tags = "菜品的控制层")
public class ResFoodController {

    @Autowired
    private ResFoodBiz resFoodBiz;

    @Autowired
    private RedisTemplate redisTemplate;

    //@ApiOperation 注解在用于对一个操作或HTTP方法进行描述
    @ApiOperation("查看详情次数增加")
    @RequestMapping(value = "detailCountAdd",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "fid",
                    value = "菜品号",
                    required = true
            )
    )
    //ApiParam 用在请求参数前面,用于对参数进行描述或说明是否为必添项等说明
    public Map<String,Object> detailCountAdd(
            @ApiParam(name = "fid",value = "菜品的id号")
            @RequestParam Integer fid){
        Map<String ,Object> map = new HashMap<>();
        Long count = 1L;
        if (redisTemplate.hasKey(RedisKeys.RESFOOD_DETAIL_COUNT_FID_ + fid) == false){
            redisTemplate.opsForValue().set(RedisKeys.RESFOOD_DETAIL_COUNT_FID_ + fid,1);
        }else{
            count = redisTemplate.opsForValue().increment(RedisKeys.RESFOOD_DETAIL_COUNT_FID_ + fid);
        }
        map.put("code",1);
        map.put("obj",count);  // obj存正常返回的结果
        return map;
    }

    //@ApiOperation 注解在用于对一个操作或HTTP方法进行描述
    @ApiOperation("查询菜品")
    @RequestMapping("/findById")
    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "fid",
                    value = "菜品的id号2",
                    required = true
            )
    )
    //ApiParam 用在请求参数前面,用于对参数进行描述或说明是否为必添项等说明
    public Resfood findById(
            @ApiParam(name = "fid",value = "菜品的id号")
            @RequestParam Integer fid){
        Resfood resfood = resFoodBiz.findById(fid);
        return resfood;
    }

    @RequestMapping("/findAll")
    public List<Resfood> findAll(){
        List<Resfood> resfood = resFoodBiz.findAll();
        return resfood;
    }

    @RequestMapping("/findByPage")
    public Map<String,Object> findByPage(@RequestParam int pageno,@RequestParam int pagesize,@RequestParam(required = false)  String sortby,@RequestParam (required = false) String sort){
        Map<String,Object> map = new HashMap<>(); //返回的json字符串
        //此处的Page 是dao层的组件,这种被称为PO对象（持久化对象-》与表结构相同），到controller层要进行转化 转化成vo对象(值对象->为了页面展示需要)
        Page<Resfood> page = null;

        try{
            page = this.resFoodBiz.findByPage(pageno,pagesize,sortby,sort);
        }catch (Exception ex){
            map.put("code",0);
            map.put("msg",ex.getCause());
            ex.printStackTrace();
            return map;
        }

        map.put("code",1);
        MyPageBean pageBeanVO = new MyPageBean();
        pageBeanVO.setPageno(pageno);
        pageBeanVO.setPagesize(pagesize);
        pageBeanVO.setSort(sort);
        pageBeanVO.setSortby(sortby);
        pageBeanVO.setTotal(page.getTotal());
        pageBeanVO.setDataset(page.getRecords());

        //计算总页数
        long totalPages=page.getTotal()%pageBeanVO.getPagesize()==0?
                page.getTotal()/pageBeanVO.getPagesize():page.getTotal()/pageBeanVO.getPagesize()+1;
        pageBeanVO.setTotalpages((int) totalPages);
        //上一页页号的计算
        if (pageBeanVO.getPageno()<=1){
            pageBeanVO.setPre(1);
        }else {
            pageBeanVO.setPre(pageBeanVO.getPageno()-1);
        }
        //下一页的计算
        if (pageBeanVO.getPageno()==totalPages){
            pageBeanVO.setNext((int) totalPages);
        }else {
            pageBeanVO.setNext(pageBeanVO.getPageno()+1);
        }
        map.put("data",pageBeanVO);
        //TODO
        return map;


    }

}
