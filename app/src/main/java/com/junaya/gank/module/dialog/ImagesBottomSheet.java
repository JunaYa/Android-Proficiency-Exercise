package com.junaya.gank.module.dialog;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.junaya.gank.R;
import com.junaya.gank.base.BaseBottomSheet;
import com.junaya.gank.databinding.DialogSheetImageBinding;
import com.junaya.gank.module.adapter.ImagesAdapter;
import com.junaya.gank.widget.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aya on 2016/11/27.
 */

public class ImagesBottomSheet extends BaseBottomSheet {


    private DialogSheetImageBinding mBinding;
    private ImagesAdapter mImagesAdapter;

    private List<String> mImages = new ArrayList<>();

    private CirclePageIndicator mIndicator;

    public ImagesBottomSheet(Context context) {
        super(context);
    }

    public void setImages(List<String> images) {
        this.mImages = images;
        mImagesAdapter.setItems(images);
        mImagesAdapter.notifyDataSetChanged();
    }

    @Override
    protected int LayoutResId() {
        return R.layout.dialog_sheet_image;
    }

    @Override
    public void initView(View view) {
        mBinding = DataBindingUtil.bind(view);
        mImagesAdapter = new ImagesAdapter(mImages);

        mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        mBinding.viewPager.setAdapter(mImagesAdapter);
        mIndicator.setViewPager(mBinding.viewPager);
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        mBinding.btnClose.setOnClickListener(v -> onDismiss());
    }

    @Override
    public void onShow() {
        checkOK();
        mSheetDialog.show();
    }

    @Override
    public void onDismiss() {
        checkOK();
        mSheetDialog.dismiss();
    }

    public void checkOK() {
        if (mImagesAdapter == null)
            return;
    }
}
