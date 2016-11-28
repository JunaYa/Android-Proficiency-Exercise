package com.junaya.gank.module.adapter;

import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.junaya.gank.data.Gank;
import com.junaya.gank.module.activity.ImageActivity;

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
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(items.get(pos))
                .setAutoPlayAnimations(true)
                .setControllerListener(controllerListener)
                .build();

        LayoutParams sParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        SimpleDraweeView img = new SimpleDraweeView(container.getContext());
        img.setLayoutParams(sParams);
        img.setAspectRatio(0.618f);
        img.setController(controller);
        img.setOnClickListener(v -> {
            String[] strings = new String[items.size()];
            for (int i = 0; i < items.size(); i++) {
                strings[i] = items.get(i).toString();
            }
            container.getContext()
                .startActivity(ImageActivity.newIntent(container.getContext(), strings, mGank.desc));

        });


//        ImageView img = new ImageView(container.getContext());
//        img.setEnabled(true);
//        img.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        img.setLayoutParams(params);
//        img.setOnClickListener(v -> {
//            String[] strings = new String[items.size()];
//            for (int i = 0; i < items.size(); i++) {
//                strings[i] = items.get(i).toString();
//            }
//            container.getContext()
//                    .startActivity(ImageActivity.newIntent(container.getContext(), strings, mGank.desc));
//        });



//        // save discharge for gank
//        StringBuilder builder = new StringBuilder();
//        builder.append(items.get(pos));
//        builder.append("?imageView2/0/w/300");
//
//        Glide.with(container.getContext())
//                .load(builder.toString())
//                .asBitmap()
//                .into(img);
        container.addView(img);
        return img;
    }

    public void setGifAnimStart(boolean b) {
        isGifAnim = b;
    }
}
