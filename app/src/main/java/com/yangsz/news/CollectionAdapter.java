package com.yangsz.news;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yangsz.news.DBmodel.Collection;
import java.util.ArrayList;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class CollectionAdapter  extends RecyclerView.Adapter<CollectionAdapter.myViewHolder> {
    private ArrayList<Collection> collectionList;

    //构造函数
    public CollectionAdapter(ArrayList<Collection> collectionList){
        this.collectionList=collectionList;
    }

    @NonNull
    @Override
    public CollectionAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection,parent,false);
        CollectionAdapter.myViewHolder holder= new CollectionAdapter.myViewHolder(itemView);
        return holder;
    }

    //绑定数据
    @Override
    public void onBindViewHolder(CollectionAdapter.myViewHolder holder, int position){
        Collection data=collectionList.get(position);
        holder.collPoster.setText(data.getCollectPoster());
        holder.collPostContent.setText(data.getCollectPostContent());

    }

    @Override
    public int getItemCount(){
        return collectionList.size();
    }



    class myViewHolder extends RecyclerView.ViewHolder{
        private TextView collPoster;
        private TextView collPostContent;
        private Button cancelCol;
        private LinearLayout clickIn;

        public myViewHolder(View itemView){
            super(itemView);
            collPoster=(TextView)itemView.findViewById(R.id.collPoster);
            collPostContent=(TextView)itemView.findViewById(R.id.collPostCotent);
            cancelCol=(Button)itemView.findViewById(R.id.calcel_collection);
            clickIn=(LinearLayout)itemView.findViewById(R.id.clickInDetail);

            cancelCol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.OnItemClick(view,collectionList.get(getLayoutPosition()));
                }
            });

            clickIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.OnItemClick(view,collectionList.get(getLayoutPosition()));
                }
            });
            //点击事件可以在这儿设置
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(onItemClickListener!=null){
//                        onItemClickListener.OnItemClick(view,collectionList.get(getLayoutPosition()));
//                    }
//                }
//            });
        }
    }


    //设置item监听事件的接口
    public interface OnItemClickListener{
        public void OnItemClick(View view, Collection data);
    }
    private CollectionAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(CollectionAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }



}
