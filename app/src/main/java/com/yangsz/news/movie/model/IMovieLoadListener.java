package com.yangsz.news.movie.model;

import com.yangsz.news.Bean.MoviesBean;

public interface IMovieLoadListener {
    void success(MoviesBean moviesBean);
    void fail(Throwable throwable);
}
