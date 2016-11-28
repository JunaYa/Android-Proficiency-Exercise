package com.junaya.gank.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.junaya.gank.data.Gank;
import com.junaya.gank.ui.activity.ImageActivity;

import java.util.List;

/**
 * Created by aya on 2016/11/25.
 */

public class ImagesAdapter extends PagerAdapter {

    private List<String> items;
    private Gank mGank;
    private boolean isGifAnim;

    public ImagesAdapter(Gank gank) {
        super();
        mGank = gank;
        this.items = gank.images;
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

        String[] strings = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            strings[i] = items.get(i).toString();
        }
        View img = configFresco(container.getContext(),items.get(pos),strings,mGank.desc);
        container.addView(img);
        return img;
    }

    // config SimpleDraweeView load gif
    private SimpleDraweeView configFresco(final Context context, final String url, final String[] urlArr, final String desc) {
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(
                    String id,
                    @Nullable ImageInfo imageInfo,
                    @Nullable Animatable anim) {
                if (anim != null) {
                    anim.start();
                }
            }
        };

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(url);
        urlBuilder.append("?imageView2/0/w/300");

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(urlBuilder.toString())
                .setAutoPlayAnimations(true)
                .setControllerListener(controllerListener)
                .build();

        LayoutParams sParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        SimpleDraweeView img = new SimpleDraweeView(context);
        img.setLayoutParams(sParams);
        img.setAspectRatio(0.618f);
        img.setController(controller);
        img.setOnClickListener(v -> {

            context.startActivity(ImageActivity.newIntent(context, urlArr, desc));

        });
        return img;
    }

    public void setGifAnimStart(boolean b) {
        isGifAnim = b;
    }
}
