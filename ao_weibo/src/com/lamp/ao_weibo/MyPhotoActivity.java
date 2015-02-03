package com.lamp.ao_weibo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.etsy.android.grid.StaggeredGridView;
import com.lamp.ao_weibo.adapter.MyPhotoAdapter;
import com.lamp.ao_weibo.utils.ImageLoaderutils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class MyPhotoActivity extends Activity implements OnItemClickListener {
	private StaggeredGridView gridview;
	private Context context=MyPhotoActivity.this;
	private MyPhotoAdapter adapter; 
	private ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_myphoto);
		SysApplication.getInstance().addActivity(this); 
		loader = ImageLoaderutils.getInstance(context);
		options = ImageLoaderutils.getOpt();
		initViews();
	}
	private void initViews() {
		gridview=(StaggeredGridView) findViewById(R.id.myphoto_gridview);
	    pd=new ProgressDialog(context);
	    pd.show();
		new Thread(){
	    	public void run() {
	    		adapter=new MyPhotoAdapter(context);
	    		handler.sendEmptyMessage(0);
	    	}
	    }.start();
	    gridview.setOnItemClickListener(this);
	    ab = new Builder(context);
	}
	
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			gridview.setAdapter(adapter);
			pd.dismiss();
		}
	};
	
	private AlertDialog.Builder ab;
	private ImageLoader loader;
	private DisplayImageOptions options;
	private ProgressBar pb;
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		View v = getLayoutInflater().inflate(R.layout.item_myphoto_dialog, null);
		ImageView iv = (ImageView) v.findViewById(R.id.myphoto_dialog_iv);
		pb = (ProgressBar) v.findViewById(R.id.myphoto_dialog_pb);
		ab.setView(v);
		ab.show();
		String img_url = adapter.getData().get(position).getBigpic();
		loader.displayImage(img_url, iv, options,new ImageLoadingListener() {
			@Override
			public void onLoadingStarted(String arg0, View arg1) {
			}
			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			}
			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				pb.setVisibility(View.GONE);
			}
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
			}
		});
	}
	
	public void backmy2Click(View v){
		finish();
	}
}
