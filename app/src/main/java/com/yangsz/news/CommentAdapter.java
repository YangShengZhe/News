package com.yangsz.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yangsz.news.DBmodel.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.myViewHolder> {
    private ArrayList<Comment> commentList;

    //构造函数
    public CommentAdapter(ArrayList<Comment> commentList){
        this.commentList=commentList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
        myViewHolder holder= new myViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder,int position){
        Comment data=commentList.get(position);
        holder.replyer.setText(data.getReplyer());
        holder.replyContent.setText(data.getReplyContent());

    }

    @Override
    public int getItemCount(){
        return commentList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        private TextView replyer;
        private TextView replyContent;

        public myViewHolder(View itemView){
            super(itemView);
            replyer=(TextView)itemView.findViewById(R.id.replyer);
            replyContent=(TextView)itemView.findViewById(R.id.replyContent);

        }
    }

     // 设置item的监听事件的接口

    public interface OnItemClickListener {

        public void OnItemClick(View view, Comment data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
