package com.yunkang.prenatalgt.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.ClipboardManager;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.yunkang.prenatalgt.widget.dialog.VDialog;

public class MyLinkTextView extends android.support.v7.widget.AppCompatTextView {
	public MyLinkTextView(Context context) {
		super(context);
	}

	public MyLinkTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyLinkTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void send(Activity context, TextView tv) {
		try {
			CharSequence text = tv.getText();
			if (text instanceof Spannable) {
				int end = text.length();
				Spannable sp = (Spannable) tv.getText();
				URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
				SpannableStringBuilder style = new SpannableStringBuilder(text);
				for (URLSpan url : urls) {
					MyURLSpan myURLSpan = new MyURLSpan(url.getURL(), context);
					style.setSpan(myURLSpan, sp.getSpanStart(url),
							sp.getSpanEnd(url),
							Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				}
				tv.setText(style);
			}
			tv.setMovementMethod(LinkMovementClickMethod.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	private class MyURLSpan extends ClickableSpan {

		private String contentString;
		private Activity context;

		MyURLSpan(String url, Activity context) {
			contentString = url;
			this.context = context;
		}

		@Override
		public void onClick(View widget) {
			try {
                starIntent(context, widget ,contentString);
                widget.setBackgroundColor(Color.parseColor("#00000000"));
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
	}
	
	
	private void starIntent(Activity context, View view, String content) {
		try {
            String text = content.substring(0, content.indexOf(":"));
            if (text.equals("tel")) {// 拨打电话
            	showPhoneDialog(context,view,content);
            } else if (text.equals("mailto")) {// 发送邮件
            	getEmail(context, content);
            } else if (text.equals("http")) {// 打开网页
            	getWeb(context, content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/***
	 * @author WH1512032
	 * @param context
	 * @param view
	 * @param url
	 * @method showPhoneDialog
	 * @description 识别手机号，弹出操作框
	 */
	public static void showPhoneDialog(Activity context, View view, String content) {
		// 如果键盘打开，则仅关闭键盘 ZYY-2016.10.25
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0)){  
			//软键盘已弹出  
			return;
		}  
		String phone = content.substring(content.indexOf(":") + 1, content.length());

		VDialog.getDialogInstance().showCallPhotoPopu(context, view, phone);
	}

	/***
	 * @method getPhone
	 * @description 拨打电话
	 */
	public static void getPhone(Context context, String url) {
		Uri uri = Uri.parse(url);
		Intent it = new Intent(Intent.ACTION_DIAL, uri);
		it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(it);
	}

	/**
	 * 打开网页
	 */
	public static void getWeb(Context context, String url) {
		Uri uri = Uri.parse(url);
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			context.startActivity(it);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送短信
	 */
	public static void getSms(Context context, String url){
		Uri uri = Uri.parse("smsto:"+url);
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(it);
		
	}

	/**
	 * 打开Email
	 */
	public static void getEmail(Context context, String url) {
	    Log.e("ChenWei","url = "+url);
		Uri uri = Uri.parse(url);
	    Log.e("ChenWei","url = "+uri);
		Intent mailIntent = new Intent(Intent.ACTION_SENDTO, uri);
		mailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(mailIntent, "请选择邮件类应用"));
	}
	
	 public static class LinkMovementClickMethod extends LinkMovementMethod {

	        private long lastClickTime;
	        private static LinkMovementClickMethod sInstance;
	        private static final long CLICK_DELAY = 500l;

	        @Override
	        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
	            try {
                    int action = event.getAction();
 
                    if (action == MotionEvent.ACTION_UP ||
                            action == MotionEvent.ACTION_DOWN) {
                        int x = (int) event.getX();
                        int y = (int) event.getY();

                        x -= widget.getTotalPaddingLeft();
                        y -= widget.getTotalPaddingTop();

                        x += widget.getScrollX();
                        y += widget.getScrollY();

                        Layout layout = widget.getLayout();
                        int line = layout.getLineForVertical(y);
                        int off = layout.getOffsetForHorizontal(line, x);

                        ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

                        if (link.length != 0) {
                            if (action == MotionEvent.ACTION_UP) {
                                if(System.currentTimeMillis() - lastClickTime < CLICK_DELAY){
                                    link[0].onClick(widget);
                                }
                            } else if (action == MotionEvent.ACTION_DOWN) {
                                Selection.setSelection(buffer,
                                        buffer.getSpanStart(link[0]),
                                        buffer.getSpanEnd(link[0]));
                                lastClickTime = System.currentTimeMillis();
                            }

                            return true;
                        } else {
                            Selection.removeSelection(buffer);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return true;
                }
	            return super.onTouchEvent(widget, buffer, event);
	        }

	        public static LinkMovementClickMethod getInstance(){
	            if(null == sInstance){
	                sInstance = new LinkMovementClickMethod();
	            }
	            return sInstance;
	        }

	    }
	 
	 
	 /***
      * @method: copy 
      * @Description: 实现文本复制功能
      * @param content
      * @param context
      * @throws
     */
    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }
}
