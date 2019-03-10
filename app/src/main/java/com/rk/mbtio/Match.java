package com.rk.mbtio;

public class Match {
    public float score;
    public int height;
    public int age;
    public String name;
    public String bio;
    public String mbti;
    public int rid;

    public Match() {

    }

    public Match(int id) {
    }

    public Match(User u) {
        this.score = u.score;
        this.height = u.height;
        this.age = u.age;
        this.name = u.name;
        this.rid = u.uid;
        this.bio = u.bio;
        this.mbti = u.mbti;

    }
/*
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
    */
}
