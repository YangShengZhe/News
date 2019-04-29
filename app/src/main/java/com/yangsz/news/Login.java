package com.yangsz.news;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.String;
import android.widget.EditText;
import android.widget.TextView;


import com.yangsz.news.DBmodel.User;

import cn.bmob.v3.exception.BmobException;

import cn.bmob.v3.listener.SaveListener;


public class Login extends AppCompatActivity {

    private EditText input_account;
    private EditText input_password;
    private Button signin_button;
    private TextView signUp;
    private String acc;
    private String psw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        input_account=(EditText)findViewById(R.id.loginAccount);
        input_password=(EditText)findViewById(R.id.password);
        signin_button=(Button)findViewById(R.id.signIn);
        signUp=(TextView)findViewById(R.id.signUp);

        //登录事件监听
        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                acc=input_account.getText().toString();
                psw=input_password.getText().toString();
                login(view);
            }
        });

        //注册监听
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(Login.this,Regist.class);
                startActivity(it);
            }
        });


    }

    private void login(final View view) {
        final User user = new User();
        //此处替换为你的用户名
        user.setUsername(input_account.getText().toString());
        //此处替换为你的密码
        user.setPassword(input_password.getText().toString());
        user.login(new SaveListener<User>() {
            @Override
            public void done(User bmobUser, BmobException e) {
                if (e == null) {
//                    User user = BmobUser.getCurrentUser(User.class);
//                    Snackbar.make(view, "登录成功：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
                    Intent intent =new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Snackbar.make(view, "登录失败,请重新输入或注册" , Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }



}
