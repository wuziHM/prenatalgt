package com.yunkang.prenatalgt.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrixColorFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.constant.ConfigData;


/**
 * @作者： [欢欢 email：HuanHuanFu@wistronits.com]<br>
 * @版本： [V1.0.0, 2015-08-25]<br>
 * @描述：聊天右边文件控件<br>
 */
public class VMessageRightFileView extends RelativeLayout {

	private ImageView fileRightIcon; // 图标
	private TextView mForward; // 文件原上传人名和日期
	private TextView mFileTitle; // 文件名称
	private TextView mSize; // 文件大小
	private ProgressBar mSeekBar;// 文件上传进度
	private TextView mStatus; // 文件状态
	private LinearLayout rightReceiverLayout; //接收文件效果
	private TextView mreceiverFileContext;// 接收文件内容
	public TextView rightFileUnRead;// 已读回执
	public ImageView rightFileSendFailure; // 发送失败图标
	private RelativeLayout rFileLayout; // 文件背景

	public VMessageRightFileView(Context context, AttributeSet attrs) {
		super(context, attrs);
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		li = (LayoutInflater) context.getSystemService(infService);
		li.inflate(R.layout.message_file_right, this);

		findViewsById();
		setListener();
	}   

	private void findViewsById() {
		fileRightIcon = findViewById(R.id.fileRightIcon);
		mFileTitle = findViewById(R.id.mFileTitle);
		mSize = findViewById(R.id.mSize);
		mSeekBar = findViewById(R.id.seekBar);
		mStatus = findViewById(R.id.mStatus);
		rightReceiverLayout = findViewById(R.id.rightReceiverLayout);
		mreceiverFileContext = findViewById(R.id.mreceiverFileContext);
		rightFileUnRead = findViewById(R.id.rightFileUnRead);
		rightFileSendFailure = findViewById(R.id.rightFileSendFailure);
		mForward = findViewById(R.id.forward);//转发文件 人和时间
		rFileLayout = findViewById(R.id.rFileLayout);
	}

	private void setListener() {
		this.setOnTouchListener(VIEW_TOUCH_DARK);
	}
	
	 /*
     * 转发时需要设置的字段
     */
    public void setForwardText(String content) {
        if (mForward != null && !TextUtils.isEmpty(content)) {
        	mForward.setText(content);
        	mForward.setVisibility(View.VISIBLE);
        }
    }
    
    /*
     * 显示还是隐藏该空间
     */
    public void setForwardHidden(boolean showOrHidden){
        if(mForward!=null){
            if(showOrHidden){
            	mForward.setVisibility(View.VISIBLE);
            }else{
            	mForward.setVisibility(View.GONE);
            }
        }
        }
	
	/*
	 * 设置发送失败图标是否显示
	 */
	public void setSendFailureIcon(boolean status){
		if(status){
			rightFileSendFailure.setVisibility(View.VISIBLE);
		}else{
			rightFileSendFailure.setVisibility(View.GONE);
		}
	}
	
	/*
	 * 设置文件回执
	 */
	public void setFileReceipt(String number){
		if(!TextUtils.isEmpty(number)){
			rightFileUnRead.setText(number);
		}
	}
	
	/*
	 * 设置接收效果隐藏还是显示
	 */
	public void setReceiverContextHiddenOrShow(boolean status){
		if(rightReceiverLayout != null){
			rightReceiverLayout.setVisibility(status ? View.VISIBLE : View.GONE);
		}
	}

	/*
	 * 设置对方接收文件效果
	 */
	public void setFileReceiverContext(String context){
		if(!TextUtils.isEmpty(context)){
			mreceiverFileContext.setText(context);
		}
	}
	
	/*
	 * 状态设置
	 */
	public void setFileStatus(String status){
		if(!TextUtils.isEmpty(status)){
			mStatus.setText(status);
		}
	}
 
	
	/*
	 * 设置文件图片
	 */
	public void setFileImage(int resoucesId) {
		if (fileRightIcon != null) {
			fileRightIcon.setImageBitmap(BitmapFactory.decodeResource(getResources(), resoucesId));
		}
	}
	
	/*
	 * 设置图片
	 */
	public void setFileImage(Bitmap bitMap){
		if(bitMap != null){
			fileRightIcon.setImageBitmap(bitMap);
		}
	}
	
	/*
	 * 设置图片
	 */
	public void setFileBackground(int resid){
		fileRightIcon.setBackgroundResource(resid);
	}

	/*
	 *设置标题名称  
	 */
	public void setTitleName(String fileName) {
		if (mFileTitle != null && !TextUtils.isEmpty(fileName)) {
			mFileTitle.setText(fileName);
		}
	}

	/*
	 * 设置文件大小
	 */
	public void setFileSize(String fileSize) {
		if (mSize != null && !TextUtils.isEmpty(fileSize)) {
			mSize.setText(fileSize);
		}
	}

	/**
	 * 设置进度条进度，请确保设置之前进度条已经显示
	 * @see showProgress()
	 * @param fileProgress
	 */
	public void setFileProgress(final int fileProgress) {
		post(new Runnable() {
			@Override
			public void run() {
				// 需要在主线程进行
				if (mSeekBar.getVisibility() != View.VISIBLE) {
					mSeekBar.setVisibility(View.VISIBLE);
				}
				mSeekBar.setProgress(fileProgress);
			}
		});
	}

	/**
	 * 是否显示进度条
	 */
	public void showProgress(final boolean showView) {
//	    mSeekBar.setVisibility(showView ? View.VISIBLE : View.GONE);
		post(new Runnable() {
			@Override
			public void run() {
				// 需要在主线程进行
				mSeekBar.setVisibility(showView ? View.VISIBLE : View.GONE);
			}
		});
	}
	
	
	public final OnTouchListener VIEW_TOUCH_DARK = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				rFileLayout.getBackground().setColorFilter(
						new ColorMatrixColorFilter(ConfigData.BT_SELECTED_DARK));
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				cleaeFilter();
			} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
				cleaeFilter();
			}
			return false; // 如为false，执行ACTION_DOWN后不再往下执行
		}
	}; 
	   
    public void cleaeFilter(){
    	if(rFileLayout != null){
    		rFileLayout.getBackground().clearColorFilter();
    	}
    }
}
