package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resorderitem {
    @TableId(type = IdType.AUTO)
    private Integer roiid;
    private Integer roid;
    private Integer fid;
    private Double dealprice;
    private Integer num;
}
