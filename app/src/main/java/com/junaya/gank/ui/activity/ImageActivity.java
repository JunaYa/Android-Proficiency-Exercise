package com.junaya.gank.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.junaya.gank.R;
import com.junaya.gank.base.BaseActivity;
import com.junaya.gank.databinding.ActivityImageBinding;

import java.util.Arrays;
import java.util.List;

public class ImageActivity extends BaseActivity {

    private static final String ARG_IMAGES = "gank_images";
    private static final String ARG_TITLE = "gank_title";

    private ActivityImageBinding mBinding;

    private HighImagesAdapter mAdapter;

    private String[] mImages;

    private String mTitle;

    public static Intent newIntent(Context context, String[] strings, String title) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(ARG_IMAGES, strings);
        intent.putExtra(ARG_TITLE, title);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_image);

        mImages = getIntent().getStringArrayExtra(ARG_IMAGES);
        mTitle = getIntent().getStringExtra(ARG_TITLE);
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initTitle();

        mAdapter = new HighImagesAdapter(Arrays.asList(mImages));
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.indicator.setViewPager(mBinding.viewPager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initTitle() {
        mBinding.tvTitle.setFactory(() -> {
            TextView textView = new TextView(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textView.setTextAppearance(R.style.WebTitle);
            } else {
                textView.setTextAppearance(this, R.style.WebTitle);
            }
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setSingleLine(true);
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            textView.postDelayed(() -> textView.setSelected(true), 100);
            return textView;
        });

        mBinding.tvTitle.setInAnimation(this, android.R.anim.fade_in);
        mBinding.tvTitle.setOutAnimation(this, android.R.anim.fade_out);
        mBinding.tvTitle.setText(mTitle);
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

            final int itemsSize = items.size();
            int pos = position;
            if (position == 0) {
                pos = itemsSize - 1;
            } else if (position == itemsSize + 1) {
                pos = 0;
            } else {
                pos = position - 1;
            }

            View img = configFresco(container.getContext(), items.get(pos));
            container.addView(img);
            return img;
        }
    }

    // config SimpleDraweeView load gif
    private SimpleDraweeView configFresco(Context context, String url) {

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(url)
                .setAutoPlayAnimations(true)
                .build();

        LayoutParams sParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
        simpleDraweeView.setLayoutParams(sParams);
        simpleDraweeView.setAspectRatio(0.618f);
        simpleDraweeView.setController(controller);
        return simpleDraweeView;
    }

}
