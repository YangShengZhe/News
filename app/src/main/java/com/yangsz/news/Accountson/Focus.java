package com.yangsz.news.Accountson;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yangsz.news.AccountTopic;
import com.yangsz.news.DBmodel.FocusUser;
import com.yangsz.news.DBmodel.User;
import com.yangsz.news.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class Focus extends AppCompatActivity {
    private RecyclerView focusRecyclerview;
    private FocusAdapter focusAdapter;
    private ArrayList<FocusUser> focusList=new ArrayList<FocusUser>();
    private ImageView bacFF;
    private SwipeRefreshLayout srl_friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        focusRecyclerview=(RecyclerView)findViewById(R.id.focus_recyclerview);
        srl_friends=(SwipeRefreshLayout)findViewById(R.id.srl_friends);
        bacFF=(ImageView)findViewById(R.id.focus_back);

        //刷新监听
        srl_friends.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                equalUser();
            }
        });
        //返回监听
        bacFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        equalUser();
    }

    //初始化列表数据
    private void initRecyclerView() {
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        focusRecyclerview.setLayoutManager(new LinearLayoutManager(Focus.this, LinearLayoutManager.VERTICAL, false));

        focusAdapter = new FocusAdapter(focusList);
        focusAdapter.notifyDataSetChanged();
        //给RecyclerView设置adapter
        focusRecyclerview.setAdapter(focusAdapter);
        //设置item的分割线  参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        focusRecyclerview.addItemDecoration(new DividerItemDecoration(Focus.this,DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        focusAdapter.setOnItemClickListener(new FocusAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, FocusUser data) {
                switch (view.getId()){
                    //设置取消的点击事件
                    case R.id.cancel_focus:
                        canFocus(data.focusId);
                        break;
                    case R.id.focus_username:
                        Intent i9=new Intent(Focus.this, AccountTopic.class);
                        Bundle bb=new Bundle();
                        bb.putString("poster",data.getBeFocused());
                        i9.putExtras(bb);
                        startActivity(i9);
                        break;
                }
            }
        });
    }

    //查询关注的人
    private void equalUser(){
        User u = BmobUser.getCurrentUser(User.class);
        BmobQuery<FocusUser> q=new BmobQuery<>();
        q.addWhereEqualTo("FocusUsers",u.getUsername());
        focusList=new ArrayList<FocusUser>();
        q.findObjects(new FindListener<FocusUser>() {
            @Override
            public void done(List<FocusUser> list, BmobException e) {
                if(e==null){
                    //成功
                    for(int i=0;i<list.size();i++){
                        FocusUser fu=new FocusUser(list.get(i).getFocusUsers(),list.get(i).getBeFocused());
                        fu.focusId=list.get(i).getObjectId();
                        focusList.add(fu);
                    }
                    initRecyclerView();
                }else{
                    //查询失败
                    Toast.makeText(Focus.this,"未成功找到关注表中数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
        srl_friends.setRefreshing(false);
    }

    //取消关注
    private void canFocus(String id){
        FocusUser fu =new FocusUser();
        fu.delete(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    //成功
                    Toast.makeText(Focus.this,"成功取消",Toast.LENGTH_SHORT).show();
                }else{
                    //失败
                    Toast.makeText(Focus.this,"未成功取消",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
