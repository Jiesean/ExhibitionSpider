package com.jiesean.exhibitionspider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    RecyclerView mShowView;
    SwipeRefreshLayout mRefreshLayout;
    RelativeLayout activityMain;

    private Document document;

    private List<ExhibitionBean> mBeans;

    private LocalAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_main);

        mShowView = (RecyclerView) findViewById(R.id.show_recycler_view);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        activityMain = (RelativeLayout) findViewById(R.id.activity_main);

        mBeans = new ArrayList<>();
        mAdapter = new LocalAdapter(MainActivity.this);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        mShowView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mShowView.setAdapter(mAdapter);
        mShowView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.tv_title:
                    case R.id.tv_content:
                    case R.id.iv_primary:
                        break;
                    case R.id.iv_avatar:
                    case R.id.tv_author:
                        break;
                    case R.id.tv_collectTag:
                        break;
                    default:
                        break;
                }
            }
        });

        getData();

        mRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    private void getData() {
        mRefreshLayout.setRefreshing(true);
        mBeans.clear();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"Spider Start  ---->");
                int index = 0 ;

                while(true){
                    try {

                        document = Jsoup.connect("https://beijing.douban.com/events/week-exhibition?start=" + index + "0")
//                                .proxy("222.74.225.231",3128)
                                .timeout(10000)
                                .get();
                        Elements li = document.select("li.list-entry");
                        if (li.size() == 0) {
                            break;
                        }

                        for (Element element : li) {
                            Elements meta = element.select("ul.event-meta");
                            if (meta.toString().equals("")) {
                                continue;
                            }
                            ExhibitionBean bean = new ExhibitionBean();
                            bean.setImgURL(element.select("img").attr("data-lazy")); // 图片链接
                            bean.setTitle(element.select("div.title").select("a").attr("title"));//标题
                            bean.setURL(element.select("div.title").select("a").attr("href"));//链接

                            Elements tagElements = element.select("p.event-cate-tag hidden-xs").select("a");
                            if (!tagElements.toString().equals("")) {
                                for (int i = 0; i<tagElements.size() ; i++){
                                    bean.setTag(tagElements.get(i).text());
                                }
                            }
                            bean.setTime(meta.select("li.event-time").text());
                            bean.setLocation(meta.select("li").get(1).text());
                            bean.setCost(meta.select("li.fee").select("strong").text());

                            System.out.println("***************************");
                            System.out.println(bean.getTitle());
                            System.out.println(bean.getTime());
                            System.out.println(bean.getImgURL());
                            System.out.println(bean.getURL());
                            System.out.println(bean.getLocation());
                            System.out.println(bean.getCost());

                            mBeans.add(bean);
                        }
                        index ++;
                        Thread.sleep(10000);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i(TAG, "run: " + e.getMessage());
                        break;
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

                Log.d(TAG,"Spider End  ---->");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setNewData(mBeans);
                        mRefreshLayout.setRefreshing(false);
                    }
                });

            }
        }).start();
    }

    private String timeChange(String time){
        String[] ts = time.split("T");
        String[] split = ts[1].split("\\+");
        return ts[0] + "    " +  split[0];
    }


}