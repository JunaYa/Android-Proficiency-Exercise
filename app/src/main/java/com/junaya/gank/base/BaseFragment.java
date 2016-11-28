package com.junaya.gank.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.junaya.gank.GankExceptionHandler;

/**
 * Created by aya on 2016/11/24.
 */

public class BaseFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new GankExceptionHandler(getActivity()));
    }
}
