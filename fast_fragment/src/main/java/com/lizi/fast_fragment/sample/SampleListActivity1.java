package com.lizi.fast_fragment.sample;

import android.os.Bundle;

import com.lizi.fast_fragment.R;
import com.lizi.fast_fragment.core.BaseActivity;

public class SampleListActivity1 extends BaseActivity {

    private SampleListFragment mSampleListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_sample_list_1, R.string.title_recycler_fragment);
    }

    @Override
    protected void setUpView() {
        mSampleListFragment = new SampleListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mSampleListFragmentLayout, mSampleListFragment).commit();
    }

    @Override
    protected void setUpData() {
    }
}

