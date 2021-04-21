package com.githubapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.litepal.LitePal;


public class Lookcollect extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lookcollect);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_v);
        tocollect();
    }

    public void tocollect() {
        Lookcollect.this.runOnUiThread(new Runnable() {
            public void run() {
                //    LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                StaggeredGridLayoutManager layoutManager = new
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                TextAdapter1 adapter = new TextAdapter1(LitePal.findAll(Text.class), Lookcollect.this);
                recyclerView.setAdapter(adapter);
                adapter.setLongClickLisenter(new TextAdapter1.LongClickLisenter() {
                    @Override
                    public void LongClickLisenter(int position) {
                       // adapter.del(position);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Lookcollect.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
