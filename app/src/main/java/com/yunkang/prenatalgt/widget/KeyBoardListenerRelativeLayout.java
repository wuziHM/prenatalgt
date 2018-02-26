
package com.yunkang.prenatalgt.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @作者： [付欢欢 email：HuanHuanFu@wistronits.com]<br>
 * @版本： [V1.0.0, 2015-8-05]<br>
 * @描述：可监听输入法隐藏显示<br>
 */
public class KeyBoardListenerRelativeLayout extends RelativeLayout {
	
	
	private int oldBottom =0;
	private int keyBoardState = -1 ; // 键盘状态
	
	public static class KeyBoardState {
		public static int SHOW_STATE =1;
		public static int HIDE_STATE =2;
	}

    public KeyBoardListenerRelativeLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyBoardListenerRelativeLayout(Context context) {
        super(context);
    }

    private OnSoftKeyboardListener onSoftKeyboardListener;

    public final void setOnSoftKeyboardListener(final OnSoftKeyboardListener listener) {
        this.onSoftKeyboardListener = listener;
    }
    
   public boolean isKeyBoardStateShow(){
       return keyBoardState == KeyBoardState.SHOW_STATE;
   }
    
    private void notifyUIKeyboardStateChange(boolean isHide){
    	if(onSoftKeyboardListener != null){
    		if(isHide){
    			if(keyBoardState == KeyBoardState.HIDE_STATE) {
    				return ;
    			}
    			onSoftKeyboardListener.onKeyBoardHidden();
    			keyBoardState = KeyBoardState.HIDE_STATE;
        	}else{
        		
        		if(keyBoardState == KeyBoardState.SHOW_STATE) {
    				return ;
    			}
        		onSoftKeyboardListener.onKeyBoardShown();
        		keyBoardState = KeyBoardState.SHOW_STATE;
        	}
    	}
    }
    
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,int newbottom) {
		// 键盘收起 或者 展开 引起布局变化 由此来判断键盘的状态
		if(changed){
			// 键盘展开
			if(newbottom -oldBottom < 0){
				notifyUIKeyboardStateChange(false);
			}else if(newbottom -oldBottom > 0) { // 键盘收起
				notifyUIKeyboardStateChange(true);
			}
				
		   oldBottom = newbottom;
		}
		super.onLayout(changed, left, top, right, newbottom);
	}

    // Simplest possible listener :)
    public interface OnSoftKeyboardListener {
        void onKeyBoardShown();

        void onKeyBoardHidden();
    }
}
