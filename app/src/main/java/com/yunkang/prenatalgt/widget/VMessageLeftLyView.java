
package com.yunkang.prenatalgt.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hlib.util.ScreenUtil;
import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.listener.MessageRecordingListener;


/**
 * @作者： [欢欢 email：HuanHuanFu@wistronits.com]<br>
 * @版本： [V1.0.0, 2014-07-15]<br>
 * @描述：聊天录音左边控件<br>
 */
public class VMessageLeftLyView extends RelativeLayout implements OnClickListener {
    public ImageView left_play;
    private TextView mlyName, mlySize, mRecodingTime;
    private RelativeLayout rly_left;
    private ProgressBar progressBar;
    //录音已读未读状态
    private ImageView recodingUnread;
    

    public VMessageLeftLyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater) context.getSystemService(infService);
        li.inflate(R.layout.message_ly_left, this);

        findViewsById();
        setListener();
    }

    private void findViewsById() {
        rly_left = findViewById(R.id.rly_left);
        left_play = findViewById(R.id.left_play);
        
        if (left_play != null) {
            if (animationDrawable == null) {
                animationDrawable = (AnimationDrawable) left_play.getBackground();
                animationDrawable.stop();
            }
        }
        
        mlyName = findViewById(R.id.mlyName);
        mlySize = findViewById(R.id.mlySize);
        mRecodingTime = findViewById(R.id.recoding_time);
        recodingUnread = findViewById(R.id.recodingUnread);
        progressBar = findViewById(R.id.progressBar);
    }

   
    /**
     * @method: showProgress 
     * @Description: 显示进度条状态
     * @param showView
     * @throws
    */
	public void showProgress(final boolean showView) {
		post(new Runnable() {
			@Override
			public void run() {
				// 需要在主线程进行
				progressBar.setVisibility(showView ? View.VISIBLE : View.GONE);
			}
		});
	}
	/**
     * @method: isProgressVisible 
     * @Description: 进度条显示状态    
     * @throws
    */
	public boolean isProgressVisible() {
        return progressBar.getVisibility() == View.VISIBLE;
	}
    
    /**
      * @method: setUnreadState 
      * @Description: 显示已读已读状态
      * @param state
      * @throws
     */
    public void setUnreadState(boolean state){
        if(recodingUnread != null){
            if(state){
                recodingUnread.setVisibility(View.VISIBLE);
            }else{
                recodingUnread.setVisibility(View.INVISIBLE);
            }
            recodingUnread.invalidate();
        }
    }
    
    public void setLeftRecodingWidth(int width) {
        int settingWidth = 90 + (width * 3);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ScreenUtil.dip2px(
                getContext(), settingWidth > 180 ? 180 : settingWidth),
                LayoutParams.WRAP_CONTENT);
        rly_left.setLayoutParams(params);
    }

    public void playAnmin() {
        if (left_play != null) {
//            if(animationDrawable == null) {
//                animationDrawable = (AnimationDrawable) left_play.getBackground();
//            }
            if(!animationDrawable.isRunning()) {
                animationDrawable.stop();
                
                animationDrawable.start();
            }
            
//            final AnimationDrawable animationDrawable = (AnimationDrawable) left_play
//                    .getBackground();
//            new Handler().post(new Runnable() {
//                public void run() {
//                    animationDrawable.start();
//                }
//            });
        }
    }

    private AnimationDrawable animationDrawable = null;
    
    public void stopAnmin() {
        if (left_play != null) {
//            if(animationDrawable == null) {
//                animationDrawable = (AnimationDrawable) left_play.getBackground();
//            }
            if(animationDrawable.isRunning()) {
                
//                App.sGlobalHandler.post(new Runnable() {
//                    public void run() {
//                        animationDrawable.stop();
//                        animationDrawable.selectDrawable(0);
//                    }
//                });
                
                animationDrawable.stop();
                animationDrawable.selectDrawable(0);
            }
            
            
//            new Handler().post(new Runnable() {
//                public void run() {
//                    animationDrawable.stop();
//                    animationDrawable.selectDrawable(0);
//                }
//            });
        }
    }

    public void setName(String name) {
        if (mlyName != null) {
            mlyName.setText(name);
        }
    }

    public void setRecodingTime(String time) {
        if (mRecodingTime != null) {
            mRecodingTime.setText(time);
        }
    }

    public void setSize(String size) {
        if (mlySize != null) {
            mlySize.setText(size);
        }
    }

    private void setListener() {
        left_play.setOnClickListener(this);
        rly_left.setOnClickListener(this);
        //rly_left.setOnLongClickListener(new );
    }

    public void setLongListener(OnLongClickListener li){
        rly_left.setOnLongClickListener(li);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rly_left:
                if (messageRecodingListener != null){
                    messageRecodingListener.onClick();
                }
                break;
            case R.id.left_play:
                if (messageRecodingListener != null){
                    messageRecodingListener.onClick();
                }
                break;
           

        }
    }

    // 设置播放状态
    public void setPlayState(int bgId) {
        left_play.setBackgroundResource(bgId);
    }

    private MessageRecordingListener messageRecodingListener;

    public void setOnButtonClickListener(MessageRecordingListener listener) {
        this.messageRecodingListener = listener;
    }
}
