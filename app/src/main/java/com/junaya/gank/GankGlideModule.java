package com.junaya.gank;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

import okhttp3.OkHttpClient;


/**
 * Created by aya on 2016/11/25.
 */

public class GankGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

        //动态值，应用 可用内存的1/8
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 8);

//        Glide.get(context).setMemoryCategory(MemoryCategory.HIGH);

        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        int sizeInBytes = 1024 * 1024 * 100;
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, sizeInBytes));


    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // register ModelLoaders here.

//        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(Client.configClient());
//        glide.register(GlideUrl.class, InputStream.class, factory);

        OkHttpClient client = new OkHttpClient();
        GlideProgressSupport.init(glide, client);
    }
}
