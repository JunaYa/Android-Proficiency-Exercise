package com.junaya.gank.module.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

import com.junaya.gank.base.BaseViewHolder;
import com.junaya.gank.data.Gank;
import com.junaya.gank.databinding.ItemGankTextImageBinding;
import com.junaya.gank.listener.RItemClickListener;
import com.junaya.gank.module.activity.GankDetailActivity;

import java.lang.ref.WeakReference;

/**
 * Created by aya on 2016/11/27.
 */

public class TextImageViewHolder extends BaseViewHolder<Gank> {

    private ItemGankTextImageBinding mBinding;

    private Handler mMHandler;

    private GankRunnable mMRunnable;

    private RItemClickListener mRItemClickListener;

    public void setRItemClickListener(RItemClickListener RItemClickListener) {
        mRItemClickListener = RItemClickListener;
    }


    public TextImageViewHolder(View itemView) {
        super(itemView);
        mBinding = DataBindingUtil.bind(itemView);
    }

    @Override
    public void bindViewHolder(Gank gank) {

        mBinding.desc.setText(gank.desc);
        mBinding.who.setText(gank.who);
        mBinding.publishAt.setText(gank.getPublishedAt());
        ImagesAdapter imagesAdapter = new ImagesAdapter(gank.images);
        imagesAdapter.setRItemClickListener(mRItemClickListener);
        mBinding.viewPager.setAdapter(imagesAdapter);
        mBinding.indicator.setViewPager(mBinding.viewPager);
        mBinding.viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                final int lastPosition = mBinding.viewPager.getAdapter().getCount() - 1;
                if (position == 0) {
                    mBinding.viewPager.setCurrentItem(lastPosition == 0 ? 0 : lastPosition - 1, false);
                } else if (position == lastPosition) {
                    mBinding.viewPager.setCurrentItem(1, false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        if (mMHandler == null) {
            mMHandler = new Handler();
        }
        if (mMRunnable == null) {
            mMRunnable = new GankRunnable(mMHandler, mBinding.viewPager);
            mMHandler.removeCallbacks(mMRunnable);
            mMHandler.postDelayed(mMRunnable, 5000);
        }

        mBinding.itemRl.setOnClickListener(v -> {
            Context context = itemView.getContext();
            context.startActivity(GankDetailActivity.newIntent(context, gank.url, gank.desc));
        });

    }

    // loop play images
    // it often GC and null
    private static class GankRunnable implements Runnable {
        private static WeakReference<Handler> mWeakReference;
        private static WeakReference<ViewPager> mPagerProvider;

        public GankRunnable(Handler weakReference, ViewPager viewHolder) {
            mWeakReference = new WeakReference<Handler>(weakReference);
            mPagerProvider = new WeakReference<ViewPager>(viewHolder);
        }

        @Override
        public void run() {
            if (mPagerProvider == null || mWeakReference == null) {
                return;
            }
            int currentPos = mPagerProvider.get().getCurrentItem();
            int count = mPagerProvider.get().getAdapter().getCount();
            int nextPos = (currentPos + 1) % count;
            mPagerProvider.get().setCurrentItem(nextPos, false);
            mWeakReference.get().postDelayed(this, 5000);
        }
    }

}
