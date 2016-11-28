package com.junaya.gank.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by aya on 2016/11/27.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public Context mContext;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
    }
    public abstract void bindViewHolder(T t);
}
