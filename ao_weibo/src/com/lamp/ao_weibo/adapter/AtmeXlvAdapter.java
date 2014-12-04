package com.lamp.ao_weibo.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
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

public class AtmeXlvAdapter extends BaseAdapter{
	private Context context;
	private LayoutInflater inflater;
	private List<Weiboinfo> datas;
	private ImageLoader loader;
	private DisplayImageOptions opt;
	private User user;
	
	public AtmeXlvAdapter(Context context) {
		super();
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		loader=ImageLoaderutils.getInstance(context);
		opt=ImageLoaderutils.getOpt();
		getData();
	}

	private void getData() {
		DbUtils db = DbUtils.create(context);
		try {
			user = db.findFirst(User.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		try {
			List<String> keys = new ArrayList<String>();
			List<String> values = new ArrayList<String>();
			keys.add("access_token");
			values.add(user.getToken());
			datas = Tools.getWeiboinfos(Tools.sendGet(Constant.ATME_URL, keys , values, null));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			convertView = inflater.inflate(R.layout.item_atme_xlv, null);
			vh.icon = (ImageView) convertView.findViewById(R.id.item_atme_icon);
			vh.username = (TextView) convertView.findViewById(R.id.item_atme_username);
			vh.time = (TextView) convertView.findViewById(R.id.item_atme_time);
			vh.source = (TextView) convertView.findViewById(R.id.item_atme_source);
			vh.text = (TextView) convertView.findViewById(R.id.item_atme_text);
			vh.pic = (ImageView) convertView.findViewById(R.id.item_atme_pic);
			vh.zhuanfa = (TextView) convertView.findViewById(R.id.item_atme_zhuanfa);
			vh.pinglun = (TextView) convertView.findViewById(R.id.item_atme_pinglun);
			vh.zan = (TextView) convertView.findViewById(R.id.item_atme_zan);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		loader.displayImage(weiboinfo.getWeibo_user_img(), vh.icon, opt);
		if(weiboinfo.getWeibo_spic() == null){
			vh.pic.setVisibility(View.GONE);
		}else{
			vh.pic.setVisibility(View.VISIBLE);
			loader.displayImage(weiboinfo.getWeibo_spic(), vh.pic, opt);
		}
		vh.username.setText(weiboinfo.getWeibo_user_name());
		vh.time.setText(weiboinfo.getWeibo_time());
		vh.source.setText(weiboinfo.getWeibo_source());
		vh.text.setText(weiboinfo.getWeibo_text());
		vh.zhuanfa.setText(weiboinfo.getWeibo_zhuanfa()+"");
		vh.pinglun.setText(weiboinfo.getWeibo_pinglun()+"");
		vh.zan.setText(weiboinfo.getWeibo_zan()+"");
		return convertView;
	}

	static class ViewHolder{
		ImageView icon,pic;
		TextView username,time,source,text,zhuanfa,pinglun,zan;
	}
	
	public void loadNewData() {
		List<String> keys = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		keys.add("access_token");
		keys.add("max_id");
		values.add(user.getToken());
		values.add(""+datas.get(datas.size()-1).getWeibo_id());
		List<Weiboinfo> newDatas = Tools.getWeiboinfos(Tools.sendGet(Constant.ATME_URL, keys , values, null));
		newDatas.remove(0);
		datas.addAll(newDatas);
	}
	
}
