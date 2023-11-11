package com.yc.model;

import com.yc.bean.Resfood;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Resfood food;//购买的商品
    private Integer num;//这个商品的数量
    private Double smallCount;//小计

    public Double getSmallCount(){
        if (food!=null){
            smallCount = this.food.getRealprice() * this.num;
        }
        return smallCount;
    }

    public Resfood getFood() {
        return food;
    }

    public void setFood(Resfood food) {
        this.food = food;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setSmallCount(Double smallCount) {
        this.smallCount = smallCount;
    }

    public CartItem(Resfood food, Integer num, Double smallCount) {
        this.food = food;
        this.num = num;
        this.smallCount = smallCount;
    }

    public CartItem() {
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "food=" + food +
                ", num=" + num +
                ", smallCount=" + smallCount +
                '}';
    }
}
