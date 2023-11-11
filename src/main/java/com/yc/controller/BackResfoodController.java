package com.yc.controller;

import com.yc.bean.Resadmin;
import com.yc.bean.Resfood;
import com.yc.biz.FastDFSBiz;
import com.yc.biz.ResFoodBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("back/resfood")
@Slf4j
@Api(tags = "后台菜品管理")
public class BackResfoodController {

    @Autowired
    private FastDFSBiz fastDFSBiz;

    @Autowired
    private ResFoodBiz resFoodBiz;

    @RequestMapping(value = "addNewFood",method = {RequestMethod.POST})
    public Map<String,Object> addNewFood(
            String fname,
            Double normprice,
            Double realprice,
            String detail,
            MultipartFile fphoto){
        Map<String,Object> map = new HashMap<>();
        Resfood rf = new Resfood();
        try{
            //1.将图片上传到fastDFS中 返回图片地址 group1/M00/00/00/xx.jpg
            // 真正图片   http://storage服务器中的nginx:8888 + 上述图片地址
            //以配置文件形式在springboot配置  存到数据库resfood表中的fphoto
            String path = this.fastDFSBiz.uploadFile(fphoto);
            //步骤二 操作数据

            rf.setFname(fname);
            rf.setNormprice(normprice);
            rf.setRealprice(realprice);
            rf.setDetail(detail);
            rf.setFphoto(path);
            resFoodBiz.addResFood(rf);
        }catch (Exception ex){
            ex.printStackTrace();
            map.put("code",0);
            map.put("msg",ex.getMessage());
            return map;
        }
        map.put("code",1);
        map.put("obj",rf);
        return map;
    }
}
