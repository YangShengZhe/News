package com.yangsz.news;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yangsz.news.DBmodel.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class EditUserInfo extends AppCompatActivity {

    private EditText editName;
    private EditText editGender;
    private EditText editAge;
    private ImageView backToMain;
    private Button editSubmmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        //初始化控件
        editName=(EditText)findViewById(R.id.editUserName);
        editGender=(EditText)findViewById(R.id.editUserGender);
        editAge=(EditText)findViewById(R.id.editUserAge);
        editSubmmit=(Button)findViewById(R.id.edit_submmit);
        backToMain=(ImageView)findViewById(R.id.back_edit);
        //初始化内容
        initViewContent();

        //设置监听
        editSubmmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //初始化控件内容
    private void initViewContent(){
        User user= BmobUser.getCurrentUser(User.class);
        editName.setText(user.getUsername());
        editGender.setText(user.getGender());
        editAge.setText(user.getAge());
    }


    //更新数据
    private void edit(){
        final User us=BmobUser.getCurrentUser(User.class);
        us.setUsername(editName.getText().toString());
        us.setGender(editGender.getText().toString());
        us.setAge(editAge.getText().toString());
        us.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(EditUserInfo.this,"编辑成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(EditUserInfo.this,"编辑失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
