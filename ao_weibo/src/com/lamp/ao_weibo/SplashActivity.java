package com.lamp.ao_weibo;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.lamp.ao_weibo.model.User;
import com.lamp.ao_weibo.utils.Tools;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

public class SplashActivity extends Activity implements AnimationListener {
	private Context context = SplashActivity.this;
	private ImageView icon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_splash);
		SysApplication.getInstance().addActivity(this); 
		icon = (ImageView) findViewById(R.id.splash_icon);
		AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(context, R.anim.splash_set);
		icon.setAnimation(animationSet);
		animationSet.setAnimationListener(this);
	}
	@Override
	public void onAnimationStart(Animation animation) {
		boolean flag = Tools.isConnect(context);
		if(!flag){
			toast("请打开网络后再开启应用！");
			//System.exit(-1);
			finish();
		}
	}
	@Override
	public void onAnimationEnd(Animation animation) {
		DbUtils db = DbUtils.create(context);
		List<User> users = null;
		try {
			users = db.findAll(User.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		if(users == null){
			startActivity(new Intent(context,AuthActivity.class));
		}else{
			startActivity(new Intent(context,LoginActivity.class));
		}
		finish();
	}
	@Override
	public void onAnimationRepeat(Animation animation) {
		
	}
	
	public void toast(String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}
