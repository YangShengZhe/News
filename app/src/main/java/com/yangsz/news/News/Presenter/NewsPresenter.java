package com.yangsz.news.News.Presenter;

import com.yangsz.news.Bean.NewsBean;
import com.yangsz.news.Http.Api;
import com.yangsz.news.News.Model.INewsModel;
import com.yangsz.news.News.Model.IOnLoadListener;
import com.yangsz.news.News.Model.NewsModel;
import com.yangsz.news.News.View.INewsView;
import com.yangsz.news.fragments.Page;

public class NewsPresenter implements INewsPresenter, IOnLoadListener {
    private INewsModel iNewsModel;
    private INewsView iNewsView;

    public NewsPresenter(INewsView iNewsView){//初始化新闻存储类和view层
        this.iNewsView=iNewsView;
        this.iNewsModel=new NewsModel();
    }

    @Override
    public void success(NewsBean newsBean){//若请求成功则展示新闻
        iNewsView.hideDialog();
        if(newsBean!=null){
            iNewsView.showNews(newsBean);
        }
    }

    @Override
    public void fail(String error){//若请求失败则展示错误
        iNewsView.hideDialog();
        iNewsView.showErrorMsg(error);
    }

    @Override
    public void loadNews(int type,int startPage){//加载新闻，有三类：分别为头条，体育，娱乐新闻
        iNewsView.showDialog();
        switch (type){
            case Page.NEWS_TYPE_TOP:
                iNewsModel.loadNews("headline",startPage, Api.HEADLINE_ID,this);
                break;
            case Page.NEWS_TYPE_NBA:
                iNewsModel.loadNews("list",startPage, Api.NBA_ID,this);
                break;
            case Page.NEWS_TYPE_JOKES:
                iNewsModel.loadNews("list",startPage, Api.JOKE_ID,this);
                break;
        }
    }
}
