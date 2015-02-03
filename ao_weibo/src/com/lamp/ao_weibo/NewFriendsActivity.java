package com.lamp.ao_weibo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.lamp.ao_weibo.adapter.NewFriendsXlvAdapter;
import com.lamp.ao_weibo.customview.XListView;
import com.lamp.ao_weibo.customview.XListView.IXListViewListener;

public class NewFriendsActivity extends Activity implements IXListViewListener {
	private Context context = NewFriendsActivity.this;
	private XListView xlv;
	private NewFriendsXlvAdapter adapter;
	private ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_newfriends);
		SysApplication.getInstance().addActivity(this); 
		xlv = (XListView) findViewById(R.id.newfriends_xlv);
		xlv.setPullLoadEnable(false);
		new Thread(){
			public void run() {
				adapter = new NewFriendsXlvAdapter(context,flag);
				handler.sendEmptyMessage(0);
			}
		}.start();
		pd = new ProgressDialog(context);
		pd.show();
		xlv.setXListViewListener(this);
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				xlv.setAdapter(adapter);
				pd.dismiss();
				break;
			case 1:
				xlv.setAdapter(adapter);
				xlv.stopRefresh();
				break;
			}
		}
	};
	
	public void backmeClick(View v){
		finish();
	}

	private int flag;
	@Override
	public void onRefresh() {
		new Thread(){
			public void run() {
				flag++;
				adapter = new NewFriendsXlvAdapter(context,flag);
				handler.sendEmptyMessage(1);
			}
		}.start();
	}

	@Override
	public void onLoadMore() {
		
	}
}
