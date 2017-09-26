package com.lizi.fast_fragment.widgets.pull;

import android.support.v7.widget.GridLayoutManager;


/**
 * Created by lizi on 2016/11/12.
 */

//用于处理加载更多时，固定LOAD_MORE单独一行所用
public class FooterSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private BaseListAdapter adapter;
    private int spanCount;//当前RecyclerView一行要显示几列

    public FooterSpanSizeLookup(BaseListAdapter adapter, int spanCount) {
        this.adapter = adapter;
        this.spanCount = spanCount;
    }

    @Override
    public int getSpanSize(int position) {
        if (adapter.isLoadMoreFooter(position)) {
            return spanCount;
        }
        return 1;
    }
}
