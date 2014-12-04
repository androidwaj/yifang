package com.lamp.ao_weibo.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lamp.ao_weibo.R;
import com.lamp.ao_weibo.constants.Constant;
import com.lamp.ao_weibo.model.User;
import com.lamp.ao_weibo.model.Weiboinfo;
import com.lamp.ao_weibo.utils.ImageLoaderutils;
import com.lamp.ao_weibo.utils.Tools;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HotnewsAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ImageLoader loader;
	private DisplayImageOptions options;
	private List<Weiboinfo> datas;
	private DbUtils db;
	private boolean issuccess;
	private Handler handler;
	
	public HotnewsAdapter(Context context) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
		loader = ImageLoaderutils.getInstance(context);
		options = ImageLoaderutils.getOpt();
		db = DbUtils.create(context);
		getData();
	}

	private void getData() {
		try {
			List<String> keys = new ArrayList<String>();
			keys.add("access_token");
			List<String> values = new ArrayList<String>();
			values.add(db.findFirst(User.class).getToken());
			String res = Tools.sendGet(Constant.HOTNEWS_URL, keys, values, null);
			datas = Tools.getWeiboinfos(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getCount() {
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				String str=null;
				if(issuccess)
					str="关注成功!";
				else str="关注失败!";
				Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
				super.handleMessage(msg);
			}
		};
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vh = new ViewHolder();
		Weiboinfo weiboinfo = datas.get(position);
		convertView = inflater.inflate(R.layout.item_hotnews_xlv, null);
		vh.icon = (ImageView) convertView.findViewById(R.id.item_hotnews_icon);
		vh.username = (TextView) convertView.findViewById(R.id.item_hotnews_username);
		vh.time = (TextView) convertView.findViewById(R.id.item_hotnews_time);
		vh.source = (TextView) convertView.findViewById(R.id.item_hotnews_source);
		vh.text = (TextView) convertView.findViewById(R.id.item_hotnews_text);
		vh.pic = (ImageView) convertView.findViewById(R.id.item_hotnews_pic);
		vh.zhuanfa = (TextView) convertView.findViewById(R.id.item_hotnews_zhuanfa);
		vh.pinglun = (TextView) convertView.findViewById(R.id.item_hotnews_pinglun);
		vh.zan = (TextView) convertView.findViewById(R.id.item_hotnews_zan);
		vh.guanzhu = (Button) convertView.findViewById(R.id.hotnews_xlv_guanzhu);
		
		loader.displayImage(weiboinfo.getWeibo_user_img(), vh.icon, options);
		if(weiboinfo.getWeibo_spic() == null){
			vh.pic.setVisibility(View.GONE);
		}else{
			vh.pic.setVisibility(View.VISIBLE);
			loader.displayImage(weiboinfo.getWeibo_spic(), vh.pic, options);
		}
		vh.username.setText(weiboinfo.getWeibo_user_name());
		vh.time.setText(weiboinfo.getWeibo_time());
		vh.source.setText(weiboinfo.getWeibo_source());
		vh.text.setText(weiboinfo.getWeibo_text());
		vh.zhuanfa.setText(weiboinfo.getWeibo_zhuanfa()+"");
		vh.pinglun.setText(weiboinfo.getWeibo_pinglun()+"");
		vh.zan.setText(weiboinfo.getWeibo_zan()+"");
		vh.guanzhu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(){
					public void run() {
						try {
						long uid = Tools.name2uid(db.findFirst(User.class).getToken(),datas.get(position).getWeibo_user_name());
						guanzhu(uid);
						} catch (DbException e) {
							e.printStackTrace();
						}
					}
				}.start();
			}
		});
		return convertView;
	}

	protected void guanzhu(long uid) {
		List<String> keys=new ArrayList<String>();
	    keys.add("access_token");
	    keys.add("uid");
	    List<String> values=new ArrayList<String>();
	    try {
			values.add(db.findFirst(User.class).getToken());
		} catch (DbException e) {
			e.printStackTrace();
		}
	    values.add(uid+"");
		String response=Tools.sendGuanZhuPost(Constant.GUANZHU_URL, keys, values);
		try {
			JSONObject jo=new JSONObject(response);
			if(jo.has("error"))//如果返回结果有error 就是关注失败
			{
				issuccess=false;
			}else{
				issuccess=true;
			}
			handler.sendEmptyMessage(0);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	static class ViewHolder{
		ImageView icon,pic;
		TextView username,time,source,text,zhuanfa,pinglun,zan;
		Button guanzhu;
	}
}
