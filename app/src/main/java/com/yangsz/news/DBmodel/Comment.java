package com.yangsz.news.DBmodel;

import cn.bmob.v3.BmobObject;

public class Comment extends BmobObject {
    private String poster;
    private String postContent;
    private String replyer;
    private String replyContent;

    public Comment(String replyer,String replyContent){
        this.replyer=replyer;
        this.replyContent=replyContent;
    }
    public Comment(){

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

    public String getReplyer() {
        return replyer;
    }

    public void setReplyer(String replyer) {
        this.replyer = replyer;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }


}
