package com.yangsz.news.movie.presenter;

import com.yangsz.news.Bean.MoviesBean;
import com.yangsz.news.movie.model.IMovieLoadListener;
import com.yangsz.news.movie.model.IMovieModel;
import com.yangsz.news.movie.model.MovieModel;
import com.yangsz.news.movie.view.IMovieView;

public class MoviePresenter implements  IMoviePresenter, IMovieLoadListener {
    private IMovieModel iMoviesModel;
    private IMovieView iMoviesView;

    public MoviePresenter(IMovieView iMoviesView) {
        this.iMoviesView = iMoviesView;
        this.iMoviesModel =new MovieModel();
    }


    @Override
    public void success(MoviesBean moviesBean) {
        iMoviesView.hideDialog();
        iMoviesView.showNews(moviesBean);
    }

    @Override
    public void fail(Throwable throwable) {
        iMoviesView.hideDialog();
        iMoviesView.showErrorMsg(throwable);
    }


    @Override
    public void loadMovie(String total) {
        iMoviesView.showDialog();
        iMoviesModel.loadMovie(total,this);
    }
}
