package com.yangsz.news.DBmodel;

import cn.bmob.v3.BmobObject;

public class Collection extends BmobObject {
    private String collector;
    private String collectPoster;
    private String collectPostContent;
    public String collectId;
    //构造函数
    public Collection(String collectId,String collectPoster,String collectPostContent){
        this.collectPoster=collectPoster;
        this.collectPostContent=collectPostContent;
        this.collectId=collectId;
    }
    public Collection(){}
    public String getCollector() {
        return collector;
    }

    public void setCollector(String collector) {
        this.collector = collector;
    }

    public String getCollectPoster() {
        return collectPoster;
    }

    public void setCollectPoster(String collectPoster) {
        this.collectPoster = collectPoster;
    }

    public String getCollectPostContent() {
        return collectPostContent;
    }

    public void setCollectPostContent(String collectPostContent) {
        this.collectPostContent = collectPostContent;
    }
}
