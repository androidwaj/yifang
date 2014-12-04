package com.lamp.ao_weibo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.lamp.ao_weibo.adapter.MyCommentsXlvAdapter;
import com.lamp.ao_weibo.customview.XListView;
import com.lamp.ao_weibo.customview.XListView.IXListViewListener;

public class MycommentsActivity extends Activity implements IXListViewListener{
	private Context context = MycommentsActivity.this;
	private XListView mycomments_xlv;
	private ProgressDialog pd;
	private MyCommentsXlvAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_mycomments);
		mycomments_xlv = (XListView) findViewById(R.id.mycomments_xlv);
		initData();
	}

	public void back2Click(View v){
		finish();
	}
	
	private void initData() {
		pd = new ProgressDialog(context);
		pd.show();
		mycomments_xlv.setPullLoadEnable(false);
		new Thread(){
			public void run() {
				adapter = new MyCommentsXlvAdapter(context);
				handler.sendEmptyMessage(0);
			}
		}.start();
		mycomments_xlv.setXListViewListener(this);
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				mycomments_xlv.setAdapter(adapter);
				pd.dismiss();
				break;
			case 1:
				mycomments_xlv.setAdapter(adapter);
				mycomments_xlv.stopRefresh();
				break;
			}
		}
	};

	@Override
	public void onRefresh() {
		new Thread(){
			public void run() {
				adapter = new MyCommentsXlvAdapter(context);
				handler.sendEmptyMessage(1);
			}
		}.start();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
}
