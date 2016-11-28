package com.junaya.gank.module.gank;

import android.content.Context;
import android.support.v4.widget.PopupWindowCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.junaya.gank.R;

/**
 * Created by aya on 2016/11/25.
 */

public class ImageFragmentDialog {

    private PopupWindowCompat mPopupWindowCompat;

    private Context mContext;

    public ImageFragmentDialog(Context context) {
        mContext = context;

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_sheet_image, null);

    }


}
