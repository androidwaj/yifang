package com.lamp.ao_weibo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lamp.ao_weibo.auth.AsyncWeiboRunner;
import com.lamp.ao_weibo.auth.RequestListener;
import com.lamp.ao_weibo.constants.Constant;
import com.lamp.ao_weibo.model.User;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.WeiboParameters;
import com.sina.weibo.sdk.exception.WeiboException;

public class AuthActivity extends Activity {
	private Context context = AuthActivity.this;
	private DbUtils db;
	private AlertDialog.Builder ab;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_auth);
		SysApplication.getInstance().addActivity(this); 
		db = DbUtils.create(context);
		ab = new Builder(context);
		ab.setIcon(android.R.drawable.ic_dialog_alert);
		ab.setTitle("通知");
		ab.setMessage("您尚未认证,请先去认证！");
		ab.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				authrize();
			}
		});
		ab.show();
	}
	
	protected void authrize() {
		WeiboAuth weiboAuth = new WeiboAuth(context, Constant.APPKEY, Constant.REDIRECT_URL, "all");
		weiboAuth.authorize(new WeiboAuthListener() {
			@Override
			public void onWeiboException(WeiboException arg0) {
			}
			
			@Override
			public void onComplete(Bundle arg0) {
				String code = arg0.getString("code");
				WeiboParameters params = new WeiboParameters();
				params.add("client_id", Constant.APPKEY);
				params.add("client_secret", Constant.APP_SECRET);
				params.add("grant_type", "authorization_code");
				params.add("code", code);
				params.add("redirect_uri", Constant.REDIRECT_URL);
				AsyncWeiboRunner.request(Constant.AUTH_URL, params, "POST", new RequestListener() {
					@Override
					public void onIOException(IOException e) {
					}
					
					@Override
					public void onError(WeiboException e) {
					}
					
					@Override
					public void onComplete4binary(ByteArrayOutputStream responseOS) {
					}
					
					@Override
					public void onComplete(String response) {
						Oauth2AccessToken token = new Oauth2AccessToken(response);
						final String access_token = token.getToken();
						final String uid = token.getUid();
						HttpUtils httpUtils = new HttpUtils();
						httpUtils.send(HttpMethod.GET, Constant.USERS_SHOW+"?access_token="+access_token+"&uid="+uid, new RequestCallBack<String>() {
							@Override
							public void onSuccess(ResponseInfo<String> responseInfo) {
								try {
									JSONObject jsonObject = new JSONObject(responseInfo.result);
									User user = new User();
									user.setUser_id(uid);
									user.setToken(access_token);
									user.setUsername(jsonObject.getString("name"));
									user.setDescription(jsonObject.getString("description"));
									user.setHead_img(jsonObject.getString("profile_image_url"));
									db.save(user);
									handler.sendEmptyMessage(1);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							
							@Override
							public void onFailure(HttpException error,String msg) {
							}
						});
					}
				});
			}
			@Override
			public void onCancel() {
			}
		}, WeiboAuth.OBTAIN_AUTH_CODE);
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			startActivity(new Intent(context,LoginActivity.class));
			finish();
		}
	};
}
