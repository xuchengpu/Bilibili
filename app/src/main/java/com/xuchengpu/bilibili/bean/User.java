package com.xuchengpu.bilibili.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 许成谱 on 2017/3/29 9:43.
 * qq:1550540124
 * for:
 */
@Entity
public class User {
    @Id
    private Long id;
    private String cover_price;
    private String figure;
    private String name;
    private int number = 1;
    private boolean isChecked = true;
    @Transient
    private int tempUsageCount; // not persisted

    public boolean getIsChecked() {
        return this.isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFigure() {
        return this.figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getCover_price() {
        return this.cover_price;
    }

    public void setCover_price(String cover_price) {
        this.cover_price = cover_price;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 536996040)
    public User(Long id, String cover_price, String figure, String name, int number, boolean isChecked) {
        this.id = id;
        this.cover_price = cover_price;
        this.figure = figure;
        this.name = name;
        this.number = number;
        this.isChecked = isChecked;
    }

    @Generated(hash = 586692638)
    public User() {
    }
}
