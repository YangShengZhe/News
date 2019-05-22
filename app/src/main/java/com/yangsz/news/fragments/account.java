package com.yangsz.news.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yangsz.news.CommentCollection;
import com.yangsz.news.CommentDetail;
import com.yangsz.news.DBmodel.User;
import com.yangsz.news.EditUserInfo;
import com.yangsz.news.Login;
import com.yangsz.news.R;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;


public class account extends Fragment {

    private CircleImageView userImage;
    private TextView account_username;
    private TextView logout_button;
    private TextView editUserInfo;
    private TextView collect;

    public account() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_account, container, false);

        find(view);
        initView();

        //登录监听的一系列事件
        if(!BmobUser.isLogin()){
            userImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), Login.class);
                    startActivity(intent);
                }
            });

        }else if(BmobUser.isLogin()){
            //点击图片进入个人信息页面

            //点击退出登录
            logout_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BmobUser.logOut();
                    //将刚才新创建的活动销毁
                    getActivity().finish();
                    Snackbar.make(view, "退出成功" , Snackbar.LENGTH_LONG).show();
                }
            });
        }

        //监听是否进入编辑个人资料界面
        editUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!BmobUser.isLogin()){
                    //未登录提示
                    Snackbar.make(view, "未登录，不能进入编辑" , Snackbar.LENGTH_LONG).show();
                }else{
                    Intent it2=new Intent(getActivity(), EditUserInfo.class);
                    startActivity(it2);
                }
            }
        });

        //收藏监听
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!BmobUser.isLogin()){//未登录
                    Snackbar.make(view, "未登录，不能进入编辑" , Snackbar.LENGTH_LONG).show();
                }else {
                    Intent i =new Intent(getActivity(), CommentCollection.class);
                    startActivity(i);
                }
            }
        });
        return view;
    }

    //控件初始化
    public void find(View view){
        account_username=(TextView)view.findViewById(R.id.account_username);
        userImage=(CircleImageView)view.findViewById(R.id.user_center);
        logout_button=(TextView)view.findViewById(R.id.frag_account_logout);
        editUserInfo=(TextView)view.findViewById(R.id.frag_editUserInfo);
        collect=(TextView)view.findViewById(R.id.frag_account_focus);
    }

    //个人页面初始化
    public void initView(){
        if(!BmobUser.isLogin()){
            userImage.setImageResource(R.drawable.defaultimage);
            account_username.setText("尚未登录");
        }else if(BmobUser.isLogin()){
            User user = BmobUser.getCurrentUser(User.class);
            account_username.setText(user.getUsername());
        }
    }

}
