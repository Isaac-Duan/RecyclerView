package cn.lemon.recyclerview.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import alien95.cn.util.Utils;
import cn.lemon.recyclerview.R;
import cn.lemon.recyclerview.model.bean.Consumption;
import cn.lemon.view.RefreshRecyclerView;
import cn.lemon.view.adapter.Action;


public class MainActivity extends AppCompatActivity {

    private RefreshRecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private int page = 1;
    private Toolbar toolbar;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        setTitle("一卡通消费记录");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAdapter = new MyAdapter(this);
        //添加Header
        final TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dip2px(48)));
        textView.setTextSize(16);
        textView.setGravity(Gravity.CENTER);
        textView.setText("重庆邮电大学");
        mAdapter.setHeader(textView);
        //添加footer
        final TextView footer = new TextView(this);
        footer.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dip2px(48)));
        footer.setTextSize(16);
        footer.setGravity(Gravity.CENTER);
        footer.setText("我是Footer");
        mAdapter.setFooter(footer);

        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction() {
                getData(true);
            }
        });

        mRecyclerView.setLoadMoreAction(new Action() {
            @Override
            public void onAction() {
                getData(false);
                page++;
            }
        });

        getData(true);
    }

    public void getData(final boolean isRefresh) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    page = 1;
                    mAdapter.clear();
                    mAdapter.addAll(getVirtualData());
                    mRecyclerView.dismissRefresh();
                } else {
                    mAdapter.addAll(getVirtualData());
                    if (page >= 5) {
                        mRecyclerView.showNoMore();
                    }
                }
            }
        }, 2000);
    }

    public Consumption[] getVirtualData() {
        return new Consumption[]{
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼"),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼"),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼"),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼"),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼"),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼"),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼"),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼"),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼"),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼")
        };
    }

}