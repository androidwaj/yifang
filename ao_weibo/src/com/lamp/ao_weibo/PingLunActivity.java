package com.lamp.ao_weibo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lamp.ao_weibo.constants.Constant;
import com.lamp.ao_weibo.utils.Tools;

public class PingLunActivity extends Activity {
	private Context context = PingLunActivity.this;
	private boolean isSucess;
	
	private EditText pinglunweibo_content;
	private long id;
	private String access_token;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_pinglunweibo);
		pinglunweibo_content = (EditText) findViewById(R.id.pinglunweibo_content);
		id = getIntent().getLongExtra("id", 0);
		access_token = getIntent().getStringExtra("access_token");
	}
	
	public void pinglunClick(View v){
		String content = pinglunweibo_content.getText().toString().trim();
		final List<String> keys=new ArrayList<String>();
		final List<String> values = new ArrayList<String>();
		keys.add("access_token");
		keys.add("id");
		keys.add("comment");
		values.add(access_token);
		values.add(id+"");
		values.add(content);
		new Thread(){
			public void run() {
				String result = Tools.sendPost(Constant.PINGLUN_WEIBO, keys, values);
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
				toast("评论成功!");
				pinglunweibo_content.setText("");
			}else{
				toast("评论失败!");
			}
		}
	};

	protected void toast(String string) {
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
	}
	
}
