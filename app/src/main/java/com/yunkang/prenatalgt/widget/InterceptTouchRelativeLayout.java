package com.yunkang.prenatalgt.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class InterceptTouchRelativeLayout extends RelativeLayout {
	private boolean childClickable=true;

	public InterceptTouchRelativeLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public InterceptTouchRelativeLayout(Context context, AttributeSet attrs,
                                        int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public InterceptTouchRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// 返回true则拦截子控件所有点击事件，如果childclickable为true，则需返回false
		return !childClickable;
	}
/**
 * 
 * @param clickable ture 则子控件可以点击
 */
	public void setChildClickable(boolean clickable) {
		childClickable = clickable;
	}
}
