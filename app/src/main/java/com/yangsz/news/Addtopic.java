package com.yangsz.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yangsz.news.DBmodel.PostTopic;
import com.yangsz.news.DBmodel.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Addtopic extends AppCompatActivity {

    private Button cancel;
    private Button add;
    private EditText topicText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtopic);

        //初始化控件
        cancel=(Button)findViewById(R.id.addCancel);
        add=(Button)findViewById(R.id.add);
        topicText=(EditText)findViewById(R.id.topicText);

        //取消事件监听
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//直接销毁添加的活动
            }
        });

        //添加的事件活动，其中包括添加相应的数据库
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先添加数据库，再销毁该活动
                saveTopic();
            }
        });
    }
    //数据库操作
    private void saveTopic(){
        PostTopic postTopic=new PostTopic();
        //获取当前用户信息,用户名
        User user = BmobUser.getCurrentUser(User.class);
        String name=(String) BmobUser.getObjectByKey("username");

        //获取输入文本
        String text=topicText.getText().toString();
        postTopic.setPoster(name);
        postTopic.setPostContent(text);
        postTopic.setCommentsCount("0");
        postTopic.setLikesCount("0");
        postTopic.setBrowseCount("0");

        //存入数据库
        postTopic.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){//存储成功
                    Toast.makeText(Addtopic.this,"成功存储",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(Addtopic.this,"未成功存储",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
