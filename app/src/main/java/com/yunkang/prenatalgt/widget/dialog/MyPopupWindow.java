package com.yunkang.prenatalgt.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

public class MyPopupWindow extends PopupWindow {
	// dialog 所依附的对话框
	private Activity mAttachActivity;

	public MyPopupWindow(Context context){
		
	}
	public MyPopupWindow(Activity activity, View layout, int width, int height,
                         boolean canBeFocus) {
		super(layout, width, height, canBeFocus);
		setAttachActivity(activity);
	}

	protected void setAttachActivity(Activity a) {
		mAttachActivity = a;
	}

	protected Activity getAttachActivity() {
		return mAttachActivity;
	}

	protected void closePopupWindow() {
		if (mAttachActivity != null) {
			View contentViwe = getContentView();
			if (contentViwe != null) {
				contentViwe.setBackgroundDrawable(null);
				contentViwe.destroyDrawingCache();
				contentViwe = null;
			}
			dismiss();
			mAttachActivity = null;
		}
	}

}
