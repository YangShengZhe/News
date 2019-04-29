package com.yangsz.news.DBmodel;

import java.util.Date;

import cn.bmob.v3.datatype.BmobDate;

public class PostTopic {
    private String poster;
    private String postContent;
    private BmobDate postTime;

    //构造函数
    public PostTopic(String poster,String postContent,BmobDate postTime){
        this.poster=poster;
        this.postContent=postContent;
        this.postTime=postTime;
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

    public BmobDate getPostTime() {
        return postTime;
    }

    public void setPostTime(BmobDate postTime) {
        this.postTime = postTime;
    }

}
