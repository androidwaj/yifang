package com.lamp.ao_weibo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lamp.ao_weibo.fragment.HomeFragment;
import com.lamp.ao_weibo.fragment.MeFragment;
import com.lamp.ao_weibo.fragment.MessageFragment;
import com.lamp.ao_weibo.fragment.MoreFragment;

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> datas;
	
	public HomeFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		getData();
	}

	private void getData() {
		datas = new ArrayList<Fragment>();
		HomeFragment homeFragment = new HomeFragment();
		MessageFragment messageFragment = new MessageFragment();
		MeFragment meFragment = new MeFragment();
		MoreFragment moreFragment = new MoreFragment();
		datas.add(homeFragment);
		datas.add(messageFragment);
		datas.add(meFragment);
		datas.add(moreFragment);
	}

	@Override
	public Fragment getItem(int arg0) {
		return datas.get(arg0);
	}

	@Override
	public int getCount() {
		return datas.size();
	}

}
