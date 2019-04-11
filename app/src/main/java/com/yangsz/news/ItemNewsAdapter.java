package com.yangsz.news;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yangsz.news.Bean.NewsBean;

import java.util.ArrayList;
import java.util.List;

public class ItemNewsAdapter extends RecyclerView.Adapter<ItemNewsAdapter.ItemNewsHolder>{
    private List<NewsBean.Bean> objects=new ArrayList<NewsBean.Bean>();

    private Context context;

    public ItemNewsAdapter(Context context){
        this.context=context;
    }

    public void setData(List<NewsBean.Bean> objects){
        this.objects=objects;
    }

    @Override
    public ItemNewsHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        return new ItemNewsHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemNewsHolder holder,int position){
        final NewsBean.Bean bean=objects.get(position);
        if(bean==null){
            return;
        }
        //加载图片,用glide库
        Glide.with(context)
                .load(bean.getImgsrc())
                .into(holder.ivNewImgage);
        if(position==0){
            holder.tvNewDig.setVisibility(View.GONE);
            holder.tvNewTitle.setText("图片:"+bean.getTitle());
        }else {
            holder.tvNewTitle.setText(bean.getTitle());
            holder.tvNewDig.setText(bean.getMtime()+":"+bean.getDigest());
            //跳转新闻页面至详细页面
            holder.CVNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //用intent实现活动之间的跳转
                    Intent intent=new Intent(context,DetailActivity.class);
                    intent.putExtra("url",bean.getUrl());
                    intent.putExtra("title",bean.getTitle());
                    context.startActivity(intent);
                }
            });
        }


    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    protected class ItemNewsHolder extends RecyclerView.ViewHolder{
        private ImageView ivNewImgage;
        private TextView tvNewTitle;
        private TextView tvNewDig;
        //用于跳转
        private CardView CVNews;


        public ItemNewsHolder(View view){
            super(view);
            ivNewImgage=(ImageView)view.findViewById(R.id.iv_news_imgage);
            tvNewTitle=(TextView)view.findViewById(R.id.tv_news_title);
            tvNewDig=(TextView)view.findViewById(R.id.tv_news_digest);
            CVNews=(CardView)view.findViewById(R.id.cv_new);
        }

    }
}
