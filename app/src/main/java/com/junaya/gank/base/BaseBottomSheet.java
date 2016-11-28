package com.junaya.gank.base;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.design.widget.BottomSheetDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.junaya.gank.R;


/**
 * Created by aya on 2016/11/18.
 */

public abstract class BaseBottomSheet {

    protected BottomSheetDialog mSheetDialog;
    protected Context mContext;

    protected BaseBottomSheet(Context context) {
        mContext = context;
        View view = rootView();
        initView(view);
        mSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogStyle);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mSheetDialog.setContentView(view, params);
        mSheetDialog.setCanceledOnTouchOutside(true);
        mSheetDialog.setCancelable(false);
    }

    abstract protected
    @LayoutRes
    int LayoutResId();

    protected View rootView() {
        return LayoutInflater.from(mContext).inflate(LayoutResId(), null);
    }

    abstract public void initView(View view);

    abstract public void onShow();

    abstract public void onDismiss();

}
