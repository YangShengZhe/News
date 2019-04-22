package com.yangsz.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yangsz.news.LoginPage.Login;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()){
                    case R.id.nav_account:
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });

        mToolbar=findViewById(R.id.toolbar);
        initBottomNavigation();//加载导航栏
        initData();
    }




    //处理导航栏逻辑
    public void initBottomNavigation(){
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

    //碎片
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
