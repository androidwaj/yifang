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

import com.lamp.ao_weibo.R;
import com.lamp.ao_weibo.constants.Constant;
import com.lamp.ao_weibo.model.MyPhoto;
import com.lamp.ao_weibo.model.User;
import com.lamp.ao_weibo.utils.ImageLoaderutils;
import com.lamp.ao_weibo.utils.Tools;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyPhotoAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ImageLoader loader;
	private DisplayImageOptions opt;
	private List<MyPhoto> datas;
	private DbUtils  db;
	private User user;
	public MyPhotoAdapter(Context context) {
		this.context=context;
		inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		loader=ImageLoaderutils.getInstance(context);
		opt=ImageLoaderutils.getOpt();
		db=DbUtils.create(context);
		try {
			user=db.findAll(User.class).get(0);
		} catch (DbException e) {
			e.printStackTrace();
		}
		getDatas();
	}

	private void getDatas() {
		List<String> keys=new ArrayList<String>();
		keys.add("access_token");
		keys.add("uid");
		List<String> values=new ArrayList<String>();
		values.add(user.getToken());
		values.add(user.getUser_id());
		String str=Tools.sendGet(Constant.MYPHOTO_URL, keys, values, null);
		datas=Tools.parseJsonMyPhoto(str);
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
		if(convertView==null)
		{
			convertView=inflater.inflate(R.layout.item_myphoto_gridview, null);
		}
		ImageView iv=(ImageView) convertView.findViewById(R.id.item_myphoto_iv);
	    loader.displayImage(datas.get(position).getBmiddlepic(), iv, opt);
		return convertView;
	}

	public List<MyPhoto> getData(){
		return datas;
	}
}
