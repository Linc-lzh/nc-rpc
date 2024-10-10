package com.nc.client.entity;

import java.io.Serializable;

public class User implements Serializable {
    private long uid;
    private short age;
    private short sex;

    public long getUid() {
        return uid;
    }

    public User setUid(long uid) {
        this.uid = uid;
        return this;
    }

    public short getAge() {
        return age;
    }

    public User setAge(short age) {
        this.age = age;
        return this;
    }

    public short getSex() {
        return sex;
    }

    public User setSex(short sex) {
        this.sex = sex;
        return this;
    }
}
