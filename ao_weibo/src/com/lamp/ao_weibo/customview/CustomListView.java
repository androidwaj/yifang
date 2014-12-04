package com.lamp.ao_weibo.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

import com.lamp.ao_weibo.R;

public class CustomListView extends ListView {

	public CustomListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CustomListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int x=(int) ev.getX();
			int y=(int) ev.getY();
			int pos=pointToPosition(x, y);
			if(pos==0)
			{
				if(pos==getAdapter().getCount()-1)
				{
					setSelector(R.drawable.more_lv_one);
				}else{
					setSelector(R.drawable.more_lv_firstitem);
				}
			}else if(pos==getAdapter().getCount()-1){
				setSelector(R.drawable.more_lv_lastitem);
			}else{
				setSelector(R.drawable.more_lv_middleitem);
			}       
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}
	

}
