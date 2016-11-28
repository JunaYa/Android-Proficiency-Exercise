package com.junaya.gank.module.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.junaya.gank.R;
import com.junaya.gank.base.BaseActivity;
import com.junaya.gank.databinding.ActivityMainBinding;
import com.junaya.gank.module.adapter.TabPagerAdapter;


public class MainActivity extends BaseActivity {

    private ActivityMainBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mBinding.toolbar);

        mBinding.viewPager.setAdapter(new TabPagerAdapter( getSupportFragmentManager()));
        mBinding.viewPager.setOffscreenPageLimit(2);
        mBinding.tabs.setupWithViewPager(mBinding.viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_search){
            item.setIcon(R.drawable.btn_search);
            startActivity(SearchActivity.newIntent(this));
        }
        return super.onOptionsItemSelected(item);
    }
}
