package com.lamp.ao_weibo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lamp.ao_weibo.model.User;
import com.lamp.ao_weibo.utils.ImageLoaderutils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserManageActivity extends Activity {
	private Context context = UserManageActivity.this;
	private ImageView iv;
	private TextView tv;
	private DbUtils db;
	private ImageLoader loader;
	private DisplayImageOptions options;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_usermanage);
		iv = (ImageView) findViewById(R.id.usermanage_iv);
		tv = (TextView) findViewById(R.id.usermanage_tv);
		db = DbUtils.create(context);
		loader = ImageLoaderutils.getInstance(context);
		options = ImageLoaderutils.getOpt();
		User user = null;
		try {
			user = db.findFirst(User.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		tv.setText(user.getUsername());	
		loader.displayImage(user.getHead_img(), iv, options);
	}
	
	public void backmoreClick(View v){
		finish();
	}
	
	public void exitClick(View v){
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("退出");
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setMessage("您确定要退出吗？");
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				HomeTabActivity.activity.finish();
			}
		});
		builder.show();
	}
	
}
