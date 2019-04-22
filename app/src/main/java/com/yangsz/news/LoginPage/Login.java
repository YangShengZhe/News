package com.yangsz.news.LoginPage;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yangsz.news.MainActivity;
import com.yangsz.news.account_login.User;

import org.json.JSONException;
import org.json.JSONObject;

import com.yangsz.news.R;

public class Login extends AppCompatActivity implements View.OnClickListener {

    TextView tv_name;
    TextView tv_content;
    ImageView imageView;
    private UserInfo mInfo;
    public static Tencent mTencent;
    public static String mAppid="101572756";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView(){
        tv_name = (TextView) findViewById(R.id.name);
        tv_content = (TextView) findViewById(R.id.content);
        imageView = (ImageView) findViewById(R.id.logo);
        findViewById(R.id.qq_login).setOnClickListener(this);
        findViewById(R.id.qq_logout).setOnClickListener(this);
        if (mTencent==null){
            mTencent = Tencent.createInstance(mAppid,this);
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.qq_login:
                onClickLogin();
                break;
            case R.id.qq_logout:
                mTencent.logout(Login.this);
                tv_name.setText("哈哈");
                tv_content.setText("ee");
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;

        }
    }

    //继承得到的baseUilistener得到的doconplete的方法信息
    IUiListener loginListener = new BaseUiListener(){
        @Override
        protected void doComplete(JSONObject values){//得到用户名，签名信息
            initOpenidAndToken(values);
            updateUserInfo();
        }
    };
    //qq返回数据个体
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == Constants.REQUEST_LOGIN||
                requestCode ==Constants.REQUEST_APPBAR){
            Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        }
        super.onActivityResult(requestCode,resultCode,data);
    }


    private class BaseUiListener implements IUiListener{
        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Toast.makeText(Login.this, "登录失败",Toast.LENGTH_LONG).show();
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Toast.makeText(Login.this, "登录失败",Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(Login.this, "登录成功",Toast.LENGTH_LONG).show();
            doComplete((JSONObject)response);
        }

        protected void doComplete(JSONObject values) {

        }
        @Override
        public void onError(UiError e) {
            //登录错误
        }

        @Override
        public void onCancel() {
            // 运行完成
        }
    }

    //获取登录qq的权限信息
    public static void initOpenidAndToken(JSONObject jsonObject){
        try{
            String token=jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires=jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId=jsonObject.getString(Constants.PARAM_OPEN_ID);
            if(!TextUtils.isEmpty(token)&&!TextUtils.isEmpty(expires)&&!TextUtils.isEmpty(openId)){
                mTencent.setAccessToken(token,expires);
                mTencent.setOpenId(openId);
            }
        }catch (Exception e){

        }
    }

    //点击登录
    private void onClickLogin(){
        if(!mTencent.isSessionValid()){
            mTencent.login(this,"all",loginListener);
        }
    }

    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {
                @Override
                public void onError(UiError e) {
                }
                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    Log.i("tag", response.toString());
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                }
                @Override
                public void onCancel() {
                }
            };
            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);
        }
    }
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    try {
                        Gson gson=new Gson();
                        User user=gson.fromJson(response.toString(),User.class);
                        if (user!=null) {
                            tv_name.setText("昵称："+user.getNickname()+"  性别:"+user.getGender()+"  地址："+user.getProvince()+user.getCity());
                            tv_content.setText("头像路径："+user.getFigureurl_qq_2());
                            Picasso.with(Login.this).load(response.getString("figureurl_qq_2")).into(imageView);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };


    //回调接口  (成功和失败的相关操作)
    private class BaseUiListener1 implements IUiListener {
        @Override
        public void onComplete(Object response) {
            doComplete(response);
        }

        protected void doComplete(Object values) {
        }

        @Override
        public void onError(UiError e) {
        }

        @Override
        public void onCancel() {
        }
    }

}
