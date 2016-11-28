package com.junaya.gank;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.junaya.gank.ui.activity.MainActivity;

import java.lang.ref.WeakReference;

/**
 * when app crash and restart
 * Created by aya on 2016/11/25.
 */

public class GankExceptionHandler implements Thread.UncaughtExceptionHandler {
    private WeakReference<Activity> mActivity;

    public GankExceptionHandler(Activity a) {
        mActivity = new WeakReference<Activity>(a);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Intent intent = new Intent(mActivity.get(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(App.getInstance(), 1, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) App.getInstance().getBaseContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 500, pendingIntent);
        mActivity.get().finish();
        System.exit(2);
    }
}
