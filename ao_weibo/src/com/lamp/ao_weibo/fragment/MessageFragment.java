package com.lamp.ao_weibo.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.lamp.ao_weibo.AtMeActivity;
import com.lamp.ao_weibo.HotnewsActivity;
import com.lamp.ao_weibo.MycommentsActivity;
import com.lamp.ao_weibo.R;
import com.lamp.ao_weibo.TocommentsActivity;

public class MessageFragment extends Fragment implements OnItemClickListener {
	private Context context;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	private ListView lv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_message, null);
		lv = (ListView) v.findViewById(R.id.fragment_message_lv);
		initData();
		return v;
	}
	
	private void initData() {
		List<HashMap<String,Object>> datas = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> data = new HashMap<String,Object>();
		data.put("icon", R.drawable.messagescenter_at);
		data.put("title", "@我的");
		datas.add(data);
		data = new HashMap<String,Object>();
		data.put("icon", R.drawable.messagescenter_comments);
		data.put("title", "我发出的评论");
		datas.add(data);
		data = new HashMap<String,Object>();
		data.put("icon", R.drawable.messagescenter_good);
		data.put("title", "我收到的评论");
		datas.add(data);
		data = new HashMap<String,Object>();
		data.put("icon", R.drawable.topic_image_default);
		data.put("title", "热门微博");
		datas.add(data);
		SimpleAdapter adapter = new SimpleAdapter(context, datas ,R.layout.item_messagefragment_lv, 
				new String[]{"icon","title"}, new int[]{R.id.item_messagefragment_icon,R.id.item_messagefragment_title});
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Class cla = null;
		switch (position) {
		case 0:
			cla = AtMeActivity.class;
			break;
		case 1:
			cla = MycommentsActivity.class;
			break;
		case 2:
			cla = TocommentsActivity.class;
			break;
		case 3:
			cla = HotnewsActivity.class;
			break;
		}
		startActivity(new Intent(context,cla));
	}
	
}
