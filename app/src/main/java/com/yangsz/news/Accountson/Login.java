package com.yangsz.news.Accountson;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.lang.String;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.yangsz.news.DBmodel.User;
import com.yangsz.news.MainActivity;
import com.yangsz.news.R;

import cn.bmob.v3.exception.BmobException;

import cn.bmob.v3.listener.SaveListener;


public class Login extends AppCompatActivity {

    private EditText input_account;
    private EditText input_password;
    private Button signin_button;
    private TextView signUp;
    private ImageView loginBack;
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
        loginBack=(ImageView)findViewById(R.id.login_back);

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

        //返回事件监听
        loginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                    Intent it6=new Intent(Login.this, MainActivity.class);
                    startActivity(it6);
                    finish();
                } else {
                    Snackbar.make(view, "登录失败,请重新输入或注册" , Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }



}
