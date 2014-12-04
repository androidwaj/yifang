package com.lamp.ao_weibo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lamp.ao_weibo.constants.Constant;
import com.lamp.ao_weibo.model.User;
import com.lamp.ao_weibo.utils.Tools;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

public class SendWeiboActivity extends Activity {
	private Context context = SendWeiboActivity.this;
	private DbUtils db;
	private boolean isSucess;
	
	private EditText sendweibo_content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_sendweibo);
		sendweibo_content = (EditText) findViewById(R.id.sendweibo_content);
		db = DbUtils.create(context);
	}
	
	public void sendClick(View v){
		String content = sendweibo_content.getText().toString().trim();
		final List<String> keys=new ArrayList<String>();
		final List<String> values = new ArrayList<String>();
		keys.add("status");
		keys.add("access_token");
		values.add(content);
		try {
			values.add(db.findFirst(User.class).getToken());
		} catch (DbException e1) {
			e1.printStackTrace();
		}
		new Thread(){
			public void run() {
				String result = Tools.sendPost(Constant.SEND_WEIBO, keys, values);
				try {
					JSONObject jsonObject = new JSONObject(result);
					if(jsonObject.has("error")){
						isSucess = false;
					}else{
						isSucess = true;
					}
					handler.sendEmptyMessage(0);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(isSucess)
			{
				toast("发送成功!");
				sendweibo_content.setText("");
			}else{
				toast("发送失败!");
			}
		}
	};

	protected void toast(String string) {
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
	}
	
}
