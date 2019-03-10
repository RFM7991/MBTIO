package com.rk.mbtio;

public class User {
    public int state;
    public int uid;
    public int pin;
    public int height;
    public int age;
    public String sex;
    public String interest;
    public String mbti;
    public String name;

    public User() {
        state = 0;
    }

    public User(int id) {
        this.uid = id;
        state = 1;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setMBTI(String mbti) {
        this.mbti = mbti;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void setAge(int age) {
        this.age =age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUid() {
        return this.uid;
    }
}
