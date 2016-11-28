package com.junaya.gank;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.junaya.gank.App;
import com.junaya.gank.module.activity.MainActivity;

/**
 * when app crash and restart
 * Created by aya on 2016/11/25.
 */

public class GankExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Activity mActivity;

    public GankExceptionHandler(Activity a) {
        mActivity = a;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Intent intent = new Intent(mActivity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(App.getInstance(), 1, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) App.getInstance().getBaseContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
        mActivity.finish();
        System.exit(2);
    }
}
