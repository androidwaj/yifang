package com.lamp.ao_weibo.fragment;

import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lamp.ao_weibo.R;
import com.lamp.ao_weibo.UserManageActivity;
import com.lamp.ao_weibo.customview.CustomListView;

public class MoreFragment extends Fragment implements OnItemClickListener {
	private Context context;
	private ProgressDialog pd;
	private SharedPreferences.Editor editor;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
		pd=new ProgressDialog(activity);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setTitle("请稍等!");
		pd.setIcon(android.R.drawable.ic_delete);
		pd.setProgress(0);
		pd.setMax(100);
		
		editor = context.getSharedPreferences("aoweibo", Context.MODE_PRIVATE).edit();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	private CustomListView clv;
	private String[] datas={"账户管理","快速滚动关闭","加载更多关闭","清除缓存"};
	private ArrayAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_more, container,false);
		clv = (CustomListView) v.findViewById(R.id.more_lv);
		adapter = new ArrayAdapter(context, R.layout.item_more_lv, R.id.item_more_lv_tv, datas);
		clv.setAdapter(adapter);
		clv.setOnItemClickListener(this);
		return v;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	private boolean loadenable=true,fastenable=true;
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 3:
			pd.show();
			new Thread(){
				public void run() {
					File file=new File("/mnt/sdcard/lampweibo/cache");
					if(file==null)
					{
						handler.sendEmptyMessage(1);
						return;
					}
					File files[]=file.listFiles();
					
					int filecount=files.length;
					if(filecount==0)
					{
						handler.sendEmptyMessage(1);
					}
					for (int i = 0; i < files.length; i++) {
						files[i].delete();
						Message msg=Message.obtain();
						msg.what=0;
						msg.arg1=100*(i+1)/filecount;
						handler.sendMessage(msg);
					}
				}
			}.start();
			break;
		case 0:
			startActivity(new Intent(context,UserManageActivity.class));
			break;
		case 1:
			fastenable=!fastenable;
			editor.putBoolean("fastenable", fastenable);
			String str1=fastenable?"开启成功!":"关闭成功!";
			Toast.makeText(context, str1, Toast.LENGTH_SHORT).show();
			editor.commit();
			break;
		case 2:
			loadenable=!loadenable;
			editor.putBoolean("loadenable", loadenable);
			editor.commit(); 
            String str=loadenable?"开启成功!":"关闭成功!";
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				if(msg.arg1>=99)
					pd.dismiss();
				else pd.setProgress(msg.arg1);
				break;
			case 1:
				pd.dismiss();
				break;
			}
		}
	};
	
}
