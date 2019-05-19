package com.yangsz.news.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.bmob.v3.datatype.BmobDate;


import com.yangsz.news.DBmodel.PostTopic;
import com.yangsz.news.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.myViewHolder> {
    private Context context;
    private ArrayList<PostTopic> PTList;

    private int resourceId;
    public TopicAdapter(Context context,ArrayList<PostTopic> objects){
        this.context=context;
        this.PTList=objects;
    }

    @Override
    public int getItemCount() {
        return PTList.size();
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView=View.inflate(context,R.layout.topic_item,null);
        return new myViewHolder(itemView);
    }

   @Override
   public void onBindViewHolder(myViewHolder holder,int position){
        PostTopic data=PTList.get(position);
        //设置数据
        holder.poster.setText(data.getPoster());
        holder.postContent.setText(data.getPostContent());
        holder.BrowseCount.setText(data.getBrowseCount());
        holder.likesCount.setText(data.getLikesCount());
        holder.CommentsCount.setText(data.getCommentsCount());

   }

   class myViewHolder extends RecyclerView.ViewHolder{
        private TextView poster;
        private TextView postContent;
        private TextView likesCount;
        private TextView BrowseCount;
        private TextView CommentsCount;

        public myViewHolder(View itemView){
            super(itemView);
            poster=(TextView) itemView.findViewById(R.id.poster);
            postContent=(TextView) itemView.findViewById(R.id.postContent);
            likesCount=(TextView)itemView.findViewById(R.id.likesCount);
            BrowseCount=(TextView)itemView.findViewById(R.id.BrowseCount);
            CommentsCount=(TextView)itemView.findViewById(R.id.CommentsCount);
            //点击事件可以在这儿设置
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(view,PTList.get(getLayoutPosition()));
                    }
                }
            });
        }
   }

   //设置item监听事件的接口
    public interface OnItemClickListener{
        public void OnItemClick(View view,PostTopic data);
   }
   private  OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    //  删除数据
    public void removeData(int position) {
        PTList.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}
