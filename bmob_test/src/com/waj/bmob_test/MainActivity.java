package com.waj.bmob_test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends Activity {
	private Person p2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Bmob.initialize(this, "2376d1116539b9401e7e4a6c3f952ba7");
	}
	
	public void addClick(View v){
		p2 = new Person();
		p2.setName("奥");
		p2.setAddress("北京昌平");
		p2.save(this, new SaveListener() {
		    @Override
		    public void onSuccess() {
		        toast("添加数据成功，返回objectId为："+p2.getObjectId());
		    }

			@Override
		    public void onFailure(int code, String msg) {
		        toast("创建数据失败：" + msg);
		    }
		});
	}

	public void findClick(View v){
		BmobQuery<Person> query = new BmobQuery<Person>();
		query.getObject(this, p2.getObjectId(), new GetListener<Person>(){
			@Override
			public void onFailure(int arg0, String arg1) {
				toast("查询失败！");
			}
			@Override
			public void onSuccess(Person arg0) {
				toast("查询成功！"+arg0.getAddress());
			}
			
		});
	}
	
	private void toast(String string) {
    	Toast.makeText(this, string, Toast.LENGTH_LONG).show();
	}
}
