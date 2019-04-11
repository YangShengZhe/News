package com.yangsz.news;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DetailActivity extends Activity {

    private WebView webNews;
    private String loadUrl;
    private String title;
    private WebViewClient webViewClient;
    private TextView tv_bat_title;
    private ImageView iv_back;
    private ProgressBar proB_load;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        loadUrl=getIntent().getStringExtra("url");
        title=getIntent().getStringExtra("title");
        initView();
        setWebViewClient();
    }

    private void setWebViewClient(){
        webViewClient=new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                proB_load.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view,String url){
                proB_load.setVisibility(View.GONE);
            }
        };
        webNews.setWebViewClient(webViewClient);

    }

    //加载视图
    private void initView(){
        webNews=(WebView)findViewById(R.id.wb_news_content);
        webNews.getSettings().setJavaScriptEnabled(true);
        webNews.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webNews.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webNews.canGoBack();
        webNews.canGoForward();
        webNews.loadUrl(loadUrl);
        tv_bat_title=(TextView)findViewById(R.id.tv_bar_title);
        tv_bat_title.setText(title);
        iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        proB_load=(ProgressBar)findViewById(R.id.proB_load);
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        webNews.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK&&webNews.canGoBack()){
            webNews.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

}
