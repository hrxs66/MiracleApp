package com.githubapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.litepal.LitePal;

import java.util.List;

public class TextAdapter1 extends RecyclerView.Adapter<TextAdapter1.ViewHolder> {
    Context mContext;
    private List<Text> mTextList;
    private LongClickLisenter longClickLisenter;

    //将要展示的数据源传进来
    public TextAdapter1(List<Text> textList, Context mContext) {
        this.mContext = mContext;
        this.mTextList = textList;

    }

    //定义内部类
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView texttitle1;
        TextView author1;
        ImageView textcover1;

        //获取实例
        public ViewHolder(View view) {
            super(view);
            texttitle1 = (TextView) view.findViewById(R.id.title1);
            author1 = (TextView) view.findViewById(R.id.author1);
            textcover1 = (ImageView) view.findViewById(R.id.cover1);
        }
    }

    //重写三个方法
    //1.创建实例
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.text1, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //对recyclerView子项进行赋值
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Text text = mTextList.get(position);
        holder.texttitle1.setText(text.getName());
        holder.author1.setText(text.getAuthor());
        Glide.with(mContext).load(mTextList.get(position).getCover()).into(holder.textcover1);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Collection.class);
                intent.putExtra("title", text);
                mContext.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                //删除时的确认
//                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext).setTitle("是否确定删除");
//                dialog
//                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                            //启动一个监听事件
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //实现删除
//                                {
//                                    int layoutPosition = holder.getLayoutPosition();
//                                    longClickLisenter.LongClickLisenter(layoutPosition);
//                                }
//                            }
//                        })
//                        .setNegativeButton("no", null)
//                        .create().show();
                Toast.makeText(mContext, "...........", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    //返回数据源长度
    @Override
    public int getItemCount() {
        return mTextList.size();
    }

    //删除相关
    public interface LongClickLisenter {
        void LongClickLisenter(int position);
    }

    public void setLongClickLisenter(LongClickLisenter longClickLisenter) {
        this.longClickLisenter = longClickLisenter;
    }


//    public void del(int i) {
////        LitePal.delete(Text.class,i);
////        LitePal.deleteAll(Text.class, "id = ?", String.valueOf(i));
//    //    notifyDataSetChanged();
//       // Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
//    }
}
