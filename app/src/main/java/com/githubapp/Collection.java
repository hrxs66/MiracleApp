package com.githubapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import org.litepal.LitePal;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class Collection extends AppCompatActivity {
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
        setContentView(R.layout.collection);

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
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            cover = d.getCover();
                            title3 = d.getName();
                            author3 = d.getAuthor();
                            aclicktotal3 = d.getClickTotal();
                            description3 = d.getDescription();
                            Glide.with(Collection.this).load(cover).into(imageView);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Collection.this, "刷新成功！", Toast.LENGTH_LONG).show();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Collection.this, Lookcollect.class);
        startActivity(intent);
        finish();
    }

    public void del(View view) {
        //删除时的确认
        AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("是否确定删除");
        dialog
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    //启动一个监听事件
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //实现删除
                        {
                            String name1 = d.getName();
                            LitePal.deleteAll(Text.class, "name = ?", name1);
                            Intent intent = new Intent(Collection.this, Lookcollect.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                })
                .setNegativeButton("no", null)
                .create().show();

    }
}