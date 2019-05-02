package com.yangsz.news;

import android.app.Activity;
import android.content.Context;
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

public class CommentDetail extends AppCompatActivity  {

    private ImageView comment;
    private TextView hide_down;
    private EditText comment_content;
    private Button comment_send;

    private LinearLayout rl_enroll;
    private RelativeLayout rl_comment;

    private RecyclerView comment_recyclerview;
    private CommentAdapter adapterComment;
    private ArrayList<Comment> commentList=new ArrayList<Comment>();

    private String poster;
    private String postContent;

    private TextView CDPoster;
    private TextView CDPostContent;

    private CommentAdapter  mCollectRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);
        CDPoster=(TextView)findViewById(R.id.CDPoster);
        CDPostContent=(TextView)findViewById(R.id.CDPostContent);
        comment_recyclerview=(RecyclerView)findViewById(R.id.comment_list);

        Bundle bundle= getIntent().getExtras();
        //初始化发帖人数据
        poster=bundle.getString("poster");
        postContent=bundle.getString("postContent");
        CDPoster.setText(poster);
        CDPostContent.setText(postContent);


        queryDataList();
       // initRecyclerView();

    }

    private void initRecyclerView() {

        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙

        //设置item的分割线

        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mCollectRecyclerAdapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, Comment data) {
                //设置评论点击事件
            }
        });

        Toast.makeText(CommentDetail.this,"加载完成",Toast.LENGTH_SHORT).show();
    }



    //查询初始评论数据
    private void queryDataList(){
        BmobQuery<Comment> bmobQuery=new BmobQuery<>();
        final List<Comment> Clist=new ArrayList<>();
        bmobQuery.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if(e==null){//找到数据
                    //给获取的表赋值
                    for(int i=0;i<list.size();i++){
                        Comment pt=new Comment(list.get(i).getReplyer(),list.get(i).getReplyContent());
                        Clist.add(pt);
                    }
                    commentList.addAll(Clist);
                    //设置数据显示

                    comment_recyclerview.setLayoutManager(new LinearLayoutManager(CommentDetail.this, LinearLayoutManager.VERTICAL, false));

                    mCollectRecyclerAdapter = new CommentAdapter(commentList);
                    //给RecyclerView设置adapter
                    comment_recyclerview.setAdapter(mCollectRecyclerAdapter);
                    comment_recyclerview.addItemDecoration(new DividerItemDecoration(CommentDetail.this,DividerItemDecoration.VERTICAL));

                    Toast.makeText(CommentDetail.this,"找到数据"+commentList.get(0).getReplyer(),Toast.LENGTH_SHORT).show();
                }else{//未找到数据
                    Toast.makeText(CommentDetail.this,"未找到数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


//    //设置监听
//    public void setListener(){
//        comment.setOnClickListener(this);
//
//        hide_down.setOnClickListener(this);
//        comment_send.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.comment:
//                // 弹出输入法
//                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                // 显示评论框
//                rl_enroll.setVisibility(View.GONE);
//                rl_comment.setVisibility(View.VISIBLE);
//                break;
//            case R.id.hide_down:
//                // 隐藏评论框
//                rl_enroll.setVisibility(View.VISIBLE);
//                rl_comment.setVisibility(View.GONE);
//                // 隐藏输入法，然后暂存当前输入框的内容，方便下次使用
//                InputMethodManager im = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
//                break;
//            case R.id.comment_send:
//                sendComment();
//                break;
//            default:
//                break;
//        }
//    }
//
//    //发表评论
//    public void sendComment(){
//        if(comment_content.getText().toString().equals("")){
//            Toast.makeText(getApplicationContext(), "评论不能为空！", Toast.LENGTH_SHORT).show();
//        }else if (BmobUser.isLogin()){
//            User user=BmobUser.getCurrentUser(User.class);
//            // 生成评论数据
//            Comment comment = new Comment(user.getUsername(),comment_content.getText().toString());
//
//            //获取上个活动传过来的发帖人数据
//            comment.setPoster(poster);
//            comment.setPostContent(postContent);
//
//            comment.save(new SaveListener<String>() {
//                @Override
//                public void done(String s, BmobException e) {
//                    if(e==null){
//                        Toast.makeText(CommentDetail.this,"上传成功",Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(CommentDetail.this,"上传失败",Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            });
//            adapterComment.addComment(comment);
//            // 发送完，清空输入框
//            comment_content.setText("");
//            Toast.makeText(getApplicationContext(), "评论成功！", Toast.LENGTH_SHORT).show();
//        }else if(!BmobUser.isLogin()){
//            Toast.makeText(getApplicationContext(), "尚未登录，不能评论！", Toast.LENGTH_SHORT).show();
//        }
//    }

}
