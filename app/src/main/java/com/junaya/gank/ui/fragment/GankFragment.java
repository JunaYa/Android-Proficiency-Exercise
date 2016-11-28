package com.junaya.gank.ui.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junaya.gank.R;
import com.junaya.gank.base.BaseFragment;
import com.junaya.gank.databinding.FragmentGankBinding;
import com.junaya.gank.listener.GankRecyclerListener;
import com.junaya.gank.data.Gank;
import com.junaya.gank.data.remote.GankRetrofit;
import com.junaya.gank.ui.adapter.GankAdapter;
import com.junaya.gank.widget.InsertDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by aya on 2016/11/24.
 */

public class GankFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String GANK_TYPE = "gank_type";

    private FragmentGankBinding mBinding;

    private int mPage = 1;
    private String mType;

    private List<Gank> mGanks = new ArrayList<>();

    private GankAdapter mGAdapter;

    public static GankFragment newFragment(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(GANK_TYPE, str);
        GankFragment gankFragment = new GankFragment();
        gankFragment.setArguments(bundle);
        return gankFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gank, container, false);
        mBinding = DataBindingUtil.bind(rootView);

        mBinding.refresh.setColorSchemeResources(R.color.colorPrimary);
        mBinding.refresh.setOnRefreshListener(this);

        mType = getArguments().getString(GANK_TYPE);

        iniRecyclerView();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // if no data to get
        if (mGanks.size() == 0) {
            mBinding.refresh.post(() -> {
                mBinding.refresh.setRefreshing(true);
                onRefresh();
            });
        }
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        getData(mType, mPage);
    }

    private void iniRecyclerView() {

        mGAdapter = new GankAdapter(mGanks);

        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setAdapter(mGAdapter);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerView.addItemDecoration(new InsertDecoration(getActivity()));
        mBinding.recyclerView.addOnScrollListener(new IGankRecyclerListener());
    }

    /**
     * fetch gank data
     *
     * @param type request type
     * @param page request page
     */
    private void getData(final String type, final int page) {
        GankRetrofit.getGankApi()
                .getGank(type, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listResults -> {
                    mBinding.refresh.setRefreshing(false);
                    if (page == 1) mGanks.clear();
                    mGanks.addAll(listResults.results);
                    mBinding.refresh.postDelayed(() -> {
                        mGAdapter.setLoadMore(false);
                        mGAdapter.notifyDataSetChanged();
                    }, 500);

                }, throwable -> {
                    mBinding.refresh.setRefreshing(false);
                    if (mPage > 1) return;
                    Snackbar.make(mBinding.rootView, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.fetch_again), v -> {
                                mBinding.refresh.setRefreshing(true);
                                onRefresh();
                            }).show();

                });
    }

    private void onLoadMore() {
        if (mGanks.size() > 0) {
            mGAdapter.setLoadMore(true);
            mGAdapter.notifyDataSetChanged();
            mPage += 1;
            getData(mType, mPage);
        }
    }

    private class IGankRecyclerListener extends GankRecyclerListener {
        @Override
        public void loadMore() {
            onLoadMore();
        }

        @Override
        public void showGif(int first, int last) {
        }
    }

}
