package com.yangsz.news.movie.view;

import com.yangsz.news.Bean.MoviesBean;

public interface IMovieView {
    void showNews(MoviesBean moviesBean);
    void hideDialog();
    void showDialog();
    void showErrorMsg(Throwable throwable);
}
