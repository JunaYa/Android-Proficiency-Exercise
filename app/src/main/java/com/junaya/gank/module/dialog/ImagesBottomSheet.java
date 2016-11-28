package com.junaya.gank.module.dialog;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.junaya.gank.R;
import com.junaya.gank.base.BaseBottomSheet;
import com.junaya.gank.databinding.DialogSheetImageBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aya on 2016/11/27.
 */

public class ImagesBottomSheet extends BaseBottomSheet {


    private DialogSheetImageBinding mBinding;
    private HighImagesAdapter mImagesAdapter;

    private List<String> mImages = new ArrayList<>();


    public ImagesBottomSheet(Context context) {
        super(context);
    }

    @Override
    protected int LayoutResId() {
        return R.layout.dialog_sheet_image;
    }

    @Override
    public void initView(View view) {
        mBinding = DataBindingUtil.bind(view);
        mImagesAdapter = new HighImagesAdapter(mImages);

        mBinding.viewPager.setAdapter(mImagesAdapter);
        mBinding.indicator.setViewPager(mBinding.viewPager);
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


    public class HighImagesAdapter extends PagerAdapter {

        private List<String> items;


        public HighImagesAdapter(List<String> images) {
            super();
            this.items = images;
        }

        @Override
        public int getCount() {
            final int itemsSize = (items == null ? 0 : items.size());
            return itemsSize > 1 ? itemsSize + 2 : itemsSize;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) instantiateItem(container, position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView img = new ImageView(container.getContext());
            img.setEnabled(true);
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            img.setLayoutParams(params);

            // save discharge for gank
            StringBuilder builder = new StringBuilder();
            builder.append(items.get(position));
            builder.append("?imageView2/0/w/600");

            Glide.with(container.getContext())
                    .load(builder.toString())
                    .asBitmap()
                    .into(img);
            container.addView(img);
            return img;
        }

    }

}
