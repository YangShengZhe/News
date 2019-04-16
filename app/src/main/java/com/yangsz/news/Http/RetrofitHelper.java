package com.yangsz.news.Http;

import com.yangsz.news.Bean.MoviesBean;
import com.yangsz.news.Bean.NewsBean;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

import com.yangsz.news.Bean.NewsBean;

public class RetrofitHelper {
    private static OkHttpClient okHttpClient;
    private RetrofitService retrofitService;

    public RetrofitHelper(String host){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(host)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        retrofitService=retrofit.create(RetrofitService.class);
    }

    //新闻
    public Observable<NewsBean> getNews(String type, String id, int startPage){
        return retrofitService.getNews(type,id,startPage);
    }

    //电影
    public Observable<MoviesBean> getMovies(String total){
        return retrofitService.getMovie(total);
    }


    public OkHttpClient getOkHttpClient(){
        if(okHttpClient==null){
            okHttpClient=new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }

}
