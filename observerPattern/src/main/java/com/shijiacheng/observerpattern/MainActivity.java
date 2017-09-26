package com.shijiacheng.observerpattern;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

	private Button btnAddObserver;
	private Button btnChangeData;
	private MyPerson observable;
	private MyObserver myObserver;
	private List<MyObserver> myObservers;
	private ListView listview;

	private int i;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			MyListAdapter myListAdapter = new MyListAdapter(MainActivity.this,
					myObservers);
			listview.setAdapter(myListAdapter);

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnAddObserver = (Button) findViewById(R.id.btn_add_observer);
		btnChangeData = (Button) findViewById(R.id.btn_change_data);
		listview = getListView();

		observable = new MyPerson();
		myObservers = new ArrayList<MyObserver>();

		btnAddObserver.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myObserver = new MyObserver(i);
				i++;
				observable.addObserver(myObserver);
				myObservers.add(myObserver);
				handler.sendEmptyMessage(0);
			}
		});

		btnChangeData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				observable.setName("a" + i);
				observable.setAge(10 + i);
				observable.setSex("ÄÐ" + i);
				handler.sendEmptyMessage(0);
			}
		});

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		observable.deleteObserver(myObserver);
	}
}
