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

public class MyCommentsXlvAdapter extends BaseAdapter {
	private Context context;
	private DbUtils db;
	private LayoutInflater inflater;
	private ImageLoader loader;
	private DisplayImageOptions opt;
	private List<Mycomments> datas;
	public MyCommentsXlvAdapter(Context context) {
		this.context=context;
		inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		loader=ImageLoaderutils.getInstance(context);
		opt=ImageLoaderutils.getOpt();
		getDatas();
	}

	private void getDatas() {
		List<String> keys=new ArrayList<String>();
		keys.add("access_token");
		List<String> values=new ArrayList<String>();
		db=DbUtils.create(context);
		User user = null;
		try {
			user = db.findAll(User.class).get(0);
		} catch (DbException e) {
			e.printStackTrace();
		}
		values.add(user.getToken());
		String response=Tools.sendGet(Constant.BYME_URL, keys, values, null);
		datas=Tools.parseCommentsJson(response);//搞定数据源
		
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
		ViewHolder vh=null;
		if(convertView==null)
		{convertView=inflater.inflate(R.layout.item_mycomments_xlv, null);
		 vh=new ViewHolder();
		 vh.icon=(ImageView) convertView.findViewById(R.id.item_mycomments_icon)	;
		 vh.comment_text=(TextView) convertView.findViewById(R.id.item_mycomments_commenttext);
		 vh.source=(TextView) convertView.findViewById(R.id.item_mycomments_source);
		 vh.time=(TextView) convertView.findViewById(R.id.item_mycomments_time);
		 vh.username=(TextView) convertView.findViewById(R.id.item_mycomments_username);
		 vh.weibo_text=(TextView) convertView.findViewById(R.id.item_mycomments_weibotext);
		 convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		loader.displayImage(datas.get(position).getUserimg(), vh.icon, opt);
		vh.comment_text.setText(datas.get(position).getComment_text());
		vh.source.setText(datas.get(position).getSource());
		vh.time.setText(datas.get(position).getTime());
		vh.username.setText(datas.get(position).getUsername());
		vh.weibo_text.setText(datas.get(position).getWeibo_text());
		return convertView;
	}
	static class ViewHolder{
		ImageView icon;
		TextView username;
		TextView time;
		TextView source;
		TextView comment_text;
		TextView weibo_text;
	}

}
