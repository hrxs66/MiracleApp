package com.githubapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ViewHolder> {
    Context mContext;
    private List<Text> mTextList;

    //将要展示的数据源传进来
    public TextAdapter(List<Text> textList, Context mContext) {
        this.mContext = mContext;
        this.mTextList = textList;
    }

    //定义内部类
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView texttitle;
        TextView author;
        ImageView textcover;

        //获取实例
        public ViewHolder(View view) {
            super(view);
            texttitle = (TextView) view.findViewById(R.id.title);
            author = (TextView) view.findViewById(R.id.author);
            textcover = (ImageView) view.findViewById(R.id.cover);
        }
    }

    //重写三个方法
    //1.创建实例
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.text, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    //对recyclerView子项进行赋值
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Text text = mTextList.get(position);
        holder.texttitle.setText(text.getName());
        holder.author.setText(text.getAuthor());
        Glide.with(mContext).load(mTextList.get(position).getCover()).into(holder.textcover);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, first.class);
                intent.putExtra("title", text);
                mContext.startActivity(intent);
            }
        });
    }

    //返回数据源长度
    @Override
    public int getItemCount() {
        return mTextList.size();
    }
}
