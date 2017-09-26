package com.shijiacheng.observerpattern;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyListAdapter extends BaseAdapter {

	private Context mContext;
	private List<MyObserver> myObservers;

	public MyListAdapter(Context context, List<MyObserver> myObservers) {
		this.mContext = context;
		this.myObservers = myObservers;
	}

	@Override
	public int getCount() {
		return myObservers.size();
	}

	@Override
	public Object getItem(int position) {
		return myObservers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tvContent = new TextView(mContext);
		tvContent.setTextSize(16);
		String personContent = "内容还未添加";
		if (myObservers.get(position).getMyPerson() != null) {
			personContent = myObservers.get(position).getMyPerson().toString();
		}
		tvContent.setText("观察者ID：---->" + myObservers.get(position).getId()
				+ "\n" + "观察内容：" + personContent);
		return tvContent;
	}

}
