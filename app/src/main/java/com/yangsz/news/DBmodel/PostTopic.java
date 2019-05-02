package com.yangsz.news.DBmodel;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class PostTopic extends BmobObject {
    private String poster;
    private String postContent;

    //构造函数
    public PostTopic(String poster,String postContent){
        this.poster=poster;
        this.postContent=postContent;
    }


    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

}
