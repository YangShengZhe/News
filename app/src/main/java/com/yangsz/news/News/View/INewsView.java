package com.yangsz.news.News.View;

import com.yangsz.news.Bean.NewsBean;

public interface INewsView {
    void showNews(NewsBean newsBean);
    void hideDialog();
    void showDialog();
    void showErrorMsg(String error);
}
