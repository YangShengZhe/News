package com.yangsz.news.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yangsz.news.CommentDetail;
import com.yangsz.news.DBmodel.Comment;
import com.yangsz.news.DBmodel.PostTopic;
import com.yangsz.news.ItemStyle.SpacesItemDecoration;
import com.yangsz.news.MainActivity;
import com.yangsz.news.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class topic extends Fragment {

    private SwipeRefreshLayout srltopic;
    private RecyclerView topicList;
    //数据列表
    private ArrayList<PostTopic> TList=new ArrayList<PostTopic>();
    private TopicAdapter topicAdapter;
    private String test;
    private int topicSize;


    public topic() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view= inflater.inflate(R.layout.fragment_topic, container, false);
        //获取RecyclerView
        topicList=(RecyclerView)view.findViewById(R.id.topic_list);
        srltopic=(SwipeRefreshLayout)view.findViewById(R.id.srl_topic);
        queryData();

        srltopic.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryData();
            }
        });
        return view;
    }


    //查询话题列表
    private void queryData(){
        BmobQuery<PostTopic> bmobQuery=new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<PostTopic>() {
            @Override
            public void done(List<PostTopic> list, BmobException e) {
                if(e==null){//找到数据
                    //给获取的表赋值
                    topicSize=list.size();
                    for(int i=0;i<list.size();i++) {
                        PostTopic pt = new PostTopic(list.get(i).getPoster(), list.get(i).getPostContent(),list.get(i).getLikesCount(),list.get(i).getBrowseCount(),list.get(i).getObjectId());
                        pt.setCommentsCount(list.get(i).getCommentsCount());
                        TList.add(pt);
                    }
                    //因为BMob查询操作是异步执行的所以在查询完后进行更新ui的操作
                    initUI();
                   // Toast.makeText(getActivity(),"找到数据",Toast.LENGTH_SHORT).show();
                }else{//未找到数据
                    Toast.makeText(getActivity(),"未找到数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
        srltopic.setRefreshing(false);
    }

    private void initUI(){

        //创建adapter
        topicAdapter = new TopicAdapter(getActivity(), TList);
        topicAdapter.notifyDataSetChanged();
        //给RecyclerView设置adapter
        topicList.setAdapter(topicAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        topicList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        topicList.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        topicAdapter.setOnItemClickListener(new TopicAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, PostTopic data) {
                //点击事件
                Intent it=new Intent(getActivity(),CommentDetail.class);
                Bundle bd=new Bundle();
                //添加数据至bundle并传递
                bd.putString("poster",data.getPoster());
                bd.putString("postContent",data.getPostContent());
                it.putExtras(bd);

                //点击之后更新浏览量
                int originalCount=Integer.parseInt(data.getBrowseCount());
                originalCount=originalCount+1;
                String newCount=String.valueOf(originalCount);
                update(newCount,data.id);

                startActivity(it);
            }
        });
    }

    //点击加浏览量的操作
    private void update(String newBrowseCount,String objectId) {
        PostTopic pt1 = new PostTopic();
        pt1.setBrowseCount(newBrowseCount);
        pt1.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){//更新成功
                }else {
                    Toast.makeText(getActivity(),"未更新数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
