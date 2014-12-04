package com.lamp.ao_weibo.adapter;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lamp.ao_weibo.PingLunActivity;
import com.lamp.ao_weibo.R;
import com.lamp.ao_weibo.constants.Constant;
import com.lamp.ao_weibo.model.User;
import com.lamp.ao_weibo.model.Weiboinfo;
import com.lamp.ao_weibo.utils.ImageLoaderutils;
import com.lamp.ao_weibo.utils.Tools;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeXlvAdapter extends BaseAdapter{
	private Context context;
	private LayoutInflater inflater;
	private List<Weiboinfo> datas;
	private ImageLoader loader;
	private DisplayImageOptions opt;
	private User user;
	private DbUtils db;
	
	public HomeXlvAdapter(Context context,int flag) {
		super();
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		loader=ImageLoaderutils.getInstance(context);
		opt=ImageLoaderutils.getOpt();
		db = DbUtils.create(context);
		try {
			user = db.findFirst(User.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		try {
			if(flag == 1){
				getData();
			}else if(flag == 0){
				List<Weiboinfo> weibos = db.findAll(Selector.from(Weiboinfo.class).where("weibo_id", "!=", "0"));
				if(weibos == null || weibos.size() == 0){
					getData();
				}else{
					datas = weibos;
				}
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	private void getData() {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(Constant.HOME_TIMELINE+"?access_token="+user.getToken()+"&count=10");
		try {
			HttpResponse response = client.execute(get);
			datas = Tools.getWeiboinfos(EntityUtils.toString(response.getEntity(),"UTF-8"));
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
			convertView = inflater.inflate(R.layout.item_homefragment_xlv, null);
			vh.icon = (ImageView) convertView.findViewById(R.id.item_homefragment_icon);
			vh.username = (TextView) convertView.findViewById(R.id.item_homefragment_username);
			vh.time = (TextView) convertView.findViewById(R.id.item_homefragment_time);
			vh.source = (TextView) convertView.findViewById(R.id.item_homefragment_source);
			vh.text = (TextView) convertView.findViewById(R.id.item_homefragment_text);
			vh.pic = (ImageView) convertView.findViewById(R.id.item_homefragment_pic);
			vh.zhuanfa = (TextView) convertView.findViewById(R.id.item_homefragment_zhuanfa);
			vh.pinglun = (TextView) convertView.findViewById(R.id.item_homefragment_pinglun);
			vh.zan = (TextView) convertView.findViewById(R.id.item_homefragment_zan);
			vh.home_pinglunlayout = (LinearLayout) convertView.findViewById(R.id.home_pinglunlayout);
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
		vh.home_pinglunlayout.setOnClickListener(new PinglunListener(weiboinfo.getWeibo_id()));
		return convertView;
	}

	class PinglunListener implements View.OnClickListener{
		private long id;
		
		public PinglunListener(long id) {
			super();
			this.id = id;
		}
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context,PingLunActivity.class);
			intent.putExtra("id", id);
			intent.putExtra("access_token", user.getToken());
			context.startActivity(intent);
		}
		
	}
	
	static class ViewHolder{
		ImageView icon,pic;
		LinearLayout home_pinglunlayout;
		TextView username,time,source,text,zhuanfa,pinglun,zan;
	}

	public void loadNewData() {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(Constant.HOME_TIMELINE+"?access_token="+user.getToken()+"&max_id="+datas.get(datas.size()-1).getWeibo_id()+"&count=6");
		try {
			HttpResponse response = client.execute(get);
			List<Weiboinfo> newDatas = Tools.getWeiboinfos(EntityUtils.toString(response.getEntity(),"UTF-8"));
			newDatas.remove(0);
			datas.addAll(newDatas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Weiboinfo> getWeibos() {
		return datas;
	}
}
