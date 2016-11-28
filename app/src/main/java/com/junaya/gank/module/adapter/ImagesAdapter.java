package com.junaya.gank.module.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.junaya.gank.listener.RItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aya on 2016/11/25.
 */

public class ImagesAdapter extends PagerAdapter {

    private List<String> items = new ArrayList<>();

    private RItemClickListener mRItemClickListener;

    public ImagesAdapter(List<String> images) {
        super();
        this.items = images;
    }

    public void setRItemClickListener(RItemClickListener RItemClickListener) {
        mRItemClickListener = RItemClickListener;
    }

    public void setItems(List<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        final int itemsSize = items == null ? 0 : items.size();
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

        ImageView img = new ImageView(container.getContext());
        img.setEnabled(true);
        img.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        img.setLayoutParams(params);
        img.setOnClickListener(v -> {

            if (mRItemClickListener != null) {
                mRItemClickListener.onItemClick(items);
            }
        });

        // save discharge for gank
        StringBuilder builder = new StringBuilder();
        builder.append(items.get(pos));
        builder.append("?imageView2/0/w/300");

        Glide.with(container.getContext())
                .load(builder.toString())
                .asBitmap()
                .into(img);
        container.addView(img);
        return img;
    }

}
