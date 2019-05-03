package com.yangsz.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yangsz.news.DBmodel.Comment;
import com.yangsz.news.DBmodel.PostTopic;
import com.yangsz.news.DBmodel.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CommentDetail extends AppCompatActivity  {
    private ImageView commentBack;
    private RecyclerView comment_recyclerview;
    private CommentAdapter adapterComment;
    private ArrayList<Comment> commentList=new ArrayList<Comment>();

    private String poster1;
    private String postContent1;

    private TextView CDPoster;
    private TextView CDPostContent;
    private String postId;
    private String oriLikesCount;
    private String oriCommentCount;

    private CommentAdapter  mCollectRecyclerAdapter;
    private ImageView likeif;
    private ImageView addlike;
    private Button submmitComment;
    private EditText addCommentContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);
        //控件初始化
        CDPoster=(TextView)findViewById(R.id.CDPoster);
        CDPostContent=(TextView)findViewById(R.id.CDPostContent);
        comment_recyclerview=(RecyclerView)findViewById(R.id.comment_list);
        commentBack=(ImageView)findViewById(R.id.comment_back);
        likeif=(ImageView) findViewById(R.id.likeIf);
        addlike=(ImageView)findViewById(R.id.addlikes);
        submmitComment=(Button)findViewById(R.id.submmitComment);
        addCommentContent=(EditText)findViewById(R.id.addCommenContent);

        Bundle bundle= getIntent().getExtras();
        //初始化发帖人数据
        poster1=bundle.getString("poster");
        postContent1=bundle.getString("postContent");
        postId=bundle.getString("id");
        oriLikesCount=bundle.getString("likesCount");
        oriCommentCount=bundle.getString("commentCount");
        String count=bundle.getString("BrowseCount");
        CDPoster.setText(poster1);
        CDPostContent.setText(postContent1);

        //查询数据显示
        queryDataList();

        //返回按钮的监听
        commentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //取消喜欢
        likeif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //一种是点过了再点就不喜欢，一种是没点
                subtract(postId,oriLikesCount);
            }
        });
        //喜欢
        addlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addlike.setImageResource(R.drawable.loving_heart);
                addLikes(postId,oriLikesCount);
            }
        });
        //增加评论
        submmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BmobUser.isLogin()){//如果登录
                    addCommentContent.setHint("喜欢就评论吧~");
                    addComment(poster1,postContent1,postId,oriCommentCount);
                }else {//未登录
                    Toast.makeText(CommentDetail.this,"未登录，请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //初始化列表数据
    private void initRecyclerView() {
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        comment_recyclerview.setLayoutManager(new LinearLayoutManager(CommentDetail.this, LinearLayoutManager.VERTICAL, false));

        mCollectRecyclerAdapter = new CommentAdapter(commentList);
        mCollectRecyclerAdapter.notifyDataSetChanged();
        //给RecyclerView设置adapter
        comment_recyclerview.setAdapter(mCollectRecyclerAdapter);
        //设置item的分割线  参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        comment_recyclerview.addItemDecoration(new DividerItemDecoration(CommentDetail.this,DividerItemDecoration.VERTICAL));

        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mCollectRecyclerAdapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, Comment data) {
                //设置评论点击事件
            }
        });
    }

    //查询初始评论数据
    private void queryDataList(){
        BmobQuery<Comment> bmobQuery=new BmobQuery<>();
        bmobQuery.addWhereEqualTo("poster",poster1);
        bmobQuery.addWhereEqualTo("postContent",postContent1);
        final List<Comment> Clist=new ArrayList<>();
        bmobQuery.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if(e==null){//找到数据
                    //给获取的表赋值
                    for(int i=0;i<list.size();i++){
                        Comment ct=new Comment(list.get(i).getReplyer(),list.get(i).getReplyContent());
                        Clist.add(ct);
                    }
                    commentList.addAll(Clist);
                    //设置数据显示
                    initRecyclerView();

                    //Toast.makeText(CommentDetail.this,"找到数据"+commentList.get(0).getReplyer(),Toast.LENGTH_SHORT).show();
                }else{//未找到数据
                    Toast.makeText(CommentDetail.this,"未找到数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //如果点击空爱心则喜欢数加一
    private void addLikes(String objectId,String count){
        int c=Integer.parseInt(count);
        c=c+1;
        count=String.valueOf(c);
        PostTopic p=new PostTopic();
        p.setLikesCount(count);
        p.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){//更新成功
                    Toast.makeText(CommentDetail.this,"喜欢",Toast.LENGTH_SHORT).show();
                }else{
                    //更新失败
                    Toast.makeText(CommentDetail.this,"未更新数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //减少喜欢数
    private void subtract(String objectId,String count){
        int c=Integer.parseInt(count);
        c=c-1;
        count=String.valueOf(c);
        PostTopic p=new PostTopic();
        p.setLikesCount(count);
        p.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){//更新成功
                    Toast.makeText(CommentDetail.this,"不喜欢",Toast.LENGTH_SHORT).show();
                }else{
                    //更新失败
                    Toast.makeText(CommentDetail.this,"未更新数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //增加评论
    private void addComment(String name, String Content, final String objectid, final String Commentcount){
        final Comment ct2=new Comment();
        ct2.setPoster(name);
        ct2.setPostContent(Content);
        ct2.setReplyContent(addCommentContent.getText().toString());
        //获取当前登录用户
        User user1=BmobUser.getCurrentUser(User.class);
        String poster2=(String) BmobUser.getObjectByKey("username");
        ct2.setReplyer(poster2);
        ct2.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    commentList.add(ct2);
                    updateCommentCount(objectid,Commentcount);
                    Toast.makeText(CommentDetail.this,"添加评论成功",Toast.LENGTH_SHORT).show();
                }else{//未查询到
                    Toast.makeText(CommentDetail.this,"未成功存储评论",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //增加posttopic表中的评论数
    private void updateCommentCount(String objectId,String Count){
        int c=Integer.parseInt(Count);
        c=c+1;
        Count=String.valueOf(c);
        PostTopic pt4=new PostTopic();
        pt4.setCommentsCount(Count);
        pt4.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    //成功
                }else{
                    Toast.makeText(CommentDetail.this,"未增加评论数",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
