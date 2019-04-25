package com.yangsz.news.DBmodel;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    private String Gender;
    private Integer age;
    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


}
