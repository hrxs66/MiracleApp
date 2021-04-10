package com.githubapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import org.litepal.LitePal;
import org.litepal.exceptions.DataSupportException;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class first extends AppCompatActivity {
    public ImageView imageView;
    public Text d;
    private TextView title;
    private TextView author;
    private TextView aclicktotal;
    private TextView description;
    private String cover;
    private String title3;
    private String author3;
    private String aclicktotal3;
    private String description3;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);


        d = (Text) getIntent().getSerializableExtra("title");
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        aclicktotal = findViewById(R.id.aclicktotal);
        description = findViewById(R.id.description);
        imageView = findViewById(R.id.cover);

        title.setText(d.getName());
        author.setText(d.getAuthor());
        aclicktotal.setText(d.getClickTotal());
        description.setText(d.getDescription());
        cover = d.getCover();
        title3 = d.getName();
        author3 = d.getAuthor();
        aclicktotal3 = d.getClickTotal();
        description3 = d.getDescription();
        Glide.with(this).load(cover).into(imageView);

        //下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);      //线程睡1秒
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(first.this, "刷新成功！",Toast.LENGTH_LONG).show();  //1秒后提示刷新成功
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });

    }

    public void collect(View view) {
        Text text = new Text();
        text.setName(title3);
        text.setAuthor(author3);
        text.setClickTotal(aclicktotal3);
        text.setDescription(description3);
        text.setCover(cover);
        List<Text> texts = LitePal.where("name = ?", title3).find(Text.class);
        if (texts == null || texts.size() == 0) {
            text.save();
            Toast.makeText(this, "收藏成功！", LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "请勿重复添加收藏", LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(first.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
