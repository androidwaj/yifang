package com.lamp.ao_weibo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lamp.ao_weibo.adapter.WeiboMainAdapter;
import com.lamp.ao_weibo.model.Weiboinfo;
import com.lamp.ao_weibo.utils.ImageLoaderutils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class WeiboMainActivity extends Activity{
	private Context context = WeiboMainActivity.this;
	private Weiboinfo weiboinfo;
	private ListView lv;
	private ImageView act_weibomain_icon,act_weibomain_pic;
	private TextView act_weibomain_username,act_weibomain_time,act_weibomain_text,act_weibomain_zhuanfa,act_weibomain_weibomain,act_weibomain_zan;
	private ImageLoader loader;
	private DisplayImageOptions opt;
	private WeiboMainAdapter adapter;
	private ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_weibomain);
		Intent intent = this.getIntent();
		weiboinfo=(Weiboinfo) intent.getExtras().get("Weiboinfo");
		loader=ImageLoaderutils.getInstance(context);
	    opt=ImageLoaderutils.getOpt();
	    pd = new ProgressDialog(context);
	    pd.show();
		initViews();
		setData();
	}
	private void setData() {
		loader.displayImage(weiboinfo.getWeibo_user_img(), act_weibomain_icon, opt);
		act_weibomain_username.setText(weiboinfo.getWeibo_user_name());
		act_weibomain_time.setText(weiboinfo.getWeibo_time());
		act_weibomain_text.setText(weiboinfo.getWeibo_text());
		act_weibomain_zhuanfa.setText(weiboinfo.getWeibo_zhuanfa()+"");
		act_weibomain_weibomain.setText(weiboinfo.getWeibo_pinglun()+"");
		act_weibomain_zan.setText(weiboinfo.getWeibo_zan()+"");
		if(weiboinfo.getWeibo_spic()==null)
		{
			act_weibomain_pic.setVisibility(View.GONE);
		}else{
			loader.displayImage(weiboinfo.getWeibo_mpic(),act_weibomain_pic, opt);
		}
	}
	private void initViews() {
		lv = (ListView) findViewById(R.id.item_weibomain_lv);
		act_weibomain_icon = (ImageView) findViewById(R.id.item_weibomain_icon);
		act_weibomain_pic = (ImageView) findViewById(R.id.item_weibomain_pic);
		
		act_weibomain_username = (TextView) findViewById(R.id.item_weibomain_username);
		act_weibomain_time = (TextView) findViewById(R.id.item_weibomain_time);
		act_weibomain_text = (TextView) findViewById(R.id.item_weibomain_text);
		act_weibomain_zhuanfa = (TextView) findViewById(R.id.item_weibomain_zhuanfa);
		act_weibomain_weibomain = (TextView) findViewById(R.id.item_weibomain_pinglun);
		act_weibomain_zan = (TextView) findViewById(R.id.item_weibomain_zan);
		new Thread(){
			public void run() {
				adapter = new WeiboMainAdapter(context,weiboinfo);
				handler.sendEmptyMessage(0);
			}
		}.start();
		
	}
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			lv.setAdapter(adapter);
			pd.dismiss();
		}
	};
	
	public void backHomeClick(View v){
		finish();
	}
}
