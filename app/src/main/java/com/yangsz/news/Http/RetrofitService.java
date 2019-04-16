package com.yangsz.news.Http;

import com.yangsz.news.Bean.MoviesBean;
import com.yangsz.news.Bean.NewsBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface RetrofitService {
    //网易新闻apihttp://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<NewsBean> getNews(@Path("type") String type,
                                 @Path("id") String id,
                                 @Path("startPage") int startPage);

    //电影从豆瓣获取

    @GET("/v2/movie/{total}")
    Observable<MoviesBean> getMovie(@Path("total") String total);
}
