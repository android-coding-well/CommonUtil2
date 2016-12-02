package com.gosuncn.cu.module.recyclerview;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.core.widget.RecyclerViewExtend;
import com.gosuncn.cu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerViewExtend rvList;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    RecyclerAdapter adapter;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new RecyclerAdapter(this, list);
        rvList.setEmptyView(tvEmpty);
        rvList.setAdapter(adapter);
        rvList.setLoadingMode(RecyclerViewExtend.LOADINGMODE_SLIDE_TO_BOTTOM_AND_PULL_UP);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        // rvList.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //  rvList.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.VERTICAL,10, getResources().getColor(R.color.color_error)) );
        rvList.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, R.drawable.ic_list));
        rvList.setOnLoadingListener(new RecyclerViewExtend.OnLoadingListener() {
            @Override
            public void onLoading() {
                showShortToast("已经滚到底部,开始刷新数据");
            }
        });

    }

    public void onAddClick(View view) {
        list.add(Math.random() + "");
        adapter.notifyDataSetChanged();
        srlRefresh.setRefreshing(false);

    }

    public void onReduceClick(View view) {
        if(list.size()>0){
            list.remove(0);
            adapter.notifyDataSetChanged();
        }
        srlRefresh.setRefreshing(false);
    }

    @OnClick(R.id.tv_empty)
    public void onClick() {
        srlRefresh.setRefreshing(true);
        onAddClick(null);
    }
}
