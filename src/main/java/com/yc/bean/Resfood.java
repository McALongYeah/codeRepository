package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Resfood",description = "菜品实体类")
public class Resfood implements Serializable {
    @TableId(type = IdType.AUTO)

    @ApiModelProperty(name="fid",value = "菜品id")
    private Integer fid;
    @ApiModelProperty(name = "fname",value = "菜品名称")
    private String fname;
    @ApiModelProperty(name = "normprice",value = "原件")
    private Double normprice;
    @ApiModelProperty(name = "realprice",value = "真实价")
    private Double realprice;
    @ApiModelProperty(name = "detail",value = "细节描述")
    private String detail;
    @ApiModelProperty(name = "fphoto",value = "图片")
    private String fphoto;

    @TableField(exist = false)
//    商品浏览数
    private Integer detail_count; //redis中的数据，不是数据库的
}
