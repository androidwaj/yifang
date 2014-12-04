package com.lamp.ao_weibo.fragment;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.lamp.ao_weibo.R;
import com.lamp.ao_weibo.WeiboMainActivity;
import com.lamp.ao_weibo.adapter.HomeXlvAdapter;
import com.lamp.ao_weibo.customview.XListView;
import com.lamp.ao_weibo.customview.XListView.IXListViewListener;
import com.lamp.ao_weibo.model.User;
import com.lamp.ao_weibo.model.Weiboinfo;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

public class HomeFragment extends Fragment implements OnItemClickListener {
	private Context context;
	
	private TextView title;
	private XListView xlv;
	private DbUtils db;
	private HomeXlvAdapter adapter;
	private ProgressDialog dialog;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
		db = DbUtils.create(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dialog = new ProgressDialog(context);
		dialog.show();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_home, container,false);
		title = (TextView) v.findViewById(R.id.fragment_home_title);
		xlv = (XListView) v.findViewById(R.id.fragment_home_xlv);
		User user = null;
		try {
			user = db.findFirst(User.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		title.setText(user.getUsername());
		new Thread(){
			public void run() {
				adapter = new HomeXlvAdapter(context,0);
				handler.sendEmptyMessage(0);
			}
		}.start();
		if(context.getSharedPreferences("aoweibo", context.MODE_PRIVATE).getBoolean("loadenable", true))
			xlv.setPullLoadEnable(true);
		else 
			xlv.setPullLoadEnable(false);
		if(context.getSharedPreferences("aoweibo", context.MODE_PRIVATE).getBoolean("fastenable", true))
			 xlv.setFastScrollEnabled(true);
		else 
			xlv.setFastScrollEnabled(false);
		xlv.setXListViewListener(new IXListViewListener() {
			@Override
			public void onRefresh() {
				new Thread(){
					public void run() {
						adapter = new HomeXlvAdapter(context,1);
						handler.sendEmptyMessage(1);
					}
				}.start();
			}
			
			@Override
			public void onLoadMore() {
				new Thread(){
					public void run() {
						adapter.loadNewData();
						handler.sendEmptyMessage(2);
					}
				}.start();
			}
		});
		xlv.setOnItemClickListener(this);
		return v;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}  

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				xlv.setAdapter(adapter);
				dialog.dismiss();
				break;
			case 1:
				xlv.setAdapter(adapter);
				xlv.stopRefresh();
				break;
			case 2:
				adapter.notifyDataSetChanged();
				xlv.stopLoadMore();
				break;
			}
		}
	};
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		DbUtils db = DbUtils.create(context);
		List<Weiboinfo> datas = adapter.getWeibos();
		try {
			if(db.findAll(Weiboinfo.class) != null){
				db.deleteAll(Weiboinfo.class);
			}
			for (int i = 0; i < datas.size(); i++) {
				db.saveBindingId(datas.get(i));
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(context,WeiboMainActivity.class);
		Weiboinfo weiboinfo = adapter.getWeibos().get(position-1);
		intent.putExtra("Weiboinfo",weiboinfo);
		startActivity(intent);
	}
	
}
