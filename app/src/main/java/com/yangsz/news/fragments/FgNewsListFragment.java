package com.yangsz.news.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yangsz.news.Bean.NewsBean;
import com.yangsz.news.ItemNewsAdapter;
import com.yangsz.news.News.Presenter.NewsPresenter;
import com.yangsz.news.News.View.INewsView;
import com.yangsz.news.R;

import java.util.List;


public class FgNewsListFragment extends Fragment implements INewsView {
    private int type;
    private TextView tv_news_list;

    //新增下拉刷新和mvp布局
    private NewsPresenter presenter;
    private SwipeRefreshLayout srl_news;

    //新闻详细页面
    private RecyclerView rv_news;
    private ItemNewsAdapter adapter;
    private List<NewsBean.Bean> newsBeanList;

    public static FgNewsListFragment newInstance(int type){
        Bundle args=new Bundle();
        FgNewsListFragment fragment=new FgNewsListFragment();
        args.putInt("type",type);
        fragment.setArguments(args);
        return fragment;
    }



    public FgNewsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fg_news_list, container, false);
    }


    @Override
    public void onViewCreated(View view,Bundle savedInsanceState){
        super.onViewCreated(view,savedInsanceState);
        type=getArguments().getInt("type");


        srl_news=view.findViewById(R.id.srl_news);
        presenter=new NewsPresenter(this);
        rv_news=view.findViewById(R.id.rv_news);
        adapter=new ItemNewsAdapter(getActivity());
        tv_news_list=view.findViewById(R.id.tv_news_list);

        srl_news.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadNews(type,0);
            }
        });
        presenter.loadNews(type,0);
    }

    @Override
    public void showNews(final NewsBean newsBean) {
        switch (type){
            case Page.NEWS_TYPE_TOP:
                newsBeanList=newsBean.getTop();
                break;
            case Page.NEWS_TYPE_NBA:
                newsBeanList=newsBean.getNba();
                break;
            case Page.NEWS_TYPE_JOKES:
                newsBeanList=newsBean.getJoke();
                break;
        }
        adapter.setData(newsBeanList);
        rv_news.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        rv_news.setAdapter(adapter);
        tv_news_list.setVisibility(View.GONE);
    }

    @Override
    public void hideDialog() {
        srl_news.setRefreshing(false);
    }

    @Override
    public void showDialog() {
        srl_news.setRefreshing(true);
    }

    @Override
    public void showErrorMsg(String error) {
        tv_news_list.setText("加载失败"+error);
    }
}
