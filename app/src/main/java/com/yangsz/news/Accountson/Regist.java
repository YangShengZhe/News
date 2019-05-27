package com.yangsz.news.Accountson;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yangsz.news.DBmodel.User;
import com.yangsz.news.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class Regist extends AppCompatActivity {

    private EditText regist_name;
    private EditText regist_password;
    private Button regist;
    private ImageView select_image;
    private CircleImageView choosed_image;
    private String path;
    private Uri userImageUri;
    private ImageView registBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        //控件初始化
        regist_name=(EditText)findViewById(R.id.registName);
        regist_password=(EditText)findViewById(R.id.registPassword);
        regist=(Button)findViewById(R.id.regist);
        select_image=(ImageView)findViewById(R.id.selectImage);
        choosed_image=(CircleImageView)findViewById(R.id.choosedimage);
        registBack=(ImageView)findViewById(R.id.regist_back);

        //选择头像事件监听
        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent可以应用于广播和发起意图，其中属性有：ComponentName,action,data等
                Intent intent=new Intent();
                intent.setType("image/*");
                //action表示intent的类型，可以是查看、删除、发布或其他情况；我们选择ACTION_GET_CONTENT，系统可以根据Type类型来调用系统程序选择Type
                //类型的内容给你选择
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //如果第二个参数大于或等于0，那么当用户操作完成后会返回到本程序的onActivityResult方法
                startActivityForResult(intent, 1);
            }
        });

        //注册的监听事件
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(view);
                finish();
            }
        });

        //返回时间监听
        registBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //注册
    private void signUp(final View view) {

            final User user = new User();
            user.setUsername(regist_name.getText().toString());
            user.setPassword(regist_password.getText().toString());
            //图片
            //final BmobFile file=new BmobFile(new File(new URI(userImageUri.toString())));
            //user.setUserImage(file);
            user.signUp(new SaveListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if (e == null) {
                        Snackbar.make(view, "注册成功", Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(view, "尚未失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            });
    }

    //选择照片后的回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Uri imageuri;
        if (resultCode==RESULT_CANCELED){
            Toast.makeText(this,"获取失败",Toast.LENGTH_SHORT).show();
            choosed_image.setImageDrawable(getResources().getDrawable(R.drawable.account));
        }else if (resultCode==RESULT_OK){
            imageuri=data.getData();
            userImageUri=imageuri;
            //获取图片路径已存储
            path=getImagePath(imageuri,null);

            ContentResolver cr=this.getContentResolver();
            try{
                //获取图片
                Bitmap bitmap=BitmapFactory.decodeStream(cr.openInputStream(imageuri));
                choosed_image.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                Log.e("Exception",e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //获取图片路径
    private String getImagePath(Uri uri, String seletion) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, seletion, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();

        }
        return path;
    }

}
