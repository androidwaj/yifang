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
import com.lamp.ao_weibo.model.Mycomments;
import com.lamp.ao_weibo.model.User;
import com.lamp.ao_weibo.utils.ImageLoaderutils;
import com.lamp.ao_weibo.utils.Tools;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ToCommentsXlvAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ImageLoader loader;
	private DisplayImageOptions options;
	private List<Mycomments> datas;
	private DbUtils db;
	
	public ToCommentsXlvAdapter(Context context) {
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
		String res = Tools.sendGet(Constant.TOME_URL, keys, values, null);
		datas = Tools.parseCommentsJson(res);
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
		Mycomments comment = datas.get(position);
		if(convertView == null){
			vh = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_tocomments_xlv, null);
			vh.icon = (ImageView) convertView.findViewById(R.id.item_tocomments_icon);
			vh.username = (TextView) convertView.findViewById(R.id.item_tocomments_username);
			vh.time = (TextView) convertView.findViewById(R.id.item_tocomments_time);
			vh.source = (TextView) convertView.findViewById(R.id.item_tocomments_source);
			vh.comment_text = (TextView) convertView.findViewById(R.id.item_tocomments_commenttext);
			vh.weibo_text = (TextView) convertView.findViewById(R.id.item_tocomments_weibotext);
			convertView.setTag(comment);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		loader.displayImage(comment.getUserimg(), vh.icon, options);
		vh.username.setText(comment.getUsername());
		vh.time.setText(comment.getTime());
		vh.source.setText(comment.getSource());
		vh.comment_text.setText(comment.getComment_text());
		vh.weibo_text.setText(comment.getWeibo_text());
		return convertView;
	}
	
	static class ViewHolder{
		ImageView icon;
		TextView username,time,source,comment_text,weibo_text;
	}
}
