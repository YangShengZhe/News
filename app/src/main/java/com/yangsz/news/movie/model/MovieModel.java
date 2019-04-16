package com.yangsz.news.movie.model;

import android.util.Log;

import com.yangsz.news.Bean.MoviesBean;
import com.yangsz.news.Http.Api;
import com.yangsz.news.Http.RetrofitHelper;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieModel implements IMovieModel{
    private static final String TAG = "MovieModel";
    @Override
    public void loadMovie(String total,final IMovieLoadListener iMovieLoadListener){
        RetrofitHelper retrofitHelper = new RetrofitHelper(Api.MOVIE_HOST);
        retrofitHelper.getMovies(total)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<MoviesBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iMovieLoadListener.fail(e);
                    }

                    @Override
                    public void onNext(MoviesBean moviesBean) {
                        iMovieLoadListener.success(moviesBean);
                        Log.i(TAG,"onNext:"+moviesBean.getTotal());
                    }
                });
    }
}
