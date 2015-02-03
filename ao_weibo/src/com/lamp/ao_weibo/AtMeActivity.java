package com.lamp.ao_weibo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.lamp.ao_weibo.adapter.AtmeXlvAdapter;
import com.lamp.ao_weibo.customview.XListView;
import com.lamp.ao_weibo.customview.XListView.IXListViewListener;

public class AtMeActivity extends Activity implements IXListViewListener {
	private Context context = AtMeActivity.this;
	private AtmeXlvAdapter adapter;
	
	private TextView atme_title;
	private XListView atme_xlv;
	private ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_atme);
		SysApplication.getInstance().addActivity(this); 
		atme_title = (TextView) findViewById(R.id.atme_title);
		atme_xlv = (XListView) findViewById(R.id.atme_xlv);
		initData();
	}

	private void initData() {
		atme_title.setText("@我的");
		new Thread(){
			public void run() {
				adapter = new AtmeXlvAdapter(context);
				handler.sendEmptyMessage(0);
			}
		}.start();
		pd = new ProgressDialog(context);
		pd.show();
		atme_xlv.setPullLoadEnable(false);
		atme_xlv.setXListViewListener(this);
	}
	
	public void backClick(View v){
		finish();
	}
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				atme_xlv.setAdapter(adapter);
				pd.dismiss();
				break;
			case 1:
				atme_xlv.setAdapter(adapter);
				atme_xlv.stopRefresh();
				break;
			case 2:
				adapter.notifyDataSetChanged();
				atme_xlv.stopLoadMore();
				break;
			}
		}
	};

	@Override
	public void onRefresh() {
		new Thread(){
			public void run() {
				adapter = new AtmeXlvAdapter(context);
				handler.sendEmptyMessage(1);
			}
		}.start();
	}

	@Override
	public void onLoadMore() {
		new Thread(){
			public void run() {
				adapter.loadNewData();
				handler.sendEmptyMessage(2);	
			}
		}.start();
	}
}
