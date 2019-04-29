package com.yangsz.news.DBmodel;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser {
    private String Gender;
    private Integer age;
    private BmobFile userImage;


    public BmobFile getUserImage() {
        return userImage;
    }

    public void setUserImage(BmobFile userImage) {
        this.userImage = userImage;
    }
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
