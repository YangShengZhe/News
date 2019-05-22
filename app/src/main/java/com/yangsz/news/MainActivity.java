package com.yangsz.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.yangsz.news.fragments.Page;
import com.yangsz.news.fragments.account;
import com.yangsz.news.fragments.search;
import com.yangsz.news.fragments.topic;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {
    //初始化工作
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    private DrawerLayout drawerLayout;

    private DrawerLayout mDrawerLayout;

    private int lastIndex;
    List<Fragment> mFragments;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //加载Bmob数据库
        Bmob.initialize(this,"3bda5b17bc4bdebf8428b26cdc154e30");

        mToolbar=findViewById(R.id.toolbar);
        initBottomNavigation();//加载导航栏
        initData();
    }
//    返回活动时决定启动哪个fragment
//    @Override
//    protected void onRestart(){
//        super.onRestart();
//        int fragmentId=getIntent().getIntExtra("id",2);
//        setFragmentPosition(fragmentId);
//
//    }


    public void initData() {
        setSupportActionBar(mToolbar);
        mFragments = new ArrayList<>();
        mFragments.add(new Page());
        mFragments.add(new search());
        mFragments.add(new topic());
        mFragments.add(new account());
        // 初始化展示主页
        setFragmentPosition(0);
    }


    public void initBottomNavigation(){//处理导航栏逻辑
        mBottomNavigationView=findViewById(R.id.bv_bottomNavigation);
        //添加监听,第一次点击时，若再点击添加reselect监听
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mpage:
                        setFragmentPosition(0);
                        break;
                    case R.id.msearch:
                        setFragmentPosition(1);
                        break;
                    case R.id.mtopic:
                        setFragmentPosition(2);
                        break;
                    case R.id.maccount:
                        setFragmentPosition(3);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
    //碎片,根据用户点击位置不同获取位置并展示对应的fragment碎片在主活动中
    private void setFragmentPosition(int position){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        Fragment currentFragment=mFragments.get(position);
        Fragment lastFragment=mFragments.get(lastIndex);
        lastIndex=position;
        ft.hide(lastFragment);
        if(!currentFragment.isAdded()){
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            ft.add(R.id.contentContainer,currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();
    }

}
