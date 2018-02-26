
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
import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.constant.ConfigData;


/**
 * @作者： [欢欢 email：HuanHuanFu@wistronits.com]<br>
 * @版本： [V1.0.0, 2014-7-30]<br>
 * @描述： 图片工具<br>
 */
public class VMessageLeftStaticImage extends RelativeLayout {
	// 默认的图片背景图 left
    public static final String KEY_DEFAULT_BITMAP = "VMessageLeftStaticImage_default_bkg";

    public static final int TYPE_IMAGE = 0;
    public static final int TYPE_MAP = 1;
    public BubbleImageView mImageView;
    private RelativeLayout rPprogress;
    private TextView mProccess;
    private ImageView imgProccess;
    //图片背景
    public RelativeLayout imageBg;
    //转发时所需要使用字段
    public TextView forward;
    public RelativeLayout mRLeftImg;

    public VMessageLeftStaticImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) context
                .getSystemService(infService);
        li.inflate(R.layout.static_image_left, this);
        findViewsById();
    }

    public VMessageLeftStaticImage(Context context) {
        super(context);
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) context.getSystemService(infService);
        li.inflate(R.layout.static_image_left, this);
        findViewsById();
    }

    private void findViewsById() {
        mImageView = findViewById(R.id.static_map);
        rPprogress = findViewById(R.id.progress);
        mProccess = findViewById(R.id.mProccess);
        imgProccess = findViewById(R.id.imgProccess);
        imageBg = findViewById(R.id.imageBg);
        forward = findViewById(R.id.forward);
        mRLeftImg = findViewById(R.id.mRLeftImg);
        
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
     * 设置图片
     * @param path
     */
//    public void setPic_left(String path) {
//        Bitmap bit = getBitmapByWidth(path, 216, 1);
//        setScaleImage(bit);
//    }

    /**
     * 默认 216 2
     * @method: setPic_right
     * @Description: 将图片缓存到内存中，缓存管理自动维护内存，如果过多则自己删除
     * @param path
     * @throws
     */
//    public void setPic_left(String path, String fileUrl, boolean isFireMsg) {
//    	// setFordingSetting(true);
//
//        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(fileUrl)) {
//        	// 没加载到图片，使用默认图片
//        	setDefaultBkg(isFireMsg);
//            return;
//        }
//        Bitmap getBit = MemoryCache.getInstance().get(fileUrl);
//
//        if (getBit != null) {
//            setScaleImage(getBit, isFireMsg, true);
//            return;
//        } else {
//            Bitmap bit = ImageUtil.getBitmapByWidth(path, 200,200,1);
//            if(bit == null){
//                // add by miaojun, 默认图片放入缓存，且不是每次都需要  bugId: 248
//            	// 没加载到图片，使用默认图片
//            	setDefaultBkg(isFireMsg);
//            	return;
//            }
//            if (bit != null) {
//                //设置图片倒角
//                MemoryCache.getInstance().put(MemoryCache.BitmapType_ChatMsgImg,fileUrl, bit);
//            }
//            setScaleImage(bit,isFireMsg, true);
//        }
//    }

    // begin add by miaojun, 默认图片放入缓存，且不是每次都需要  bugId: 248
    // 使用默认背景图
//    private void setDefaultBkg(boolean isFireMsg) {
//    	// 没加载到图片，使用默认图片  ZYY 修改
//    	ImageLoadUtils.loadImage("", mImageView, R.mipmap.no_down_load_default_left);
////    	Bitmap bit = MemoryCache.getInstance().get(KEY_DEFAULT_BITMAP);
////
////    	if (bit == null) {
////    		bit = BitmapFactory.decodeResource(getResources(), R.drawable.no_down_load_default_left);
////    		// 缓存默认图片
////    		MemoryCache.getInstance().put(MemoryCache.BitmapType_ChatMsgImg, KEY_DEFAULT_BITMAP, bit);
////    	}
////    	// 设置为默认的图片背景
////    	  @Deprecated
////        setScaleImage(bit, isFireMsg, true);
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
			//WH1605064 修改图片下面或者右边出现拉伸的情况 同时修改了传值的地方
			//WH1512032 修改图片出现拉伸的情况  2016.8.4
//			if (isFireMsg) {
//				params.height = height-SystemUtil.dip2px(App.getContext(), 3);
//				params.width = width-SystemUtil.dip2px(App.getContext(), 11);
//			} else {
				params.height = height;
				params.width = width;
//			}
			
			
			mImageView.setLayoutParams(params);
			mImageView.setImageBitmap(bm);
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
                // 需要在主线程进行
                if (rPprogress.getVisibility() != View.VISIBLE) {
                    rPprogress.setVisibility(View.VISIBLE);
                }
                mProccess.setText(progress + "%");
                
                /*RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imgProccess.getLayoutParams();
                int imgHeight = mImageView.getLayoutParams().height;
                float total =  progress / 100.0f;
                float height =  imgHeight * (1- total);
                params.height = (int)height;
                params.width = mImageView.getLayoutParams().width;
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
