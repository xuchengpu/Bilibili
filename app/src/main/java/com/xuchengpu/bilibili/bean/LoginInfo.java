package com.xuchengpu.bilibili.bean;

/**
 * Created by 许成谱 on 2017/3/26 16:49.
 * qq:1550540124
 * for:
 */

public class LoginInfo {
    private String name;
    private String phone;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
