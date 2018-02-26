
package com.yunkang.prenatalgt.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorMatrixColorFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hlib.util.ScreenUtil;
import com.yunkang.prenatalgt.MApplication;
import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.constant.ConfigData;


/**
 * @作者： [欢欢 email：HuanHuanFu@wistronits.com]<br>
 * @版本： [V1.0.0, 2014-7-30]<br>
 * @描述： 图片工具<br>
 */
public class VMessageRightStaticImage extends RelativeLayout {
	// 默认的图片背景图 right
    public static final String KEY_DEFAULT_BITMAP = "VMessageRightStaticImage_default_bkg";

    public static final int TYPE_IMAGE = 0;
    public static final int TYPE_MAP = 1;
    public BubbleImageView mImageView;
    private ImageView fireImgView;
    private RelativeLayout rPprogress;
    private TextView mProccess;
    private ImageView imgProccess;
    public ImageView send_failure;
    //图片背景
    public RelativeLayout imageBg;
    //转发时所需要使用字段
    public TextView forward;

    public VMessageRightStaticImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) context
                .getSystemService(infService);
        li.inflate(R.layout.static_image_right, this);
        findViewsById();
    }

    public VMessageRightStaticImage(Context context) {
        super(context);
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) context.getSystemService(infService);
        li.inflate(R.layout.static_image_right, this);
        findViewsById();
    }

    private void findViewsById() {
        mImageView = findViewById(R.id.static_map);
        fireImgView = findViewById(R.id.fire_img);
        rPprogress = findViewById(R.id.progress);
        mProccess = findViewById(R.id.mProccess);
        imgProccess = findViewById(R.id.imgProccess);
        send_failure = findViewById(R.id.send_failure);
        imageBg = findViewById(R.id.imageBg);
        forward = findViewById(R.id.forward);
        
        this.setOnTouchListener(VIEW_TOUCH_DARK);
    }
    
    public void setFordingSetting(boolean isSetting){
    	if(isSetting){
    		mImageView.setmArrowHeight(0);
        	mImageView.setmArrowWidth(0);
        	mImageView.setmAngle(0);
    	}else{
    		mImageView.setmArrowHeight(15);
        	mImageView.setmArrowWidth(15);
        	mImageView.setmAngle(10);
    	}
    }
    
    /*
     * 转发时需要设置的字段
     */
    public void setForwardText(String content) {
        if (forward != null && !TextUtils.isEmpty(content)) {
            forward.setText(content);
            forward.setVisibility(View.VISIBLE);
        }
    }
    
    /*
     * 显示还是隐藏该空间
     */
    public void setForwardHidden(boolean showOrHidden){
        if(forward!=null){
            if(showOrHidden){
                forward.setVisibility(View.VISIBLE);
            }else{
                forward.setVisibility(View.GONE);
            }
        }
    }

    /**
      * @method: setPicSendState 
      * @Description: 设置发送图片隐藏显示
      * @param state
      * @throws
     */
    public void setPicSendState(boolean state){
        if(send_failure == null){
            return;
        }
        if(state){
            send_failure.setVisibility(View.VISIBLE);
        }else{
            send_failure.setVisibility(View.GONE);
        }
    }
    
    public void setProShow() {
        if (rPprogress != null) {
            rPprogress.setVisibility(View.VISIBLE);
        }
    }

    public void setProHidden() {
        if (rPprogress != null) {
            rPprogress.setVisibility(View.GONE);
        }
    }

    /**
     * 默认 216 2
     * @method: setPic_right
     * @Description: 将图片缓存到内存中，缓存管理自动维护内存，如果过多则自己删除
     * @param path
     * @param msgId
     * @throws
     */
//    public void setPic_right(String path, String fileUrl, boolean isFireMsg) {
//        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(fileUrl)) {
//        	// 没加载到图片，使用默认图片
//        	setDefaultBkg(isFireMsg);
//            return;
//        }
//        Bitmap getBit = MemoryCache.getInstance().get(fileUrl);
//
//        if (getBit != null) {
//            setScaleImage(getBit, isFireMsg, false);
//            return;
//        } else {
//            Bitmap bit = ImageUtil.getBitmapByWidth(path, 200,200,1);
//            if(bit == null){
//            	// add by miaojun, 默认图片放入缓存，且不是每次都需要  bugId: 248
//            	// 没加载到图片，使用默认图片
//                setDefaultBkg(isFireMsg);
//            	return;
//            } else {
//                //设置图片倒角
//            	//bit = ImageUtil.getRoundedCornerBitmap(bit, 10);
//            	//bit = ImageUtil.clipit(bit, 0);
//                MemoryCache.getInstance().put(MemoryCache.BitmapType_ChatMsgImg,fileUrl, bit);
//            }
//            setScaleImage(bit,isFireMsg, false);
//        }
//    }

    // begin add by miaojun, 默认图片放入缓存，且不是每次都需要  bugId: 248
    // 使用默认背景图
