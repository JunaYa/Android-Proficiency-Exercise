package com.junaya.gank.module.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junaya.gank.R;
import com.junaya.gank.base.BaseViewHolder;
import com.junaya.gank.data.Gank;

import java.util.List;

/**
 * Created by aya on 2016/11/27.
 */

public class GankAdapter extends RecyclerView.Adapter<BaseViewHolder<Gank>> {

    private static final int TYPE_PEER_TEXT = 0;
    private static final int TYPE_TEXT_IMAGE = 1;
    private static final int TYPE_SEARCH_ITEM = 2;
    private static final int TYPE_LOAD_MORE = 3;

    private List<Gank> mGanks;

    private boolean loadMore;

    public GankAdapter(List<Gank> ganks) {
        mGanks = ganks;
    }

    @Override
    public BaseViewHolder<Gank> onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_PEER_TEXT:
                View peerTextView = inflater.inflate(R.layout.item_gank_peer_text, parent, false);
                return new PeerTextViewHolder(peerTextView);
            case TYPE_TEXT_IMAGE:
                View textImageView = inflater.inflate(R.layout.item_gank_text_image, parent, false);
                return new TextImageViewHolder(textImageView);
            case TYPE_SEARCH_ITEM:
                View searchTextView = inflater.inflate(R.layout.item_gank_peer_text, parent, false);
                return new SearchViewHolder(searchTextView);
            default:
                View loadMoreView = inflater.inflate(R.layout.item_gank_load_more, parent, false);
                return new LoadViewHolder(loadMoreView) {
                };
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<Gank> holder, int position) {
        if (getItemViewType(position) == TYPE_LOAD_MORE) return;
        else {
            holder.bindViewHolder(mGanks.get(position));
        }
    }

    @Override
    public int getItemCount() {
        int count = mGanks == null ? 0 : mGanks.size();
        return loadMore ? count + 1 : count;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (loadMore && position == mGanks.size()) {
            type = TYPE_LOAD_MORE;
        } else {
            type = mGanks.get(position).getType();
        }
        return type;
    }

    public void setLoadMore(boolean loadMore) {
        this.loadMore = loadMore;
    }


}
