package com.yangsz.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yangsz.news.DBmodel.Collection;
import com.yangsz.news.DBmodel.Comment;
import com.yangsz.news.DBmodel.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class CommentCollection extends AppCompatActivity {

    private RecyclerView collection_recyclerview;
    private CollectionAdapter colAdapter;
    private ArrayList<Collection> collectionList= new ArrayList<Collection>();
    private ImageView bac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_collection);

        //初始化控件
        collection_recyclerview=(RecyclerView)findViewById(R.id.collectionRecyclerview);
        bac=(ImageView)findViewById(R.id.collect_back);

        //返回监听
        bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        queryList();
    }

    //初始化列表数据
    private void initRecyclerView() {
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        collection_recyclerview.setLayoutManager(new LinearLayoutManager(CommentCollection.this, LinearLayoutManager.VERTICAL, false));

        colAdapter = new CollectionAdapter(collectionList);
        colAdapter.notifyDataSetChanged();
        //给RecyclerView设置adapter
        collection_recyclerview.setAdapter(colAdapter);
        //设置item的分割线  参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        collection_recyclerview.addItemDecoration(new DividerItemDecoration(CommentCollection.this,DividerItemDecoration.VERTICAL));

        //设置取消的点击事件
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        colAdapter.setOnItemClickListener(new CollectionAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, Collection data) {
                switch (view.getId()){
                    case R.id.calcel_collection:
                        cancelFocus(data.collectId);
                        break;
                }
            }
        });
    }
    //查询数据库中收藏列表数据
    private void queryList(){
        User u = BmobUser.getCurrentUser(User.class);
        BmobQuery<Collection> qc=new BmobQuery<>();
        qc.addWhereEqualTo("collector",u.getUsername());
        final List<Collection> CList = new ArrayList();
        qc.findObjects(new FindListener<Collection>() {
            @Override
            public void done(List<Collection> list, BmobException e) {
                if(e==null){
                    //给表赋值
                    for(int i=0;i<list.size();i++){
                        Collection n = new Collection(list.get(i).getObjectId(),list.get(i).getCollectPoster(),list.get(i).getCollectPostContent());
                        CList.add(n);
                    }
                    collectionList.addAll(CList);
                    initRecyclerView();
                }else{
                    //未找到数据
                    Toast.makeText(CommentCollection.this,"未找到数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //取消收藏
    public void cancelFocus(String id){
        Collection c = new Collection();
        c.delete(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    //成功
                    Toast.makeText(CommentCollection.this,"取消收藏",Toast.LENGTH_SHORT).show();
                }else{
                    //失败
                    Toast.makeText(CommentCollection.this,"未成功取消",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
