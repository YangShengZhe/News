package com.yangsz.news.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yangsz.news.DBmodel.User;
import com.yangsz.news.Login;
import com.yangsz.news.R;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class account extends Fragment {

    private CircleImageView userImage;
    private TextView account_username;
    private TextView logout_button;

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
                    account_username.setText("尚未登录");
                    userImage.setImageResource(R.drawable.defaultimage);
                    Snackbar.make(view, "退出成功：" , Snackbar.LENGTH_LONG).show();

                }
            });
        }



        return view;
    }

    //控件初始化
    public void find(View view){
        account_username=(TextView)view.findViewById(R.id.account_username);
        userImage=(CircleImageView)view.findViewById(R.id.user_center);
        logout_button=(TextView)view.findViewById(R.id.frag_account_logout);
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