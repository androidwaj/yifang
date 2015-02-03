package com.lamp.ao_weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lamp.ao_weibo.adapter.HomeFragmentPagerAdapter;

public class HomeTabActivity extends FragmentActivity implements OnPageChangeListener {
	private Context context = HomeTabActivity.this;
	private TextView home_tv,message_tv,me_tv,more_tv;
	private TextView[] textViews = new TextView[4];
	private ImageView home_iv,message_iv,me_iv,more_iv;
	private ImageView[] imageViews = new ImageView[4];
	private ViewPager vp;
	private HomeFragmentPagerAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this); 
		setContentView(R.layout.act_hometab);
		initView();
		initData();
	}

	private void initData() {
		home_iv.setSelected(true);
		imageViews[0] = home_iv;
		imageViews[1] = message_iv;
		imageViews[2] = me_iv;
		imageViews[3] = more_iv;
		textViews[0] = home_tv;
		textViews[1] = message_tv;
		textViews[2] = me_tv;
		textViews[3] = more_tv;
		adapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
		vp.setAdapter(adapter);
		vp.setOnPageChangeListener(this);
	}

	private void initView() {
		home_tv = (TextView) findViewById(R.id.hometab_home_tv);
		message_tv = (TextView) findViewById(R.id.hometab_message_tv);
		me_tv = (TextView) findViewById(R.id.hometab_me_tv);
		more_tv = (TextView) findViewById(R.id.hometab_more_tv);
		home_iv = (ImageView) findViewById(R.id.hometab_home_iv);
		message_iv = (ImageView) findViewById(R.id.hometab_message_iv);
		me_iv = (ImageView) findViewById(R.id.hometab_me_iv);
		more_iv = (ImageView) findViewById(R.id.hometab_more_iv);
		vp = (ViewPager) findViewById(R.id.hometab_vp);
	}
	
	public void homeClick(View v){
		changeSelected(0);
		vp.setCurrentItem(0);
	}
	public void messageClick(View v){
		changeSelected(1);
		vp.setCurrentItem(1);
	}
	public void meClick(View v){
		vp.setCurrentItem(2);
		changeSelected(2);
	}
	public void moreClick(View v){
		changeSelected(3);
		vp.setCurrentItem(3);
	}
	
	public void sendWeiboClick(View v){
		startActivity(new Intent(context,SendWeiboActivity.class));
	}
	
	public void myClick(View v){
		Class cla = null;
		switch (v.getId()) {
		case R.id.me_newfriends:
			cla = NewFriendsActivity.class;
			break;
		case R.id.myphoto:
			cla = MyPhotoActivity.class;
			break;
		case R.id.mycollect:
			cla = MyCollectActivity.class;
			break;
		}
		startActivity(new Intent(context,cla));
	}

	private void changeSelected(int index) {
		for (int i = 0; i < 4; i++) {
			if(i == index){
				textViews[i].setTextColor(Color.parseColor("#FF9224"));
				imageViews[i].setSelected(true);
			}else{
				textViews[i].setTextColor(Color.parseColor("#8E8E8E"));
				imageViews[i].setSelected(false);
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		changeSelected(arg0);
	}
	
}
