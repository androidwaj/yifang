package com.lamp.ao_weibo.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lamp.ao_weibo.R;
import com.lamp.ao_weibo.constants.Constant;
import com.lamp.ao_weibo.model.User;
import com.lamp.ao_weibo.utils.ImageLoaderutils;
import com.lamp.ao_weibo.utils.Tools;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NewFriendsXlvAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ImageLoader loader;
	private DisplayImageOptions opt;
	private DbUtils db;
	private User user;
	private List<User> datas;
	private boolean issuccess=false;
	private int flag;
	public NewFriendsXlvAdapter(Context context,int flag) {
		this.context=context;
		this.flag = flag;
		inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		loader=ImageLoaderutils.getInstance(context);
		opt=ImageLoaderutils.getOpt();
		db=DbUtils.create(context);
		try {
			user=db.findFirst(User.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		getDatas();
	}
	private void getDatas() {
		datas=new ArrayList<User>();
		List<String> keys=new ArrayList<String>();
		keys.add("access_token");
		keys.add("category");
		List<String> values=new ArrayList<String>();
		values.add(user.getToken());
		values.add("games");
		String str=Tools.sendGet(Constant.TUIJIAN_HOT_URL, keys, values, null);
		datas=Tools.parseTuijian(str, flag);
	}
	@Override
	public int getCount() {
		return 6;
	}

	@Override
	public Object getItem(int arg0) {
		return datas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//不同position  不同布局
		if(position==0||position==2)
		{
			convertView=inflater.inflate(R.layout.item_newfriendxlv, null);
			TextView tv=(TextView) convertView.findViewById(R.id.item_newfriend_tv);
		    tv.setText(position==0?"新粉丝":"可能感兴趣的人");
		}else if(position==1)
		{
			convertView=inflater.inflate(R.layout.item_newfriend_fensi, null);
			
		}else if(position>2)
		{  
			convertView=inflater.inflate(R.layout.item_newfriend_tuijianuser, null);
			Button button=(Button) convertView.findViewById(R.id.item_newfriend_tuijianuser_guanzhu);
			TextView username=(TextView) convertView.findViewById(R.id.item_newfriend_tuijian_username);
			TextView des=(TextView) convertView.findViewById(R.id.item_newfriend_tuijian_description);
			ImageView img=(ImageView) convertView.findViewById(R.id.item_newfriend_tuijian_userimg);
			
			button.setOnClickListener(new TuijianListener(datas.get(position-3).getUser_id()));
		    username.setText(datas.get(position-3).getUsername());
		    des.setText(datas.get(position-3).getDescription());
		    loader.displayImage(datas.get(position-3).getHead_img(), img, opt);
		}
		return convertView;
	}
	class TuijianListener implements View.OnClickListener{
		private String uid; 
		private Handler handler=new Handler(){
			public void handleMessage(android.os.Message msg) {
				Toast.makeText(context, issuccess?"关注成功":"关注失败", Toast.LENGTH_SHORT).show();
			}
		};
		
		public TuijianListener(String uid) {
			this.uid=uid;
		}

		@Override
		public void onClick(View v) {
			new Thread(){
				public void run() {
					PostGuanzhu(uid);
					handler.sendEmptyMessage(0);
				}
			}.start();
		}
		
	}
	//关注逻辑
		private void PostGuanzhu(String uid) {
			List<String> keys=new ArrayList<String>();
		    keys.add("access_token");
		    keys.add("uid");
		    List<String> values=new ArrayList<String>();
		    try {
				values.add(db.findAll(User.class).get(0).getToken());
			} catch (DbException e) {
				e.printStackTrace();
			}
		    values.add(uid);
			String response=Tools.sendGuanZhuPost(Constant.GUANZHU_URL, keys, values);
			try {
				JSONObject jo=new JSONObject(response);
				if(jo.has("error"))
				{
					issuccess=false;
				}else{
					issuccess=true;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}


}
