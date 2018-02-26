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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunkang.prenatalgt.R;


/**
 * @作者： [欢欢 email：HuanHuanFu@wistronits.com]<br>
 * @版本： [V1.0.0, 2015-08-25]<br>
 * @描述：聊天左边文件控件<br>
 */
public class VMessageLeftFileView extends RelativeLayout {

	private TextView mFileTitle; // 文件左边标题
	private TextView mForward; // 文件原上传人名和日期
	private TextView mSize; // 文件大小
	private ProgressBar mSeekBar; // 文件进度
	private TextView mStatus; // 文件状态
	private ImageView fileLeftIcon; // 文件图标
	private RelativeLayout rRead_left;

	public VMessageLeftFileView(Context context, AttributeSet attrs) {
		super(context, attrs);
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		li = (LayoutInflater) context.getSystemService(infService);
		li.inflate(R.layout.message_file_left, this);

		findViewsById();
		setListener();
	}

	private void findViewsById() {
		mFileTitle = findViewById(R.id.mFileTitle);
		mSize = findViewById(R.id.mSize);
		mSeekBar = findViewById(R.id.seekBar);
		mStatus = findViewById(R.id.mStatus);
		fileLeftIcon = findViewById(R.id.fileLeftIcon);
		mForward = findViewById(R.id.forward);//转发文件 人和时间
		rRead_left = findViewById(R.id.rRead_left);
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
	 * 设置文件图片
	 */
	public void setFileImage(int resoucesId) {
		if (fileLeftIcon != null) {
			fileLeftIcon.setImageBitmap(BitmapFactory.decodeResource(getResources(), resoucesId));
			//fileLeftIcon.setBackgroundResource(resoucesId);
		}
	}
	
	/*
	 * 设置图片
	 */
	public void setFileImage(Bitmap bitMap){
		if(bitMap != null){
			fileLeftIcon.setImageBitmap(bitMap);
		}
	}
	
	/*
	 * 设置背景图片
	 */
	public void setFileBackground(int resid){
		fileLeftIcon.setBackgroundResource(resid); 
	}
	

	/*
	 * 设置文件状态
	 */
	public void setFileStatus(String status) {
		if (!TextUtils.isEmpty(status)) {
			mStatus.setText(status);
		}
	}

	/*
	 * 设置标题名称
	 */
	public void setTitleName(String name) {
		if (mFileTitle != null && !TextUtils.isEmpty(name)) {
			mFileTitle.setText(name);
		}
	}

	/*
	 * 设置文件大小
	 */
	public void setFileSize(String siee) {
		if (mSize != null && !TextUtils.isEmpty(siee)) {
			mSize.setText(siee);
		}
	}

	/*
	 * 设置进度条进度，请确保设置之前进度条已经显示
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

	/*
	 * 是否显示控件进度条
	 */
	public void showProgress(final boolean showView) {
		post(new Runnable() {
			@Override
			public void run() {
				// 需要在主线程进行
				mSeekBar.setVisibility(showView ? View.VISIBLE : View.GONE);
			}
		});
	}


	//变暗(三个-50，值越大则效果越深)
	public final float[] BT_SELECTED_DARK = new float[] { 1, 0, 0, 0, -50, 0, 1,
			0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };
	
	public final OnTouchListener VIEW_TOUCH_DARK = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				rRead_left.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_SELECTED_DARK));
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				cleaeFilter();
			} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
				cleaeFilter();
			}
			return false; // 如为false，执行ACTION_DOWN后不再往下执行
		}
	}; 
	   
    public void cleaeFilter(){
    	if(rRead_left != null){
    		rRead_left.getBackground().clearColorFilter();
    	}
    }
}
