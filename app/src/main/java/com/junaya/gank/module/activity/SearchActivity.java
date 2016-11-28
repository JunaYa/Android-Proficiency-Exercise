package com.junaya.gank.module.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.junaya.gank.R;
import com.junaya.gank.base.BaseActivity;
import com.junaya.gank.databinding.ActivitySearchBinding;
import com.junaya.gank.listener.GankRecyclerListener;
import com.junaya.gank.data.Gank;
import com.junaya.gank.data.remote.GankRetrofit;
import com.junaya.gank.module.adapter.GankAdapter;
import com.junaya.gank.widget.InsertDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchActivity extends BaseActivity {

    private ActivitySearchBinding mBinding;

    private List<Gank> mGanks = new ArrayList<>();

    private GankAdapter mAdapter;

    private int mPage = 1;

    private String mType ;

    private String mKeyWords;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBinding.tabs.addTab(mBinding.tabs.newTab().setText("Android"));
        mBinding.tabs.addTab(mBinding.tabs.newTab().setText("iOS"));
        mBinding.tabs.addTab(mBinding.tabs.newTab().setText("前端"));

        mBinding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mType = tab.getText().toString();
                if (mKeyWords != null) {
                    getData(mKeyWords, mType, mPage);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        iniRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item_search:
                mType = mBinding.tabs.getTabAt(mBinding.tabs.getSelectedTabPosition()).getText().toString();
                mKeyWords = mBinding.etSearch.getText().toString().trim();
                getData(mKeyWords, mType, mPage);
                break;
        }

        return true;
    }


    private void iniRecyclerView() {

        mAdapter = new GankAdapter(mGanks);
        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.addItemDecoration(new InsertDecoration(this));
        mBinding.recyclerView.addOnScrollListener(new IGankRecyclerListener());
    }

    /**
     * fetch gank data
     *
     * @param type request type
     * @param page request page
     */
    private void getData(final String keyWords, final String type, final int page) {
        GankRetrofit.getGankApi()
                .getSearch(keyWords, type, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listResults -> {
                    if (page == 1) {
                        mGanks.clear();
                    }
                    mGanks.addAll(listResults.results);
                    mAdapter.setLoadMore(false);
                    mAdapter.notifyDataSetChanged();
                }, throwable -> {
                    String s = throwable.getMessage();
                });
    }

    private void onLoadMore() {
        if (mGanks.size() > 0) {
            mAdapter.setLoadMore(true);
            mAdapter.notifyDataSetChanged();
            mPage += 1;
            getData(mKeyWords, mType, mPage);
        }
    }

    private class IGankRecyclerListener extends GankRecyclerListener {
        @Override
        public void loadMore() {
            onLoadMore();
        }

        @Override
        public void showGif(int first, int last) {
        }
    }

}
