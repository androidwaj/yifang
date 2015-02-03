package com.lamp.ao_weibo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.lamp.ao_weibo.adapter.HotnewsAdapter;
import com.lamp.ao_weibo.customview.XListView;
import com.lamp.ao_weibo.customview.XListView.IXListViewListener;

public class HotnewsActivity extends Activity implements IXListViewListener {
	private Context context = HotnewsActivity.this;
	private XListView hotnews_xlv;
	private ProgressDialog pd;
	private HotnewsAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_hotnews);
		SysApplication.getInstance().addActivity(this); 
		hotnews_xlv = (XListView) findViewById(R.id.hotnews_xlv);
		initData();
	}

	private void initData() {
		pd = new ProgressDialog(context);
		pd.show();
		new Thread(){
			public void run() {
				adapter = new HotnewsAdapter(context);
				handler.sendEmptyMessage(0);
			}
		}.start();
		hotnews_xlv.setPullLoadEnable(false);
		hotnews_xlv.setXListViewListener(this);
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				hotnews_xlv.setAdapter(adapter);
				pd.dismiss();
				break;
			case 1:
				hotnews_xlv.setAdapter(adapter);
				hotnews_xlv.stopRefresh();
				break;
			}
		}
	};
	
	public void back4Click(View v){
		finish();
	}

	@Override
	public void onRefresh() {
		new Thread(){
			public void run() {
				adapter = new HotnewsAdapter(context);
				handler.sendEmptyMessage(1);
			}
		}.start();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
}
