package com.lamp.ao_weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lamp.ao_weibo.model.User;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.exception.DbException;

public class LoginActivity extends Activity {
	private Context context = LoginActivity.this;
	private User user;
	
	private ImageView icon;
	private TextView des;
	private Spinner name;
	private ArrayAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_login);
		DbUtils dbUtils = DbUtils.create(context);
		try {
			user = dbUtils.findFirst(User.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		initView();
		initData();
	}
	
	private void initData() {
		BitmapUtils bitmapUtils = new BitmapUtils(context);
		bitmapUtils.display(icon, user.getHead_img(), new BitmapLoadCallBack<ImageView>() {
			@Override
			public void onLoadCompleted(ImageView container, String uri,
					Bitmap bitmap, BitmapDisplayConfig config,
					BitmapLoadFrom from) {
				icon.setImageBitmap(bitmap);
				icon.setAnimation(AnimationUtils.loadAnimation(context, R.anim.login_set));
			}

			@Override
			public void onLoadFailed(ImageView container, String uri,
					Drawable drawable) {
			}
		});
		des.setText(user.getDescription());
		adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item,new String[]{user.getUsername()});
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		name.setAdapter(adapter);
	}

	private void initView() {
		icon = (ImageView) findViewById(R.id.login_icon);
		des = (TextView) findViewById(R.id.login_des);
		name = (Spinner) findViewById(R.id.login_name);
	}
	
	public void loginClick(View v){
		startActivity(new Intent(context,HomeTabActivity.class));
		finish();
	}
	
	public void cancelClick(View v){
		finish();
	}

	private void toast(String string) {
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
	}
}
