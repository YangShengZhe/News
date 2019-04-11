package com.yangsz.news.News.Model;

import com.yangsz.news.Bean.NewsBean;

public interface IOnLoadListener {
    void success(NewsBean newsBean);
    void fail(String error);
}
