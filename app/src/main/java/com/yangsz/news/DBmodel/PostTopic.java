package com.yangsz.news.DBmodel;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class PostTopic extends BmobObject {
    private String poster;
    private String postContent;
    private String likesCount;
    private String BrowseCount;
    private String CommentsCount;
    public String id;


    //构造函数
    public PostTopic(String poster,String postContent,String likesCount,String BrowseCount,String id){
        this.poster=poster;
        this.postContent=postContent;
        this.likesCount=likesCount;
        this.BrowseCount=BrowseCount;
        this.id=id;
    }
    public  PostTopic(){}

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

    public String getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(String likesCount) {
        this.likesCount = likesCount;
    }

    public String getBrowseCount() {
        return BrowseCount;
    }

    public void setBrowseCount(String browseCount) {
        BrowseCount = browseCount;
    }

    public String getCommentsCount() {
        return CommentsCount;
    }

    public void setCommentsCount(String commentsCount) {
        CommentsCount = commentsCount;
    }

}
