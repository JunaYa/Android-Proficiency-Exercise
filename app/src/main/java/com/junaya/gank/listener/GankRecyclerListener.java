package com.junaya.gank.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by aya on 2016/11/26.
 */

public abstract class GankRecyclerListener extends RecyclerView.OnScrollListener {

    private int mFirstVisibleItem;

    private int mLastVisibleItem;


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (mLastVisibleItem + 1 == recyclerView.getAdapter().getItemCount()) {
                loadMore();
            }

            int centerPosition = ((int)(mLastVisibleItem + mFirstVisibleItem)/2);
            showGif(mFirstVisibleItem,mLastVisibleItem);
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mLastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findLastCompletelyVisibleItemPosition();

        recyclerView.findViewHolderForLayoutPosition(0);
        mFirstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findFirstCompletelyVisibleItemPosition();
    }

    public abstract void loadMore();

    public abstract void showGif(int firstVisibleItem,int lastVisibleItem);

}
