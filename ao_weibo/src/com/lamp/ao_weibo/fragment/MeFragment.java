package com.lamp.ao_weibo.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lamp.ao_weibo.R;
import com.lamp.ao_weibo.constants.Constant;
import com.lamp.ao_weibo.model.User;
import com.lamp.ao_weibo.utils.ImageLoaderutils;
import com.lamp.ao_weibo.utils.Tools;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MeFragment extends Fragment {
	private Context context;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	private ImageView icon;
	private TextView name,des,weibo,fensi,guanzhu;
	private DbUtils db;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_me, container,false);
		initview(v);
		initData();
		return v;
	}

	private ImageLoader loader;
	private DisplayImageOptions options;
	private void initData() {
		db = DbUtils.create(context);
		loader = ImageLoaderutils.getInstance(context);
		options = ImageLoaderutils.getOpt();
		try {
			User user = db.findFirst(User.class);
			loader.displayImage(user.getHead_img(), icon, options);
			name.setText(user.getUsername());
			des.setText(user.getDescription());
			new Thread(){
				public void run() {
					List<String> keys = new ArrayList<String>();
					List<String> values = new ArrayList<String>();
					keys.add("access_token");
					keys.add("uids");
					try {
						values.add(db.findFirst(User.class).getToken());
						values.add(db.findFirst(User.class).getUser_id());
						String result = Tools.sendGet(Constant.USER_COUNTS, keys , values, null);
						JSONArray ja = new JSONArray(result);
						JSONObject ja2 = ja.getJSONObject(0);
						HashMap<String,String> data = new HashMap<String,String>();
						data.put("fensi",ja2.getString("followers_count"));
						data.put("guanzhu",ja2.getString("friends_count"));
						data.put("weibo",ja2.getString("statuses_count"));
						Message msg = Message.obtain();
						msg.obj = data;
						handler.sendMessage(msg);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			HashMap<String,String> data = (HashMap<String, String>) msg.obj;
			weibo.setText(data.get("weibo"));
			fensi.setText(data.get("fensi"));
			guanzhu.setText(data.get("guanzhu"));
		}
	};

	private void initview(View v) {
		icon = (ImageView) v.findViewById(R.id.me_icon);
		name = (TextView) v.findViewById(R.id.me_name);
		des = (TextView) v.findViewById(R.id.me_des);
		weibo = (TextView) v.findViewById(R.id.me_weibo);
		fensi = (TextView) v.findViewById(R.id.me_fensi);
		guanzhu = (TextView) v.findViewById(R.id.me_guanzhu);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}  
}
