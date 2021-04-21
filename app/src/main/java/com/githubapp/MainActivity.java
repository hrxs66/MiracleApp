package com.githubapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    List<Text> textList = new ArrayList<>();
    private RecyclerView recyclerView;
    private String name;
    private ImageView imageView;
    private TextView title;
    private TextView description;
    private TextView author;
    private TextView aclicktotal;
    private EditText editText;

    @RequiresApi(api = Build.VERSION_CODES.M)

    //主菜单相关
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();
                break;
            case R.id.collect:
               Intent intent = new Intent(this,Lookcollect.class);
               startActivity(intent);
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建主页面

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //获取实例
        editText = (EditText) findViewById(R.id.inchar);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        author = (TextView) findViewById(R.id.author);
        aclicktotal = (TextView) findViewById(R.id.aclicktotal);
        imageView = (ImageView) findViewById(R.id.cover);



}
    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    name = editText.getText().toString();
                    String url1 = "https://app.u17.com/v3/appV3_3/android/phone/search/searchResult?&come_from=xiaomi&serialNumber=7de42d2e&v=4500102&model=MI+6&android_id=f5c9b6c9284551ad&q="
                            +name;
                    OkHttpClient client = new OkHttpClient();//创建实例
                    Request request = new Request.Builder()
                            .url(url1)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData) {
        textList.clear();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject data = jsonObject.optJSONObject("data");
            JSONObject returnData = data.optJSONObject("returnData");
            JSONArray comics = returnData.optJSONArray("comics");
            for (int i = 0; i < comics.length(); ++i) {
                JSONObject jsonObject1 = comics.optJSONObject(i);

                if (jsonObject1 != null) {
                    String title1 = jsonObject1.getString("name");
                    String description1 = jsonObject1.getString("description");
                    String author1 = jsonObject1.getString("author");
                    String aclicktotal1 = jsonObject1.getString("clickTotal");
                    String url = jsonObject1.getString("cover");
                    Text text2 = new Text("作品名：" + title1, url, "描述：" + description1, "点击量：" + aclicktotal1, "作者:" + author1);
                    textList.add(text2);
                }

            }
            //处理不能修改主线程的问题
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                 //   StaggeredGridLayoutManager layoutManager = new
                   //         StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(new TextAdapter(textList, MainActivity.this));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sou(View view) {
        sendRequestWithOkHttp();
    }


    public void kan(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();//创建实例
                    Request request = new Request.Builder()
                            .url("https://app.u17.com/v3/appV3_3/android/phone/list/getRankComicList?period=total&type=2&come_from=xiaomi&serialNumber=7de42d2e&v=450010&model=MI+6&android_id=f5c9b6c9284551ad&page=1")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    //以下代码不与搜索复用，是因为，排行榜没有点击量这一数据。
                    textList.clear();
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONObject data = jsonObject.optJSONObject("data");
                    JSONObject returnData = data.optJSONObject("returnData");
                    JSONArray comics = returnData.optJSONArray("comics");
                    for (int i = 0; i < comics.length(); ++i) {
                        JSONObject jsonObject1 = comics.optJSONObject(i);

                        if (jsonObject1 != null) {
                            String title1 = jsonObject1.getString("name");
                            String description1 = jsonObject1.getString("description");
                            String author1 = jsonObject1.getString("author");
                            String url = jsonObject1.getString("cover");
                            Text text2 = new Text("作品名：" + title1, url, "描述：" + description1, "作者:" + author1);
                            textList.add(text2);
                        }
                    }

                    //处理不能修改主线程的问题
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                           // StaggeredGridLayoutManager layoutManager = new
                            //        StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(new TextAdapter(textList, MainActivity.this));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }}
