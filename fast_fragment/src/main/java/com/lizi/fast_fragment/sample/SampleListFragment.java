package com.lizi.fast_fragment.sample;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lizi.fast_fragment.R;
import com.lizi.fast_fragment.core.BaseListFragment;
import com.lizi.fast_fragment.widgets.pull.BaseViewHolder;
import com.lizi.fast_fragment.widgets.pull.ILayoutManager;
import com.lizi.fast_fragment.widgets.pull.MyGridLayoutManager;
import com.lizi.fast_fragment.widgets.pull.PullRecycler;

import java.util.ArrayList;

/**
 * Created by lizi on 2016/11/12.
 */

public class SampleListFragment extends BaseListFragment<String> {
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sample_list_item, parent, false);
        return new SampleViewHolder(view);
    }

    @Override
    protected ILayoutManager getLayoutManager() {
        MyGridLayoutManager layoutManager = new MyGridLayoutManager(getContext(), 3);
        return layoutManager;
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler.setRefreshing();
    }

    //    @Override
//    protected ILayoutManager getLayoutManager() {
//        return new MyStaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//    }

    @Override
    public void onRefresh(final int action) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }

        recycler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
                    mDataList.clear();
                }
                int size = mDataList.size();
                for (int i = size; i < size + 20; i++) {
                    mDataList.add("sample list item " + i);
                }
                adapter.notifyDataSetChanged();
                recycler.onRefreshCompleted();
                if (mDataList.size() < 100) {
                    recycler.enableLoadMore(true);
                } else {
                    recycler.enableLoadMore(false);
                }
            }
        }, 3000);
    }

    class SampleViewHolder extends BaseViewHolder {

        TextView mSampleListItemLabel;

        public SampleViewHolder(View itemView) {
            super(itemView);
            mSampleListItemLabel = (TextView) itemView.findViewById(R.id.mSampleListItemLabel);
        }

        @Override
        public void onBindViewHolder(int position) {
            mSampleListItemLabel.setText(mDataList.get(position));
        }

        @Override
        public void onItemClick(View view, int position) {

        }

    }
}
