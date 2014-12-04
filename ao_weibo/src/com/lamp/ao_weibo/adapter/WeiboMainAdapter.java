package com.lamp.ao_weibo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lamp.ao_weibo.R;
import com.lamp.ao_weibo.constants.Constant;
import com.lamp.ao_weibo.model.InfoList;
import com.lamp.ao_weibo.model.User;
import com.lamp.ao_weibo.model.Weiboinfo;
import com.lamp.ao_weibo.utils.ImageLoaderutils;
import com.lamp.ao_weibo.utils.Tools;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class WeiboMainAdapter extends BaseAdapter{
	private List<InfoList> datas;
	private Context context;
	private LayoutInflater inflater;
	private Weiboinfo weiboinfo;
	private DbUtils db;
	private ImageLoader loader;
	private DisplayImageOptions opt;
	public WeiboMainAdapter(Context context,Weiboinfo weiboinfo) {
		this.context=context;
		this.weiboinfo= weiboinfo;
		db=DbUtils.create(context);
		inflater = LayoutInflater.from(context);
		loader=ImageLoaderutils.getInstance(context);
	    opt=ImageLoaderutils.getOpt();
		initDatas();
	}

	private void initDatas() {
		List<String> keys = new ArrayList<String>();
		keys.add("access_token");
		keys.add("id");
		List<String> values = new ArrayList<String>();
		String token=null;
		try {
			token=db.findFirst(User.class).getToken();
		} catch (DbException e) {
			e.printStackTrace();
		}
		values.add(token);
		values.add(weiboinfo.getWeibo_id()+"");
		String res=Tools.sendGet(Constant.pinglun_url, keys, values, null);
		datas = Tools.ParseInfoList(res);
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
		viewHolder vh =null;
		if(convertView ==null){
			convertView = inflater.inflate(R.layout.item_infolist_lv, null);
			vh = new viewHolder();
			vh.icon = (ImageView) convertView.findViewById(R.id.item_infolist_icon);
			vh.name = (TextView) convertView.findViewById(R.id.item_infolist_username);
			vh.time = (TextView) convertView.findViewById(R.id.item_infolist_time);
			vh.text = (TextView) convertView.findViewById(R.id.item_infolist_content);
			convertView.setTag(vh);
		}
		vh = (viewHolder) convertView.getTag();
		InfoList pl=  datas.get(position);
		loader.displayImage(pl.getInfo_icon(), vh.icon, opt);
		vh.name.setText(pl.getInfo_name());
		vh.time.setText(pl.getInfo_time());
		vh.text.setText(pl.getInfo_content());
		return convertView;
	}
	static class viewHolder{
		ImageView icon;
		TextView name,time,text;
	}
	
}
