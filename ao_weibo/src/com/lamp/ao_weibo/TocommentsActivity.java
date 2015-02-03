package com.lamp.ao_weibo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.lamp.ao_weibo.adapter.MyCommentsXlvAdapter;
import com.lamp.ao_weibo.adapter.ToCommentsXlvAdapter;
import com.lamp.ao_weibo.customview.XListView;
import com.lamp.ao_weibo.customview.XListView.IXListViewListener;

public class TocommentsActivity extends Activity implements IXListViewListener{
	private Context context = TocommentsActivity.this;
	private XListView tocomments_xlv;
	private ProgressDialog pd;
	private ToCommentsXlvAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this); 
		setContentView(R.layout.act_tocomments);
		tocomments_xlv = (XListView) findViewById(R.id.tocomments_xlv);
		initData();
	}

	public void back3Click(View v){
		finish();
	}
	
	private void initData() {
		pd = new ProgressDialog(context);
		pd.show();
		tocomments_xlv.setPullLoadEnable(false);
		new Thread(){
			public void run() {
				adapter = new ToCommentsXlvAdapter(context);
				handler.sendEmptyMessage(0);
			}
		}.start();
		tocomments_xlv.setXListViewListener(this);
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				tocomments_xlv.setAdapter(adapter);
				pd.dismiss();
				break;
			case 1:
				tocomments_xlv.setAdapter(adapter);
				tocomments_xlv.stopRefresh();
				break;
			}
		}
	};

	@Override
	public void onRefresh() {
		new Thread(){
			public void run() {
				adapter = new ToCommentsXlvAdapter(context);
				handler.sendEmptyMessage(1);
			}
		}.start();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
}
