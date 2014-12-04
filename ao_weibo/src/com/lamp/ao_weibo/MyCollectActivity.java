package com.lamp.ao_weibo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.lamp.ao_weibo.adapter.MyCollectXlvAdapter;
import com.lamp.ao_weibo.customview.XListView;
import com.lamp.ao_weibo.customview.XListView.IXListViewListener;

public class MyCollectActivity extends Activity implements IXListViewListener {
	private Context context = MyCollectActivity.this;
	private XListView xlv;
	private MyCollectXlvAdapter adapter;
	private ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_mycollect);
		initView();
	}
	
	private void initView() {
		xlv = (XListView) findViewById(R.id.mycollect_xlv);
		xlv.setPullLoadEnable(false);
		pd = new ProgressDialog(context);
		pd.show();
		new Thread(){
			public void run() {
				adapter = new MyCollectXlvAdapter(context);
				handler.sendEmptyMessage(0);
			}
		}.start();
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
	
	public void backmy4Click(View v){
		finish();
	}

	@Override
	public void onRefresh() {
		new Thread(){
			public void run() {
				adapter = new MyCollectXlvAdapter(context);
				handler.sendEmptyMessage(1);
			}
		}.start();
	}

	@Override
	public void onLoadMore() {
	}
}
