package com.lizi.fast_fragment.widgets.pull;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lizi on 2016/11/11.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v, getAdapterPosition());
            }
        });
    }

    public abstract void onBindViewHolder(int position);
    public abstract void onItemClick(View view, int position);
}

