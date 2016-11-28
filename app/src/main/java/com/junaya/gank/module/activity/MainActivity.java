package com.junaya.gank.module.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.junaya.gank.R;
import com.junaya.gank.base.BaseActivity;
import com.junaya.gank.databinding.ActivityMainBinding;
import com.junaya.gank.module.adapter.TabPagerAdapter;
import com.junaya.gank.module.fragment.GankFragment;


public class MainActivity extends BaseActivity {

    private ActivityMainBinding mBinding;

    private TabPagerAdapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mBinding.toolbar);

        mAdapter = new TabPagerAdapter(getSupportFragmentManager());
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.viewPager.setOffscreenPageLimit(0);
        mBinding.tabs.setupWithViewPager(mBinding.viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_search) {
            item.setIcon(R.drawable.btn_search);
            startActivity(SearchActivity.newIntent(this));
        }
        return super.onOptionsItemSelected(item);
    }
}
