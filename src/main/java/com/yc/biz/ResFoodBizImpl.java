package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;
import com.yc.config.RedisKeys;
import com.yc.dao.ResFoodMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ResFoodBizImpl implements ResFoodBiz{

    @Autowired
    private ResFoodMapper resFoodMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${nginx.address}")
    private String nginxAddress = "http://localhost:8888/";


    @Override
    public List<Resfood> findAll() {
        List<Resfood> list = resFoodMapper.selectList(null);
        return list;
    }

    @Override
    public Resfood findById(Integer fid) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("fid",fid);
        Resfood resfood = resFoodMapper.selectOne(wrapper);
        return resfood;
    }

    /**
     *
     * @param pageno
     * @param pagesize
     * @param sortby
     * @param sort
     * 在mybatis-plus框架中，这些属性代表如下意思：
     *
     * pageno: 表示查询的页码。即希望返回结果的页数。
     * pagesize: 表示每页的数据条数。即每页显示多少条记录。
     * sortby: 表示按照哪个字段进行排序。可以是实体类中的任意字段。
     * sort: 表示排序的方式，可以是升序（ASC）或降序（DESC）。
     * @return
     */
    @Override
    public Page<Resfood> findByPage(int pageno, int pagesize, String sortby, String sort) {
        Page<Resfood> page = new Page<>(pageno,pagesize);
        QueryWrapper<Resfood> wrapper = new QueryWrapper();
        // 根据sortby和sort来设置排序条件
        if (sortby != null && sort != null) {
            if (sort.equalsIgnoreCase("asc")) {
                wrapper.orderByAsc(sortby);
            } else if (sort.equalsIgnoreCase("desc")) {
                wrapper.orderByDesc(sortby);
            }
        }
        Page<Resfood> resfoodPage = resFoodMapper.selectPage(page,wrapper);


//        TODO: 要到redis 要查询这些Resfood 的浏览数
        List<Resfood> list = resfoodPage.getRecords();

        List<String> keys = new ArrayList<String>();
        for (Resfood rf : list) {
            keys.add(RedisKeys.RESFOOD_DETAIL_COUNT_FID_ + rf.getFid());
        }
        List<Integer> allFoodDetailCountValues =  redisTemplate.opsForValue().multiGet(keys);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setDetail_count(allFoodDetailCountValues.get(i));

            //再修改图片的地址
            list.get(i).setFphoto( nginxAddress +list.get(i).getFphoto());

        }
//        for (int i = 0; i < list.size(); i++) {
//            Resfood rf = list.get(i);
//            Long count = (Long) redisTemplate.opsForValue().get(RedisKeys.RESFOOD_DETAIL_COUNT_FID_ + rf.getFid());
//            rf.setDetail_count(count);
//        }
        return resfoodPage;

    }

    @Override
    @Transactional(
            propagation = Propagation.SUPPORTS, //支持事务环境下运行
            readOnly = false,
            isolation = Isolation.DEFAULT,  //隔离级别与数据库一致
            timeout = 2000,                 //超时时间
            rollbackFor = RuntimeException.class  //什么情况下回滚
    )
    public Integer addResFood(Resfood food) {
        int i = resFoodMapper.insert(food);
        return i;
    }
}