//    private void setDefaultBkg(boolean isFireMsg) {
//    	// 没加载到图片，使用默认图片  ZYY 修改
//    	ImageLoadUtils.loadImage("", mImageView, R.drawable.no_down_load_default_left);
////    	Bitmap bit = MemoryCache.getInstance().get(KEY_DEFAULT_BITMAP);
////    	if (bit == null) {
////    		bit = BitmapFactory.decodeResource(getResources(), R.drawable.no_down_load_default_right);
////    		// 缓存默认图片
////    		MemoryCache.getInstance().put(MemoryCache.BitmapType_ChatMsgImg, KEY_DEFAULT_BITMAP, bit);
////    	}
////    	// 设置为默认的图片背景
////        setScaleImage(bit, isFireMsg, false);
//    }
    // end add by miaojun, 默认图片放入缓存，且不是每次都需要  bugId: 248

    /**
     * 设置图片，同时会根据图片的大小设置ImageView控件的大小
     * @param bm
     * isBurn 是否已焚毁未焚毁才有火的图标
     */
    public void setScaleImage(Bitmap bm, boolean isFireMsg, boolean isLeftMsg) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mImageView
                .getLayoutParams();
        if (bm != null) {
			int width = bm.getWidth();
			int height = bm.getHeight();

			params.height = height;
			params.width = width;

			// 添加阅后即焚图标
            if (isFireMsg) {
                // 阅后即焚消息
                RelativeLayout.LayoutParams fireImgParams = (RelativeLayout.LayoutParams) fireImgView
                        .getLayoutParams();
                if (isLeftMsg) {
                    // 左边消息

                } else {
                    // 右边消息
                    fireImgView.setBackgroundResource(R.mipmap.fire_right_bg);
                    fireImgParams.bottomMargin = ScreenUtil.dip2px(MApplication.getContext(), 1);
                    fireImgParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.static_map);
                }
                // 设置布局
                fireImgView.setLayoutParams(fireImgParams);
                fireImgView.setVisibility(VISIBLE);
            }

            mImageView.setLayoutParams(params);
            mImageView.setImageBitmap(bm);
        }
    }

    /**
      * 函数名     ：setFireImgHidden
      * 功能描述：设置火的图标隐藏
      * 输入参数：
      * 返回值     ：void
      * 异常        ：无
      * 创建人     ：WH1406011
      * 日期        ：2015年8月20日
     */
    public void setFireImgHidden(){
        if(fireImgView != null){
            fireImgView.setVisibility(GONE);
        }
    }
    
    /**
     * 获取图片需要的宽度
     * @return
     */
    public static float getImageViewWidth(Context context) {
        return ScreenUtil.dip2px(context, 200); // 108
    }

    /**
     * 获取图片需要的高度
     * @return
     */
    public static float getImageViewHeight(Context context) {
        // 高度与宽度为黄金分割比
        return getImageViewWidth(context) * 0.618f;
    }


    /**
     * 设置进度条进度，请确保设置之前进度条已经显示
     * @param progress
     */
    public void setProgress(final int progress) {
        post(new Runnable() {
            @Override
            public void run() {
            	if(mProccess != null){
            		 // 需要在主线程进行
                    if (rPprogress.getVisibility() != View.VISIBLE) {
                        rPprogress.setVisibility(View.VISIBLE);
                    }
                    mProccess.setText(progress + "%");
            	}
               
                //从上到下的效果暂时屏蔽用转圈的方式代替
               /* RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imgProccess.getLayoutParams();
                int imgHeight = mImageView.getLayoutParams().height;
                float total =  progress / 100.0f;
                float height =  imgHeight * (1- total);
                params.height = (int)height;
                params.width = mImageView.getLayoutParams().width;
                params.rightMargin = 5;
                params.leftMargin = 10;
                imgProccess.setLayoutParams(params);
                imgProccess.setBackgroundResource(R.color.btm);*/
            }
        });
    }

    /**
     * 显示进度条
     */
    public void showProgress() {
        showProgress(true);
    }

    public void hideProgress() {
        showProgress(false);
    }

    /**
     * 是否显示进度条
     */
    public void showProgress(final boolean show) {
        post(new Runnable() {
            @Override
            public void run() {
                // 需要在主线程进行
                rPprogress.setVisibility(show ? View.VISIBLE : View.GONE);
                imgProccess.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
    
    
    public final OnTouchListener VIEW_TOUCH_DARK = new OnTouchListener() {
         
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
            	//判断BubbleDrawable 非空  WH1605070 陈晓东
            	if (mImageView.bubbleDrawable != null) {
            		mImageView.bubbleDrawable.setColorFilter(new ColorMatrixColorFilter(ConfigData.BT_SELECTED_DARK));
            		mImageView.invalidate();
				}
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
            	mImageView.cleaeFilter();
            } else if(event.getAction() == MotionEvent.ACTION_CANCEL){
            	mImageView.cleaeFilter();
            }
            return false;  //如为false，执行ACTION_DOWN后不再往下执行
        }  
    }; 
  
}
