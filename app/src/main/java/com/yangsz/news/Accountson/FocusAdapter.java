package com.yangsz.news.Accountson;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yangsz.news.DBmodel.FocusUser;
import com.yangsz.news.R;

import java.util.ArrayList;

public class FocusAdapter extends RecyclerView.Adapter<FocusAdapter.myViewHolder>{
    private ArrayList<FocusUser> FocusList;

    //构造函数
    public FocusAdapter(ArrayList<FocusUser> l){
        this.FocusList=l;
    }

    @NonNull
    @Override
    public FocusAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_focus,parent,false);
        FocusAdapter.myViewHolder holder= new FocusAdapter.myViewHolder(itemView);
        return holder;
    }

    //绑定数据
    @Override
    public void onBindViewHolder(FocusAdapter.myViewHolder holder,int position){
        FocusUser data=FocusList.get(position);
        holder.beFocused.setText(data.getBeFocused());
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        private TextView beFocused;
        private Button focusCancel;

        public myViewHolder(View itemView){
            super(itemView);
            beFocused=(TextView)itemView.findViewById(R.id.focus_username);
            focusCancel=(Button) itemView.findViewById(R.id.cancel_focus);

            //取消点击事件
            focusCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.OnItemClick(view,FocusList.get(getLayoutPosition()));
                }
            });
            //进入个人发帖界面的点击事件
            beFocused.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.OnItemClick(view,FocusList.get(getLayoutPosition()));
                }
            });

        }
    }


    @Override
    public int getItemCount(){
        return FocusList.size();
    }



    //设置item监听事件的接口
    public interface OnItemClickListener{
        public void OnItemClick(View view, FocusUser data);
    }
    private FocusAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(FocusAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}
