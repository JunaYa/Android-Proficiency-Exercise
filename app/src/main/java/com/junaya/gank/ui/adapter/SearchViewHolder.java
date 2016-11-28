package com.junaya.gank.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.junaya.gank.R;
import com.junaya.gank.base.BaseViewHolder;
import com.junaya.gank.data.Gank;
import com.junaya.gank.ui.activity.WebActivity;

/**
 * Created by aya on 2016/11/27.
 */

public class SearchViewHolder extends BaseViewHolder<Gank> {

    private TextView mDesc;
    private TextView mWho;
    private TextView mPublishAt;

    public SearchViewHolder(View itemView) {
        super(itemView);

        mDesc = (TextView) itemView.findViewById(R.id.desc);
        mWho = (TextView) itemView.findViewById(R.id.who);
        mPublishAt = (TextView) itemView.findViewById(R.id.publishAt);
    }

    @Override
    public void bindViewHolder(Gank gank) {
        // need design
        String readability = Html.fromHtml(gank.readability).toString();
        mDesc.setText(gank.desc);
        mWho.setText(gank.who);
        mPublishAt.setText(gank.getPublishedAt());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = itemView.getContext();
                context.startActivity(WebActivity.newIntent(context, gank.url, gank.desc));
            }
        });
    }
}
