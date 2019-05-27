package com.yangsz.news;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yangsz.news.DBmodel.FocusUser;
import com.yangsz.news.DBmodel.PostTopic;
import com.yangsz.news.DBmodel.User;
import com.yangsz.news.fragments.TopicAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class AccountTopic extends AppCompatActivity {
    private String poster1;
    private TextView accountUsername;
    private RecyclerView accountTopicRecyclerview;
    private ArrayList<PostTopic> PTAList=new ArrayList<PostTopic>();
    private TopicAdapter topicAdapter;
    private ImageView ATbac;
    private Button addFocus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_topic);

        //初始化控件
        accountUsername=(TextView)findViewById(R.id.account_topic_username);
        accountTopicRecyclerview=(RecyclerView)findViewById(R.id.account_topic_recyclerview);
        ATbac=(ImageView)findViewById(R.id.account_topic_back);
        addFocus=(Button)findViewById(R.id.addFocus);


        //获取当前是谁的主页
        Bundle bd =getIntent().getExtras();
        poster1=bd.getString("poster");

        //返回按钮监听
        ATbac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //关注事件监听
        addFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BmobUser.isLogin()){
                    //已经登录则处理关注时间
                    queryThisFocus(poster1);
                }else{
                    //未登录,提示
                    Toast.makeText(AccountTopic.this,"未登录，请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //赋值
        accountUsername.setText(poster1);
        //初始化列表数据
        equalTopic(poster1);

    }


    //查询该人发布过的帖子
    private void equalTopic(String name){
        BmobQuery<PostTopic> pt = new BmobQuery<>();
        pt.addWhereEqualTo("poster",name);
        pt.findObjects(new FindListener<PostTopic>() {
            @Override
            public void done(List<PostTopic> list, BmobException e) {
                if(e==null){
                    for(int i=0;i<list.size();i++) {
                        PostTopic pt = new PostTopic(list.get(i).getPoster(), list.get(i).getPostContent(),
                                list.get(i).getLikesCount(), list.get(i).getBrowseCount(),list.get(i).getObjectId());
                        pt.setCommentsCount(list.get(i).getCommentsCount());
                        PTAList.add(pt);
                    }
                    initUI();
                }else{
                    //查找失败
                    Toast.makeText(AccountTopic.this,"未找到该人发布的帖子",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initUI(){
        //创建adapter
        topicAdapter = new TopicAdapter(AccountTopic.this, PTAList);
        topicAdapter.notifyDataSetChanged();
        //给RecyclerView设置adapter
        accountTopicRecyclerview.setAdapter(topicAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        accountTopicRecyclerview.setLayoutManager(new LinearLayoutManager(AccountTopic.this, LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        accountTopicRecyclerview.addItemDecoration(new DividerItemDecoration(AccountTopic.this,DividerItemDecoration.VERTICAL));

        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        topicAdapter.setOnItemClickListener(new TopicAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, PostTopic data) {
                //点击事件
                Intent it=new Intent(AccountTopic.this,CommentDetail.class);
                Bundle bd=new Bundle();
                //添加数据至bundle并传递有发帖人，内容，表的id
                bd.putString("poster",data.getPoster());
                bd.putString("postContent",data.getPostContent());
                bd.putString("likesCount",data.getLikesCount());
                bd.putString("commentCount",data.getCommentsCount());
                bd.putString("id",data.id);
                it.putExtras(bd);

                startActivity(it);
            }
        });
    }

    //查询是否有该关注
    private void queryThisFocus(final String name){
        final User uu =BmobUser.getCurrentUser(User.class);
        BmobQuery<FocusUser> fu =new BmobQuery<>();
        fu.addWhereEqualTo("FocusUsers",uu.getUsername());
        fu.addWhereEqualTo("BeFocused",name);
        fu.findObjects(new FindListener<FocusUser>() {
            @Override
            public void done(List<FocusUser> list, BmobException e) {
                if(e==null){
                    //找到
                    if (list.size()==0){
                        add(uu.getUsername(),name);
                    }else{
                        //找到了
                        Toast.makeText(AccountTopic.this,"已经关注",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //未找到
                    Toast.makeText(AccountTopic.this,"未成功查询关注列表",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    //增加关注
    private void add(String fo,String befo){
        FocusUser ff=new FocusUser();
        ff.setBeFocused(befo);
        ff.setFocusUsers(fo);
        ff.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(AccountTopic.this,"成功存储关注数据",Toast.LENGTH_SHORT).show();
                }else{
                    //未成功存储关注数据
                    Toast.makeText(AccountTopic.this,"未成功存储关注数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
