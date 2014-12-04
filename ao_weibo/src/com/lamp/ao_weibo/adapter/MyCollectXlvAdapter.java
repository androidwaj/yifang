package com.lamp.ao_weibo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

public class MyCollectXlvAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ImageLoader loader;
	private DisplayImageOptions options;
	private List<Weiboinfo> datas;
	private DbUtils db;
	
	public MyCollectXlvAdapter(Context context) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
		loader = ImageLoaderutils.getInstance(context);
		options = ImageLoaderutils.getOpt();
		db = DbUtils.create(context);
		getData();
	}

	private void getData() {
		User user = null;
		try {
			user = db.findFirst(User.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		List<String> keys = new ArrayList<String>();
		keys.add("access_token");
		List<String> values = new ArrayList<String>();
		values.add(user.getToken());
		String res = Tools.sendGet(Constant.FAVORITES_URL, keys, values, null);
		datas = Tools.parseFavoritesJson(res);
	}

	@Override
	public int getCount() {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		Weiboinfo weiboinfo = datas.get(position);
		if(convertView == null){
			vh = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_mycollect_xlv, null);
			vh.icon = (ImageView) convertView.findViewById(R.id.item_mycollect_icon);
			vh.username = (TextView) convertView.findViewById(R.id.item_mycollect_username);
			vh.time = (TextView) convertView.findViewById(R.id.item_mycollect_time);
			vh.source = (TextView) convertView.findViewById(R.id.item_mycollect_source);
			vh.text = (TextView) convertView.findViewById(R.id.item_mycollect_text);
			vh.pic = (ImageView) convertView.findViewById(R.id.item_mycollect_pic);
			convertView.setTag(weiboinfo);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
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
		return convertView;
	}
	
	static class ViewHolder{
		ImageView icon,pic;
		TextView username,time,source,text;
	}
}
