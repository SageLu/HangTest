package com.lizi.fast_fragment.widgets.pull;

import android.support.v7.widget.RecyclerView;

/**
 * Created by lizi on 2016/11/12.
 */

//策略模式，把 findLastVisiblePosition 看成一个算法
public interface ILayoutManager {

    RecyclerView.LayoutManager getLayoutManager();

    int findLastVisiblePosition();
    void setUpAdapter(BaseListAdapter adapter);
}
