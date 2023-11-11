package com.yc.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resadmin implements Serializable {
        @TableId(value = "raid")
        private Integer raid;
        private String raname;
        private String rapwd;

}
